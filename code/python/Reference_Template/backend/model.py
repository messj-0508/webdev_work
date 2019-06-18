import os
import just
import numpy as np
from keras.layers import Activation, Dense, LSTM
from keras.models import Sequential, load_model
from keras.optimizers import RMSprop


# LSTM基础模块
class LSTMBase(object):

    # 初始化
    def __init__(self, model_name, encoder_decoder=None, hidden_units=128, base_path="models/"):
        self.model_name = model_name                                         # 模型名称
        self.h5_path = base_path + model_name + ".h5"                        # 模型h5路径
        self.pkl_path = base_path + model_name + ".pkl"                      # 数据pkl路径
        self.model = None                                                    # 模型
        self.hidden_units = hidden_units                                     # 隐含单元
        if encoder_decoder is None:                                          # 判断有无编译码器
            self.encoder_decoder = just.read(self.pkl_path)
        else:
            self.encoder_decoder = encoder_decoder

    # 辅助函数从概率数组中采样索引
    def sample(self, preds, temperature=1.0):
        preds = np.asarray(preds).astype('float64')                          # 转换格式为ndarray
        preds = np.log(preds) / temperature                                  # 公式为P = EXP(LOG(p)/diversity)
        exp_preds = np.exp(preds)                                            
        preds = exp_preds / np.sum(exp_preds)                                # 标准化
        probas = np.random.multinomial(1, preds, 1)                          # 多样式分布采样
        return np.argmax(probas)                                             # 根据采样结构确定最终输出对应的索引

    # 模型搭建
    def build_model(self):
        # 判断是否断点续训
        if os.path.isfile(self.h5_path):
            model = self.load()
        else:
            print('Building model...')
            # 结构参数读取
            num_unique_q_tokens = len(self.encoder_decoder.ex)
            num_unique_a_tokens = len(self.encoder_decoder.ey)
            input_s = (None, num_unique_q_tokens)
            # 模型结构:输入->LSTM->Dense->输出
            model = Sequential()
            model.add(LSTM(self.hidden_units, input_shape=input_s))
            # 输出为（m*n）的矩阵，m为样例数，n为词典中词汇数量
            model.add(Dense(num_unique_a_tokens))
            model.add(Activation('softmax'))
            # 优化方式：RMSprop
            optimizer = RMSprop(lr=0.01)
            # 损失函数：交叉熵损失函数
            model.compile(loss='categorical_crossentropy', optimizer=optimizer)
        return model

    # 训练函数
    def train(self, test_cases=None, iterations=20, batch_size=256, num_epochs=3, **kwargs):
        # 判断模型是否存在
        if self.model is None:
            self.model = self.build_model()
        # 判断数据集是否读取
        if not hasattr(self.encoder_decoder, "X"):
            X, y = self.encoder_decoder.get_xy()
            self.encoder_decoder.X, self.encoder_decoder.y = X, y
        # 开始训练
        for iteration in range(iterations):
            print()
            print('-' * 50)
            print('Iteration', iteration)
            self.model.fit(self.encoder_decoder.X, self.encoder_decoder.y,
                           batch_size=batch_size, nb_epoch=num_epochs,
                           **kwargs)
            self._show_test_cases(test_cases)

    # 预测函数
    def predict(self, text, diversity, max_prediction_steps, break_at_token=None):
        # 如无模型，则建模
        if self.model is None:
            self.model = self.build_model()
        outputs = []
        for _ in range(max_prediction_steps):
            # 对输入编码
            X = self.encoder_decoder.encode_question(text)
            # 预测
            preds = self.model.predict(X, verbose=0)[0]
            # 通过采样确定最终输出对应的索引码
            answer_token = self.sample(preds, diversity)
            # 将输出解码
            new_text_token = self.encoder_decoder.decode_y(answer_token)
            # 将输出存放起来，实际上每一次都是一个词汇
            outputs.append(new_text_token)
            # 将预测到的值累加到输入中，再次预测
            text += new_text_token
            # 如果预测到中断符，则停止预测。这里使用的是'\n'
            if break_at_token is not None and break_at_token == new_text_token:
                break
        # 将每个词汇练成整个句子
        return self.encoder_decoder.untokenize(outputs)

    # 模型保存
    def save(self):
        if hasattr(self.encoder_decoder, "X"):
            del self.encoder_decoder.X
        if hasattr(self.encoder_decoder, "y"):
            del self.encoder_decoder.y
        just.write(self.encoder_decoder, self.pkl_path)
        self.model.save(self.h5_path)

    # 模型导入
    def load(self):
        return load_model(self.h5_path)

    # 测试用例
    def _show_test_cases(self, test_cases):
        if test_cases is None:
            return
        for test_case in test_cases:
            print('----- Generating with seed: \n\n', test_case)
            print("\n\n--PREDICTION--\n\n")
            for diversity in [0.2, 0.5, 1]:
                print("--------- diversity {} ------- ".format(diversity))
                print(self.predict(test_case, diversity, self.encoder_decoder.maxlen))


import just

from encoder_decoder import TextEncoderDecoder, text_tokenize
from model import LSTMBase

TRAINING_TEST_CASES = ["from keras.layers import"]

# 获取数据集列表
def get_data():
    return list(just.multi_read("data/*.py").values())

# 训练函数
def train(ted, model_name):
    # 创建基础模型
    lb = LSTMBase(model_name, ted)
    # 开始训练
    try:
        lb.train(test_cases=TRAINING_TEST_CASES)
    except KeyboardInterrupt:
        pass
    # 训练结束保存
    print("saving")
    lb.save()

# 字符级训练
def train_char(model_name):
    # 获取数据集列表
    data = get_data()
    # list makes a str "str" into a list ["s","t","r"]
    ted = TextEncoderDecoder(data, tokenize=list, untokenize="".join, padding=" ",
                             min_count=1, maxlen=40)
    # 训练
    train(ted, model_name)

# 词句级训练
def train_token(model_name):
    # 获取数据集列表
    data = get_data()
    # text tokenize将源代码拆分为python标记
    ted = TextEncoderDecoder(data, tokenize=text_tokenize, untokenize="".join, padding=" ",
                             min_count=1, maxlen=20)
    # 训练
    train(ted, model_name)

# 获取模型
def get_model(model_name):
    return LSTMBase(model_name)

# 预测函数
def neural_complete(model, text, diversities):
    predictions = [model.predict(text, diversity=d, max_prediction_steps=80,
                                 break_at_token="\n")
                   for d in diversities]
    # returning the latest sentence, + prediction
    suggestions = [text.split("\n")[-1] + x.rstrip("\n") for x in predictions]
    return suggestions


if __name__ == "__main__":
    import sys

    # 判断指令是否符合标准，标准格式如“python neural_complete.py test char”
    if len(sys.argv) != 3:
        raise Exception(
            "expecting model name, such as 'neural' and type (either 'char' or 'token'")

    # 组合模型名称，如“test_char”
    model_name = "_".join(sys.argv[1:])

    # 判断训练模式：char或token
    if sys.argv[2] == "char":
        train_char(model_name)
    elif sys.argv[2] == "token":
        train_token(model_name)
    else:
        msg = "The second argument cannot be {}, but should be either 'char' or 'token'"
        raise Exception(msg.format(sys.argv[2]))

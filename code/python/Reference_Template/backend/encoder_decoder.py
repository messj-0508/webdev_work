from collections import Counter
import numpy as np

import tokenize as tk
from io import BytesIO


def text_tokenize(txt):
    """ specific tokenizer suitable for extracting 'python tokens' """
    toks = []
    try:
        for x in tk.tokenize(BytesIO(txt.encode('utf-8')).readline):
            toks.append(x)
    except tk.TokenError:
        pass
    tokkies = []
    old = (0, 0)
    for t in toks:
        if not t.string:
            continue
        if t.start[0] == old[0] and t.start[1] > old[1]:
            tokkies.append(" " * (t.start[1] - old[1]))
        tokkies.append(t.string)
        old = t.end
    if txt.endswith(" "):
        tokkies.append(" ")
    toks = [x for x in tokkies if not x.startswith("#")]
    return toks[1:]

# 该模型疑似解决一轮对话问题，且输出只有对与错两种情况
class EncoderDecoder():

    # 初始化
    def __init__(self, maxlen, min_count, unknown, padding, tokenize, untokenize):
        self.maxlen = maxlen                  # 最大长度
        self.min_count = min_count            # 对需记录词汇的出现的次数的最低要求
        self.unknown = unknown                # 未知词汇的表示
        self.padding = padding                # 要补全的值
        self.tokenize = tokenize              # 分词函数
        self.untokenize = untokenize          # 词语连接成句函数
        self.questions = []                   # 问题数据
        self.answers = []                     # 回答数据
        self.ex, self.dx = None, None         # 输入编码解码用词典
        self.ey, self.dy = None, None         # 真实输出编码解码用词典
        self.X, self.y = self.build_data()    # 获取输入和真实输出
    
    # 根据任务类型，自定义数据创建函数
    def build_data(self):
        raise NotImplementedError
    
    # 将输入词汇转为输入序号
    def encode_x(self, x):
        return self.ex.get(x, 0)
    
    # 将标签词汇转为标签序号
    def encode_y(self, y):
        return self.ey.get(y, 0)
    
    # 将输入序号转为输入词汇
    def decode_x(self, x):
        return self.dx.get(x, self.unknown)
    
    # 将标签序号转为标签词汇
    def decode_y(self, y):
        return self.dy.get(y, self.unknown)
    
    # 基础编码器
    def build_coders(self, tokens):
        tokens = [item for sublist in tokens for item in sublist]
        # Counter类，字典的子类，记录k出现的次数
        word_to_index = {k: v for k, v in Counter(tokens).items() if v >= self.min_count}
        # 将key值与index序号对应
        word_to_index = {k: i for i, (k, v) in enumerate(word_to_index.items(), 1)}
        # 将未知词汇的序列设为 0
        word_to_index[self.unknown] = 0
        # 设置序号-词汇表
        index_to_word = {v: k for k, v in word_to_index.items()}
        # 设置0-未知词汇的关系
        index_to_word[0] = self.unknown
        # 返回词典
        return word_to_index, index_to_word
    
    # 问答对话用的编码系统
    def build_qa_coders(self):
        # 获得问题词汇的词典表
        self.ex, self.dx = self.build_coders(self.questions)
        print("unique question tokens:", len(self.ex))
        # 获得回答词汇的词典表
        self.ey, self.dy = self.build_coders([self.answers])
        print("unique answer tokens:", len(self.ey))
    
    # 获取输入输出函数——训练时用
    def get_xy(self):
        # 获取输入样例的数目
        n = len(self.questions)
        # X为样例数目*句子最大词汇数目*字典长度
        X = np.zeros((n, self.maxlen, len(self.ex)), dtype=np.bool)
        # y为样例数目*字典长度
        y = np.zeros((n, len(self.ey)), dtype=np.bool)
        # 将样例转为one-hot向量
        for num_pair, (question, answer) in enumerate(zip(self.questions, self.answers)):
            for num_token, q_token in enumerate(question):
                X[num_pair, num_token, self.encode_x(q_token)] = 1
            y[num_pair, self.encode_y(answer)] = 1
        # 返回
        return X, y
    
    # 句子补全函数
    def pad(self, tokens):
        seqlen = len(tokens)
        return [self.padding] * (self.maxlen - seqlen + 1) + tokens
    
    # 获取输入函数——预测时用
    def encode_question(self, text):
        # X为样例数目（1）*句子最大词汇数目*字典长度
        X = np.zeros((1, self.maxlen, len(self.ex)), dtype=np.bool)
        # 补全
        prepped = self.pad(self.tokenize(text)[-self.maxlen:])
        # 转换为one-hot向量
        for num, x in enumerate(prepped[1:]):
            X[0, num, self.encode_x(x)] = 1
        # 返回
        return X

# 文本专用的编码解码器
class TextEncoderDecoder(EncoderDecoder):

    # 初始化
    def __init__(self, texts, tokenize=str.split, untokenize=" ".join,
                 window_step=3, maxlen=20, min_count=1,
                 unknown="UNKNOWN", padding="PADDING"):
        self.texts = texts                           # 一个文本文件（如.py文件）的内容
        self.window_step = window_step               # 每句话隔多少词作为预测的内容
        c = super(TextEncoderDecoder, self)
        c.__init__(maxlen, min_count, unknown, padding, tokenize, untokenize)
    
    def build_data(self):
        # 初始化questions和answers
        self.questions = []
        self.answers = []
        # 将文本切割并补全放入
        for text in self.texts:
            tokens = self.tokenize(text)
            text = self.pad(tokens)
            seqlen = len(text)
            for i in range(0, seqlen - self.maxlen, self.window_step):
                self.questions.append(text[i: i + self.maxlen])
                self.answers.append(text[i + self.maxlen])
        self.build_qa_coders()
        print("number of QA pairs:", len(self.questions))
        # 返回执行get_xy（）的结果
        return self.get_xy()

# 问答专用的编码解码器
class QuestionAnswerEncoderDecoder(EncoderDecoder):
    pass

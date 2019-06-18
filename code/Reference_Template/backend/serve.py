import os
from cors import crossdomain
from flask import Flask, jsonify, request

from neural_complete import neural_complete
from neural_complete import get_model


def read_models(base_path="models/"):
    return set([x.split(".")[0] for x in os.listdir(base_path)])


app = Flask(__name__)

models = {x: get_model(x) for x in read_models()}


def get_args(req):
    if request.method == 'POST':
        args = request.json
    elif request.method == "GET":
        args = request.args
    return args


# url设置：predict
@app.route("/predict", methods=["GET", "POST", "OPTIONS"])
@crossdomain(origin='*', headers="Content-Type")

# 响应函数：predict，预测
def predict():
    args = get_args(request)
    sentence = args.get("keyword", "from ")
    model_name = args.get("model", "char")
    if model_name not in models:
        models[model_name] = get_model(model_name)
    suggestions = neural_complete(models[model_name], sentence, [0.2, 0.5, 1])
    return jsonify({"data": {"results": [x.strip() for x in suggestions]}})

# url设置：get_models
@app.route("/get_models", methods=["GET", "POST", "OPTIONS"])
@crossdomain(origin='*', headers="Content-Type")

# 响应函数：get_models，获取模型列表
def get_models():
    return jsonify({"data": {"results": list(models)}})

# 主函数
def main(host="127.0.0.1", port=9078):
    # 启动本地服务器在host:port，如127.0.0.1：9078
    app.run(host=host, port=port, debug=True)


if __name__ == "__main__":
    # 调用主函数
    main()

# Neural Complete (backend)

This repository contains the backend for [Neural Complete](../). It uses Flask to expose a LSTM Recurrent Neural Network. This is where you can experiment with your models and your own data.

### Installation

    pip install -r requirements.txt

### Training the model

Not such a nice setup, but at least possible to reproduce:

    python neural_complete.py test char
    python neural_complete.py test token

### Using created models

    python serve.py


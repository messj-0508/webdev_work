# coding: utf-8
from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.

def index(request):
    return HttpResponse(u"测试")

def eval(request):
    print request
    if request.POST:
        return HttpResponse(u"11111111111")
    else:
        return HttpResponse(u"2222222222")

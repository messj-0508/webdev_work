# -*- coding:utf-8 -*-


from ysid.indentify import front_picture_identify
from ysid.indentify import back_picture_identify

if __name__ == "__main__":
    data = front_picture_identify("test/weng_1_3.jpg",False)
    for k in data:
        print k,data[k]
    data = back_picture_identify("test/weng_2_5.jpg",False)
    for k in data:
        print k,data[k]


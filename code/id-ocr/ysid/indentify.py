# -*- coding:utf-8 -*-

import re
import cv2
import os
import traceback
from opencv import image_cut, image_cut2
from tesserast_ocr import tesseractChinese
from tesserast_ocr import tesseractSex
from tesserast_ocr import tesseractNationality
from tesserast_ocr import tesseractDate
from tesserast_ocr import tesseractID 
from tesserast_ocr import tesseractValidperriod
import tempfile



try:  
    from cStringIO import StringIO  
except ImportError:  
    from StringIO import StringIO  

def write_debug_image(img, temp_file):
    if not os.path.exists('temp'):
        os.makedirs('temp')
    if os.path.isfile(temp_file):
        os.remove(temp_file)
    cv2.imwrite(temp_file, img)


def front_picture_identify(file_path,TEST=False):
    ''''
    正面身份证信息识别
    '''
    if (not os.path.isfile(file_path)):
        msg = u"文件不存在%s"%(file_path)
        raise Exception( msg )

    temp = tempfile.NamedTemporaryFile()
    temp_file = str(temp.name) + ".jpg"
    temp.close()

    try:
        try:
            img = image_cut(file_path, TEST)
            if TEST: write_debug_image(img, "temp/front.jpg")
        except TypeError:
            img = image_cut2(file_path, TEST)
            if TEST: write_debug_image(img, "temp/front.jpg")
        cv2.imwrite(temp_file, img)
        img = cv2.imread("temp/front.jpg", 0)
        """
            调试用 用于设置尺寸信息 
            身份证尺寸 85.6 mm * 54.0 mm * 1.0 mm 
            或者等比例缩小放大 修改imgWidth 和 imgHeight
        """
        height,width= img.shape[:2]
        imgWidth = width
        imgHeight = height
        img = cv2.resize(img, (imgWidth, imgHeight))

        # 降噪处理
        # blur = cv2.GaussianBlur(img, (3, 3), 0)
        # retval, img = cv2.threshold(blur, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

        """
            对指定区域进行图像裁剪
        """
        # 姓名
        Name = []
        name_height = int(imgHeight / 10.8)
        name_width = int(imgWidth / 5.7)
        name_addHeight = int(imgHeight / 10.8 + imgHeight / 6.75)
        name_addWidth = int(imgWidth / 5.7 + imgWidth / 2.31)
        name = img[name_height:name_addHeight, name_width:name_addWidth]
        Name.append(name)

        # 性别
        Sex = []
        sex_height = int(imgHeight / 4.5)
        sex_width = int(imgWidth / 5.7)
        sex_addHeight = int(imgHeight / 4.5 + imgHeight / 7.71)
        sex_addWidth = int(imgWidth / 5.7 + imgWidth / 12.22)
        sex = img[sex_height:sex_addHeight, sex_width:sex_addWidth]
        Sex.append(sex)

        # 民族
        Nationality = []
        nationality_height = int(imgHeight / 4.5)
        nationality_width = int(imgWidth / 2.55)
        nationality_addHeight = int(imgHeight / 4.5 + imgHeight / 7.71)
        nationality_addWidth = int(imgWidth / 2.55 + imgWidth / 4.28)
        nationality = img[nationality_height:nationality_addHeight, nationality_width:nationality_addWidth]
        Nationality.append(nationality)

        # 生日 年
        Birth_year = []
        birth_year_height = int(imgHeight / 2.84)
        birth_year_width = int(imgWidth / 5.35)
        birth_year_addHeight = int(imgHeight / 2.84 + imgHeight / 7.71)
        birth_year_addWidth = int(imgWidth / 5.35 + imgWidth / 8.56)
        birth_year = img[birth_year_height:birth_year_addHeight, birth_year_width:birth_year_addWidth]
        Birth_year.append(birth_year)

        # 生日 月
        Birth_month = []
        birth_month_height = int(imgHeight / 2.84)
        birth_month_width = int(imgWidth / 2.85)
        birth_month_addHeight = int(imgHeight / 2.84 + imgHeight / 7.71)
        birth_month_addWidth = int(imgWidth / 2.85 + imgWidth / 21.4)
        birth_month = img[birth_month_height:birth_month_addHeight, birth_month_width:birth_month_addWidth]
        Birth_month.append(birth_month)

        # 生日 日
        Birth_day = []
        birth_day_height = int(imgHeight / 2.84)
        birth_day_width = int(imgWidth / 2.31)
        birth_day_addHeight = int(imgHeight / 2.84 + imgHeight / 7.71)
        birth_day_addWidth = int(imgWidth / 2.31 + imgWidth / 17.12)
        birth_day = img[birth_day_height:birth_day_addHeight, birth_day_width:birth_day_addWidth]
        Birth_day.append(birth_day)

        # 地址
        Address = []
        address_height = int(imgHeight / 2.07)
        address_width = int(imgWidth / 5.7)
        address_addHeight = int(imgHeight / 2.07 + imgHeight / 3.17)
        address_addWidth = int(imgWidth / 5.7 + imgWidth / 2.25)
        address = img[address_height:address_addHeight, address_width:address_addWidth]
        Address.append(address)

        # 身份证号码
        ID_number = []
        ID_height = int(imgHeight / 1.27)
        ID_width = int(imgWidth / 3.06)
        ID_addHeight = int(imgHeight / 1.27 + imgHeight / 6.75)
        ID_addWidth = int(imgWidth / 3.06 + imgWidth / 1.55)
        ID = img[ID_height:ID_addHeight, ID_width:ID_addWidth]
        ID_number.append(ID)

        if TEST:
            # 测试用 显示信息图片
            # 总图
            cv2.namedWindow("font",cv2.WINDOW_NORMAL)
            cv2.imshow("font", img)
            cv2.waitKey(1500)
            # 姓名
            cv2.imshow("name", name)
            cv2.waitKey(700)
            # 性别
            cv2.imshow("sex", sex)
            cv2.waitKey(700)
            # 民族
            cv2.imshow("nationality", nationality)
            cv2.waitKey(700)
            # 生日 年
            cv2.imshow("birth_year", birth_year)
            cv2.waitKey(700)
            # 生日 月
            cv2.imshow("birth_month", birth_month)
            cv2.waitKey(700)
            # 生日 日
            cv2.imshow("birth_day", birth_day)
            cv2.waitKey(700)
            # 地址
            cv2.imshow("address", address)
            cv2.waitKey(700)
            # 身份证号码
            cv2.imshow("ID", ID)
            cv2.waitKey(700)
        # 释放窗口
        cv2.destroyAllWindows()

        # 文字识别
        out_name = tesseractChinese(Name)
        out_sex = tesseractSex(Sex)
        out_nationality = tesseractNationality(Nationality)
        out_birth_year = tesseractDate(Birth_year)
        out_birth_month = tesseractDate(Birth_month)
        out_birth_day = tesseractDate(Birth_day)
        out_address = tesseractChinese(Address)
        out_ID_number = tesseractID(ID_number)

        # 没有识别出结果
        identifyFailed = "识别失败"
        if (len(out_name)==0):
            out_name = identifyFailed
        if (len(out_sex) == 0):
            out_sex = identifyFailed
        if (len(out_nationality)==0):
            out_nationality = identifyFailed
        if (len(out_birth_year) == 0):
            out_birth_year = identifyFailed
        if (len(out_birth_month) == 0):
            out_birth_month = identifyFailed
        if (len(out_birth_day) == 0):
            out_birth_day = identifyFailed
        if (len(out_address) == 0):
            out_address = identifyFailed
        if not (re.match(r"^\d{17}(\d|X|x)$", out_ID_number)):
            out_ID_number = identifyFailed

        # 根据身份证信息补全
        if (re.match(r"^\d{17}(\d|X|x)$", out_ID_number)):
            if (out_sex is identifyFailed):
                if (int(out_ID_number[17:18]) % 2 == 1):
                    out_sex = "女"
                if (int(out_ID_number[17:18]) % 2 == 0):
                    out_sex = "男"
            if (out_birth_year is identifyFailed):
                out_birth_year = int(out_ID_number[6:10])
            if (out_birth_month is identifyFailed):
                out_birth_month = int(out_ID_number[10:12])
            if (out_birth_day is identifyFailed):
                out_birth_day = int(out_ID_number[12:14])

        # 打印数据
        if TEST:
            print "name",out_name
            print "sex",out_sex
            print "nationality",out_nationality
            print "birth",out_birth_year+"/" +out_birth_month+"/"+out_birth_day
            print "address",out_address
            print "ID_number",out_ID_number

        data = {
            "Name":out_name,
            "Sex":out_sex,
            "Nationality":out_nationality,
            "Birth":out_birth_year+"/" +out_birth_month+"/"+out_birth_day,
            "Address":out_address,
            "ID_number":out_ID_number,
            "code":0
        }
        return data
    except BaseException:
        traceback.print_exc()
        return {'code':-1,"msg":'身份证正面面识别失败，请调整角度重新拍照'}


def back_picture_identify(file_path,TEST=False):
    '''
        身份证背面信息读取
    '''
    if (not os.path.isfile(file_path)):
        msg = u"文件不存在%s"%(file_path)
        raise Exception( msg )

    temp = tempfile.NamedTemporaryFile()
    temp_file = str(temp.name)+".jpg"
    temp.close()

    try:
        try:
            img = image_cut(file_path, TEST)
            if TEST: write_debug_image(img, "temp/back.jpg")
        except TypeError:
            img = image_cut2(file_path, TEST)
            if TEST: write_debug_image(img, "temp/back.jpg")
        cv2.imwrite(temp_file, img)
        img = cv2.imread(temp_file,0)


        # 设置尺寸信息 身份证尺寸 85.6 mm * 54.0 mm * 1.0 mm
        height, width = img.shape
        imgWidth = width
        imgHeight = height
        img = cv2.resize(img, (imgWidth, imgHeight))

        # 降噪处理
        blur = cv2.GaussianBlur(img, (5, 5), 0)
        retval, img = cv2.threshold(blur, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

        # 签发机关
        IssuingAuthority = []
        issuingAuthority_height = int(imgHeight/1.5)
        issuingAuthority_width = int(imgWidth/2.52)
        issuingAuthority_addHeight = int(imgHeight/1.5+imgHeight/6.75)
        issuingAuthority_addWidth = int(imgWidth/2.52+imgWidth/1.72)
        issuingAuthority = img[issuingAuthority_height:issuingAuthority_addHeight,
                           issuingAuthority_width:issuingAuthority_addWidth]
        IssuingAuthority.append(issuingAuthority)

        # 有效期
        ValidPerriod = []
        validPerriod_height = int(imgHeight/1.26)
        validPerriod_width = int(imgWidth/2.52)
        validPerriod_addHeight = int(imgHeight/1.26+imgHeight/6.75)
        validPerriod_addWidth = int(imgWidth/2.52+imgWidth/1.72)
        validPerriod = img[validPerriod_height:validPerriod_addHeight,
                       validPerriod_width:validPerriod_addWidth]
        ValidPerriod.append(validPerriod)

        if TEST:
            # 调试用 显示图片
            # 总图
            cv2.namedWindow("back", cv2.WINDOW_NORMAL)
            cv2.imshow("back", img)
            cv2.waitKey(1500)
            # 签证机关
            cv2.imshow("IssuingAuthority", issuingAuthority)
            cv2.waitKey(700)
            # 有效期
            cv2.imshow("ValidPerriod", validPerriod)
            cv2.waitKey(700)
        # 释放资源
        cv2.destroyAllWindows()

        # 文字识别
        out_issuingAuthority = tesseractChinese(IssuingAuthority)
        out_validPerriod = tesseractValidperriod(ValidPerriod)

        # 调试用 前台打印信息
        if TEST:
            print "IssuingAuthority:",out_issuingAuthority
            print "ValidPerriod:",out_validPerriod
        data ={
            "IssuingAuthority":out_issuingAuthority,
            "ValidPerriodStar": out_validPerriod[:8],
            "ValidPerriodEnd":out_validPerriod[8:],
            "code":0
        }
        return data
    except BaseException:
        traceback.print_exc()
        return {'code':-1,"msg":'身份证背面识别失败，请调整角度重新拍照'}
    finally:
        if os.path.isfile(temp_file):
            os.remove(temp_file)

if __name__ == "__main__":
    data = front_picture_identify("material/pi_1.jpg",True)
    for k in data:
        print k,data[k]
    data = back_picture_identify("material/pi_2.jpg",True)
    for k in data:
        print k,data[k]

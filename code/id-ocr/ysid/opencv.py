#!/usr/bin/env python
# -*- coding:utf-8 -*-

import traceback
import heapq
import cv2
import os
import numpy as np

def write_debug_image(img, temp_file):
    if not os.path.exists('temp'):
        os.makedirs('temp')
    if os.path.isfile(temp_file):
        os.remove(temp_file)
    cv2.imwrite(temp_file, img)

def image_cut(path,TEST=False):
    if TEST:
        print "image_cut"
    '''
    图片处理程序
    '''
    try:
        """
            函数cv2.imread(path,model)来读取图像。图像应该在工作目录中，或者应该给出完整的图像路径。
                第一个参数为路径。
                第二个参数是一个指定图像应该被读取的方式的标志。
                cv2.IMREAD_COLOR：加载彩色图像。任何图像的透明度都将被忽略。它是默认的标志。
                cv2.IMREAD_GRAYSCALE：以灰度模式加载图像
                cv2.IMREAD_UNCHANGED：加载图像，包括alpha通道
                或者是直接传递 1，0，-1
            函数cvtColor(img，model)获得原图像的副本。
                第一个参数图像为图片
                第二个参数与imread相似
            可以用 图像.shape 用来获取图片长度，宽度，图像颜色通道
                返回值为长度，宽度，颜色通道
                灰度图片只有长度，宽度
            """
        # 读取图片
        img = cv2.imread(path)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

        # 获取长度宽度
        height, width = img.shape[:2]
        dstWidth = width
        dstHeight = height

        if TEST:
            print "height:", height, "width:", width, "dstHeight:", dstHeight, "dstWidth:", dstWidth

        """
            函数cv2.resize()用来改变图像的大小。
                第一个参数：传入图像
                第二个参数：图像大小
                图像的大小可以手动指定，也可以使用缩放比例。
                cv2.resize()支持多种插值算法，默认使用的是cv2.INTER_LINEAR（不管放大和缩小）。
                缩小最适合使用：cv2.INTER_AREA，放大最适合使用：cv2.INTER_CUBIC (慢) 或 cv2.INTER_LINEAR。
        """
        # 测试用 比例缩小 方便调试
        img = cv2.resize(img, (dstWidth, dstHeight))
        gray = cv2.resize(gray, (dstWidth, dstHeight))

        """
            函数cv2.namedWindow() 通过指定的名字，创建一个可以作为图像和进度条的容器窗口。
                如果具有相同名称的窗口已经存在，则函数不做任何事情。默认值WINDOW_AUTOSIZE
                第一个参数 string 型的 name，即填被用作窗口的标识符的窗口名称。
                第二个参数，窗口的标识，可以填如下的值：
                WINDOW_NORMAL设置了这个值，用户便可以改变窗口的大小（没有限制）
                WINDOW_AUTOSIZE如果设置了这个值，窗口大小会自动调整以适应所显示的图像，并且不能手动改变窗口大小。
                WINDOW_OPENGL 如果设置了这个值的话，窗口创建的时候便会支持OpenGL。
            函数cv2.imshow() 在指定的窗口中显示一幅图像。
                第一个参数，string 类型的winname，填需要显示的窗口标识名称。
                第二个参数，填需要显示的图像。
            函数cv2.waitKey() 是一个键盘绑定函数。它的时间尺度是毫秒级。
                函数等待特定的几毫秒，看是否有键盘输入。
                特定的几毫秒之内，如果按下任意键，这个函数会返回按键的ASCII 码值，程序将会继续运行。
                如果没有键盘输入，返回值为-1，如果我们设置这个函数的参数为0，那它将会无限期的等待键盘输入。
                它也可以被用来检测特定键是否被按下，例如按键 a 是否被按下
        """
        # 显示图片 测试用
        if TEST:
            cv2.namedWindow("orginal", cv2.WINDOW_NORMAL)
            cv2.imshow("orginal", img)
            cv2.waitKey(750)

            cv2.namedWindow("gray", cv2.WINDOW_NORMAL)
            cv2.imshow("gray", gray)
            cv2.waitKey(750)

        """
            函数cv2.GaussianBlur()高斯滤波
                高斯模糊本质上是低通滤波器，输出图像的每个像素点是原图像上对应像素点与周围像素点的加权和
                第一个参数为输入图像。
                第二个参数为高斯矩阵的长与宽，值为正的且为奇数
                第三个参数标准差取 0 时OpenCV会根据高斯矩阵的尺寸自己计算。
                    概括地讲，高斯矩阵的尺寸越大，标准差越大，处理过的图像模糊程度越大
            函数cv2.threshold() 对图像二值化，如果像素值大于阈值，则分配一个值（可能是白色），否则分配另一个值（可能是黑色）。
                返回值，第一个为阈值，第二个为处理后图像
                第一个参数 待处理图像，只能是灰度图像
                第二个参数 对像素值进行分类的阈值 不为零时直接返回
                第三个参数 如果像素值大于（有时小于）阈值则给出的值，即在二元阈值THRESH_BINARY和逆二元阈值THRESH_BINARY_INV中使用的最大值
                第四个参数 使用的阈值类型
                    cv2.THRESH_BINARY
                    cv2.THRESH_BINARY_INV
                    算法查找阈值，Otsu’s算法就可以自己找到一个认为最好的阈值。
                    并且Otsu’s非常适合于图像灰度直方图具有双峰的情况，他会在双峰之间找到一个值作为阈值。
                    此时本参数得加上“+cv2.THRESH_OTSU”
                    因为Otsu’s算法会产生一个阈值，并且直接返回，第二个参数也得设置为 0                                   
        """
        # 高斯模糊 qtsu二值
        blur = cv2.GaussianBlur(gray, (7, 7), 0)
        retval, binarized = cv2.threshold(blur, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

        # QTSU算出的阈值
        if TEST:
            print "retval:", retval

        # 显示图片 测试用
        if TEST:
            cv2.namedWindow("binarized", cv2.WINDOW_NORMAL)
            cv2.imshow("binarized", binarized)
            cv2.waitKey(750)

        """
           函数cv2.getStructuringElement() 用于定义一个结构元素
               第一个参数
                    定义椭圆（MORPH_ELLIPSE）结构元素
                    十字形结构（MORPH_CROSS）结构元素
                    定义矩形（MORPH_RECT）结构元素
               第二个参数 定义规格
           函数cv2.dilate() 对象面积增加。可用于连接物体的断裂部分
               第一个参数 要处理的图片
               第二个参数 内核
        """
        # # 侵蚀图片 去除干扰
        # kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
        # erode = cv2.erode(binarized, kernel)
        #
        # # 显示图片 测试用
        # if TEST:
        #     cv2.namedWindow("erode", cv2.WINDOW_NORMAL)
        #     cv2.imshow("erode", erode)
        #     cv2.waitKey(750)
        #
        # # 膨胀图片 方便裁剪
        # kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
        # dilated = cv2.dilate(binarized, kernel)
        #
        # # 显示图片 测试用
        # if TEST:
        #     cv2.namedWindow("dilated", cv2.WINDOW_NORMAL)
        #     cv2.imshow("dilated", dilated)
        #     cv2.waitKey(750)

        # 开运算 先侵蚀后膨胀 有助于消除噪音
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
        opening = cv2.morphologyEx(binarized, cv2.MORPH_OPEN, kernel)

        # 显示图片 测试用
        # if TEST:
        #     cv2.namedWindow("opening", cv2.WINDOW_NORMAL)
        #     cv2.imshow("opening", opening)
        #     cv2.waitKey(750)

        """
            函数cv2.findContours 查找轮廓
                image 返回值
                    我也不知道干嘛用的
                contours返回值
                    返回一个list，list中每个元素都是图像中的一个轮廓，用numpy中的ndarray表示
                hierarchy返回值
                    返回一个可选的hiararchy结果，这是一个ndarray，其中的元素个数和轮廓个数相同，
                    每个轮廓contours[i]对应4个hierarchy元素hierarchy[i][0] ~hierarchy[i][3]，
                    分别表示后一个轮廓、前一个轮廓、父轮廓、内嵌轮廓的索引编号，如果没有对应项，则该值为负数。
                第一个参数是寻找轮廓的图像；
                第二个参数表示轮廓的检索模式，有四种：
                    cv2.RETR_EXTERNAL表示只检测外轮廓
                    cv2.RETR_LIST检测的轮廓不建立等级关系
                    cv2.RETR_CCOMP建立两个等级的轮廓，上面的一层为外边界，里面的一层为内孔的边界信息。
                        如果内孔内还有一个连通物体，这个物体的边界也在顶层。
                    cv2.RETR_TREE建立一个等级树结构的轮廓。
                第三个参数method为轮廓的近似办法
                    cv2.CHAIN_APPROX_NONE存储所有的轮廓点，相邻的两个点的像素位置差不超过1，即max（abs（x1-x2），abs（y2-y1））==1
                    cv2.CHAIN_APPROX_SIMPLE压缩水平方向，垂直方向，对角线方向的元素，只保留该方向的终点坐标，例如一个矩形轮廓只需4个点来保存轮廓信息
                    cv2.CHAIN_APPROX_TC89_L1，CV_CHAIN_APPROX_TC89_KCOS使用teh-Chinl chain 近似算法        
        """
        # 框选图片
        image, contours, hierarchy = cv2.findContours(opening, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

        """
            函数 cv2.drawContours() 在图像上绘制轮廓。 
                第一个参数是指明在哪幅图像上绘制轮廓；
                第二个参数是轮廓本身，在Python中是一个list。
                第三个参数指定绘制轮廓list中的哪条轮廓，如果是-1，则绘制其中的所有轮廓。
                第四个为颜色
                最后一个表明轮廓线的宽度，如果是-1（cv2.FILLED），则为填充模式。
        """
        # 测试用 画框
        # cv2.drawContours(img, contours, -1, (0, 255, 255), 3)

        # 显示图片 测试用
        # if TEST:
        #     cv2.namedWindow("drawContours", cv2.WINDOW_NORMAL)
        #     cv2.imshow("drawContours", img)
        #     cv2.waitKey(750)

        # 进行筛选
        IDcnts = areaSelect(contours)

        """
            用 cv2.minAreaRect()用一个带旋转的最小的矩形，把找到的形状包起来。
            用 cv2.boxPoints() 找出四个顶点

        """
        # 带角度的图选中最小矩形
        box = None
        for index, IDcnt in enumerate(IDcnts):
            rect = cv2.minAreaRect(IDcnt)
            box = cv2.boxPoints(rect)
            box1 = np.int0(box)
            cv2.drawContours(img, [box1], 0, (100, 100, 100), 2)

        # 获取四个点 依次为左下，左上，右上，右下
        first_x = box[0][0]
        first_y = box[0][1]

        second_x = box[1][0]
        second_y = box[1][1]

        third_x = box[2][0]
        third_y = box[2][1]

        fourth_x = box[3][0]
        fourth_y = box[3][1]

        length_1 = np.math.sqrt(pow(first_x - second_x, 2) + pow(first_y - second_y, 2))
        length_2 = np.math.sqrt(pow(second_x - third_x, 2) + pow(second_y - third_y, 2))

        # 调试用 长和宽
        if TEST:
            print "矫正："
            print "length_1:",length_1
            print "length_2",length_2

        if (length_1 < 856.0 or length_2 < 856.0):
            print "imageProcessing error:", "图片处理失败"

        # 画出四个点 调试用
        # 红(255, 0, 0)
        # 绿(0, 255, 0)
        # 蓝(0, 0, 255)
        # 白(255, 255, 255)
        # cv2.circle(img, (first_x, first_y), 2, (255, 0, 0), 3)
        # cv2.circle(img, (second_x, second_y), 2, (0, 255, 0), 3)
        # cv2.circle(img, (third_x, third_y), 2, (0, 0, 255), 3)
        # cv2.circle(img, (fourth_x, fourth_y), 2, (255, 255, 255), 3)

        # 显示图片 测试用
        # if TEST:
        #     cv2.namedWindow("img2", cv2.WINDOW_NORMAL)
        #     cv2.imshow("img2", img)
        #     cv2.waitKey(750)

        dstImgWidth = 856 * 2
        dstImgHeigt = 540 * 2

        # 矫正图像
        if (length_1 < length_2):
            pts1 = np.float32([[second_x, second_y], [third_x, third_y], [first_x, first_y], [fourth_x, fourth_y]])
            pts2 = np.float32([[0, 0], [dstImgWidth, 0], [0, dstImgHeigt], [dstImgWidth, dstImgHeigt]])
        if (length_1 > length_2):
            pts1 = np.float32([[third_x, third_y], [fourth_x, fourth_y], [second_x, second_y], [first_x, first_y]])
            pts2 = np.float32([[0, 0], [dstImgWidth, 0], [0, dstImgHeigt], [dstImgWidth, dstImgHeigt]])

        """
            cv2.getPerspectiveTransform(pts1, pts2) 变换矩阵
                pts1,pts2分别为变换前点的位置以及变换后点的位置
            cv2.warpPerspective() 投影变换
                第一个参数 要变换的图像
                第二个参数 变换矩阵
                第三个参数 输出大小
        """
        # 投影变换
        M = cv2.getPerspectiveTransform(pts1, pts2)
        dstProjection = cv2.warpPerspective(img, M, (dstImgWidth, dstImgHeigt))

        # 显示图片 测试用
        # if TEST:
        #     cv2.namedWindow("dst", cv2.WINDOW_NORMAL)
        #     cv2.imshow("dst", dstProjection)
        #     cv2.waitKey(750)

        """
            cv2.destroyAllWindows() 可以轻易删除任何建立的窗口。
            如果你想删除特定的窗口可以使用cv2.destroyWindow()，在括号内输入你想删除的窗口名。
        """
        # 释放调试图像窗口
        cv2.destroyAllWindows()

        if TEST: write_debug_image(img, "temp/processing.jpg")

        return dstProjection
    except BaseException:
        traceback.print_exc()
        raise Exception("图像识别识别，请重新扫描")
def image_cut2(path,TEST=False):
    if TEST:
        print "image_cut2"
    try:
        # 读取处理后灰度图片
        img = cv2.imread(path)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

        # 获取图片长宽 缩放 调试用
        height, width = img.shape[:2]

        dstWidth = width
        dstHeight = height

        if TEST:
            print "DstHeight:", dstHeight, "DstHeight:", dstWidth

        img = cv2.resize(img, (dstWidth, dstHeight))
        gray = cv2.resize(gray, (dstWidth, dstHeight))

        # 测试用 显示图片
        if TEST:
            cv2.namedWindow("gray2", cv2.WINDOW_NORMAL)
            cv2.imshow("gray2", gray)
            cv2.waitKey(750)

        """
            函数cv2.Canny()
                返回二值图
                第一个参数：输入是灰度图，就算是彩色图也会处理成灰度图
                第二第三个参数：是两个阈值，上限与下限，如果一个像素的梯度大于上限，则被认为是边缘像素，
                如果低于下限则被抛弃，如果介于两者之间，只有当其与高于上限阈值的像素连接时才会被接受。
        """
        # 高斯模糊 qtsu二值
        blur = cv2.GaussianBlur(gray, (7, 7), 0)
        canny = cv2.Canny(blur, 50, 200)

        # 测试用 显示图片
        if TEST:
            cv2.namedWindow("canny", cv2.WINDOW_NORMAL)
            cv2.imshow("canny", canny)
            cv2.waitKey(750)

        # 框选图片
        image, contours, hierarchy = cv2.findContours(canny, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

        # 测试用 画框
        cv2.drawContours(img, contours, -1, (255, 0, 0), 3)

        # 测试用 显示图片
        if TEST:
            cv2.namedWindow("img", cv2.WINDOW_NORMAL)
            cv2.imshow("img", img)
            cv2.waitKey(750)

        # 选取图片
        IDcnts = areaSelect(contours)
        """
                    用 cv2.minAreaRect()用一个带旋转的最小的矩形，把找到的形状包起来。
                    用 cv2.boxPoints() 找出四个顶点

                """
        # 带角度的图选中最小矩形
        box = None
        for index, IDcnt in enumerate(IDcnts):
            rect = cv2.minAreaRect(IDcnt)
            box = cv2.boxPoints(rect)
            box1 = np.int0(box)
            cv2.drawContours(img, [box1], 0, (100, 100, 100), 2)

        # 获取四个点 依次为左下，左上，右上，右下
        first_x = box[0][0]
        first_y = box[0][1]

        second_x = box[1][0]
        second_y = box[1][1]

        third_x = box[2][0]
        third_y = box[2][1]

        fourth_x = box[3][0]
        fourth_y = box[3][1]

        length_1 = np.math.sqrt(pow(first_x - second_x, 2) + pow(first_y - second_y, 2))
        length_2 = np.math.sqrt(pow(second_x - third_x, 2) + pow(second_y - third_y, 2))

        # 调试用 长和宽
        if TEST:
            print "矫正："
            print "length_1:",length_1
            print "length_2:",length_2

        if (length_1 < 856.0 or length_2 < 856.0):
            print "imageProcessing error:", "图片处理失败"

        # 画出四个点 调试用
        # 红(255, 0, 0)
        # 绿(0, 255, 0)
        # 蓝(0, 0, 255)
        # 白(255, 255, 255)
        # cv2.circle(img, (first_x, first_y), 2, (255, 0, 0), 3)
        # cv2.circle(img, (second_x, second_y), 2, (0, 255, 0), 3)
        # cv2.circle(img, (third_x, third_y), 2, (0, 0, 255), 3)
        # cv2.circle(img, (fourth_x, fourth_y), 2, (255, 255, 255), 3)

        # 显示图片 测试用
        if TEST:
            cv2.namedWindow("img2", cv2.WINDOW_NORMAL)
            cv2.imshow("img2", img)
            cv2.waitKey(750)

        dstImgWidth = 856 * 2
        dstImgHeigt = 540 * 2

        # 矫正图像
        if (length_1 < length_2):
            pts1 = np.float32([[second_x, second_y], [third_x, third_y], [first_x, first_y], [fourth_x, fourth_y]])
            pts2 = np.float32([[0, 0], [dstImgWidth, 0], [0, dstImgHeigt], [dstImgWidth, dstImgHeigt]])
        if (length_1 > length_2):
            pts1 = np.float32([[third_x, third_y], [fourth_x, fourth_y], [second_x, second_y], [first_x, first_y]])
            pts2 = np.float32([[0, 0], [dstImgWidth, 0], [0, dstImgHeigt], [dstImgWidth, dstImgHeigt]])

        """
            cv2.getPerspectiveTransform(pts1, pts2) 变换矩阵
                pts1,pts2分别为变换前点的位置以及变换后点的位置
            cv2.warpPerspective() 投影变换
                第一个参数 要变换的图像
                第二个参数 变换矩阵
                第三个参数 输出大小
        """
        # 投影变换
        M = cv2.getPerspectiveTransform(pts1, pts2)
        dstProjection = cv2.warpPerspective(img, M, (dstImgWidth, dstImgHeigt))

        # 显示图片 测试用
        if TEST:
            cv2.namedWindow("dst", cv2.WINDOW_NORMAL)
            cv2.imshow("dst", dstProjection)
            cv2.waitKey(750)

        """
            cv2.destroyAllWindows() 可以轻易删除任何建立的窗口。
            如果你想删除特定的窗口可以使用cv2.destroyWindow()，在括号内输入你想删除的窗口名。
        """
        # 释放调试图像窗口
        cv2.destroyAllWindows()

        if TEST: write_debug_image(dstProjection, "temp/processing2.jpg")

        return dstProjection
    except BaseException:
        traceback.print_exc()
        raise Exception("图像识别识别，请重新扫描")

def areaSelect(contours):
    # 保存所有轮廓的面积
    areas = []
    for index, cnt in enumerate(contours):
        area = cv2.contourArea(cnt)
        areas.insert(index, area)

    # 寻找面积最大的
    maxArea = heapq.nlargest(1, areas)

    # 找出最长的这个图形的矩形框
    IDcnts = []
    for index, item in enumerate(maxArea):
        index2 = areas.index(item)
        IDcnts.insert(index, contours[index2])
    return IDcnts


def widthSelect(contours):
    # 保存所有框选出的图像宽度
    widths = []
    for index, cnt in enumerate(contours):
        x, y, width, height = cv2.boundingRect(cnt)
        widths.insert(index, width)

    # 寻找最长的一个宽度
    IDList = heapq.nlargest(1, widths)

    # 找出最长的这个图形的矩形框
    IDcnts = []
    for index, item in enumerate(IDList):
        index2 = widths.index(item)
        IDcnts.insert(index, contours[index2])
    return IDcnts

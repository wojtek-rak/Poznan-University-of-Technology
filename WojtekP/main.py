from pylab import *
import skimage
from matplotlib import pylab as plt
import numpy as np
from numpy import array
from IPython.display import display
from ipywidgets import interact, interactive, fixed
from ipywidgets import *
from ipykernel.pylab.backend_inline import flush_figures
from skimage import filters, io
import sys
import math
import cv2 as cv
import numpy as np


imW = 658
imH = 490

planes0 = [
'IMG_20191107_102855.jpg',
'IMG_20191107_102755.jpg',
'IMG_20191107_102852.jpg',
'IMG_20191107_102844.jpg',
'IMG_20191107_102828.jpg',
'IMG_20191107_102826.jpg',
'IMG_20191107_102810.jpg',
'IMG_20191107_102807.jpg',
'IMG_20191107_102759.jpg',
'IMG_20191107_102751.jpg',
'IMG_20191107_102750.jpg',
'IMG_20191107_102644.jpg',
'IMG_20191107_102631.jpg',
'IMG_20191107_102613.jpg',
'IMG_20191107_102609.jpg',
'IMG_20191107_102606.jpg',
'IMG_20191107_102555.jpg',
'IMG_20191107_102552.jpg',
'IMG_20191107_102548.jpg',
'IMG_20191107_102243.jpg',
'IMG_20191107_102239.jpg',
'IMG_20191107_102226.jpg',
'IMG_20191107_102149.jpg',
'IMG_20191107_102120.jpg',
'IMG_20191107_102100.jpg',
'IMG_20191107_101755.jpg',
'IMG_20191107_101712.jpg',
'IMG_20191107_101706.jpg',
'IMG_20191107_101657.jpg',
'IMG_20191107_101645.jpg',
'IMG_20191107_101645(1).jpg',
'IMG_20191107_102425_005.jpg',
'IMG_20191107_102845_1.jpg']

# planes1 = [
#     'IMG_20191107_102855.jpg',
# 'IMG_20191107_102852.jpg',
# 'IMG_20191107_102844.jpg',
# 'IMG_20191107_102828.jpg',
# 'IMG_20191107_102826.jpg',
# 'IMG_20191107_102810.jpg']
# planes1 = ['IMG_20191107_102855.jpg']
# planes1 = ['IMG_20191107_102149.jpg']
# planes1 = ['IMG_20191107_102552.jpg']
# planes = []
planes = []
for plane in planes0:
    planes.append('Zdjęcia pięciolinia/5/' + plane)

plt.figure(figsize=(50, 26))
plt.subplots_adjust(left=0, bottom=0, right=0.985, hspace=0, wspace=0)
i = 1
t = 140

def getA(x1, y1, x2, y2):
    if x2-x1 == 0:
        return 1000000
    a = (y2-y1)/(x2-x1)
    return a

def getB(x1, y1, x2, y2):
    m = getA(x1, y1, x2, y2)
    b = y1 - m * x1
    return b

def Hough (filePath):
    print('Hough')

    #src = cv.imread(cv.samples.findFile(filePath), cv.IMREAD_GRAYSCALE)
    #src = cv.imread(filePath, cv.IMREAD_GRAYSCALE)
    src = cv.cvtColor(filePath, cv.COLOR_BGR2GRAY)
    #src = filePath
    filePath
    # 489, 652
    dst = cv.Canny(src, 50, 200, None, 3)
    cdst = cv.cvtColor(dst, cv.COLOR_GRAY2BGR)
    #cdst = np.copy(dst)
    cdstP = np.copy(cdst)

    lines = cv.HoughLines(dst, 1, np.pi / 180, 150, None, 0, 0)

    #print(lines)

    lineL = [(0, 0), (0, 0)]
    lineR = [(1000, 0), (1000, 0)]
    lineB = []
    lineU = []
    hasP = False
    hasPCount = 0

    if lines is not None:
        for i in range(0, len(lines)):
            rho = lines[i][0][0]
            theta = lines[i][0][1]
            a = math.cos(theta)
            b = math.sin(theta)
            x0 = a * rho
            y0 = b * rho
            pt1 = (int(x0 + 1000 * (-b)), int(y0 + 1000 * (a)))
            pt2 = (int(x0 - 1000 * (-b)), int(y0 - 1000 * (a)))
            print (pt1, pt2)
            # a = getA(pt1[0], pt1[1], pt2[0], pt2[1])
            # b = getB(pt1[0], pt1[1], pt2[0], pt2[1])
            a = getA(pt1[0], pt1[1], pt2[0], pt2[1])
            b = getB(pt1[0], pt1[1], pt2[0], pt2[1])

            if a == 1000000:
                hasP = True
                hasPCount += 1
                if pt1[0] < (imW / 2) and pt1[0] > lineL[0][0]:
                    lineL = [pt1, pt2]
                if pt1[0] > (imW / 2) and pt1[0] < lineR[0][0]:
                    lineR = [pt1, pt2]

            if a > 1:




            cv.line(cdst, pt1, pt2, (0, 0, 255), 3, cv.LINE_AA)

    #cv.line(cdst, (pt1[0], pt1[1], pt2[0], pt2[1]), (0, 255, 0), 3, cv.LINE_AA)
    cv.line(cdst, (0, 0), (10, 10), (0, 255, 0), 3, cv.LINE_AA)







    linesP = cv.HoughLinesP(dst, 1, np.pi / 180, 50, None, 50, 10)
    # lineUL = [imW, 0, imW, 0]
    # lineUR = [imW, 0, 0, 0]
    # lineBL = [imW, imH, imW, imH]
    # lineBR = [0, imH, 0, imH]

    # lineUL = []
    # lineUR = []
    # lineBL = []
    # lineBR = []

    # for i in range(0, len(linesP)):
    #     line = linesP[i][0]



        # if line[1] < line[3]:
        #     if line[1] > lineUL[1] and line[0] < lineUL[0] and (imW / 2) > line[0] and (imW / 2) > line[2] and abs(line[0] - line[2]) < abs(line[1] - line[3]):
        #         lineUL = line
        #     if line[1] > lineBR[1] and line[2] > lineBR[2] and (imW / 2) < line[0] and (imW / 2) < line[2] and abs(line[0] - line[2]) < abs(line[1] - line[3]):
        #         lineBR = line
        #
        # if line[0] < line[2]:
        #     if line[0] < lineUR[0] and line[1] > lineUR[1] and  (imH / 2) < line[0] and (imH / 2) < line[2] and abs(line[0] - line[2]) > abs(line[1] - line[3]):
        #         lineUR = line
        #     if line[0] < lineBL[0] and line[1] < lineBL[1] and (imH / 2) > line[0] and (imH / 2) > line[2] and abs(line[0] - line[2]) > abs(line[1] - line[3]):
        #         lineBL = line

    # for l in [lineUL, lineBR, lineUR, lineBL]:
    #     print(l)
    #     cv.line(cdstP, (l[0], l[1]), (l[2], l[3]), (0, 0, 255), 3, cv.LINE_AA)

    if linesP is not None:
        for i in range(0, len(linesP)):
            l = linesP[i][0]
            cv.line(cdstP, (l[0], l[1]), (l[2], l[3]), (0, 0, 255), 3, cv.LINE_AA)

    #print(linesP)






    #cv.imshow("Source", src)
    cv.imshow("Detected Lines (in red) - Standard Hough Line Transform", cdst)
    cv.imshow("Detected Lines (in red) - Probabilistic Line Transform", cdstP)
    cv.waitKey(0)
    cv.destroyAllWindows()

def Bright(filename):
    image = cv.imread(cv.samples.findFile(filename))
    global imW
    global imH
    imW = image.shape[1]
    imH = image.shape[0]
    #print(imW[0])
    #imH = image.rows
    new_image = np.zeros(image.shape, image.dtype)
    alpha = 1.0  # Simple contrast control
    beta = 100  # Simple brightness control

    for y in range(image.shape[0]):
        for x in range(image.shape[1]):
            for c in range(image.shape[2]):
                new_image[y, x, c] = np.clip(alpha * image[y, x, c] + beta, 0, 255)
    #cv.imshow('Original Image', image)
    #cv.imshow('New Image', new_image)
    return new_image

def White(new_image):
    frame = new_image
    hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)

    # define range of white color in HSV
    # change it according to your need !
    lower_white = np.array([0, 0, 0], dtype=np.uint8)
    upper_white = np.array([0, 0, 255], dtype=np.uint8)

    # Threshold the HSV image to get only white colors
    mask = cv.inRange(hsv, lower_white, upper_white)
    # Bitwise-AND mask and original image
    res = cv.bitwise_and(frame, frame, mask=mask)



    cv.imshow('frame', frame)
    cv.imshow('mask', mask)
    cv.imshow('res', res)
    return res
for path in planes:

    #image = Bright(path)

    #Hough(image)

    image = Bright(path)
    image = White(image)
    Hough(image)

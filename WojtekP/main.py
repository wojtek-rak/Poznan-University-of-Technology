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
import imutils
from statistics import median
import pandas as pd

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


def getA(x1, y1, x2, y2):
    if x2-x1 == 0:
        return 1000000
    a = (y2-y1)/(x2-x1)
    return a

def getB(x1, y1, x2, y2):
    m = getA(x1, y1, x2, y2)
    b = y1 - m * x1
    return b

def Slice (img, rotationDegrees):
    rotated = imutils.rotate_bound(img, rotationDegrees)
    global imW
    global imH
    imW = rotated.shape[1]
    imH = rotated.shape[0]

    src = cv.cvtColor(rotated, cv.COLOR_BGR2GRAY)
    # 489, 652
    dst = cv.Canny(src, 50, 200, None, 3)
    cdst = cv.cvtColor(dst, cv.COLOR_GRAY2BGR)
    #cdst = np.copy(dst)
    cdstP = np.copy(cdst)

    cv.line(cdst, (0, 0), (10, 10), (0, 255, 0), 3, cv.LINE_AA) #testlline

    linesP = cv.HoughLinesP(dst, 1, np.pi / 180, 50, None, 50, 10)
    lineB = [0, 0, 0, 0]
    lineU = [0, imH, 0, imH]
    lineL = [imW, 0, imW, 0]
    lineR = [0, 0, 0, 0]
    multip = 18
    for i in range(0, len(linesP)):
        line = linesP[i][0]

        print(line)

        if line[1] > lineB[1]:
            if abs(line[0] - line[2]) > abs(line[1] - line[3]) * multip:
                lineB = line
        if line[1] < lineU[1]:
            if abs(line[0] - line[2]) > abs(line[1] - line[3]) * multip:
                lineU = line

        if line[0] > lineR[0]:
            if abs(line[0] - line[2]) < abs(line[1] - line[3]) * multip:
                lineR = line
        if line[0] < lineL[0]:
            if abs(line[0] - line[2]) < abs(line[1] - line[3]) * multip:
                lineL = line


    #slaice
    top_left_x = min([lineB[0], lineU[0], lineR[0], lineL[0]])
    top_left_y = min([lineB[1], lineU[1], lineR[1], lineL[1]])
    bot_right_x = max([lineB[0], lineU[0], lineR[0], lineL[0]])
    bot_right_y = max([lineB[1], lineU[1], lineR[1], lineL[1]])

    slaicedImage = dst[top_left_y:bot_right_y, top_left_x:bot_right_x]
    #cv.imshow('slaicedImage', slaicedImage)

    sliceMore = 20
    sliceMoreY = 20
    return [top_left_y + sliceMore + sliceMoreY, bot_right_y - sliceMore - sliceMoreY, top_left_x + sliceMore, bot_right_x - sliceMore]

def Hough (filePath, rotationDegrees, slices):

    global imW
    global imH

    src = cv.imread(cv.samples.findFile(filePath), cv.IMREAD_GRAYSCALE)
    #src = cv.cvtColor(filePath, cv.COLOR_BGR2GRAY)
    rotated = imutils.rotate_bound(src, rotationDegrees)
    slaicedImage = rotated[slices[0]:slices[1],slices[2]:slices[3]]
    src = slaicedImage
    imW = src.shape[1]
    imH = src.shape[0]

    # 489, 652
    dst = cv.Canny(src, 50, 200, None, 3)
    cdst = cv.cvtColor(dst, cv.COLOR_GRAY2BGR)
    #cdst = np.copy(dst)
    cdstP = np.copy(cdst)


    cv.line(cdst, (0, 0), (10, 10), (0, 255, 0), 3, cv.LINE_AA) #testlline


    #for l in [lineB, lineU, lineR, lineL]:
    #    print(l)
    #    cv.line(cdstP, (l[0], l[1]), (l[2], l[3]), (0, 0, 255), 3, cv.LINE_AA)

    plotList = []

    linesP = cv.HoughLinesP(dst, 1, np.pi / 180, 50, None, 50, 10)
    if linesP is not None:
        for i in range(0, len(linesP)):
            l = linesP[i][0]
            cv.line(cdstP, (l[0], l[1]), (l[2], l[3]), (0, 0, 255), 3, cv.LINE_AA)
            plotList.append(l[1])
            plotList.append(l[3])
    #for i in range(700):
    #    plotList.append(i)
    #plt.plot(plotList)
    plotList.sort()
    #td = pd.value_counts(plotList).sort_index()
    #pd.value_counts(plotList).sort_index().plot(kind="bar")
    #plt.show()

    #print(pd.value_counts(plotList).sort_index())
    leng = len(plotList)

    partLines = [[],[],[],[],[],[],[],[]]
    tempI = 0
    part = 0
    for i in plotList:

        if tempI > (leng / 8) * (part + 1):
            part += 1
        partLines[part].append(i)
        tempI += 1

    averages = []
    for i in range (0, imH, int(imH/8)):
        averages.append(i)
    # for partLine in partLines:
    #     averages.append(average(partLine))
    #
    # cutParts = []
    #
    # it = 0
    # last = 0
    #
    # for i in averages:
    #     if it == 0:
    #         last = i
    #     else:
    #         cutParts.append(average([last, i]))
    #         last = i
    #     it +=1


    for i in averages:
        cv.line(cdst, (0, int(i)), (500, int(i)), (0, 0, 255), 3, cv.LINE_AA)
        #plotList.append(l[1])
        #plotList.append(l[3])
    #

    #cv.imshow("Source", src)
    #cv.imshow("Detected Lines (in red) - Standard Hough Line Transform", cdst)
    #cv.imshow("Detected Lines (in red) - Probabilistic Line Transform", cdstP)
    #cv.waitKey(0)
    #cv.destroyAllWindows()

    ite = 0
    slaicedImages = []
    lastAver = 0
    slicePlus = 5
    for i in averages:
        if ite == 0:
            lastAver = i
        else:
            if ite == 1:
                slaicedImages.append(src[lastAver:i + slicePlus, 0:imW])
            elif ite == 8:
                slaicedImages.append(src[lastAver - slicePlus:i + slicePlus, 0:imW])
            else:
                slaicedImages.append(src[lastAver - slicePlus:i, 0:imW])
            lastAver = i
        ite += 1

    #
    # for i in slaicedImages:
    #     cv.imshow("Iterator", i)
    #     cv.waitKey(0)
    #     cv.destroyAllWindows()
    return slaicedImages




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



    # cv.imshow('frame', frame)
    # cv.imshow('mask', mask)
    # cv.imshow('res', res)

    return res

def Rotate(image_to_rotate):
    src = cv.cvtColor(image_to_rotate, cv.COLOR_BGR2GRAY)
    dst = cv.Canny(src, 50, 200, None, 3)
    cdst = cv.cvtColor(dst, cv.COLOR_GRAY2BGR)
    # cdst = np.copy(dst)
    cdstP = np.copy(cdst)

    lines = cv.HoughLines(dst, 1, np.pi / 180, 150, None, 0, 0)

    # print(lines)

    lineL = [(0, 0), (0, 0)]
    lineR = [(1000, 0), (1000, 0)]
    lineB = []
    lineU = []
    hasP = False
    hasPCount = 0

    a_array = []

    cv.line(cdst, (0, 0), (10, 10), (0, 255, 0), 3, cv.LINE_AA) #testlline
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
            a = getA(pt1[0], pt1[1], pt2[0], pt2[1])
            b = getB(pt1[0], pt1[1], pt2[0], pt2[1])
            a_array.append(a)

            print(a)
            # if a > 1:
            cv.line(cdst, pt1, pt2, (0, 0, 255), 3, cv.LINE_AA)
    a_med = median(a_array)

    rotationRadians = math.atan(a);
    rad2deg = 180 / math.pi;
    rotationDegrees = - rotationRadians * rad2deg;

    rotated = imutils.rotate_bound(image_to_rotate, rotationDegrees)
    #cv.imshow("Rotated (Correct)", rotated)
    return rotationDegrees

def HoughOne (image):

    global imW
    global imH

    src = image#cv.cvtColor(image, cv.COLOR_BGR2GRAY)
    # src = filePath

    dst = cv.Canny(src, 50, 200, None, 3)
    cdst = cv.cvtColor(dst, cv.COLOR_GRAY2BGR)
    cdstP = np.copy(cdst)

    imW = src.shape[1]
    imH = src.shape[0]

    plotList = []

    linesP = cv.HoughLinesP(dst, 1, np.pi / 180, 50, None, 50, 10)
    # if linesP is not None:
    #     for i in range(0, len(linesP)):
    #         l = linesP[i][0]
    #         cv.line(cdstP, (l[0], l[1]), (l[2], l[3]), (0, 0, 255), 3, cv.LINE_AA)

    print('Kappa')
    print(linesP)

    lineU = imH
    lineB = 0
    for i in linesP:
        line = i[0]
        if line[1] > lineB:
            lineB = line[1]
        if line[1] < lineU:
            lineU = line[1]

    # cv.line(cdstP, (0, lineU), (500, lineU), (0, 0, 255), 3, cv.LINE_AA)
    # cv.line(cdstP, (0, lineB), (500, lineB), (0, 0, 255), 3, cv.LINE_AA)
    # cv.imshow("Detected Lines (in red) - Probabilistic Line Transform", cdstP)
    # cv.waitKey(0)
    # cv.destroyAllWindows()
    return [image, lineU, lineB]

for path in planes:

    #image = Bright(path)

    #Hough(image)

    image = Bright(path)
    image = White(image)
    angleToRotate = Rotate(image)
    slice = Slice(image, angleToRotate)
    slaicedImages = Hough(path, angleToRotate, slice)

    for i in slaicedImages:
        HoughOne(i) # zwraca liste z [Obrazek, wsp y górnej lini, wsp x dolnej lini]
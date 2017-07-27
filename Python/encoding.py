# -*- coding: utf-8 -*-
"""
Created on Wed Jul 26 09:36:36 2017

@author: ggarcia
"""

import cv2

import base64
import numpy as np



#>>> encoded = base64.b64encode('data to be encoded')
#>>> encoded
#'ZGF0YSB0byBiZSBlbmNvZGVk'
#>>> data = base64.b64decode(encoded)
#>>> data
#'data to be encoded'

img = cv2.imread('C:\code\Garage_iPal\Pictures\Test\\empty_area.jpg')
encoded_img = base64.b64encode(img)


decoded_bytes = base64.b64decode(encoded_img)

nparray =  np.frombuffer(decoded_bytes)


#(720, 1280, 3)
decoded_img = decoded_bytes


one_dimention = np.array([0, 1, 2, 3, 4, 5, 6, 7, 8])

two_dimention = one_dimention.reshape((3,3))

three_dimention = 
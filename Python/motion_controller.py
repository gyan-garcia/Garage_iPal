# -*- coding: utf-8 -*-
"""
Created on Tue Jul 25 09:40:26 2017

@author: ggarcia
"""

import cv2
import sys

sys.path.append("C:\code\Garage_iPal\Python")

from people_detection import detect_humans_in_image
from distance_calculator import get_distance_from_object

mask = cv2.imread('C:\code\Garage_iPal\Pictures\Test\\mask.jpg') #as_grey=True
img = cv2.imread('C:\code\Garage_iPal\Pictures\Test\\IMG_2017.07.25_12.13.27.jpg')

distance_inches = get_distance_from_object(img, mask)

# To convert to millimeters 25.4 * distance_inches

                
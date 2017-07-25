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
img = cv2.imread('C:\code\Garage_iPal\Pictures\Test\\empty_area.jpg')

distance_inches = get_distance_from_object(img, mask)

#-1 inches means there is nothing on the robot area. 
print ("distance inches: %s, distance milimeters %s" %(distance_inches, distance_inches * 25.4))

# To convert to millimeters 25.4 * distance_inches
# TODO: To figure out a way to konw if the user is to close to be detected efectively



                
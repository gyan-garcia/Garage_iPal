# -*- coding: utf-8 -*-
"""
Created on Tue Jul 25 09:40:26 2017

@author: ggarcia
"""

import numpy as np
import cv2
import os
import sys
from skimage import io

#sys.path.append("C:\code\Garage_iPal\Python")

from people_detection import detect_humans_in_image
from distance_calculator import get_closest_activated_y_coodinate, get_top_of_robot_area_y_coordinate

img = cv2.imread('C:\code\\Garage_iPal\\Pictures\\test.jpg')
img_with_humans_highlighted = detect_humans_in_image(img)

# Load the best mask for this location:
mask = io.imread('C:\code\\Garage_iPal\\Pictures\\mask2.jpg') #as_grey=True

# Turns this into this 
#img_with_humans_highlighted = img_with_humans_highlighted / 255
mask = mask / 255
                
# Apply the mask
image_with_mask = img_with_humans_highlighted * mask

# From here we need to calculate how many inches away we are from the object.
top_of_robot_area_y = get_top_of_robot_area_y_coordinate(image_with_mask)

picture_height = 720
picture_weight = 1280

# This is necessary to translate pixels to physical measurements, but we need
# to know where the robot is located.
robot_area_on_pixels = picture_height - top_of_robot_area_y

# For starters let's assume the robot is standing on the edge and the center
# of the mat.

# 180 is the to height of the two mats, 19 is the area of the robot.
pixels_to_inch = robot_area_on_pixels / (180 - 19)


closest_y = get_closest_activated_y_coodinate(image_with_mask)

distance_inches = (picture_height - closest_y) / pixels_to_inch



#I need to calculate the distance to the top of the area, so we can triangulate
# the distance.

# Everything green in the area is something on the area.

#io.imsave('C:\code\\Garage_iPal\\Pictures\\wa.jpg', image_with_mask)
#cv2.imwrite('C:\code\\Garage_iPal\\Pictures\\test_res.jpg', image_with_mask)
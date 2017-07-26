# -*- coding: utf-8 -*-
"""
Created on Mon Jul 24 18:21:54 2017

@author: ggarcia
"""

import matplotlib.pyplot as plt
from skimage import data
from skimage.filters import threshold_otsu
from skimage import io
import sys
import cv2

from app.logic.people_detection import detect_humans_in_image

def get_closest_activated_y_coodinate(image):    
    for y in range(image.shape[0] - 1, 0, -1):
        for x in range(image.shape[1] - 1, 0, -1):
            if image[y][x][0] == 0 and image[y][x][1] == 255 and image[y][x][2] == 0:
                return y
    return -1 # Not found

def get_top_of_robot_area_y_coordinate(image):
    for y in range(image.shape[0]):
        for x in range(image.shape[1]):
            if image[y][x][0] != 0 and image[y][x][1] != 255 and image[y][x][2] != 0:
                return y
    
    
def get_distance_from_object(img, mask):
    img_with_humans_highlighted = detect_humans_in_image(img)
    mask = mask / 255
                    
    # Apply the mask
    image_with_mask = img_with_humans_highlighted * mask
    cv2.imwrite('C:\code\\Garage_iPal\\Pictures\\Test\\temp.jpg', image_with_mask)
    
    # From here we need to calculate how many inches away we are from the object.
    top_of_robot_area_y = get_top_of_robot_area_y_coordinate(image_with_mask)
    
    picture_height = 720
    picture_weight = 1280
    
    # This is necessary to translate pixels to physical measurements, but we need
    # to know where the robot is located. 
    # For now let's asume the robot is located at the Edge of the area looking inside
    # The board measures 180 inches by 44 inches, but 88 inches are outside of the robot
    # view. 
    robot_area_on_pixels = picture_height - top_of_robot_area_y
    
    
    # For starters let's assume the robot is standing on the edge and the center
    # of the mat.
    
    # 180 - 88 inches the robot can not see.
    pixels_to_inch = robot_area_on_pixels / (92)
    
    closest_y = get_closest_activated_y_coodinate(image_with_mask)
    
    if closest_y == -1:
        return -1 # This means that not object was detected on the area
    
    # Let's add the 88 inches that the robot is not seeing.
    distance_inches = ((picture_height - closest_y) / pixels_to_inch) + 88
    
    # TODO: Adjust for the robot perspective. The closer it gets to the border the more
    # unaccurate the calculations are.               
                          
    return distance_inches


#image = io.imread('C:\fcode\\hackathon\\pics\\people_on_area.jpg')
#io.imshow(image)

#closest_green_y = get_closest_activated_y_coodinate(image)
    
#io.imshow(image)

#At this point we know how many pixels away are we from the target, we just
# need to translate this to distance.

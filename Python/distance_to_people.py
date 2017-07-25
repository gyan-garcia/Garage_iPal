# -*- coding: utf-8 -*-
"""
Created on Mon Jul 24 18:21:54 2017

@author: ggarcia
"""

import matplotlib.pyplot as plt
from skimage import data
from skimage.filters import threshold_otsu
from skimage import io


def get_closest_activated_y_coodinate(image):    
    for y in range(image.shape[0] - 1, 0, -1):
        for x in range(image.shape[1] - 1, 0, -1):
            if image[y][x][0] == 0 and image[y][x][1] == 255 and image[y][x][2] == 0:
                #image[y][x][1] = 0
                #image[y][x][0] = 255
                return y


image = io.imread('C:\code\\hackathon\\pics\\people_on_area.jpg')
io.imshow(image)

closest_green_y = get_closest_activated_y_coodinate(image)
    
io.imshow(image)


#At this point we know how many pixels away are we from the target, we just
# need to translate this to distance.

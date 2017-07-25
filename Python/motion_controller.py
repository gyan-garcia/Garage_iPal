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
from distance_to_people import get_closest_activated_y_coodinate

img = cv2.imread('C:\code\\Garage_iPal\\Pictures\\test.jpg')
img_with_humans_highlighted = detect_humans_in_image(img)

# Load the best mask for this location:
mask = io.imread('C:\code\\Garage_iPal\\Pictures\\mask2.jpg') #as_grey=True

# Turns this into this 
#img_with_humans_highlighted = img_with_humans_highlighted / 255
mask = mask / 255
                
# Apply the mask
image_with_mask = img_with_humans_highlighted * mask

# From here we need to calculate how meters away we are.
closest_y = get_closest_activated_y_coodinate(image_with_mask)


# Everything green in the area is something on the area.

#io.imsave('C:\code\\Garage_iPal\\Pictures\\wa.jpg', image_with_mask)
#cv2.imwrite('C:\code\\Garage_iPal\\Pictures\\test_res.jpg', image_with_mask)
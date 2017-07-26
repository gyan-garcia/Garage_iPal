# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""
import matplotlib.pyplot as plt
from skimage import data
from skimage.filters import threshold_otsu

# How to thresholding a grayscele image using scikit-image, this is good enough to remove all the noise.
# http://scikit-image.org/docs/stable/auto_examples/segmentation/plot_thresholding.html#sphx-glr-auto-examples-segmentation-plot-thresholding-py
# How do we remove everything else beside the board? We can not use a mask since the image
# will be diferent at certain points.


from skimage import io
image = io.imread('C:\code\Garage_iPal\Pictures\Test\\IMG_2017.07.25_12.12.51.jpg', as_grey=True)

thresh = threshold_otsu(image)
binary = image > thresh

fig, axes = plt.subplots(ncols=3, figsize=(28, 12.5))
ax = axes.ravel()
ax[0] = plt.subplot(1, 3, 1, adjustable='box-forced')
ax[1] = plt.subplot(1, 3, 2)
ax[2] = plt.subplot(1, 3, 3, sharex=ax[0], sharey=ax[0], adjustable='box-forced')

ax[0].imshow(image, cmap=plt.cm.gray)
ax[0].set_title('Original')
ax[0].axis('off')

ax[1].hist(image.ravel(), bins=256)
ax[1].set_title('Histogram')
ax[1].axvline(thresh, color='r')

ax[2].imshow(binary, cmap=plt.cm.gray)
ax[2].set_title('Thresholded')
ax[2].axis('off')

plt.show()
#'true' if True else 'false'
mask = binary * 255

io.imsave('C:\code\Garage_iPal\Pictures\Test\\mask.jpg', mask)

# One basic algorithm I think (it requires a lot of data though).
# 1. The Robot knows all the time where it is located, so it knows how a picture taken from tha perspective
# should look like. So it can easily take a pictute where is located, and applid a well know mask to know if there
# is something knew. This will require a lot of masks but should work.
# For the masking I can look into here:
# http://scikit-image.org/docs/stable/auto_examples/numpy_operations/plot_camera_numpy.html#sphx-glr-auto-examples-numpy-operations-plot-camera-numpy-py

# What would be killer would be to generate the mask on run time?


import numpy as np
from skimage import measure

# Find contours at a constant value of 0.8
contours = measure.find_contours(image, 0.3)

for n, contour in enumerate(contours):
    plt.plot(contour[:, 1], contour[:, 0], linewidth=2)
    
    
#
#

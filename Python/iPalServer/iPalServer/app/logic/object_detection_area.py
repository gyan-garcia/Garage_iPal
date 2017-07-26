# -*- coding: utf-8 -*-
"""
Created on Mon Jul 24 13:25:34 2017

@author: ggarcia
"""

import matplotlib.pyplot as plt
from skimage import data
from skimage.filters import threshold_otsu
from skimage import io


# INPUT IMAGE IS AN IMAGE ALREADY PREPROCESSED WITH OPENCV HOG Default peoPle detector (people_detection.py file), 
# with all the detections covered by a green box. After the mask is applied, anything left in green is actually and
# object in the area.

image = io.imread('C:\code\\hackathon\\pics\\test_res.jpg')
mask = io.imread('C:\code\\hackathon\\pics\\mask2.jpg') #as_grey=True

# Turns this into this 
image = image / 255
mask = mask / 255
                
#io.imshow(mask)
#io.imshow(image)

#io.imsave('C:\code\\hackathon\\pics\\test_result.jpg', image_applied)
#width = image.shape[0]
#height = image.shape[1]
#mask = mask[0:width, 0:height, 0:3]

#mask = mask / 255
image_with_mask = image * mask

# At this point anything green means there is something on the board. 
# Calculate the distance now from the robot to the square.
# !!!!!!



io.imshow(image_with_mask)
io.imsave('C:\code\\hackathon\\pics\\wa.jpg', image_with_mask)


thresh = threshold_otsu(image_with_mask)
binary = image_with_mask > thresh
clean_image = binary * 1.0
io.imshow(clean_image)

io.imsave('C:\code\\hackathon\\pics\\wa.jpg', clean_image)

# At this point I can try another function of scikit-learn to try to
# find the position on the object.






from math import sqrt
from skimage import data
from skimage.feature import blob_dog, blob_log, blob_doh
from skimage.color import rgb2gray

import matplotlib.pyplot as plt


# Here we can either use clean_image or image_with_mask
image_gray = clean_image

# This is the blob detection code as show in here:
# http://scikit-image.org/docs/dev/auto_examples/features_detection/plot_blob.html#sphx-glr-auto-examples-features-detection-plot-blob-py

blobs_log = blob_log(image_gray, max_sigma=30, num_sigma=10, threshold=.1)

# Compute radii in the 3rd column.
blobs_log[:, 2] = blobs_log[:, 2] * sqrt(2)

blobs_dog = blob_dog(image_gray, max_sigma=30, threshold=.1)
blobs_dog[:, 2] = blobs_dog[:, 2] * sqrt(2)

blobs_doh = blob_doh(image_gray, max_sigma=30, threshold=.01)

blobs_list = [blobs_log, blobs_dog, blobs_doh]
colors = ['yellow', 'lime', 'red']
titles = ['Laplacian of Gaussian', 'Difference of Gaussian',
          'Determinant of Hessian']
sequence = zip(blobs_list, colors, titles)

fig, axes = plt.subplots(1, 3, figsize=(29, 9), sharex=True, sharey=True,
                         subplot_kw={'adjustable': 'box-forced'})
ax = axes.ravel()

for idx, (blobs, color, title) in enumerate(sequence):
    ax[idx].set_title(title)
    ax[idx].imshow(image, interpolation='nearest')
    for blob in blobs:
        y, x, r = blob
        c = plt.Circle((x, y), r, color=color, linewidth=2, fill=False)
        ax[idx].add_patch(c)
    ax[idx].set_axis_off()

plt.tight_layout()
plt.show()
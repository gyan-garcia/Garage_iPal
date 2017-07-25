
import numpy as np
import cv2

# THIS FILE FINDS PEOPLE ON A NORMAL IMAGE AND SETS GREEN RECTANGLE ON THE AREA.


def inside(r, q):
    rx, ry, rw, rh = r
    qx, qy, qw, qh = q
    return rx > qx and ry > qy and rx + rw < qx + qw and ry + rh < qy + qh

#cv2.rectangle(img, (x+pad_w, y+pad_h), (x+w-pad_w, y+h-pad_h), (0, 255, 0), thickness)
	
def draw_detections(img, rects, thickness = 1):
    for x, y, w, h in rects:
        # the HOG detector returns slightly larger rectangles than the real objects.
        # so we slightly shrink the rectangles to get a nicer output.
        pad_w, pad_h = int(0.15*w), int(0.05*h)
        cv2.rectangle(img, (x+pad_w, y+pad_h), (x+w-pad_w, y+h-pad_h), (0, 255, 0), -1)
 			


img = cv2.imread('C:\code\\hackathon\\pics\\test.jpg')

hog = cv2.HOGDescriptor()
hog.setSVMDetector( cv2.HOGDescriptor_getDefaultPeopleDetector() )
found, w = hog.detectMultiScale(img, winStride=(8,8), padding=(32,32), scale=1.05)

found_filtered = []
for ri, r in enumerate(found):
    for qi, q in enumerate(found):
        if ri != qi and inside(r, q):
            break
        else:
            found_filtered.append(r)
        
draw_detections(img, found)
cv2.imwrite('C:\code\\hackathon\\pics\\test_res.jpg', img)

draw_detections(img, found_filtered, 3)
cv2.imwrite('C:\code\\hackathon\\pics\\test_res.jpg', img)


print('%d (%d) found' % (len(found_filtered), len(found)))
cv2.imshow('img', img)
cv2.destroyAllWindows()
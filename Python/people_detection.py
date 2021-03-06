
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
        #pad_w, pad_h = int(0.15*w), int(0.05*h)
        pad_w, pad_h = int(0.30*w), int(0.15*h)
        cv2.rectangle(img, (x+pad_w, y+pad_h), (x+w-pad_w, y+h-pad_h), (0, 255, 0), thickness)

def detect_humans_in_image(img):
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
            
    draw_detections(img, found, -1) # -1 means drawing a solid square   
    return img


#img = cv2.imread('C:\code\\Garage_iPal\\Pictures\\test.jpg')
#new_img = detect_humans_in_image(img)
#cv2.imwrite('C:\code\\Garage_iPal\\Pictures\\test_res.jpg', new_img)


#print('%d (%d) found' % (len(found_filtered), len(found)))
#cv2.imshow('img', img)
#cv2.destroyAllWindows()

import qi
import argparse
import sys
import math
from naoqi import ALProxy

robotIP = "192.168.0.100"
tts = ALProxy("ALTextToSpeech", robotIP, 9559)
tts.say("Hello, world!")


tts.say("1. A robot may not injure a human being or, through inaction, allow a human being to come to harm.")
tts.say("2. A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.")
tts.say("3. A robot must protect its own existence as long as such protection does not conflict with the First or Second Laws.")

tts.post.say("3. A robot must protect its own existence as long as such protection does not conflict with the First or Second Laws.")
animation.post.run("animations/Stand/Gestures/Explain_1")


posture = ALProxy("ALRobotPosture", robotIP, 9559)
posture.goToPosture("StandInit", 0.5)

motion = ALProxy("ALMotion", robotIP, 9559)
motion.moveInit()
motion.setStiffnesses("Body", 1.0)
motion.setWalkArmsEnabled(True, True)


#####################
## FOOT CONTACT PROTECTION   
#####################
motionProxy.setMotionConfig([["ENABLE_FOOT_CONTACT_PROTECTION", True]])


X = 0.3
Y = 0.1
Theta = math.pi/2.0
motion.moveTo(X, Y, Theta)

motion.moveTo(0.5, 0, 0)


motion.moveTo(2, 0, 0)
tts.say("Hi Rolly, how are you doing?")
tts.say("I am feeling great")
animation.run("animations/Stand/Emotions/Positive/Hysterical_1")


motion.closeHand('RHand')
motion.openHand('RHand')



photoCapture = ALProxy("ALPhotoCapture", robotIP, 9559)
photoCapture.takePicture("C:\code\Garage_iPal\Pictures\Test", "pepper.jpg", True)



# http://doc.aldebaran.com/2-4/naoqi/motion/alanimationplayer-advanced.html#animationplayer-list-behaviors-pepper
animation = ALProxy("ALAnimationPlayer", robotIP, 9559)
animation.run("animations/Stand/Gestures/CalmDown_1")




#
names  = ["HeadYaw", "HeadPitch"]
angles  = [0.2, -0.2]
fractionMaxSpeed  = 0.2
motion.setAngles(names, angles, fractionMaxSpeed)

time.sleep(3.0)
motion.setStiffnesses("Head", 0.0)

#




AL::ALRobotPostureProxy (robotIp);
  robotPosture.goToPosture("Stand", 0.5f);



X = -0.5  #backward
Y = 0.0
Theta = 0.0
Frequency =0.0 # low speed
motionProxy.setWalkTargetVelocity(X, Y, Theta, Frequency)

time.sleep(4.0)


X = 0.3
Y = 0.1
Theta = math.pi/2.0
motionProxy.post.moveTo(X, Y, Theta)
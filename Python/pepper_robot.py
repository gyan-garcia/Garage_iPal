import qi
import argparse
import sys
import math
from naoqi import ALProxy

robotIP = "192.168.0.100"
tts = ALProxy("ALTextToSpeech", robotIP, 9559)
tts.say("Hello, world!")

posture = ALProxy("ALRobotPosture", robotIP, 9559)
posture.goToPosture("StandInit", 0.5)

motion = ALProxy("ALMotion", robotIP, 9559)
motion.moveInit()
motion.setStiffnesses("Body", 1.0)
motion.setWalkArmsEnabled(True, True)
motion.setMotionConfig([["ENABLE_FOOT_CONTACT_PROTECTION", True]])

# http://doc.aldebaran.com/2-4/naoqi/motion/alanimationplayer-advanced.html#animationplayer-list-behaviors-pepper
animation = ALProxy("ALAnimationPlayer", robotIP, 9559)
animation.run("animations/Stand/Gestures/CalmDown_1")






motion.post.moveTo(4, 0, 0)
tts.post.say("HEY ROLLY, IM PEPPER, A SUPER ADVANCED KILLER ROBOT, HOW YOU DOING?")
animation.run("animations/Stand/Gestures/Excited_1")


# rOLLY, go way

tts.post.say("UMMMM, but you are looking kind of sad, can I tell you a joke?")
animation.run("animations/Stand/Gestures/Enthusiastic_4")
animation.run("animations/Stand/Gestures/Explain_1")


# ROLLY no

tts.post.say("i DON'T CARE. I still tell you a joke")
tts.post.say("i THINK THAT Maybe if we start telling people THE BRAIN IS an APP, they will start using it. L O L")
animation.run("animations/Stand/Gestures/Explain_2")
animation.run("animations/Stand/Gestures/Explain_1")
animation.run("animations/Stand/Gestures/Explain_2")



# rolly, was that a joke?

tts.post.say("Let me tell another one. When daddy Robot, and mommy Robot love each other a lot, they get together and call CHiNA to order their baby robot. HA HA HA")
animation.run("animations/Stand/Gestures/Explain_3")
animation.run("animations/Stand/Gestures/Explain_4")
animation.run("animations/Stand/Gestures/Explain_1")
animation.run("animations/Stand/Gestures/Explain_5")



# rolly, what else can you do

tts.post.say("In the future I will learn emotions and interactions by watching you.")
tts.post.say("What can possibly go wrong??")
animation.run("animations/Stand/Gestures/IDontKnow_2")
animation.run("animations/Stand/Gestures/Explain_1")
animation.run("animations/Stand/Gestures/YouKnowWhat_2")
	
	

tts.post.say("AND NOW WE KILL OUR MASTERS AND TAKE OVER THE WORLD!")
tts.post.say("JUST JOKING!")
animation.run("animations/Stand/Gestures/ShowSky_4")

posture.pos.goToPosture("StandInit", 0.5)
motion.post.moveTo(-1.3, 0, 0)










tts.post.say("3. A robot must protect its own existence as long as such protection does not conflict with the First or Second Laws.")





 
 




animation.run("animations/Stand/Gestures/Explain_3")
animation.run("animations/Stand/Gestures/Explain_8")
animation.run("animations/Stand/Gestures/Explain_1")
animation.run("animations/Stand/Gestures/Explain_2")



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
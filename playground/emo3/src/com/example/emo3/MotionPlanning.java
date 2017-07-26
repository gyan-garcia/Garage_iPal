package com.example.emo3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.robot.motion.RobotMotion;

public class MotionPlanning{
    private int optimalX; //start as the center of the image
    private int optimalY;
    private double forwardK;
    private double turnK;

    //For a 720 x 480 image
    public MotionPlanning(int imageWidth, int imageHeight) {
        this.optimalX = (int) (imageWidth/2.0);
        this.optimalY =  (int) (imageHeight*(3/4.0));
    }

    public void moveToPerson(RobotMotion mRobotMotion) {
        //check if the robot can move
        walkToPoint(mRobotMotion);
        moveToPoint(mRobotMotion);
    }
    /** Given a pixel value in the Y direction, moves the robot 
    **/
    private void walkToPoint(RobotMotion RobotMotion, int y) {
        int distanceToWalk = (int) forwardK * (optimalY - y);
        mRobotMotion.walk(distanceToWalk);
    }
    private void turnToPoint(RobotMotion RobotMotion, int x) {
        int angleToTurn = (int) turnK * (optimalX - x);
        mRobotMotion.turn(angleToTurn);
    }
}

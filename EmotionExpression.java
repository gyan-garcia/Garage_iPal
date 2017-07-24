import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.robot.motion.RobotMotion;

public class EmotionExpression{
    private int emojiDuration = 5;
    private int actionDuration = 10;
    
    public EmotionExpression() {
    }

    public void showEmoji(RobotMotion mRobotMotion, string emotion){
        int emoji = (int) RobotMotion.Emoji.SMILE;
        switch(emotion) {
            case "Contempt":
                emoji = RobotMotion.Emoji.ANGRY;
            case "Disgust":
                emoji = RobotMotion.Emoji.GRIMACE;
            case "Fear":
                emoji = RobotMotion.Emoji.FROWN;
            case "Happiness":
                emoji = RobotMotion.Emoji.SMILE;
            case "Neutral":
                emoji = RobotMotion.Emoji.CLEAR;
            case "Sadness":
                emoji = RobotMotion.Emoji.SAD;
            case "Surprise":
                emoji = RobotMotion.Emoji.SURPRISE;

        mRobotMotion.emoji(emoji,this.emojiDuration,0);
    }

    public void doGesture(RobotMotion mRobotMotion, string emotion) {
        int action = (int) RobotMotion.Action.SMILE; 
        switch(emotion) {
            case "Contempt":
                action = (int) RobotMotion.Action.ANGRY;
            case "Disgust":
                action = (int) RobotMotion.Action.GRIMACE;
            case "Fear":
                action = (int) RobotMotion.Action.FROWN;
            case "Happiness":
                action = (int) RobotMotion.Action.SMILE;
            case "Neutral":
                action = (int) RobotMotion.Action.CLEAR;
            case "Sadness":
                action = (int) RobotMotion.Action.SAD;
            case "Surprise":
                action = (int) RobotMotion.Action.SURPRISE;
        mRobotMotion.doAction(action,0,this.actionDuration);
    }

    public void showEmotion(RobotMotion mRobotMotion, string emotion){
        this.showEmoji(mRobotMotion, emotion);
        this.doGesture(mRobotMotion, emotion);
    }

}

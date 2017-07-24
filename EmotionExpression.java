import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.robot.motion.RobotMotion;

public class EmotionExpression{
    private int emojiDuration = 5;
    private int actionDuration = 10;
    private RobotMotion mRobotMotion;
    
    public EmotionExpression(RobotMotion rm ) {
        this.mRobotMotion = rm;
    }

    public void showEmoji(string emotion){
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

        this.mRobotMotion.emoji(emoji,this.emojiDuration,0);
    }

    public void doGesture(string emotion) {
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
        this.mRobotMotion.doAction(action,0,this.actionDuration);
    }

    public void showEmotion(string emotion){
        this.showEmoji(emotion);
        this.doGesture(emotion);
    }

}

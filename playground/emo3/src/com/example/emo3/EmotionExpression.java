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

    public void showEmoji(String emotion){
        int emoji = (int) RobotMotion.Emoji.SMILE;
        if (emotion.equals("Contempt")) {
        	emoji = (int) RobotMotion.Emoji.ANGRY;
        }
        if (emotion.equals("Fear")) {
        	emoji = (int) RobotMotion.Emoji.FROWN;
        }
        if (emotion.equals("Happiness")) {
        	emoji = (int) RobotMotion.Emoji.SMILE;
        }
        if (emotion.equals("Neutral")) {
        	emoji = (int) RobotMotion.Emoji.CLEAR;
        }
        if (emotion.equals("Disgust")) {
        	emoji = (int) RobotMotion.Emoji.GRIMACE;
        }
        if (emotion.equals("Sadness")) {
        	emoji = (int) RobotMotion.Emoji.SAD;
        }
        if (emotion.equals("Surprise")) {
        	emoji = (int) RobotMotion.Emoji.SURPRISE;
        }
        if (emotion.equals("Anger")) {
        	emoji = (int) RobotMotion.Emoji.ANGRY;
        }
        

        this.mRobotMotion.emoji(emoji,this.emojiDuration,0);
    }

    public void doGesture(String emotion) {
        int action = (int) RobotMotion.Action.SMILE; 
        
        if (emotion.equals("Contempt")) {
        	action = (int) RobotMotion.Action.ANGRY;
        }
        if (emotion.equals("Fear")) {
        	action = (int) RobotMotion.Action.FROWN;
        }
        if (emotion.equals("Happiness")) {
        	action = (int) RobotMotion.Action.SMILE;
        }
        if (emotion.equals("Neutral")) {
        	action = (int) RobotMotion.Action.CLEAR;
        }
        if (emotion.equals("Disgust")) {
        	action = (int) RobotMotion.Action.GRIMACE;
        }
        if (emotion.equals("Sadness")) {
        	action = (int) RobotMotion.Action.SAD;
        }
        if (emotion.equals("Surprise")) {
        	action = (int) RobotMotion.Action.SURPRISE;
        }
        if (emotion.equals("Anger")) {
        	action = (int) RobotMotion.Action.FOLDARM;
        }
       
                
        this.mRobotMotion.doAction(action,0,this.actionDuration);
    }

    public void showEmotion(String emotion){
        this.showEmoji(emotion);
        this.doGesture(emotion);
    }

}

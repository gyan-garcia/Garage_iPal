import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.robot.motion.RobotMotion;
import MotionPlanner;
import EmotionExpression;
public class MainActivity extends Activity {
    private RobotMotion mRobotMotion; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mRobotMotion=new RobotMotion(); 
        mRobotMotion.setListener(new RobotMotion.Listener() {
            
            @Override
            public void onCompleted(int session_id, int result, int errorcode) {
                // TODO Auto-generated method stub
                
            }
        });
       EmotionExpression expresser = new EmotionExpression();
       expresser.showEmotion("Happiness");
       mRobotMotion.doAction(RobotMotion.Action.CHEER,0,1000);
    }
     
    
}
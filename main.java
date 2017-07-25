import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.robot.motion.RobotMotion;
import MotionPlanner;
import EmotionExpression;
public class MainActivity extends Activity {
    private RobotMotion mRobotMotion; 
    private EmotionExpression expresser;
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
       this.expresser = new EmotionExpression();
       //Create camera event and listener
       //in the callback for the camera, call the server. that should run instead of the while loop
       while (true) {
           cameraCallback();
       }
 
    }
    protected void cameraCallback() {
        //Get camera image from arguments
        boolean isInBox = isPersonInBox();    
        if (isInBox) {
            //Given bounded box image, call the cortana emotion API on it
            string emotion; //Call microsoft cognitive services API
            emotion = "Happiness";
            this.expresser.showEmotion(emotion);
        }
    }
    
    protected boolean isPersonInBox(){
        return true;
    }

     
}


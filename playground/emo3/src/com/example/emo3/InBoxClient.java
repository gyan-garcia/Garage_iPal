import java.io.*;
import org.json.*;
import java.net.*;

public class InBoxClient{
    public inBoxResponse inBox() {
         
        String hostName = "localhost";
        int portNumber = 8000;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse res = httpClient.execute(req); 
        JSONObject obj = new JSONObject(res);
        String inBox = obj.getString("inBox");
        double dist = obj.getString("dist");
        return InBoxResponse(inBox, dist);
    }

}

public class inBoxResponse{
    boolean inBox;
    double dist;
    public inBoxResponse(bool inputInBox, int inputDist) {
        this.inBox = inputInBox;
        this.dist = inputDist; 
    }
}

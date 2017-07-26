package com.example.emo3;


import java.io.*;

import org.json.*;

import android.graphics.Bitmap;
import android.util.Base64;

import java.net.*;

public class ResponseFromServer{
	
    public InBoxResponse callAPI(byte[] b) throws IOException, JSONException {
         
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		//bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
		
		String encodedImage = Base64.encodeToString(b , Base64.DEFAULT);
		
        HttpURLConnection urlConnection = null;
        URL url = new URL("IP address");
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        JSONObject obj = new JSONObject(jsonString);
        
        boolean inBox = true;
        String inBoxString = obj.getString("inBox");
        if (inBoxString.equals("false")) {
        	inBox = false;
        } 
        Double dist = new Double(obj.getString("dist"));
        String emotion = obj.getString("emotion");
        return new InBoxResponse(inBox, dist, emotion);
    }

    public class InBoxResponse{
        public boolean inBox;
        public double dist;
        public String emotion;
        public InBoxResponse(boolean inBox, double inputDist, String inputEmotion) {
            this.inBox = inBox;
            this.dist = inputDist; 
            this.emotion = inputEmotion;
        }
    }
}


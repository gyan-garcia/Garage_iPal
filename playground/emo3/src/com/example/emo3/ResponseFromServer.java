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


		//FIXME send encodedImage, get JSON

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


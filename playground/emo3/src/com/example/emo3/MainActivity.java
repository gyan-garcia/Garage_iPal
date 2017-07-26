package com.example.emo3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.emo3.ResponseFromServer.InBoxResponse;

//import com.microsoft.projectoxford.emotion.EmotionServiceClient;
//import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.robot.motion.RobotMotion;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;



public class MainActivity extends Activity {
	Button btncam;
	//ImageView img;
	String currentEmotion = "Happiness"; 
     private RobotMotion mRobotMotion = new RobotMotion();
     private EmotionExpression expresser = new EmotionExpression(mRobotMotion);
//	private EmotionExpression expresser = new EmotionExpression(this.mRobotMotion);
	//public EmotionServiceClient emotionServiceClient = new EmotionServiceRestClient("1492e36d5c0b4135bdd99282da703b7c");

	
	private Camera mCamera;
    private CameraPreview mCameraPreview;
    
    @Override 
    public boolean onCreateOptionsMenu (Menu menu)
    {
         //  getMenuInflater.inflate (R.menu.main, main);        
           MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.main, menu);
          return true;
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem Item) {
      
           android.os.Process.killProcess(android.os.Process.myPid());
           
      System.exit(1);
           return true; 
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCamera = getCameraInstance();
		 mCameraPreview = new CameraPreview(this, mCamera);
		btncam = (Button) findViewById(R.id.btncam);
		//img = (ImageView) findViewById(R.id.img);
		 FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	        preview.addView(mCameraPreview);
		

		btncam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//dispatchTakePictureIntent();

				//capturePhoto();
				 mCamera.takePicture(null, null, mPicture);
				//capturePhoto();
				//useCogAPI();
			}
		});


	}
	 PictureCallback mPicture = new PictureCallback() {
	        @Override
	        public void onPictureTaken(byte[] data, Camera camera) {
	        	useCogAPI(data);
	            File pictureFile = getOutputMediaFile();
	            if (pictureFile == null) {
	                return;
	            }
	            try {
	                FileOutputStream fos = new FileOutputStream(pictureFile);
	                fos.write(data);
	                fos.close();
	            } catch (FileNotFoundException e) {

	            } catch (IOException e) {
	            }
	        }
	    };

	    private static File getOutputMediaFile() {
	    	  
	       
	    	String mediaStorageDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";
	    	   
	    	// File file = File.createTempFile("IMG_", ".jpg", storageDir);
	    	
	    	 
//	    	File mediaStorageDir = new File(
//	                Environment
//	                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//	                "MyCameraApp");
//	        if (!mediaStorageDir.exists()) {
//	            if (!mediaStorageDir.mkdirs()) {
//	                Log.d("MyCameraApp", "failed to create directory");
//	                return null;
//	            }
//	        }
//	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
	                .format(new Date());
	        File mediaFile;
	        mediaFile = new File(mediaStorageDir + "IMG_" + timeStamp + ".jpg");
//
	        return mediaFile;
	    }
	
	private Camera getCameraInstance() {
		// TODO Auto-generated method stub
		 Camera camera = null;
	        try {
	            camera = Camera.open();
	        } catch (Exception e) {
	            // cannot get camera or does not exist
	        }
	        return camera;
	}

	public void useCogAPI(byte[] b) {
            ResponseFromServer responseFromServer = new ResponseFromServer();
            InBoxResponse response = responseFromServer.callAPI(b);
            expresser.showEmotion(response.emotion);
        }
	//{
	//	Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cam);
		//ImageView imageView = (ImageView)findViewById(R.id.img);
		//imageView.setImageBitmap(mBitmap);
		
		//Button btnProcess = (Button)findViewById(R.id.btnEmotion)
		//ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
	//	mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
		//code from https://www.youtube.com/watch?v=AA0cgjECOSI
	//	ByteArrayInputStream inputStream = new ByteArrayInputStream(outputstream.toByteArray());
//		ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
//		btnProcess.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//			    AsyncTask<InputStream,String,List<RecognizeResult>> emotionTask = new AsyncTask<inputStream,String, List<RecognizeResult>>
//				@Override
//				protected List<RecognizeResult> doInBackground(InputStream... params)
//				{
//				 try{
//				 publishProgress("Recognizing ...");
//				 List<RecognizeResult> result = emotionServiceClient.recognizeImage(params[0]);
//				 return result;
//			         }catch(Exception ex){
//				return null;
//				}
//					
//				}
//
//		    protected void onPreExecute(){
//		mDialog.show();
//		}
//		protected void onPostExecute(List<RecognizeResult> recognizeResults)
//		{
//		   mDialog.dismiss();
//		   for(RecognizeResult res:recognizeResults)
//		   {
//		      String status = getEmo(res);
//		      imageview.setImageBitmap(Imagehelper.drawRectOnBitmap(mBitmap,res.faceRectangle, status);
//		   }
//		}
//
//		protected void onProgressUpdate(String... values)
//		{
//		     mDialog.setMessage(values[0]);
//		}
//
//		private String getEmo(RecognizeResult res)
//		{
//		    List<Double> list = new arrayList<>();
//		    Scores scores = res.scores;
//
//		    list.add(scores.anger);
//		 list.add(scores.surprise);//need to add all 8 options
//		    Collections.sort(list);
//
//		double mxNum = list.get(listsize()-1);
//		if(mxNum == scores.anger)
//		{
//		    return "Anger";
//		}
//
//		}
//		emotionTask.execute(inputStream);		
//				}
			
	
//	}
	
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int SELECT_PICTURE = 2;
	
	public void pickImage() {
        
	    Intent intent = new Intent();
	    intent.setType("image/*");
	    intent.setAction(Intent.ACTION_GET_CONTENT);
	    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
	}
//
//	public void useCogAPI()
//	{
//		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cam);
//		ImageView imageView = (ImageView)findViewById(R.id.img);
//		imageView.setImageBitmap(mBitmap);
//
//		Button btnProcess = (Button)findViewById(R.id.btnEmotion);
//		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
//		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
//		//code from https://www.youtube.com/watch?v=AA0cgjECOSI
//		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputstream.toByteArray());
//		ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
//		btnProcess.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AsyncTask<InputStream,String,List<RecognizeResult>> emotionTask = new AsyncTask<inputStream,String, List<RecognizeResult>>
//				@Override
//				protected List<RecognizeResult> doInBackground(InputStream... params)
//				{
//					try{
//						publishProgress("Recognizing ...");
//						List<RecognizeResult> result = emotionServiceClient.recognizeImage(params[0]);
//						return result;
//					}catch(Exception ex){
//						return null;
//					}
//
//				}
//
//				protected void onPreExecute(){
//					mDialog.show();
//				}
//				protected void onPostExecute(List<RecognizeResult> recognizeResults)
//				{
//				    mDialog.dismiss();
//					for(RecognizeResult res:recognizeResults)
//					{
//						String status = getEmo(res);
//						this.currentEmotion  = status;
//						this.expresser.showEmotion(this.currentEmotion);
//						imageview.setImageBitmap(Imagehelper.drawRectOnBitmap(mBitmap,res.faceRectangle, status));
//					}
//				}
//
//				protected void onProgressUpdate(String... values)
//				{
//					mDialog.setMessage(values[0]);
//				}
//
//				private String getEmo(RecognizeResult res)
//				{
//					List<Double> list = new arrayList<Double>();
//					Scores scores = res.scores;
//
//					list.add(scores.anger);
//					list.add(scores.fear);
//					list.add(scores.disgust);
//					list.add(scores.happiness);
//					list.add(scores.neutral);
//					list.add(scores.sadness);
//					list.add(scores.contempt);
//					list.add(scores.surprise);//need to add all 8 options
//					Collections.sort(list);
//
//					//TODO add rest of emotions
//					double mxNum = list.get(listsize()-1);
//					if(mxNum == scores.anger)
//					{
//						return "Anger";
//					} 
//					if(mxNum == scores.fear)
//					{
//						return "Fear";
//					} 
//					if(mxNum == scores.disgust)
//					{
//						return "Disgust";
//					} 
//					if(mxNum == scores.happiness)
//					{
//						return "Happiness";
//					} 
//					if(mxNum == scores.neutral)
//					{
//						return "Neutral";
//					} 
//					if(mxNum == scores.sadness)
//					{
//						return "Sadness";
//					} 
//					if(mxNum == scores.contempt)
//					{
//						return "Contempt";
//					} 
//					if(mxNum == scores.surprise)
//					{
//						return "Surprise";
//					} 
//
//
//
//				}
//				emotionTask.execute(inputStream);
//			}
//
//
//		}
//
//		
//
//
//

		//static final int REQUEST_IMAGE_CAPTURE = 1;

		private void dispatchTakePictureIntent() {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if(intent.resolveActivity(getPackageManager()) != null) {
				// Save the photo taken to a temporary file.
				File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
				try {
					File file = File.createTempFile("IMG_", ".jpg", storageDir);
					//mUriPhotoTaken = ;
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					startActivityForResult(intent, 0);
				} catch (IOException e) {
					// setInfo(e.getMessage());
				}
			}


			//	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			//	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			//	        startActivityForResult(takePictureIntent, 1);
			//	    }
		}

		private void capturePhoto() {
			Intent photo = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

			//Intent photo = new Intent();
			//photo.setType("image/*");
			//photo.setAction(Intent.ACTION_GET_CONTENT);
			//photo.addCategory(Intent.CATEGORY_OPENABLE);

			startActivityForResult(photo, 0);
		}




		public void clickExit(View v)
		{
			// mCamera.takePicture(null, null, mPicture);
			finish(); 
		}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bp = (Bitmap) data.getExtras().get("data");
		//img.setImageBitmap(bp);
		
	}
	}
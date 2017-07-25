package com.example.emo3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;

import android.app.Activity;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.robot.motion.RobotMotion;



public class MainActivity extends Activity {
	Button btncam;
	ImageView img;
	String currentEmotion = "Happiness"; 
	private RobotMotion mRobotMotion = new RobotMotion();
	private EmotionExpression expresser = new EmotionExpression(this.mRobotMotion);
	public EmotionServiceClient emotionServiceClient = new EmotionServiceRestClient("1492e36d5c0b4135bdd99282da703b7c");


	private Camera mCamera;
	private CameraPreview mCameraPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCamera = getCameraInstance();
		mCameraPreview = new CameraPreview(this, mCamera);
		btncam = (Button) findViewById(R.id.btncam);
		img = (ImageView) findViewById(R.id.img);
		// FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		//    preview.addView(mCameraPreview);


		btncam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//dispatchTakePictureIntent();

				//capturePhoto();
				mCamera.takePicture(null, null, mPicture);
				capturePhoto();
				ImageView imageView = (ImageView)findViewById(R.id.img);
				useCogAPI(imageView);
			}
		});


	}
	protected void useCogAPI(final ImageView imageView) {

			
			final Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cam);
			imageView.setImageBitmap(mBitmap);

			Button btnProcess = (Button)findViewById(R.id.btncam);
			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
			//code from https://www.youtube.com/watch?v=AA0cgjECOSI
			final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputstream.toByteArray());
			ProgressDialog mDialog = new ProgressDialog(MainActivity.this);



			btnProcess.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {


					EmotionTask emotionTask = new EmotionTask(imageView, mBitmap);
					emotionTask.execute(inputStream); 

				}

			});


		
		
	}
	protected void capturePhoto() {
		Intent photo = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		//Intent photo = new Intent();
		//photo.setType("image/*");
		//photo.setAction(Intent.ACTION_GET_CONTENT);
		//photo.addCategory(Intent.CATEGORY_OPENABLE);

		startActivityForResult(photo, 0);



	}

	private class EmotionTask extends AsyncTask<InputStream,String,List<RecognizeResult>>  {

		ImageView imageView;
		Bitmap mBitmap;
		public EmotionTask(ImageView inputImageView, Bitmap inputMBitmap) {
			this.imageView = inputImageView;
			this.mBitmap = inputMBitmap;
			
			
		}


		@Override
		protected List<RecognizeResult> doInBackground(InputStream ... params)
		{
			try{
				//publishProgress("Recognizing ...");
				List<RecognizeResult> result = emotionServiceClient.recognizeImage(params[0]);
				return result;
			}catch(Exception ex){
				return null;
			}

		}


		protected void onPostExecute(List<RecognizeResult> recognizeResults)
		{
			//mDialog.dismiss();
			for(RecognizeResult res:recognizeResults)
			{
				String status = getEmo(res);
				expresser.showEmotion(status);
				
				imageView.setImageBitmap(ImageHelper.drawRectOnBitmap(mBitmap,res.faceRectangle, status));
			}
		}

		protected void onProgressUpdate(String... values)
		{
			//mDialog.setMessage(values[0]);
		}

		private String getEmo(RecognizeResult res)
		{
			List<Double> list = new ArrayList<Double>();
			Scores scores = res.scores;

			list.add(scores.anger);
			list.add(scores.fear);
			list.add(scores.disgust);
			list.add(scores.happiness);
			list.add(scores.neutral);
			list.add(scores.sadness);
			list.add(scores.contempt);
			list.add(scores.surprise);//need to add all 8 options
			Collections.sort(list);

			//TODO add rest of emotions
			double mxNum = list.get(list.size()-1);
			if(mxNum == scores.anger)
			{
				return "Anger";
			} 
			if(mxNum == scores.fear)
			{
				return "Fear";
			} 
			if(mxNum == scores.disgust)
			{
				return "Disgust";
			} 
			if(mxNum == scores.happiness)
			{
				return "Happiness";
			} 
			if(mxNum == scores.neutral)
			{
				return "Neutral";
			} 
			if(mxNum == scores.sadness)
			{
				return "Sadness";
			} 
			if(mxNum == scores.contempt)
			{
				return "Contempt";
			} 
			if(mxNum == scores.surprise)
			{
				return "Surprise";
			} 



		}


		


		}
		PictureCallback mPicture = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
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

		private File getOutputMediaFile() {
			File mediaStorageDir = new File(
					Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"MyCameraApp");
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d("MyCameraApp", "failed to create directory");
					return null;
				}
			}
			// Create a media file name
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(new Date());
			File mediaFile;
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");

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

		//public void useCogAPI()
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








		public void clickExit(View v)
		{
			finish(); 
		}




	
	}
}

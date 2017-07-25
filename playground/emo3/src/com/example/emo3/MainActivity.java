package com.example.emo3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import android.robot.motion.RobotMotion;
import MotionPlanner;
import EmotionExpression;

public class MainActivity extends Activity {
	Button btncam;
	ImageView img;
	String currentEmotion = "Happiness"; 
	    private RobotMotion mRobotMotion = new RobotMotion(); 
	private EmotionExpression expresser = new EmotionExpression(this.mRobotMotion);
	public EmotionServiceClient emotionServiceClient = new EmotionServiceRestClient("1492e36d5c0b4135bdd99282da703b7c");


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btncam = (Button) findViewById(R.id.btncam);
		img = (ImageView) findViewById(R.id.img);
		btncam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//dispatchTakePictureIntent();
				capturePhoto();
				useCogAPI();
			}
		});


	}

	public void useCogAPI()
	{
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cam);
		ImageView imageView = (ImageView)findViewById(R.id.img);
		imageView.setImageBitmap(mBitmap);

		Button btnProcess = (Button)findViewById(R.id.btnEmotion);
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
		//code from https://www.youtube.com/watch?v=AA0cgjECOSI
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputstream.toByteArray());
		ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
		btnProcess.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AsyncTask<InputStream,String,List<RecognizeResult>> emotionTask = new AsyncTask<inputStream,String, List<RecognizeResult>>
				@Override
				protected List<RecognizeResult> doInBackground(InputStream... params)
				{
					try{
						publishProgress("Recognizing ...");
						List<RecognizeResult> result = emotionServiceClient.recognizeImage(params[0]);
						return result;
					}catch(Exception ex){
						return null;
					}

				}

				protected void onPreExecute(){
					mDialog.show();
				}
				protected void onPostExecute(List<RecognizeResult> recognizeResults)
				{
				    mDialog.dismiss();
					for(RecognizeResult res:recognizeResults)
					{
						String status = getEmo(res);
						this.currentEmotion  = status;
						this.expresser.showEmotion(this.currentEmotion);
						imageview.setImageBitmap(Imagehelper.drawRectOnBitmap(mBitmap,res.faceRectangle, status));
					}
				}

				protected void onProgressUpdate(String... values)
				{
					mDialog.setMessage(values[0]);
				}

				private String getEmo(RecognizeResult res)
				{
					List<Double> list = new arrayList<Double>();
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
					double mxNum = list.get(listsize()-1);
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
				emotionTask.execute(inputStream);		n
			}


		}

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

		private void capturePhoto() {
			Intent photo = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

			//Intent photo = new Intent();
			//photo.setType("image/*");
			//photo.setAction(Intent.ACTION_GET_CONTENT);
			//photo.addCategory(Intent.CATEGORY_OPENABLE);

			startActivityForResult(photo, 0);
		}




		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			Bitmap bp = (Bitmap) data.getExtras().get("data");
			img.setImageBitmap(bp);

		}


		public void clickExit(View v)
		{
			finish(); 
		}



	}

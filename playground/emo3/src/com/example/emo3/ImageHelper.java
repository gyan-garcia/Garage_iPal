package com.example.emo3;

import com.microsoft.projectoxford.emotion.contract.FaceRectangle;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageHelper {
	public static Bitmap drawRectOnBitmap(Bitmap mBitmap, FaceRectangle faceRectangle, String status)
	{
		Bitmap bitmap = mBitmap.copy(Bitmap.Config.ARGB_8888,true);
		Canvas canvas = new Canvas(bitmap);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color.white);
		paint.setStrokeWidth(12);
		
		canvas.drawRect(faceRectangle.left, 
				faceRectangle.top,
				faceRectangle.left+faceRectangle.width,
				faceRectangle.top+faceRectangle.height,
				paint);
		
		int cX = faceRectangle.left+faceRectangle.width;
		int cY = faceRectangle.top+faceRectangle.height;
		drawTextOnBitmap(canvas,100,cX/2+cX/5,cY+100, Color.WHITE, status);
		return bitmap;
		
	}

	private static void drawTextOnBitmap(Canvas canvas, int textSize, int cX, int cY, int color, String status) {
		// TODO Auto-generated method stub
		Paint tempTextPaint = new Paint();
		tempTextPaint.setAntiAlias(true);
		tempTextPaint.setStyle(Paint.Style.FILL);
		tempTextPaint.setColor(color);
		tempTextPaint.setTextSize(textSize);
		
		canvas.drawText(status, cX, cY, tempTextPaint);
	}

}

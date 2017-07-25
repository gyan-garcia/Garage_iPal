package com.avatarmind.camera.util;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

public interface CameraListener {

    void onCameraStopped(byte[] data);

    void onTouchFocus(View v, MotionEvent event);

    void onRecordingStarted(String videoPath);

    void onRecordingStopped(String videoPath);

    void onRecordingError();

    void onMaxDurationStopped();

    void onAnimationEnd(Bitmap bm);

}

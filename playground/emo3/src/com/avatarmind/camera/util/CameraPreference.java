package com.avatarmind.camera.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("CommitPrefEdits")
public class CameraPreference {
    SharedPreferences preferences;

    public CameraPreference(Context context) {
        preferences = context.getSharedPreferences("camera_prefernce",
                Context.MODE_PRIVATE);
    }

    public void saveCamera(int camera) {
        preferences.edit().putInt("camera", camera).commit();
    }

    public int getCamera() {
        return preferences.getInt("camera", 0);
    }

    public void savePhotoWidth(int photoWidth) {
        preferences.edit().putInt("photoWidth", photoWidth).commit();
    }

    public int getPhotoWidth() {
        return preferences.getInt("photoWidth", 1280);
    }

    public void savePhotoHeight(int photoHeight) {
        preferences.edit().putInt("photoHeight", photoHeight).commit();
    }

    public int getPhotoHeight() {
        return preferences.getInt("photoHeight", 720);
    }

    public void saveVideoTime(int photoSize) {
        preferences.edit().putInt("videoTime", photoSize).commit();
    }

    public int getVideoTime() {
        return preferences.getInt("videoTime", 6000);
    }

    public void saveCameraChecked(int cameraChecked) {
        preferences.edit().putInt("cameraChecked", cameraChecked).commit();
    }

    public int getCameraChecked() {
        return preferences.getInt("cameraChecked", 0);
    }

    public void savePhotoSizeChecked(int PhotoSizeChecked) {
        preferences.edit().putInt("photoSizeChecked", PhotoSizeChecked)
                .commit();
    }

    public int getPhotoSizeChecked() {
        return preferences.getInt("photoSizeChecked", 2);
    }

    public void saveVideoTimeChecked(int VideoTime) {
        preferences.edit().putInt("videoTimeChecked", VideoTime).commit();
    }

    public int getVideoTimeChecked() {
        return preferences.getInt("videoTimeChecked", 7);
    }

    public void saveLastImage(String image) {
        preferences.edit().putString("LastImage", image).commit();
    }

    public String getLastImage() {
        return preferences.getString("LastImage", "");
    }

    public void saveLastModel(Boolean isVideo) {
        preferences.edit().putBoolean("LastModel", isVideo).commit();
    }

    public boolean getLastLastModel() {
        return preferences.getBoolean("LastModel", false);
    }
}

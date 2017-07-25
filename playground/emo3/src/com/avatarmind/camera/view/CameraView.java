package com.avatarmind.camera.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.CamcorderProfile;
import android.media.MediaActionSound;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

//import com.avatarmind.camera.R;
import com.avatarmind.camera.util.BestResolution;
import com.avatarmind.camera.util.CameraListener;
import com.avatarmind.camera.util.PhotoProcess;

import java.io.File;
import java.io.IOException;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback,
        OnInfoListener, OnErrorListener, OnTouchListener {

    private static final String TAG = "CameraView";
    private final SurfaceHolder surfaceHolder;
    private Camera.CameraInfo cameraInfo;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private CameraListener mCameraStatusListener;
    private final Context mContext;
    private Camera.Parameters mParameters;
    private static final int CAMERA_BACK = 0;
    private static final int CAMERA_FRONT = 1;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final long WAIT_TIME = 200;
    private static String videoPath;
    private final int videoTotalTime = 30000;
    private final int FOCUS_MODE = 1;
    private final int FLASH_MODE = 0;
    private final int screenWidth;
    private final int screenHeight;
    private int cameraCount;
    private final Handler mCameraHandler;
    private final MediaActionSound mCameraSound;
    private final MediaMetadataRetriever mMediaMetadataRetriever;

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setOnTouchListener(this);
        surfaceHolder.setKeepScreenOn(true);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        mMediaMetadataRetriever = new MediaMetadataRetriever();

        HandlerThread handlerThread = new HandlerThread("Camera");
        handlerThread.start();
        mCameraHandler = new CameraHandler(handlerThread.getLooper());

        // Setup the Camera shutter sound
        mCameraSound = new MediaActionSound();
        mCameraSound.load(MediaActionSound.SHUTTER_CLICK);

    }

    public void setOnCameraStatusListener(CameraListener cameraStatusListener) {
        mCameraStatusListener = cameraStatusListener;
    }

    private static final int MSG_OPEN_CAMERA = 0;
    private static final int MSG_RELEASE_CAMERA = 1;
    private static final int MSG_INIT_RECORDER = 2;
    private static final int MSG_RELEASE_RECORDER = 3;
    private static final int MSG_TAKE_PICTURE = 4;

    private class CameraHandler extends Handler {
        public CameraHandler(Looper loop) {
            super(loop);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_OPEN_CAMERA:
                    openCamera();
                    break;

                case MSG_RELEASE_CAMERA:
                    releaseCamera();
                    break;

                case MSG_INIT_RECORDER:
                    initMediaRecorder();
                    break;

                case MSG_RELEASE_RECORDER:
                    releaseMediaRecorder(false);
                    break;

                case MSG_TAKE_PICTURE:
                    takePictureInThread();
                    break;

                default:
                    break;
            }
        };
    };

    private final PictureCallback pictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mCamera.startPreview();
            mCameraStatusListener.onCameraStopped(data);
        }
    };

    private final ShutterCallback mShutterCallback = new ShutterCallback() {
        @Override
        public void onShutter() {
            // Play the shutter sound to notify that we've taken a picture
            mCameraSound.play(MediaActionSound.SHUTTER_CLICK);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void openCamera() {
        try {
            if (null != mCamera) {
                return;
            }
            cameraCount = Camera.getNumberOfCameras();
            if (cameraCount < 1) {
               // Toast.makeText(mContext, mContext.getString(R.string.camera_null), Toast.LENGTH_SHORT)
               // .show();
                return;
            }
            if (cameraCount > 1) {
                mCamera = Camera.open(CAMERA_BACK);
            } else {
                cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(CAMERA_BACK, cameraInfo);
                if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                    mCamera = Camera.open(CAMERA_BACK);
                } else {
                    mCamera = Camera.open(CAMERA_FRONT);
                }
            }

            if (null == mCamera) {
                return;
            }
            mCamera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.set("orientation", "landscape");
            parameters.set("jpeg-quality", 100);
            Camera.Size previewSize = BestResolution
                    .findBestPreview(mCamera);
            Camera.Size pictureSize = BestResolution
                    .findBestPicture(mCamera);
            parameters.setPreviewSize(previewSize.width,
                    previewSize.height);
            parameters.setPictureSize(pictureSize.width,
                    pictureSize.height);
            // parameters.setExposureCompensation(1);
            // CameraSetting.setFocusMode(mContext, parameters, FOCUS_MODE);
            mCamera.setParameters(parameters);
            // CameraSetting.setFlashMode(mContext, mCamera, FLASH_MODE, cameraPosition);
            // mCamera.setDisplayOrientation(0);
            mCamera.startPreview();
            // mCamera.cancelAutoFocus();
        } catch (Exception e) {
//            closeCamera();
            e.printStackTrace();
        }
    }

    private void releaseCamera() {
        synchronized (CameraView.this) {
            if (mCamera == null) {
                return;
            }
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void initMediaRecorder() {
        synchronized (CameraView.this) {
            if (null == mCamera) {
                return;
            }
            mCamera.stopPreview();
            if (null == mMediaRecorder) {
                mMediaRecorder = new MediaRecorder();
            } else {
                mMediaRecorder.reset();
            }
            mParameters = mCamera.getParameters();
            mCamera.unlock();
            mMediaRecorder.setCamera(mCamera);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mMediaRecorder.setMaxDuration(videoTotalTime);
            mMediaRecorder.setOnInfoListener(this);
            mMediaRecorder.setOnErrorListener(this);
            mMediaRecorder.setProfile(CamcorderProfile.get(CAMERA_BACK,
                    CamcorderProfile.QUALITY_HIGH));
            mMediaRecorder.setVideoFrameRate(30);
            long dateTaken = System.currentTimeMillis();
            String filename = "VID"
                    + DateFormat.format("_yyyy.MM.dd_kk.mm.ss", dateTaken)
                            .toString() + ".mp4";
            videoPath = PhotoProcess.insertVideo(PhotoProcess.PHOTO_PATH,
                    filename);
            mMediaRecorder.setOutputFile(videoPath);
            mMediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());

            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                releaseMediaRecorder(true);
                cleanupEmptyFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCameraStatusListener.onRecordingStarted(videoPath);
        }
    }

    private void releaseMediaRecorder(boolean isError) {
        synchronized (CameraView.this) {
            if (null != mMediaRecorder) {
                mMediaRecorder.setOnErrorListener(null);
                mMediaRecorder.setOnInfoListener(null);
                mMediaRecorder.setPreviewDisplay(null);
                try {
                    mMediaRecorder.stop();
                    mMediaRecorder.release();
                    mMediaRecorder = null;
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != mCamera) {
                mCamera.lock();
            }
            if (isError) {
                mCameraStatusListener.onRecordingError();
            } else {
                mCameraStatusListener.onRecordingStopped(videoPath);
            }
        }
    }

    private void takePictureInThread() {
        if (null == mCamera) {
            return;
        }
        mCamera.takePicture(mShutterCallback, null, pictureCallback);
    }

    public void takePicture() {
        mCameraHandler.sendEmptyMessage(MSG_TAKE_PICTURE);
    }

    public void startCameraRecording() {
        if (mCamera == null) {
            return;
        }
        mCameraHandler.sendEmptyMessage(MSG_INIT_RECORDER);
    }

    public void stopCameraRecording() {
        if (mMediaRecorder == null) {
            return;
        }
        mCameraHandler.sendEmptyMessage(MSG_RELEASE_RECORDER);
    }

    public void require() {
        mCameraHandler.sendEmptyMessageDelayed(MSG_OPEN_CAMERA, WAIT_TIME);
    }

    public void release() {
        stopCameraRecording();
        mCameraHandler.sendEmptyMessageDelayed(MSG_RELEASE_CAMERA, WAIT_TIME);
    }

    public boolean cleanupEmptyFile() {
        if (null == videoPath) {
            return false;
        }

        mMediaMetadataRetriever.setDataSource(videoPath);
        String duration = mMediaMetadataRetriever
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        Integer time = Integer.valueOf(duration).intValue();
        if (time > 1000) {
            return false;
        }

        File f = new File(videoPath);
        if (!f.delete()) {
            return false;
        }
        videoPath = null;
        return true;
    }

    public boolean isCameraExist() {
        return (null != mCamera);
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
            mCameraStatusListener.onMaxDurationStopped();
        }
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        try {
            if (null != mr)
                mr.reset();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mCameraStatusListener.onTouchFocus(v, event);
        return false;
    }
}

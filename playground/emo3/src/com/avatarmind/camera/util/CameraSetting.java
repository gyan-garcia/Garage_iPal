package com.avatarmind.camera.util;

import java.util.List;

import android.content.Context;
import android.hardware.Camera;

public class CameraSetting {

    public static void setFocusMode(Context context,
            Camera.Parameters parameters, int type) {
        List<String> FocusModes = parameters.getSupportedFocusModes();

        switch (type) {
            case 0:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                }
                break;
            case 1:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                }
                break;
            case 2:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_EDOF)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_EDOF);
                }
                break;
            case 3:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
                }
                break;
            case 4:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
                }
                break;
            case 5:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_MACRO)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
                }
                break;
        }
    }

    public static void setFlashMode(Context context, Camera mCamera, int type,
            int CameraId) {
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> FlashModes = parameters.getSupportedFlashModes();
        switch (type) {
            case 0:
                if (CameraId == 0
                        && FlashModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                }
                break;
            case 1:
                if (FlashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
                break;
            case 2:
                if (FlashModes.contains(Camera.Parameters.FLASH_MODE_ON)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                }
                break;
            case 3:
                if (FlashModes.contains(Camera.Parameters.FLASH_MODE_RED_EYE)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_RED_EYE);
                }
                break;
            case 4:
                if (FlashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }
                break;
        }
        mCamera.setParameters(parameters);
    }

    /**
     * @param zoom
     */
    public void setZoom(Camera mCamera, Camera.Parameters mParameters, int zoom) {
        if (mCamera == null)
            return;
        Camera.Parameters parameters;
        // 娉ㄦ剰姝ゅ涓哄綍鍍忔ā寮忎笅鐨剆etZoom鏂瑰紡銆傚湪Camera.unlock涔嬪悗锛岃皟鐢╣etParameters鏂规硶浼氬紩璧穉ndroid妗嗘灦搴曞眰鐨勫紓甯�
        // stackoverflow涓婄湅鍒扮殑瑙ｉ噴鏄敱浜庡绾跨▼鍚屾椂璁块棶Camera瀵艰嚧鐨勫啿绐侊紝鎵�浠ュ湪姝や娇鐢ㄥ綍鍍忓墠淇濆瓨鐨刴Parameters銆�
        if (mParameters != null) {
            parameters = mParameters;
        } else {
            parameters = mCamera.getParameters();
        }

        if (!parameters.isZoomSupported()) {
            return;
        }

        parameters.setZoom(zoom);
        mCamera.setParameters(parameters);
    }
}

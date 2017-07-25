package com.avatarmind.camera.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

@SuppressLint("NewApi")
public class BestResolution {
    private static int screenWidth;
    private static int screenHeight;
    /**
     * 鏈�灏忛瑙堢晫闈㈢殑鍒嗚鲸鐜�
     */
    private static final int MIN_PREVIEW_PIXELS = 480 * 320;
    /**
     * 鏈�澶у楂樻瘮宸�
     */
    private static final double MAX_ASPECT_DISTORTION = 0.15;

    /**
     * 鎵惧嚭鏈�閫傚悎鐨勯瑙堢晫闈㈠垎杈ㄧ巼
     * 
     * @return
     */
    public static Camera.Size findBestPreview(Camera mCamera) {
        Camera.Parameters cameraParameters = mCamera.getParameters();
        Camera.Size defaultPreviewResolution = cameraParameters
                .getPreviewSize();
        List<Camera.Size> rawSupportedSizes = cameraParameters
                .getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
            return defaultPreviewResolution;
        }

        // 鎸夌収鍒嗚鲸鐜囦粠澶у埌灏忔帓搴�
        List<Camera.Size> supportedPreviewResolutions = new ArrayList<Camera.Size>(
                rawSupportedSizes);
        Collections.sort(supportedPreviewResolutions, new Comparator<Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });

        StringBuilder previewResolutionSb = new StringBuilder();
        for (Camera.Size supportedPreviewResolution : supportedPreviewResolutions) {
            previewResolutionSb.append(supportedPreviewResolution.width)
                    .append('x').append(supportedPreviewResolution.height)
                    .append(' ');
        }

        // 绉婚櫎涓嶇鍚堟潯浠剁殑鍒嗚鲸鐜�
        double screenAspectRatio = (double) screenWidth / screenHeight;
        Iterator<Size> it = supportedPreviewResolutions.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewResolution = it.next();
            int width = supportedPreviewResolution.width;
            int height = supportedPreviewResolution.height;

            // 绉婚櫎浣庝簬涓嬮檺鐨勫垎杈ㄧ巼锛屽敖鍙兘鍙栭珮鍒嗚鲸鐜�
            if (width * height < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }

            // 鍦╟amera鍒嗚鲸鐜囦笌灞忓箷鍒嗚鲸鐜囧楂樻瘮涓嶇浉绛夌殑鎯呭喌涓嬶紝鎵惧嚭宸窛鏈�灏忕殑涓�缁勫垎杈ㄧ巼
            // 鐢变簬camera鐨勫垎杈ㄧ巼鏄痺idth>height锛屾垜浠缃殑portrait妯″紡涓紝width<height
            // 鍥犳杩欓噷瑕佸厛浜ゆ崲鐒秔review瀹介珮姣斿悗鍦ㄦ瘮杈�
            boolean isCandidatePortrait = width > height;
            int maybeFlippedWidth = isCandidatePortrait ? height : width;
            int maybeFlippedHeight = isCandidatePortrait ? width : height;
            double aspectRatio = (double) maybeFlippedWidth
                    / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }

            // 鎵惧埌涓庡睆骞曞垎杈ㄧ巼瀹屽叏鍖归厤鐨勯瑙堢晫闈㈠垎杈ㄧ巼鐩存帴杩斿洖
            if (maybeFlippedWidth == screenWidth
                    && maybeFlippedHeight == screenHeight) {
                return supportedPreviewResolution;
            }
        }

        // 濡傛灉娌℃湁鎵惧埌鍚堥�傜殑锛屽苟涓旇繕鏈夊�欓�夌殑鍍忕礌锛屽垯璁剧疆鍏朵腑鏈�澶ф瘮渚嬬殑锛屽浜庨厤缃瘮杈冧綆鐨勬満鍣ㄤ笉澶悎閫�
        if (!supportedPreviewResolutions.isEmpty()) {
            Camera.Size largestPreview = supportedPreviewResolutions.get(0);
            return largestPreview;
        }

        // 娌℃湁鎵惧埌鍚堥�傜殑锛屽氨杩斿洖榛樿鐨�

        return defaultPreviewResolution;
    }

    public static Camera.Size findBestPicture(Camera mCamera) {
        Camera.Parameters cameraParameters = mCamera.getParameters();
        List<Camera.Size> supportedPicResolutions = cameraParameters
                .getSupportedPictureSizes(); // 鑷冲皯浼氳繑鍥炰竴涓��

        StringBuilder picResolutionSb = new StringBuilder();
        for (Camera.Size supportedPicResolution : supportedPicResolutions) {
            picResolutionSb.append(supportedPicResolution.width).append('x')
                    .append(supportedPicResolution.height).append(" ");
        }

        Camera.Size defaultPictureResolution = cameraParameters
                .getPictureSize();

        // 鎺掑簭
        List<Camera.Size> sortedSupportedPicResolutions = new ArrayList<Camera.Size>(
                supportedPicResolutions);
        Collections.sort(sortedSupportedPicResolutions,
                new Comparator<Camera.Size>() {
                    @Override
                    public int compare(Camera.Size a, Camera.Size b) {
                        int aPixels = a.height * a.width;
                        int bPixels = b.height * b.width;
                        if (bPixels < aPixels) {
                            return -1;
                        }
                        if (bPixels > aPixels) {
                            return 1;
                        }
                        return 0;
                    }
                });

        // 绉婚櫎涓嶇鍚堟潯浠剁殑鍒嗚鲸鐜�
        double screenAspectRatio = screenWidth / (double) screenHeight;
        Iterator<Camera.Size> it = sortedSupportedPicResolutions.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewResolution = it.next();
            int width = supportedPreviewResolution.width;
            int height = supportedPreviewResolution.height;

            // 鍦╟amera鍒嗚鲸鐜囦笌灞忓箷鍒嗚鲸鐜囧楂樻瘮涓嶇浉绛夌殑鎯呭喌涓嬶紝鎵惧嚭宸窛鏈�灏忕殑涓�缁勫垎杈ㄧ巼
            // 鐢变簬camera鐨勫垎杈ㄧ巼鏄痺idth>height锛屾垜浠缃殑portrait妯″紡涓紝width<height
            // 鍥犳杩欓噷瑕佸厛浜ゆ崲鐒跺悗鍦ㄦ瘮杈冨楂樻瘮
            boolean isCandidatePortrait = width > height;
            int maybeFlippedWidth = isCandidatePortrait ? height : width;
            int maybeFlippedHeight = isCandidatePortrait ? width : height;
            double aspectRatio = (double) maybeFlippedWidth
                    / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }
        }

        // 濡傛灉娌℃湁鎵惧埌鍚堥�傜殑锛屽苟涓旇繕鏈夊�欓�夌殑鍍忕礌锛屽浜庣収鐗囷紝鍒欏彇鍏朵腑鏈�澶ф瘮渚嬬殑锛岃�屼笉鏄�夋嫨涓庡睆骞曞垎杈ㄧ巼鐩稿悓鐨�
        if (!sortedSupportedPicResolutions.isEmpty()) {
            return sortedSupportedPicResolutions.get(0);
        }

        // 娌℃湁鎵惧埌鍚堥�傜殑锛屽氨杩斿洖榛樿鐨�
        return defaultPictureResolution;
    }

    public static Camera.Size findBestVideoResolution(Camera mCamera,
            int screenWidth, int screenHeight) {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> videoSizes = parameters.getSupportedVideoSizes();
        List<String> list = new ArrayList<String>();
        for (Camera.Size videoSize : videoSizes) {
            list.add(String.valueOf(videoSize.width) + "=="
                    + String.valueOf(videoSize.height));
        }
        Log.d("", String.valueOf(list.size()));
        // // 鎸夌収鍒嗚鲸鐜囦粠澶у埌灏忔帓搴�
        // List<Camera.Size> supportedVideoResolutions = new
        // ArrayList<Camera.Size>(
        // videoSizes);
        // Collections.sort(supportedVideoResolutions, new Comparator<Size>() {
        // @Override
        // public int compare(Camera.Size a, Camera.Size b) {
        // int aPixels = a.height * a.width;
        // int bPixels = b.height * b.width;
        // if (bPixels < aPixels) {
        // return -1;
        // }
        // if (bPixels > aPixels) {
        // return 1;
        // }
        // return 0;
        // }
        // });
        // StringBuilder previewResolutionSb = new StringBuilder();
        // for (Camera.Size supportedVideoResolution :
        // supportedVideoResolutions) {
        // previewResolutionSb.append(supportedVideoResolutions.)
        // .append('x').append(supportedVideoResolutions.height)
        // .append(' ');
        // }
        return null;

    }
}

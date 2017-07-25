package com.avatarmind.camera.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

@SuppressLint("NewApi")
public class PhotoProcess {
    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final String PHOTO_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";
    public static final String VIDEO_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Video/";

    /**
     * 瀛樺偍鍥惧儚骞跺皢淇℃伅娣诲姞鍏ュ獟浣撴暟鎹簱
     */
    public static String insertImage(ContentResolver cr, String name,
            long dateTaken, String directory, String filename, Bitmap source,
            byte[] jpegData) {

        OutputStream outputStream = null;
        String filePath = directory + filename;
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(directory, filename);
            if (file.createNewFile()) {
                outputStream = new FileOutputStream(file);
                if (source != null) {
                    // 鍥剧墖鍘嬬缉
                    source.compress(CompressFormat.JPEG, 30, outputStream);
                } else {
                    outputStream.write(jpegData);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        ContentValues values = new ContentValues(7);
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, filePath);
        cr.insert(IMAGE_URI, values);
        return filePath;
    }

    /**
     * 瀛樺偍瑙嗛骞跺皢淇℃伅娣诲姞鍏ュ獟浣撴暟鎹簱
     */
    public static String insertVideo(String directory, String filename) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(directory, filename);
        return file.getAbsolutePath();
    }

    /**
     * 灏嗘媿涓嬫潵鐨勭収鐗囧睍绀哄嚭鏉�
     */
    public static Bitmap getPhotoBitmap(byte[] data) throws IOException {
        Bitmap croppedImage;
        // 鑾峰緱鍥剧墖澶у皬
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream is = new ByteArrayInputStream(data);
        croppedImage = BitmapFactory.decodeStream(is, null, options);
        return croppedImage;
    }

    public static List<File> listFiles(String file) {
        return listFiles(new File(file), null);
    }

    /**
     * 鑾峰彇鐩爣鏂囦欢澶瑰唴鎸囧畾鍚庣紑鍚嶇殑鏂囦欢鏁扮粍,鎸夌収淇敼鏃ユ湡鎺掑簭
     */
    public static List<File> listFiles(File file, final String content) {
        File[] files = null;
        if (file == null || !file.exists() || !file.isDirectory())
            return null;
        files = file.listFiles();
        if (files != null) {
            List<File> list = new ArrayList<File>(Arrays.asList(files));
            sortList(list, false);
            return list;
        }
        return null;
    }

    /**
     * 鏍规嵁淇敼鏃堕棿涓烘枃浠跺垪琛ㄦ帓搴�
     */
    public static void sortList(List<File> list, final boolean asc) {
        // 鎸変慨鏀规棩鏈熸帓搴�
        Collections.sort(list, new Comparator<File>() {
            public int compare(File file, File newFile) {
                if (file.lastModified() > newFile.lastModified()) {
                    if (asc) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (file.lastModified() == newFile.lastModified()) {
                    return 0;
                } else {
                    if (asc) {
                        return -1;
                    } else {
                        return 1;
                    }
                }

            }
        });
    }
}

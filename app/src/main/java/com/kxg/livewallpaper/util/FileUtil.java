package com.kxg.livewallpaper.util;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kuangxiaoguo on 2017/3/20.
 */

public class FileUtil {

    private static final String TAG = "FileUtil";
    public static String mLiveWallpaperPath;

    private static String getFilePath(String finderName, String fileName) {
        if (TextUtils.isEmpty(mLiveWallpaperPath)) {
            return "";
        }
        File file = new File(mLiveWallpaperPath + finderName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath() + fileName;
    }

    private static String getFinderPath(String finderName) {
        if (TextUtils.isEmpty(mLiveWallpaperPath)) {
            return "";
        }
        File file = new File(mLiveWallpaperPath + finderName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    public static boolean deleteFinder(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return true;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                for (File files : fileList) {
                    if (files.isFile() && files.exists()) {
                        files.delete();
                    }
                }
            }
        }
        return true;
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void deleteFile(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static boolean isFileExists(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            return file.exists() && file.isFile();
        }
        return false;
    }

    /**
     * 获取音视频文件时长
     *
     * @param filePath 文件路径
     * @return 时间长度(ms)
     */
    public static int getMediaFileDuration(String filePath) {
        int mediaFileDuration;
        if (!FileUtil.isFileExists(filePath)) {
            return 0;
        }
        String duration = getVideoAttributes(filePath, MediaMetadataRetriever.METADATA_KEY_DURATION);
        try {
            mediaFileDuration = Integer.parseInt(duration);
        } catch (Exception e) {
            mediaFileDuration = 0;
        }
        return mediaFileDuration;
    }

    public static String getVideoAttributes(String fileName, int keyCode) {
        if (!isFileExists(fileName)) {
            return "";
        }
        try {
            MediaMetadataRetriever videoRetriever = new MediaMetadataRetriever();
            videoRetriever.setDataSource(fileName);
            return videoRetriever.extractMetadata(keyCode);
        } catch (Exception e) {
            if (!TextUtils.isEmpty(e.getMessage())) {
                Log.e(TAG, e.getMessage());
            }
        }
        return "";
    }

    /**
     * 更新媒体库
     *
     * @param filename 文件全名，包括后缀
     */
    public static void updateMediaStore(Context context, String filename) {
        MediaScannerConnection.scanFile(context,
                new String[]{filename}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    public static boolean copyFile(String copyFile, String newFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(copyFile));
        byte[] data = new byte[1024];
        FileOutputStream outputStream = new FileOutputStream(new File(newFilePath));
        while (inputStream.read(data) != -1) {
            outputStream.write(data);
        }
        inputStream.close();
        outputStream.close();
        return true;
    }



}

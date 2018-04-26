package com.kxg.livewallpaper.wallpaper;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.kxg.livewallpaper.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 读取本地mp4文件
 * Created by kuangxiaoguo on 2017/8/22.
 */

public class VideoUtil {

    private static final String TAG = "VideoUtil";
    private static final String NATIVE_IMAGE_FILE_PATH = "/DCIM/Camera";
    private static final String NATIVE_CAMERA_FILE_PATH = "/相机";

    public static void scanNativeImageFile(Context context) {
        scanImageFile(context, NATIVE_IMAGE_FILE_PATH);
        scanImageFile(context, NATIVE_CAMERA_FILE_PATH);
    }

    /**
     * 获取手机中所有视频的信息
     */
    public static List<MediaModel> getVideoInfo(final Context context) {
        List<MediaModel> data = new ArrayList<>();
        Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] videoProjection = {MediaStore.Video.Thumbnails._ID
                , MediaStore.Video.Thumbnails.DATA
                , MediaStore.Video.Media.DURATION
                , MediaStore.Video.Media.SIZE
                , MediaStore.Video.Media.DISPLAY_NAME
                , MediaStore.Video.Media.DATE_MODIFIED
                , MediaStore.Video.Media.HEIGHT
                , MediaStore.Video.Media.WIDTH};
        Cursor mCursor = context.getContentResolver().query(mImageUri,
                videoProjection,
                MediaStore.Video.Media.MIME_TYPE + "=?",
                new String[]{"video/mp4"},
                MediaStore.Video.Media.DATE_MODIFIED + " desc");
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                // 获取视频的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                if (TextUtils.isEmpty(path)) {
                    continue;
                }
                int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                int height = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.HEIGHT));
                int width = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.WIDTH));
                //某些设备获取size<0，直接计算
                long size = new File(path).length() / 1024;
                if (size <= 0) {
                    continue;
                }
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                MediaModel model = new MediaModel();
                model.setPath(path);
                model.setDuration(duration);
                model.setSize(size);
                model.setDisplayName(displayName);
                model.setHeight(height);
                model.setWidth(width);
                data.add(model);
            }
            mCursor.close();
        }
        return data;
    }

    public static Bitmap getVideoThumb(String path, int kind) {
        Bitmap bitmap;
        try {
            bitmap = ThumbnailUtils.createVideoThumbnail(path, kind);
        } catch (OutOfMemoryError e) {
            bitmap = null;
        }
        return bitmap;
    }

    private static boolean createImageFile(String videoPath, Bitmap bitmap) {
        if (TextUtils.isEmpty(videoPath) || !(videoPath.endsWith(".mp4"))) {
            return false;
        }
        return saveBitmapToFile(bitmap, getImageFileName(videoPath), Bitmap.CompressFormat.JPEG);
    }

    public static boolean saveBitmapToFile(Bitmap bitmap, String path, Bitmap.CompressFormat format) {
        boolean ret = false;
        File file = new File(path);
        if (!bitmap.isRecycled()) {
            try {
                ret = bitmap.compress(format, 80, new FileOutputStream(file, false));
            } catch (Exception e) {
                Log.e(TAG, "saveBitmapToFile Exception , " + e.toString());
            }
            if (!ret) {
                file.delete();
            }
        }
        return ret;
    }

    private static String getImageFileName(String videoPath) {
        if (TextUtils.isEmpty(videoPath) || !(videoPath.endsWith(".mp4"))) {
            return "";
        }
        int lastIndexOf = videoPath.lastIndexOf("/");
        int subIndex = videoPath.lastIndexOf(".");
        if (subIndex < 0 || subIndex > videoPath.length()) {
            return "";
        }
        String fileName = videoPath.substring(lastIndexOf, subIndex);
        return WallPaperUtil.getWallPaperPath() + fileName + ".jpg";
    }

    /**
     * 更新手机自带相册存储路径
     */
    private static void scanImageFile(Context context, String path) {
        if (!FileUtil.isSDCardAvailable()) {
            return;
        }
        File imageFile = new File(Environment.getExternalStorageDirectory().getPath() + path);
        if (!imageFile.exists()) {
            return;
        }
        File[] files = imageFile.listFiles();
        if (files == null) {
            return;
        }
        Date date = null;
        for (File eachFile : files) {
            if (eachFile == null) {
                continue;
            }
            if (date == null) {
                date = new Date();
            }
            String fileName = eachFile.getName();
            if (TextUtils.isEmpty(fileName) || !(fileName.endsWith(".mp4"))) {
                continue;
            }
            date.setTime(eachFile.lastModified());
            String fileTime = SimpleDateFormat.getDateInstance().format(date);
            String currentTime = SimpleDateFormat.getDateInstance().format(System.currentTimeMillis());
            //只更新当天拍照的视频
            if (!TextUtils.isEmpty(fileTime) && fileTime.equals(currentTime)) {
                FileUtil.updateMediaStore(context, eachFile.getPath());
            }
        }
    }

}

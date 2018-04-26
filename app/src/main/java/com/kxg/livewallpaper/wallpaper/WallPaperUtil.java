package com.kxg.livewallpaper.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.kxg.livewallpaper.constant.Constant;
import com.kxg.livewallpaper.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 桌面壁纸Util
 * Created by kuangxiaoguo on 2017/8/25.
 */

public class WallPaperUtil {

    private static final String TAG = "WallPaperUtil";

    private static String mWallPaperPath;
    //动态桌面插件版本号，升级时需手动更改
    private static final String WALL_PAPER_ENGINE_VERSION_NAME = "2.0";

    public static void setFilePath(Context context) {
        if (FileUtil.isSDCardAvailable() && context.getExternalCacheDir() != null) {
            mWallPaperPath = context.getExternalCacheDir().getPath() +
                    WallPaperConstant.WALL_PAPER_FINDER;
            createWallPaperFinder();
            return;
        }
        mWallPaperPath = context.getFilesDir().getPath() + WallPaperConstant.WALL_PAPER_FINDER;
        createWallPaperFinder();
    }

    private static void createWallPaperFinder() {
        File file = new File(mWallPaperPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getWallPaperPath() {
        return mWallPaperPath;
    }

    public static void saveVoiceState(Context context, boolean hasVoice) {
        SharedPreferences preferences = context
                .getSharedPreferences(Constant.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(WallPaperConstant.WALL_PAPER_HAS_VOICE, hasVoice).apply();
    }

    public static void installApk(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        AssetManager assets = context.getAssets();
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        File file = null;
        try {
            inputStream = assets.open("app-wallpaper-release.apk");
            file = new File(mWallPaperPath + WallPaperConstant.WALL_PAPER_APK_NAME);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, "com.kxg.livewallpaper.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, type);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
        context.startActivity(intent);
    }

    /**
     * 检查动态桌面插件是否需要更新
     *
     * @return true if need update
     */
    public static boolean checkNeedUpdate(Context context) {
        String versionName;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(WallPaperConstant.ENGINE_PACKAGE_NAME, 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            versionName = "";
        }
        return !WALL_PAPER_ENGINE_VERSION_NAME.equals(versionName);
    }

    public static void toSetWallPaperActivity(Context context, String videoPath) {
        toSetWallPaperActivity(context, videoPath, false);
    }

    public static void toSetWallPaperActivity(Context context) {
        toSetWallPaperActivity(context, null, true);
    }

    /**
     * @param isSetVolume 是否是单独设置声音
     */
    public static void toSetWallPaperActivity(Context context, String videoPath, boolean isSetVolume) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String className = "com.kxg.wallpaperengine.MainActivity";
        intent.setClassName(WallPaperConstant.ENGINE_PACKAGE_NAME, className);
        intent.putExtra(WallPaperConstant.VIDEO_PATH_TAG, videoPath);
        intent.putExtra(WallPaperConstant.IS_SET_VOLUME_TAG, isSetVolume);
        intent.putExtra(WallPaperConstant.HAS_VOLUME_TAG, getVolumeState(context));
        context.startActivity(intent);
    }

    public static void changeVolumeState(Context context, boolean hasVoice) {
        SharedPreferences preferences = context.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME,
                Context.MODE_PRIVATE);
        preferences.edit().putBoolean(WallPaperConstant.WALL_PAPER_HAS_VOICE, hasVoice).apply();
    }

    public static boolean getVolumeState(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME,
                Context.MODE_PRIVATE);
        return preferences.getBoolean(WallPaperConstant.WALL_PAPER_HAS_VOICE, false);
    }

}

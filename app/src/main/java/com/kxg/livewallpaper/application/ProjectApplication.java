package com.kxg.livewallpaper.application;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.kxg.livewallpaper.constant.AdConstant;
import com.kxg.livewallpaper.constant.MTAConstant;
import com.kxg.livewallpaper.wallpaper.WallPaperUtil;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatService;
import com.tencent.stat.common.StatConstants;

/**
 * Created by kuangxiaoguo on 2018/3/30.
 */

public class ProjectApplication extends Application {

    private static final String TAG = "ProjectApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        WallPaperUtil.setFilePath(this);
        try {
            StatService.startStatService(this, MTAConstant.APP_KEY, StatConstants.VERSION);
        } catch (MtaSDkException e) {
            Log.d(TAG, "onCreate: 初始化失败-->");
        }
        MobileAds.initialize(this, AdConstant.ADMOB_APP_ID);
    }
}

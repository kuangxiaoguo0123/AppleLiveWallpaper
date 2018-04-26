package com.kxg.livewallpaper.config;

import android.text.TextUtils;

import com.kxg.livewallpaper.BuildConfig;
import com.kxg.livewallpaper.factory.AdFactory;
import com.kxg.livewallpaper.factory.BaiduAdFactory;
import com.kxg.livewallpaper.factory.GooglePlayAdFactory;
import com.kxg.livewallpaper.factory.KuAnAdFactory;
import com.kxg.livewallpaper.factory.SamsungAdFactory;

/**
 * Created by kuangxiaoguo on 2018/3/30.
 */

public class AdConfig {

    public static boolean isAdOpen() {
        AdFactory factory = getAdFactory();
//        return factory == null || factory.isAdOpen();
        return false;
    }

    private static AdFactory getAdFactory() {
        AdFactory factory = null;
        String flavor = BuildConfig.FLAVOR;
        if (TextUtils.isEmpty(flavor)) {
            return null;
        }
        switch (flavor) {
            case "baidu":
                factory = new BaiduAdFactory();
                break;
            case "samsung":
                factory = new SamsungAdFactory();
                break;
            case "kuan":
                factory = new KuAnAdFactory();
                break;
            case "google_play":
                factory = new GooglePlayAdFactory();
                break;
            default:
                break;
        }
        return factory;
    }
}

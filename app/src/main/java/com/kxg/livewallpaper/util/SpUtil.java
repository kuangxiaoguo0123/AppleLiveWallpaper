package com.kxg.livewallpaper.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.kxg.livewallpaper.constant.Constant;

/**
 * Created by kuangxiaoguo on 2018/4/5.
 */

public class SpUtil {

    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences =
                context.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(key, "");
    }
}

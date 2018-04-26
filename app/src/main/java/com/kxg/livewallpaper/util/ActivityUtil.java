package com.kxg.livewallpaper.util;

import android.app.Activity;
import android.os.Build;

/**
 * Created by kuangxiaoguo on 2018/3/31.
 */

public class ActivityUtil {

    public static boolean isActivityDestroyed(Activity activity) {
        return activity == null || activity.isFinishing() ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                        activity.isDestroyed());
    }
}

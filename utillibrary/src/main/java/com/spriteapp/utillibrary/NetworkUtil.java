package com.spriteapp.utillibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kuangxiaoguo on 2018/1/12.
 */

public class NetworkUtil {

    /**
     * Judge net work is available.
     *
     * @return true if net work available.
     */
    public static boolean isAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info.isAvailable();
    }
}

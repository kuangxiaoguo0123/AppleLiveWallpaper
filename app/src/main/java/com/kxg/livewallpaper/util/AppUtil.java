package com.kxg.livewallpaper.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.kxg.livewallpaper.R;

/**
 * Created by kuangxiaoguo on 2018/4/6.
 */

public class AppUtil {

    public static void sendEmail(Context context, String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        intent.setType("message/rfc822");
        try {
            context.startActivity(Intent.createChooser(intent,
                    context.getResources().getString(R.string.choose_client)));
        } catch (Resources.NotFoundException e) {
        }
    }
}

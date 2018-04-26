package com.kxg.livewallpaper.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Created by kuangxiaoguo on 2018/4/5.
 */

public class GlideUtil {

    public static void loadImage(Context context, ImageView imageView,
                                 String path, ColorDrawable drawable) {
        if (ActivityUtil.isActivityDestroyed((Activity) context)) {
            return;
        }
        Glide.with(context)
                .load(Uri.fromFile(new File(path)))
                .placeholder(drawable)
                .error(drawable)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }
}

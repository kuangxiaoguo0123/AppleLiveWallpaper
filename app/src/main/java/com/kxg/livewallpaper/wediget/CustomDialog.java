package com.kxg.livewallpaper.wediget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kxg.livewallpaper.R;
import com.spriteapp.utillibrary.ScreenUtil;

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static CustomDialog instance(Activity activity) {
        CustomDialog dialog = new CustomDialog(activity, R.style.loading_dialog);
        dialog.setContentView(new ProgressBar(activity, null, android.R.attr.progressBarStyle),
                new ViewGroup.LayoutParams(ScreenUtil.dp2px(activity, 40),
                        ScreenUtil.dp2px(activity, 40))
        );
        return dialog;
    }
}

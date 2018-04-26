package com.kxg.livewallpaper.banner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.kxg.livewallpaper.R;

/**
 * Created by kuangxiaoguo on 2018/4/23.
 */

public class HomeBannerLayout extends BannerAdLayout {

    public HomeBannerLayout(Context context) {
        super(context);
    }

    public HomeBannerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeBannerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_banner_ad_view;
    }
}

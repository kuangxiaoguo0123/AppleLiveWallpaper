package com.kxg.livewallpaper.banner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.kxg.livewallpaper.R;

/**
 * Created by kuangxiaoguo on 2018/4/26.
 */

public class DetailBannerLayout extends BannerAdLayout {

    public DetailBannerLayout(Context context) {
        super(context);
    }

    public DetailBannerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailBannerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.detail_banner_ad_view;
    }
}

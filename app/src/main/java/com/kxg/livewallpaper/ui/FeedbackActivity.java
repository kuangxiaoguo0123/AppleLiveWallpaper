package com.kxg.livewallpaper.ui;

import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.base.BaseActivity;

/**
 * Created by kuangxiaoguo on 2018/4/6.
 */

public class FeedbackActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.back_white_image);
        mToolbar.setTitle(R.string.help_or_feedback);
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initData() {

    }
}

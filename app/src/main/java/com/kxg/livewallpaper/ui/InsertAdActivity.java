package com.kxg.livewallpaper.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.base.BaseActivity;
import com.kxg.livewallpaper.constant.AdConstant;
import com.kxg.livewallpaper.util.ActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kuangxiaoguo on 2018/3/31.
 */

public class InsertAdActivity extends BaseActivity {

    private static final String TAG = "InsertAdActivity-";
    private InterstitialAd mInsertAd;
    private boolean isAdClose;
    private AdListener mAdListener;

    @BindView(R.id.change_ad_button)
    Button mChangeAdButton;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, InsertAdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insert_ad;
    }

    @Override
    protected void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.back_white_image);
        mToolbar.setTitle(R.string.recommend_application);
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void initData() {
        loadAd();
    }

    @Override
    protected void configViews() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isAdClose && mAdListener != null) {
            mAdListener.onAdClosed();
        }
    }

    private void loadAd() {
        showDialog();
        if (mChangeAdButton != null) {
            mChangeAdButton.setVisibility(View.GONE);
        }
        mInsertAd = new InterstitialAd(this);
        mInsertAd.setAdUnitId(AdConstant.INTERSTITIAL_UNIT_ID);
        mInsertAd.loadAd(new AdRequest.Builder().build());
        mAdListener = new AdListener() {
            @Override
            public void onAdLoaded() {
                if (ActivityUtil.isActivityDestroyed(InsertAdActivity.this)) {
                    return;
                }
                dismissDialog();
                if (mChangeAdButton != null) {
                    mChangeAdButton.setVisibility(View.VISIBLE);
                }
                if (mInsertAd.isLoaded()) {
                    isAdClose = false;
                    mInsertAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                if (ActivityUtil.isActivityDestroyed(InsertAdActivity.this)) {
                    return;
                }
                dismissDialog();
                if (mChangeAdButton != null) {
                    mChangeAdButton.setVisibility(View.VISIBLE);
                }
                Toast.makeText(InsertAdActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                isAdClose = true;
            }
        };
        mInsertAd.setAdListener(mAdListener);
    }

    @OnClick(R.id.change_ad_button)
    void changeAd() {
        loadAd();
    }
}

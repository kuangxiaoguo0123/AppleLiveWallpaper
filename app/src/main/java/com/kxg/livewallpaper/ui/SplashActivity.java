package com.kxg.livewallpaper.ui;

import android.content.Intent;
import android.os.Handler;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.base.BaseActivity;
import com.kxg.livewallpaper.constant.AdConstant;
import com.kxg.livewallpaper.util.ActivityUtil;

/**
 * Created by kuangxiaoguo on 2018/4/26.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity-";
    private InterstitialAd mInsertAd;
    private boolean isAdClose;
    private AdListener mAdListener;
    private boolean hasJump = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void configViews() {
    }

    @Override
    protected void initData() {
        loadAd();
    }

    private void loadAd() {
        showDialog();
        mInsertAd = new InterstitialAd(this);
        mInsertAd.setAdUnitId(AdConstant.INTERSTITIAL_UNIT_ID);
        mInsertAd.loadAd(new AdRequest.Builder().build());
        mAdListener = new AdListener() {
            @Override
            public void onAdLoaded() {
                if (ActivityUtil.isActivityDestroyed(SplashActivity.this)) {
                    return;
                }
                dismissDialog();
                if (mInsertAd.isLoaded()) {
                    mInsertAd.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isAdClose) {
                                return;
                            }
                            toMainActivity();
                        }
                    }, 5000);
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                if (ActivityUtil.isActivityDestroyed(SplashActivity.this)) {
                    return;
                }
                dismissDialog();
                toMainActivity();
            }

            @Override
            public void onAdClosed() {
                isAdClose = true;
                toMainActivity();
            }
        };
        mInsertAd.setAdListener(mAdListener);
    }

    private void toMainActivity() {
        if (hasJump) {
            finish();
            return;
        }
        hasJump = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

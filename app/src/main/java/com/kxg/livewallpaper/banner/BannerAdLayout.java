package com.kxg.livewallpaper.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kxg.livewallpaper.R;

/**
 * Created by kuangxiaoguo on 2018/3/30.
 */

public abstract class BannerAdLayout extends LinearLayout {

    private static final String TAG = "BannerAdLayout";
    private static final int UPDATE_AD_WHAT = 1;
    private static final long UPDATE_AD_TIME = 5000;
    private Context mContext;
    private AdView mAdView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            loadAd();
        }
    };

    public BannerAdLayout(Context context) {
        this(context, null);
    }

    public BannerAdLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerAdLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        mAdView = view.findViewById(R.id.ad_view);
        addView(view);
    }

    public abstract int getLayoutId();

    public void loadAd() {
        AdRequest request = new AdRequest.Builder().build();
        mAdView.loadAd(request);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mHandler.sendEmptyMessageDelayed(UPDATE_AD_WHAT, UPDATE_AD_TIME);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                Log.d(TAG, "onAdFailedToLoad: i--->" + i);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler != null) {
            mHandler.removeMessages(UPDATE_AD_WHAT);
        }
    }
}

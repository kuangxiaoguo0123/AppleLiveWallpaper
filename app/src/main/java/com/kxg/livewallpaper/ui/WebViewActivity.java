package com.kxg.livewallpaper.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by kuangxiaoguo on 2018/3/21.
 */

public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";
    public static final String URL_TAG = "url_tag";
    public static final String TITLE_TAG = "title_tag";

    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.back_white_image);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(getIntent().getStringExtra(TITLE_TAG));
    }

    @Override
    protected void configViews() {
    }

    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class)
                .putExtra(URL_TAG, url)
                .putExtra(TITLE_TAG, title);
        context.startActivity(intent);
    }

    protected void initData() {
        initView();
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL_TAG);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mWebView.loadUrl(url);
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        mWebView.setWebViewClient(mClient);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    WebViewClient mClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            dismissDialog();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
}

package com.kxg.livewallpaper.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.util.ActivityUtil;
import com.kxg.livewallpaper.wediget.CustomDialog;

import java.security.Permission;
import java.security.Permissions;

import butterknife.ButterKnife;

/**
 * Created by kuangxiaoguo on 2018/3/31.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity-";
    protected static final int STORAGE_REQUEST_CODE = 10001;
    protected Toolbar mToolbar;
    protected CustomDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.tool_bar);
        if (mToolbar != null) {
            initToolbar();
            setSupportActionBar(mToolbar);
        }
        configViews();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initToolbar();

    protected abstract void configViews();

    protected abstract void initData();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        if (mDialog == null) {
            mDialog = CustomDialog.instance(this);
            mDialog.setCancelable(true);
        }
        mDialog.show();
    }

    public void dismissDialog() {
        if (ActivityUtil.isActivityDestroyed(this)) {
            return;
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    protected boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}

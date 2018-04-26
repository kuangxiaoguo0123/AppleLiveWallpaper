package com.kxg.livewallpaper.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.adapter.WallpaperAdapter;
import com.kxg.livewallpaper.banner.HomeBannerLayout;
import com.kxg.livewallpaper.base.BaseActivity;
import com.kxg.livewallpaper.constant.Constant;
import com.kxg.livewallpaper.model.UpdateHeaderImage;
import com.kxg.livewallpaper.model.UpdateSignatureInfo;
import com.kxg.livewallpaper.model.UpdateVoiceState;
import com.kxg.livewallpaper.mvp.contract.HomeContract;
import com.kxg.livewallpaper.mvp.persenter.HomePresenter;
import com.kxg.livewallpaper.util.DialogUtil;
import com.kxg.livewallpaper.util.GlideUtil;
import com.kxg.livewallpaper.util.PackageUtil;
import com.kxg.livewallpaper.util.SpUtil;
import com.kxg.livewallpaper.wallpaper.MediaModel;
import com.kxg.livewallpaper.wallpaper.VideoUtil;
import com.kxg.livewallpaper.wallpaper.WallListItemDecoration;
import com.kxg.livewallpaper.wallpaper.WallPaperConstant;
import com.kxg.livewallpaper.wallpaper.WallPaperUtil;
import com.spriteapp.utillibrary.CollectionUtil;
import com.spriteapp.utillibrary.ScreenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * Created by kuangxiaoguo on 2018/3/30.
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View {

    private static final String TAG = "MainActivity-";

    private static final int HORIZONTAL_SPACE = 1;
    private static final int VERTICAL_SPACE = 1;
    private static final String GITHUB_URL = "https://github.com/kuangxiaoguo0123";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.fab)
    FloatingActionButton mFloatActionButton;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.empty_text_view)
    TextView mEmptyTextView;

    @BindView(R.id.home_banner_layout)
    HomeBannerLayout mBannerLayout;

    ImageView mHeadImageView;

    private HomePresenter mPresenter;
    private WallpaperAdapter mAdapter;
    private List<MediaModel> mModelList;
    private String mImageCachePath;
    private TextView mSignatureTextView;
    private String mSignatureText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        VideoUtil.scanNativeImageFile(this);
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mModelList = new ArrayList<>();
        setAdapter();
        mBannerLayout.loadAd();
        if (hasPermission()) {
            mPresenter.getMediaData();
        }
        onEventMainThread(new UpdateHeaderImage());
    }

    @Override
    protected void configViews() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mFloatActionButton.setImageResource(WallPaperUtil.getVolumeState(this)
                ? R.drawable.ic_volume_up_white_24dp :
                R.drawable.ic_volume_off_white_24dp);
        mFloatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasVoice = WallPaperUtil.getVolumeState(getMyContext());
                WallPaperUtil.changeVolumeState(getMyContext(), !hasVoice);
                if (PackageUtil.isAppInstall(getMyContext(), WallPaperConstant.ENGINE_PACKAGE_NAME)) {
                    WallPaperUtil.toSetWallPaperActivity(getMyContext());
                }
                String show = getResources().getString(hasVoice ? R.string.voice_being_close :
                        R.string.voice_being_open);
                mFloatActionButton.setImageResource(hasVoice ? R.drawable.ic_volume_off_white_24dp :
                        R.drawable.ic_volume_up_white_24dp);
                Snackbar.make(view, show, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecyclerView.addItemDecoration(new WallListItemDecoration(ScreenUtil.dp2px(this, HORIZONTAL_SPACE),
                ScreenUtil.dp2px(this, VERTICAL_SPACE)));
        mRecyclerView.setLayoutManager(manager);
        mSwipeLayout.setEnabled(false);
        mSwipeLayout.setColorSchemeColors(Color.parseColor("#4CAF50"));
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(true);
                if (mPresenter != null) {
                    mPresenter.getMediaData();
                }
            }
        });
        mHeadImageView = mNavigationView.getHeaderView(0).findViewById(R.id.head_image_view);
        mSignatureTextView = mNavigationView.getHeaderView(0).findViewById(R.id.signature_textview);
        onEventMainThread(new UpdateSignatureInfo());
        mSignatureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignatureActivity.startActivity(getMyContext(), mSignatureText);
            }
        });
    }

    public void onEventMainThread(UpdateSignatureInfo info) {
        mSignatureText = SpUtil.getString(this, Constant.SIGNATURE_DATA);
        if (TextUtils.isEmpty(mSignatureText)) {
            mSignatureTextView.setText(R.string.signature);
        } else {
            mSignatureTextView.setText(mSignatureText);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(this, FeedbackActivity.class));
        } else if (id == R.id.nav_email) {
            startActivity(new Intent(this, EmailActivity.class));
        } else if (id == R.id.nav_about) {
            WebViewActivity.startActivity(this, GITHUB_URL, getResources().getString(R.string.about_author));
        }
        mDrawerLayout.closeDrawer(Gravity.START);
        return true;
    }

    @Override
    public void showProgress() {
        showDialog();
    }

    @Override
    public void dismissProgress() {
        dismissDialog();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void setMediaList(List<MediaModel> modelList) {
        if (mSwipeLayout != null) {
            mSwipeLayout.setEnabled(true);
            mSwipeLayout.setRefreshing(false);
        }
        if (mModelList == null) {
            mModelList = new ArrayList<>();
        }
        mModelList.clear();
        if (modelList != null) {
            mModelList.addAll(modelList);
        }
        if (TextUtils.isEmpty(mImageCachePath) && !CollectionUtil.isEmpty(mModelList)) {
            SpUtil.putString(this, Constant.NAVIGATIONVIEW_HEADER_IMAGE_PATH, mModelList.get(0).getPath());
            onEventMainThread(new UpdateHeaderImage());
        }
        setAdapter();
        if (CollectionUtil.isEmpty(mModelList)) {
            mEmptyTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void onEventMainThread(UpdateHeaderImage headerImage) {
        if (mHeadImageView == null) {
            return;
        }
        mImageCachePath = SpUtil.getString(this, Constant.NAVIGATIONVIEW_HEADER_IMAGE_PATH);
        if (TextUtils.isEmpty(mImageCachePath)) {
            return;
        }
        File file = new File(mImageCachePath);
        if (!file.exists()) {
            return;
        }
        GlideUtil.loadImage(this, mHeadImageView, mImageCachePath, new ColorDrawable(Color.parseColor("#F44336")));
    }

    public void onEventMainThread(UpdateVoiceState state) {
        if (mFloatActionButton != null) {
            mFloatActionButton.performClick();
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new WallpaperAdapter(this, mModelList, HORIZONTAL_SPACE);
            mRecyclerView.setAdapter(mAdapter);
            return;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.getMediaData();
            } else {
                DialogUtil.showPermissionSetDialog(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}

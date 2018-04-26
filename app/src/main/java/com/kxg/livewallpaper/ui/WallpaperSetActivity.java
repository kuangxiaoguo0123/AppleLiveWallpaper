package com.kxg.livewallpaper.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.base.BaseActivity;
import com.kxg.livewallpaper.constant.Constant;
import com.kxg.livewallpaper.model.UpdateHeaderImage;
import com.kxg.livewallpaper.util.FileUtil;
import com.kxg.livewallpaper.util.PackageUtil;
import com.kxg.livewallpaper.util.SpUtil;
import com.kxg.livewallpaper.wallpaper.BlurTransformation;
import com.kxg.livewallpaper.wallpaper.WallPaperConstant;
import com.kxg.livewallpaper.wallpaper.WallPaperUtil;
import com.kxg.livewallpaper.banner.DetailBannerLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kuangxiaoguo on 2018/4/3.
 */

public class WallpaperSetActivity extends BaseActivity {

    private static final String TAG = "WallpaperSetActivity-";
    public static final String VIDEO_URL = "video_url";
    private static final String PLAY_COPY_VIDEO_NAME = "/f8f2fh8f2fh82f72f7";
    private static final int INSTALL_APK_WHAT = 1;
    private Context mContext;

    public static void startActivity(Context context, String videoUrl) {
        Intent intent = new Intent(context, WallpaperSetActivity.class)
                .putExtra(VIDEO_URL, videoUrl);
        context.startActivity(intent);
    }

    @BindView(R.id.video_view)
    VideoView mVideoView;

    @BindView(R.id.image_view)
    ImageView mPreviewImageView;

    private String mVideoUrl;

    @BindView(R.id.detail_banner_layout)
    DetailBannerLayout mDetailBannerLayout;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INSTALL_APK_WHAT:
                    WallPaperUtil.installApk(WallpaperSetActivity.this);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return R.layout.activity_wallpaper_set;
    }

    @Override
    protected void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.back_white_image);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        mToolbar.setTitle("");
    }

    @Override
    protected void configViews() {
        Intent intent = getIntent();
        mVideoUrl = intent.getStringExtra(VIDEO_URL);
        setTopMargin(mToolbar);
    }

    @Override
    protected void initData() {
        mContext = this;
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#e6e6e6"));
        Glide.with(this)
                .load(Uri.fromFile(new File(mVideoUrl)))
                .bitmapTransform(new BlurTransformation(this, 15))
                .error(colorDrawable)
                .placeholder(colorDrawable)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mPreviewImageView);
        startPlay();
        mDetailBannerLayout.loadAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mVideoUrl)) {
            startPlay();
        }
    }

    private void startPlay() {
        mVideoView.setVideoPath(mVideoUrl);
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        mp.setLooping(true);
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            if (mPreviewImageView != null) {
                                mPreviewImageView.animate().setDuration(1000).alpha(0.0f);
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mp != null) {
                    mp.setDisplay(null);
                    mp.reset();
                    mp.setDisplay(mVideoView.getHolder());
                }
            }
        });
    }

    public void setTopMargin(View v) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        int topMargin;
        topMargin = (int) getResources().getDimension(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ?
                R.dimen.wall_paper_set_icon_top_margin : R.dimen.wall_paper_set_icon_api_below_19_top_margin);
        params.topMargin = topMargin;
        v.setLayoutParams(params);
    }

    private Disposable mCopyDisposable;

    @OnClick(R.id.set_button)
    void setToWallpaper() {
        if (!PackageUtil.isAppInstall(this, WallPaperConstant.ENGINE_PACKAGE_NAME) ||
                WallPaperUtil.checkNeedUpdate(this)) {
            mHandler.sendEmptyMessageDelayed(INSTALL_APK_WHAT, 1000);
            Toast.makeText(this, getResources().getString(R.string.install_wall_paper_engine), Toast.LENGTH_LONG).show();
            return;
        }
        //copy文件名称
        final String copyFilePath = WallPaperUtil.getWallPaperPath() + PLAY_COPY_VIDEO_NAME;
        showDialog();
        Observable.just(1).map(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer integer) throws Exception {
                return FileUtil.copyFile(mVideoUrl, copyFilePath);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCopyDisposable = d;
                    }

                    @Override
                    public void onNext(Boolean isCopySuccess) {
                        SpUtil.putString(mContext, Constant.NAVIGATIONVIEW_HEADER_IMAGE_PATH, mVideoUrl);
                        EventBus.getDefault().post(new UpdateHeaderImage());
                        WallPaperUtil.toSetWallPaperActivity(WallpaperSetActivity.this,
                                isCopySuccess ? copyFilePath : mVideoUrl);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        finish();
                        if (mCopyDisposable != null) {
                            mCopyDisposable.dispose();
                            mCopyDisposable = null;
                        }
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                        finish();
                        if (mCopyDisposable != null) {
                            mCopyDisposable.dispose();
                            mCopyDisposable = null;
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.stopPlayback();
    }

}

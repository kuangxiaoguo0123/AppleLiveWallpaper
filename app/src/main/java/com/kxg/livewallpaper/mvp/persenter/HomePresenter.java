package com.kxg.livewallpaper.mvp.persenter;

import com.kxg.livewallpaper.mvp.RxPresenter;
import com.kxg.livewallpaper.mvp.contract.HomeContract;
import com.kxg.livewallpaper.wallpaper.MediaModel;
import com.kxg.livewallpaper.wallpaper.VideoUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kuangxiaoguo on 2018/4/3.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";
    private Disposable mDisposable;
    private List<MediaModel> mVideoList;

    @Override
    public void getMediaData() {
        if (mView == null) {
            return;
        }
        mView.showProgress();
        Observable.just(1).map(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer integer) throws Exception {
                mVideoList = VideoUtil.getVideoInfo(mView.getMyContext());
                return true;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (mView != null) {
                            mView.setMediaList(mVideoList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.onError(e);
                            mView.dismissProgress();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mView != null) {
                            mView.dismissProgress();
                        }
                    }
                });
        addDisposable(mDisposable);
    }
}

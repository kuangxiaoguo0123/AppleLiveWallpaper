package com.kxg.livewallpaper.base;

import android.content.Context;

/**
 * Created by kuangxiaoguo on 2018/4/3.
 */

public interface BaseContract {

    interface BaseView {

        void showProgress();

        void dismissProgress();

        Context getMyContext();

        void onError(Throwable e);
    }

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }
}

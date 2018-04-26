package com.kxg.livewallpaper.mvp;

import com.kxg.livewallpaper.base.BaseContract;
import com.spriteapp.utillibrary.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by kuangxiaoguo on 2018/4/3.
 */

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    private List<Disposable> mDisposableList;

    @Override
    public void attachView(T view) {
        mView = view;
        mDisposableList = new ArrayList<>();
    }

    @Override
    public void detachView() {
        mView = null;
        dispose();
    }

    protected void dispose() {
        if (!CollectionUtil.isEmpty(mDisposableList)) {
            for (Disposable disposable : mDisposableList) {
                if (disposable == null) {
                    continue;
                }
                disposable.dispose();
            }
            mDisposableList = null;
        }
    }

    protected void addDisposable(Disposable disposable) {
        if (mDisposableList == null) {
            mDisposableList = new ArrayList<>();
        }
        mDisposableList.add(disposable);
    }
}

package com.kxg.livewallpaper.mvp.contract;

import com.kxg.livewallpaper.base.BaseContract;
import com.kxg.livewallpaper.wallpaper.MediaModel;

import java.util.List;

/**
 * Created by kuangxiaoguo on 2018/4/3.
 */

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void setMediaList(List<MediaModel> modelList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getMediaData();
    }
}

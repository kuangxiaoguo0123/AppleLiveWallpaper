package com.kxg.livewallpaper.factory;

/**
 * Created by kuangxiaoguo on 2018/4/1.
 */

public abstract class AdFactory {

    protected String TRUE_TAG = "true";

    public boolean isAdOpen() {
        return TRUE_TAG.equals(getConfigValue());
    }

    protected abstract String getConfigValue();
}

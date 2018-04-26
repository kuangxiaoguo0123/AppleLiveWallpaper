package com.kxg.livewallpaper.enumeration;

/**
 * Created by kuangxiaoguo on 2018/4/1.
 */

public enum AdEnum {

    BAI_DU("is_baidu_ad_open"),
    KU_AN("is_kuan_ad_open"),
    SAMSUNG("is_samsung_ad_open"),
    GOOGLE_PLAY("is_googleplay_ad_open");

    String value;

    AdEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

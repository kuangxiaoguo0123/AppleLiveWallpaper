package com.kxg.livewallpaper.factory;

import com.kxg.livewallpaper.enumeration.AdEnum;
import com.tencent.stat.StatConfig;

/**
 * Created by kuangxiaoguo on 2018/4/1.
 */

public class BaiduAdFactory extends AdFactory {

    @Override
    protected String getConfigValue() {
        return StatConfig.getCustomProperty(AdEnum.BAI_DU.toString());
    }
}

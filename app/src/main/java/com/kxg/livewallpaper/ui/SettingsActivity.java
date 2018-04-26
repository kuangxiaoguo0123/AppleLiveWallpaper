package com.kxg.livewallpaper.ui;

import android.view.View;
import android.widget.CheckBox;

import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.base.BaseActivity;
import com.kxg.livewallpaper.model.UpdateVoiceState;
import com.kxg.livewallpaper.wallpaper.WallPaperUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by kuangxiaoguo on 2018/4/5.
 */

public class SettingsActivity extends BaseActivity {

    private static final String TAG = "SettingsActivity-";
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.back_white_image);
        mToolbar.setTitle(R.string.settings);
    }

    @Override
    protected void configViews() {
    }

    @OnClick(R.id.volume_layout)
    void changeVolume() {
        EventBus.getDefault().post(new UpdateVoiceState());
        mCheckBox.setChecked(WallPaperUtil.getVolumeState(this));
    }

    @Override
    protected void initData() {
        mCheckBox.setChecked(WallPaperUtil.getVolumeState(this));
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVolume();
            }
        });
    }
}

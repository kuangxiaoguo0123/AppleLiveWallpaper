package com.kxg.livewallpaper.wediget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 壁纸全屏videoView
 * Created by kuangxiaoguo on 2017/8/12.
 */

public class WallPaperVideoView extends VideoView {

    public WallPaperVideoView(Context context) {
        this(context, null);
    }

    public WallPaperVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WallPaperVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}

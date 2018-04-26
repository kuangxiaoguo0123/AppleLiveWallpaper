package com.kxg.livewallpaper.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.ui.WallpaperSetActivity;
import com.kxg.livewallpaper.util.GlideUtil;
import com.kxg.livewallpaper.wallpaper.MediaModel;
import com.spriteapp.utillibrary.CollectionUtil;
import com.spriteapp.utillibrary.ScreenUtil;

import java.io.File;
import java.util.List;

/**
 * Created by kuangxiaoguo on 2018/3/21.
 */

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallPaperViewHolder> {

    private static final String TAG = "WallpaperAdapter";
    private static final int IMAGE_WIDTH = 119;
    private static final int IMAGE_HEIGHT = 158;
    private Context mContext;
    private List<MediaModel> mMediaList;
    private int mImageWidth;
    private int mImageHeight;
    private ColorDrawable mDrawable;

    public WallpaperAdapter(Context context, List<MediaModel> modelList, int horizontalSpace) {
        mContext = context;
        mMediaList = modelList;
        mImageWidth = (ScreenUtil.getScreenWidth(context) - 2
                * ScreenUtil.dp2px(context, horizontalSpace)) / 3;
        mImageHeight = mImageWidth * IMAGE_HEIGHT / IMAGE_WIDTH;
        mDrawable = new ColorDrawable(Color.parseColor("#e6e6e6"));
    }

    @Override
    public WallPaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_view, parent, false);
        return new WallPaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WallPaperViewHolder holder, int position) {
        if (CollectionUtil.isEmpty(mMediaList)) {
            return;
        }
        setImageAttributes(holder);
        holder.logoImageView.measure(0, 0);
        MediaModel mediaModel = mMediaList.get(position);
        if (holder.logoImageView == null) {
            return;
        }
        GlideUtil.loadImage(mContext, holder.logoImageView, mediaModel.getPath(), mDrawable);
        holder.logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                if (layoutPosition < 0 || layoutPosition > mMediaList.size()) {
                    return;
                }
                MediaModel model = mMediaList.get(layoutPosition);
                if (model == null) {
                    return;
                }
                WallpaperSetActivity.startActivity(mContext,
                        model.getPath());
            }
        });
    }

    private void setImageAttributes(WallPaperViewHolder holder) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                holder.logoImageView.getLayoutParams();
        params.width = mImageWidth;
        params.height = mImageHeight;
        holder.logoImageView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return CollectionUtil.isEmpty(mMediaList) ? 0 : mMediaList.size();
    }

    class WallPaperViewHolder extends RecyclerView.ViewHolder {

        ImageView logoImageView;

        public WallPaperViewHolder(View itemView) {
            super(itemView);
            logoImageView = itemView.findViewById(R.id.logo_image_view);
        }
    }
}

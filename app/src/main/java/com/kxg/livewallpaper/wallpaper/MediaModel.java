package com.kxg.livewallpaper.wallpaper;

/**
 * Created by kuangxiaoguo on 2017/8/22.
 */

public class MediaModel {

    private String path;
    private int duration;
    private long size;
    private String displayName;
    private int height;
    private int width;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "MediaModel{" +
                "path='" + path + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", displayName='" + displayName + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}

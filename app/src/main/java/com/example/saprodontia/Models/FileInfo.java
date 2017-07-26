package com.example.saprodontia.Models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */


public class FileInfo extends DataSupport implements Parcelable {

    public static final Creator<FileInfo> CREATOR = new Creator<FileInfo>() {
        @Override
        public FileInfo createFromParcel(Parcel in) {
            return new FileInfo(in);
        }

        @Override
        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };

    private transient Drawable icon;
    private transient Bitmap bitmap;
    private String name;
    private String location;
    private String size;
    private long initSize;
    private long progress;
    private int type;
//    private List<FileInfo> childs;
    private boolean isExpand;
    private long mid;

    public long getId() {
        return mid;
    }

    public void setId(long mid) {
        this.mid = mid;
    }

    private boolean isuploading = false;

    public FileInfo() {
    }

    protected FileInfo(Parcel in) {
        name = in.readString();
        location = in.readString();
        size = in.readString();
        initSize = in.readLong();
    }

    public boolean isuploading() {
        return isuploading;
    }

    public void setIsuploading(boolean isuploading) {
        this.isuploading = isuploading;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

//    public List<FileInfo> getChilds() {
//        return childs;
//    }
//
//    public void setChilds(List<FileInfo> childs) {
//        this.childs = childs;
//    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getInitSize() {
        return initSize;
    }

    public void setInitSize(long initSize) {
        this.initSize = initSize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(size);
        dest.writeLong(initSize);
    }
}
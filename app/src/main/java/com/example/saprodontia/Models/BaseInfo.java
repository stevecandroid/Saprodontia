package com.example.saprodontia.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class BaseInfo implements Parcelable {

    protected String name;
    protected String location;
    protected String format;
    protected String size;

    protected BaseInfo(){
    }


    protected BaseInfo(Parcel in) {
        name = in.readString();
        location = in.readString();
        format = in.readString();
        size = in.readString();
    }

    public static final Creator<BaseInfo> CREATOR = new Creator<BaseInfo>() {
        @Override
        public BaseInfo createFromParcel(Parcel in) {
            return new BaseInfo(in);
        }

        @Override
        public BaseInfo[] newArray(int size) {
            return new BaseInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(format);
        dest.writeString(size);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

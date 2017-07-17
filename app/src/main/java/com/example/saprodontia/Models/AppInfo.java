package com.example.saprodontia.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by 铖哥 on 2017/7/16.
 */


public class AppInfo {

    private Drawable icon;
    private String name;
    private float size;
    private String location;

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

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
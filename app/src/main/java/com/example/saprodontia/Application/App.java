package com.example.saprodontia.Application;

import android.app.Application;
import android.content.Context;

import com.example.saprodontia.Models.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class App extends Application{

    private static Context context;

    private ArrayList<FileInfo>  senDatas;

    private List<FileInfo> fileInfos;
    private List<FileInfo> videoInfos;
    private List<FileInfo> imageInfos;
    private List<FileInfo> musicInfos;
    private List<FileInfo> appInfos;

    private boolean execm  =false;
    private boolean execv =false;
    private boolean execd =false;
    private boolean execp =false;
    private boolean execa = false;

    public ArrayList<FileInfo> getSenDatas() {
        return senDatas;
    }

    public List<FileInfo> getVideoInfos() {
        return videoInfos;
    }

    public List<FileInfo> getImageInfos() {
        return imageInfos;
    }

    public List<FileInfo> getMusicInfos() {
        return musicInfos;
    }

    public List<FileInfo> getAppInfos() {
        return appInfos;
    }

    public ArrayList<FileInfo> getFileInfos() {
        return senDatas;
    }

    public boolean isExecm() {
        return execm;
    }

    public void setExecm(boolean execm) {
        this.execm = execm;
    }

    public boolean isExecv() {
        return execv;
    }

    public void setExecv(boolean execv) {
        this.execv = execv;
    }

    public boolean isExecd() {
        return execd;
    }

    public void setExecd(boolean execd) {
        this.execd = execd;
    }

    public boolean isExecp() {
        return execp;
    }

    public void setExecp(boolean execp) {
        this.execp = execp;
    }

    public boolean isExeca() {
        return execa;
    }

    public void setExeca(boolean execa) {
        this.execa = execa;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        senDatas = new ArrayList<>();
        fileInfos = new ArrayList<>();
        musicInfos = new ArrayList<>();
        videoInfos = new ArrayList<>();
        imageInfos = new ArrayList<>();
        appInfos = new ArrayList<>();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }




}

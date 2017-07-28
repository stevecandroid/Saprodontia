package com.example.saprodontia.Application;

import android.app.Application;
import android.content.Context;

import com.example.saprodontia.Models.ContentModle;
import com.example.saprodontia.Models.FileInfo;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class App extends LitePalApplication{

    private static Context context;

    private ArrayList<FileInfo>  senDatas;
    private ArrayList<FileInfo> uploadingDatas;
    private ArrayList<FileInfo> uploadedDatas;
    private ArrayList<FileInfo> downloadingDatas;
    private ArrayList<FileInfo> downloadedDatas;

    private List<FileInfo> fileInfos;
    private List<FileInfo> videoInfos;
    private List<FileInfo> imageFolderInfos;
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
        return imageFolderInfos;
    }

    public List<FileInfo> getMusicInfos() {
        return musicInfos;
    }

    public List<FileInfo> getAppInfos() {
        return appInfos;
    }

    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }

    public ArrayList<FileInfo> getUploadingDatas() {
        return uploadingDatas;
    }

    public ArrayList<FileInfo> getUploadedDatas() {
        return uploadedDatas;
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

    public ArrayList<FileInfo> getDownloadingDatas() {
        return downloadingDatas;
    }


    public ArrayList<FileInfo> getDownloadedDatas() {
        return downloadedDatas;
    }



    @Override
    public void onCreate() {
        context = getApplicationContext();
        senDatas = new ArrayList<>();
        fileInfos = new ArrayList<>();
        musicInfos = new ArrayList<>();
        videoInfos = new ArrayList<>();
        imageFolderInfos = new ArrayList<>();
        appInfos = new ArrayList<>();
        uploadingDatas = new ArrayList<>();
        uploadedDatas = new ArrayList<>();
        downloadedDatas = new ArrayList<>();
        uploadingDatas = new ArrayList<>();

        LitePal.getDatabase();
        super.onCreate();
    }


    public static Context getContext(){
        return context;
    }




}

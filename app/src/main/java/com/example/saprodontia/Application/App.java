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

    public ArrayList<FileInfo> getFileInfos() {
        return senDatas;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        senDatas = new ArrayList<>();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }



}

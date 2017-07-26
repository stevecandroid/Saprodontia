package com.example.saprodontia.Models;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.MathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class AppInfoModle {


    private  PackageManager pm = App.getContext().getPackageManager();
    private  List<ApplicationInfo> infos;
    private  List<FileInfo> initInfos ;
    private  OnDataChangeListener mOnDataChangeListener;
    private App app;

    public AppInfoModle(Context context) {
        app = (App)(context.getApplicationContext());
        initInfos =app.getAppInfos();

    }

    private  List<ApplicationInfo>  getApplicationInfos(){
        List<ApplicationInfo> infos  = pm.getInstalledApplications(0);
        removeSystemApp(infos);
        return infos;
    }

    private  long getFileSize(String path){
        File file = new File(path);
        return file.length();
    }

    public  List<FileInfo> initSimAppInfos (){
        if(!app.isExeca())
        new LoadTask().execute();
        app.setExeca(true);
        return initInfos;
    }

    private  void removeSystemApp(List<ApplicationInfo> infos) {
        for(int i = infos.size() -1  ; i > 0 ; i-- ){
            if( ( infos.get(i).flags & ApplicationInfo.FLAG_SYSTEM) > 0 ){
                infos.remove(infos.get(i));
            }
        }
    }

    public interface OnDataChangeListener{
        void onDataChange();
    }

    public void setmOnDataChangeListener(OnDataChangeListener listener){
        mOnDataChangeListener = listener;
    }

    class LoadTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            infos = getApplicationInfos();
            int t = 0 ;
            long size = 0;
            String sourceDir;
            for(ApplicationInfo i : infos){

                FileInfo ai = new FileInfo();
                sourceDir = i.sourceDir;

                ai.setIcon(i.loadIcon(pm));
                ai.setLocation(sourceDir);
                ai.setName(i.loadLabel(pm).toString()+i.sourceDir.substring(sourceDir.lastIndexOf('.'),sourceDir.length()));
                ai.setSize(MathUtil.bytoKbOrMb(size=getFileSize(sourceDir)));
                ai.setInitSize(size);

                initInfos.add(ai);

                if(t % 10 == 0  || t < 10)
                publishProgress();
                t++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mOnDataChangeListener.onDataChange();
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mOnDataChangeListener.onDataChange();
            super.onPostExecute(aBoolean);
        }
    }

    public static Drawable getApkIcon(String path , Context context){
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
            ApplicationInfo info = packageInfo.applicationInfo;
            info.sourceDir = path;
            info.publicSourceDir = path;
            return info.loadIcon(pm);
    }


}

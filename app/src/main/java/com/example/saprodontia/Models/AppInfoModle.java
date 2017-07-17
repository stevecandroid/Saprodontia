package com.example.saprodontia.Models;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.example.saprodontia.Application.App;
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
    private  List<AppInfo> initInfos = new ArrayList<>();
    private  OnDataChangeListener mOnDataChangeListener;


    private  List<ApplicationInfo>  getApplicationInfos(){
        List<ApplicationInfo> infos  = pm.getInstalledApplications(0);
        removeSystemApp(infos);
        return infos;
    }

    private  float getFileSize(String path){
        File file = new File(path);
        return MathUtil.keepTwoDecimals(file.length()/1024.f);
    }

    public  List<AppInfo> initSimAppInfos (){
        new LoadTask().execute();
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
            for(ApplicationInfo i : infos){
                AppInfo ai = new AppInfo();
                ai.setIcon(i.loadIcon(pm));
                ai.setName(i.loadLabel(pm).toString());
                ai.setSize(getFileSize(i.sourceDir));
                ai.setLocation(i.sourceDir);
                initInfos.add(ai);
                ++t;
                if(t % 10 == 0)
                publishProgress();
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


}

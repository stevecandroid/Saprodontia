package com.example.saprodontia.Models;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.Constant.Constant;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.db.DbHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class UpLoadModel {

    private onTaskStateChangeListener onTaskStateChangeListener;
    private UploadManager uploadManager;
    private static UpLoadModel upLoadModel = new UpLoadModel(App.getContext().getApplicationContext());
    private List<FileInfo> uploadDatas;

    private UpLoadModel(Context context) {
        uploadDatas = ((App)context).getUploadDatas();
    }

    public static UpLoadModel getInstance(){
        return upLoadModel;
    }

    private  String createUpToken(String accessKey, String secretKey, String bucket){
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }

    public void downFromCloud(List<String> names){

        for(int i = 0 ; i < names.size() ; i++){
            try {
                String encodeFileName = URLEncoder.encode(names.get(i),"utf-8");
                String finalUrl = Constant.baseLink+encodeFileName;
                LogUtil.e(finalUrl);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    public void upLoadFile(List<FileInfo> fileInfos){
            new UpLoadTask(fileInfos).execute();
    }

    public interface onTaskStateChangeListener{
        void onTaskStart(List<FileInfo> readyFile);
        void onPercentChanged(String key, double progress);
        void onSingleTaskFinish(FileInfo fileInfo);
        void onTaskFinish();

    }

    public void setTaskStateChangeListener(onTaskStateChangeListener onTaskStateChangeListener) {
        this.onTaskStateChangeListener = onTaskStateChangeListener;
    }


    private class UpLoadTask extends AsyncTask<Void,Object,Void>{
        private List<FileInfo> fileInfos;
        private boolean allow = true;

         public UpLoadTask(List<FileInfo> fileInfos) {
             this.fileInfos = fileInfos;
         }

        @Override
        protected void onPreExecute() {
            File tempDir = new File(Constant.tempDir);

            if(!tempDir.exists()){
                tempDir.mkdirs();
            }

            KeyGenerator keyGen = new KeyGenerator(){
                public String gen(String key, File file){
                    return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                }
            };

            try {
                Recorder recorder = new FileRecorder(Constant.tempDir);
                Configuration config = new Configuration.Builder().recorder(recorder,keyGen).build();
                uploadManager = new UploadManager(config);
                if(onTaskStateChangeListener!=null){
                    onTaskStateChangeListener.onTaskStart(fileInfos);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            DbHelper.changeFileInfoState(fileInfos,true);
            DbHelper.AddFile(fileInfos);
            uploadDatas.addAll(fileInfos);

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i = 0 ; i < fileInfos.size()  ; i++) {
                final FileInfo fileInfo = fileInfos.get(i);
                allow = false;

                String upToken = createUpToken(Constant.AccessKey, Constant.secretKey, Constant.PhotoBucket);

                uploadManager.put(new File(fileInfo.getLocation()), fileInfo.getName(), upToken, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            if(onTaskStateChangeListener!=null)
                            onTaskStateChangeListener.onSingleTaskFinish(fileInfo);
                            uploadDatas.remove(fileInfo);
                            fileInfo.setIsuploading(false);
                            fileInfo.save();
                            LogUtil.e("Upload Success");
                        } else {
                            LogUtil.e("Upload Fail");
                        }
                        LogUtil.e(key + ",\r\n " + info + ",\r\n " + response);
                        allow = true;
                    }
                },new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        publishProgress(key, percent);
                    }
                }, new UpCancellationSignal() {
                    @Override
                    public boolean isCancelled() {
                        return false;
                    }
                }));

                while(!allow){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            if(onTaskStateChangeListener!=null)
                onTaskStateChangeListener.onPercentChanged(values[0].toString() ,Double.valueOf(values[1].toString()));

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(onTaskStateChangeListener!=null){
                onTaskStateChangeListener.onTaskFinish();
            }
            super.onPostExecute(aVoid);
        }
    }
}

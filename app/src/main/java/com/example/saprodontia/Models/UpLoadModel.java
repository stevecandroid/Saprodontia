package com.example.saprodontia.Models;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.Constant.Constant;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.ToastUtil;
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
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class UpLoadModel {

    private static UpLoadModel upLoadModel = new UpLoadModel(App.getContext().getApplicationContext());
    private onTaskStateChangeListener onTaskStateChangeListener;
    private onTaskFinishLishtener onTaskFinishLishtener;
    private UploadManager uploadManager;
    private App app;


    private UpLoadModel(Context context) {
        app =  ((App) (App.getContext().getApplicationContext()));
    }

    public static UpLoadModel getInstance(){
        return upLoadModel;
    }

    private  String createUpToken(String accessKey, String secretKey, String bucket){
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }


    public void upLoadFile(List<FileInfo> sendDatas){
            new UpLoadTask(sendDatas).execute();
    }

    public void setTaskStateChangeListener(onTaskStateChangeListener onTaskStateChangeListener) {
        this.onTaskStateChangeListener = onTaskStateChangeListener;
    }

    public void setOnTaskFinishLishtener(UpLoadModel.onTaskFinishLishtener onTaskFinishLishtener) {
        this.onTaskFinishLishtener = onTaskFinishLishtener;
    }

    public interface onTaskStateChangeListener{
        void onTaskStart(List<FileInfo> readyFile);
        void onPercentChanged(String key, double progress);
        void onSingleTaskFinish(FileInfo fileInfo);
    }

    public interface onTaskFinishLishtener{
        void onTaskFinish(FileInfo sendDatas);
    }

    private class UpLoadTask extends AsyncTask<Void,Object,Void>{
        private List<FileInfo> sendDatas;
        private boolean allow = true;

         public UpLoadTask(List<FileInfo> sendDatas) {
             this.sendDatas = sendDatas;
         }

        @Override
        protected void onPreExecute() {

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
                    onTaskStateChangeListener.onTaskStart(sendDatas);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            List<FileInfo> Sqldata = DataSupport.findAll(FileInfo.class);

            List<FileInfo> templist = new ArrayList<>();

            for(int i = 0 ; i < Sqldata.size() ; i++){
                FileInfo temp = Sqldata.get(i);
                for (int j = sendDatas.size()-1 ; j >=0 ; j--) {
                    if(sendDatas.get(j).getName().equals(temp.getName()) ){
                        if(temp.isuploading()){
                            templist.add(sendDatas.get(j));
                        }
                        sendDatas.remove(j);
                    }
                    if(j == sendDatas.size()) j = sendDatas.size()-1;
                }
            }

            DbHelper.changeFileInfoState(sendDatas,true);
            ((App) (App.getContext().getApplicationContext())).getUploadingDatas().addAll(sendDatas);

            DbHelper.AddFile(sendDatas);

            sendDatas.addAll(templist);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i = 0 ; i < sendDatas.size()  ; i++) {
                final FileInfo sendData = sendDatas.get(i);
                allow = false;


                String upToken = createUpToken(Constant.AccessKey, Constant.secretKey, Constant.PhotoBucket);

                uploadManager.put(new File(sendData.getLocation()), sendData.getName(), upToken, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {

                        if (info.isOK()) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("isuploading",false);
                            DataSupport.updateAll(FileInfo.class,contentValues,"name = ?",sendData.getName());
                            if(onTaskStateChangeListener!=null)
                                onTaskStateChangeListener.onSingleTaskFinish(sendData);
                            if(onTaskFinishLishtener!=null){
                                onTaskFinishLishtener.onTaskFinish(sendData);
                            }

                        } else {

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
                        Thread.sleep(1000);
                        LogUtil.e("NO ALLOW");
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
            super.onPostExecute(aVoid);
        }
    }
}

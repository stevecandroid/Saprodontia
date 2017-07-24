package com.example.saprodontia.Models;

import android.os.Environment;
import android.util.Log;

import com.example.saprodontia.Constant.Constant;
import com.example.saprodontia.Utils.LogUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class UpLoadModel {

    private UploadManager uploadManager;

    public UpLoadModel() {
        uploadManager = new UploadManager();
    }

    public String createUpToken(String accessKey, String secretKey, String bucket){
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }

    public void upLoadFile(List<FileInfo> fileInfos){

        for(int i = 0 ; i < fileInfos.size() ; i++) {
            String upToken = createUpToken(Constant.AccessKey, Constant.secretKey, Constant.PhotoBucket);
            uploadManager.put(new File(fileInfos.get(i).getLocation()), fileInfos.get(i).getName(), upToken, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.isOK()) {
                        LogUtil.e("Upload Success");
                    } else {
                        LogUtil.e("Upload Fail");
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    }
                    LogUtil.e(key + ",\r\n " + info + ",\r\n " + response);
                }
            },null);


        }


    }

}

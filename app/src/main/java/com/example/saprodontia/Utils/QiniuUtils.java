package com.example.saprodontia.Utils;

import android.support.annotation.WorkerThread;

import com.example.saprodontia.Constant.Constant;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;

import java.util.ArrayList;

/**
 * Created by 铖哥 on 2017/7/28.
 */

public class QiniuUtils {

    public com.qiniu.storage.model.FileInfo[] listFile(){
        Auth auth = Auth.create(Constant.AccessKey, Constant.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        try {
            FileListing fileListing=  bucketManager.listFiles("photo",null,null,2000,null);
            return fileListing.items;
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WorkerThread
    public ArrayList<String> getImageSourceKey(){
        ArrayList<String> keys = new ArrayList<String>();
        QiniuUtils qiniuUtils = new QiniuUtils();
        com.qiniu.storage.model.FileInfo[] fileInfos = qiniuUtils.listFile();
        for (int i = 0; i < fileInfos.length ; i++) {
            if(fileInfos[i].mimeType.contains("image")) {
                keys.add(fileInfos[i].key);
                LogUtil.e(fileInfos[i].key);
            }
        }
        return keys;
    }
}

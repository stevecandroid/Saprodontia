package com.example.saprodontia.db;

import com.example.saprodontia.Models.FileInfo;

import java.io.File;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/25.
 */

public class DbHelper {

    public static void AddFile(List<FileInfo> fileInfos){

        for(int i = 0 ; i < fileInfos.size() ; i++){
            fileInfos.get(i).save();
        }
    }



    public static void deleteFile(FileInfo fileInfo){
        fileInfo.delete();
    }

    public static void changeFileInfoState(List<FileInfo> fileInfos,boolean isUploading){
        for(int i = 0 ; i < fileInfos.size() ; i++){
            fileInfos.get(i).setIsuploading(isUploading);
        }
    }
}

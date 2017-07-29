package com.example.saprodontia.Utils;

import com.example.saprodontia.Models.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class SearchUtil {

    private static List<String> result = new ArrayList<>();

    public static  List<String> search(String format, File rootFile){

        if(rootFile.isDirectory()){

            try{
                File[] allFile = rootFile.listFiles();
                for(int i = 0 ; i < allFile.length ; i++){

                    if(allFile[i].isDirectory()){
                        search( format , allFile[i]);
                    }else{
                        if(allFile[i].getPath().contains(format)){
                            result.add(allFile[i].getPath());
                            LogUtil.e(allFile[i].getPath());
                        }

                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }

        return result;
    }

    public static int getFileCountInFolder(String path){
        int count = 0;
        try {
            File file = new File(path);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && isPic(files[i].getPath())) {
                    count++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    public static int getFileCountInFolder(File file){
        return getFileCountInFolder(file.getPath());
    }

    public static String getFirstFilePath(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        for(int i = 0 ; i < files.length ; i++){
            String temp = files[i].getPath();
            if(files[i].isFile() && isPic(temp)){
                return files[i].getPath();
            }
        }
        return null ;
    }

    public static List<FileInfo> getPictureInPath(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        List<FileInfo> pictures = new ArrayList<>();
        for(int i = 0 ; i < files.length ; i++){
            String temp = files[i].getPath();
            if(isPic(temp)){
                FileInfo pictureInfo = new FileInfo();
                long initSize = new File(temp).length();
                pictureInfo.setLocation(temp);
                pictureInfo.setName(temp.substring(temp.lastIndexOf('/')+1,temp.length()));
                pictureInfo.setSize(MathUtil.bytoKbOrMb(initSize));
                pictureInfo.setInitSize(initSize);
                pictures.add(pictureInfo);
            }
        }
        return pictures;
    }

    public static boolean isPic(String temp){
        if(temp.endsWith("jpg") || temp.endsWith("gif") || temp.endsWith("png") || temp.endsWith("jpeg") || temp.endsWith("bmp")){
            return true;
        }else {
            return false;
        }
    }
}

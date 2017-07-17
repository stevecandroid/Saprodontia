package com.example.saprodontia.Utils;

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
}

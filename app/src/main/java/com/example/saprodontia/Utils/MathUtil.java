package com.example.saprodontia.Utils;

/**
 * Created by 铖哥 on 2017/7/17.
 */

public class MathUtil {
    public static float keepTwoDecimals(float n){
        String str = String.valueOf(n);
        return Float.parseFloat(str.substring(0,str.lastIndexOf('.')));
    }

    public static String bytoKbOrMb(long size){

        if( size >= 1024 * 1024 ){
           return (String.valueOf(MathUtil.keepTwoDecimals(size/1024.0f/1024.0f)) + " MB");
        }else{
           return  (String.valueOf(MathUtil.keepTwoDecimals(size/1024.0f)) + " KB");
        }


    }
}

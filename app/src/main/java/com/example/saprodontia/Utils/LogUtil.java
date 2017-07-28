package com.example.saprodontia.Utils;

import android.Manifest;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.util.Log;

import static android.Manifest.*;
import static android.Manifest.permission.*;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class LogUtil {

    static boolean allowLog = true;

    @RequiresPermission(INTERNET)
    public static void d ( Object content){
        if(allowLog && content != null) {
            Log.d(TAG(), content.toString());
        }

        if(content == null){
            Log.d(TAG(), "CONTENT IS NULL");
        }
    }


    public static void e (Object content){
        if(allowLog && content != null) {
            Log.e(TAG(), content.toString());
        }

        if(content == null){
            Log.e(TAG(), "CONTENT IS NULL");
        }
    }


    public static void v (Object content){
        if(allowLog && content != null) {
            Log.v(TAG(), content.toString());
        }

        if(content == null){
            Log.v(TAG(), "CONTENT IS NULL");
        }
    }


    public static void i (Object content){
        if(allowLog && content != null) {
            Log.i(TAG(), content.toString());
        }

        if(content == null){
            Log.i(TAG(), "CONTENT IS NULL");
        }
    }

    public static void w (Object content){
        if(allowLog && content != null) {
            Log.w(TAG(), content.toString());
        }

        if(content == null){
            Log.w(TAG(), "CONTENT IS NULL");
        }
    }

    public static void m (Object ... o){
        for(int i = 0 ; i < o.length ; i++){
            LogUtil.e(o);
        }

    }

    private static String TAG(){
        return Thread.currentThread().getStackTrace()[3].getClassName();
    }
}

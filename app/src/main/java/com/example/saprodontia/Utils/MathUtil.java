package com.example.saprodontia.Utils;

import android.support.annotation.CheckResult;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

/**
 * Created by 铖哥 on 2017/7/17.
 */

public class MathUtil {

    public final static long Mb = 1024 * 1024;

    public static String keepTwoDecimals(String n){

            return n.substring(0,n.lastIndexOf('.')+2);
    }


    public static String bytoKbOrMb(long size){

        if( size >= Mb ){
           return keepTwoDecimals(String.valueOf(size/1024.0f/1024.0f))+" MB";
        }else{
           return  keepTwoDecimals(String.valueOf(size/1024.0f))+" KB";
        }


    }
}

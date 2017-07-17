package com.example.saprodontia.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class App extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }



}

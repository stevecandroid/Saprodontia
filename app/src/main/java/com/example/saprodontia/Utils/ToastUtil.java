package com.example.saprodontia.Utils;

import android.widget.Toast;

import com.example.saprodontia.Application.App;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class ToastUtil {


    private static Toast toast;

    public static void showToast(String content){

        if(toast == null){
            toast = Toast.makeText(App.getContext(),content,Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        toast.show();

    }

}

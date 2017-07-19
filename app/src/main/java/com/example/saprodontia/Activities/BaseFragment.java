package com.example.saprodontia.Activities;

import android.support.v4.app.Fragment;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class BaseFragment extends Fragment {

    public onProgressChangeListener mOnProgressChangeLisnter;

    public void setOnProgressChangeListener(onProgressChangeListener listener){
        mOnProgressChangeLisnter = listener;
    }

    public interface onProgressChangeListener{
        void onProgressChange(long progress , String taskName);
    }

}

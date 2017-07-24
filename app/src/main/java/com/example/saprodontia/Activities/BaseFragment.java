package com.example.saprodontia.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saprodontia.Models.ContentModle;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public abstract class BaseFragment extends Fragment {

    protected ContentModle mContentModle;
    protected NotifyReceiver notifyReceiver;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyReceiver = new NotifyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("SEND_DATA_CHANGE");
        getActivity().registerReceiver(notifyReceiver,intentFilter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(notifyReceiver);
    }

    class NotifyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("SEND_DATA_CHANGE")){
                notifyAdapter();
            }
        }
    }

    protected abstract void notifyAdapter();
}

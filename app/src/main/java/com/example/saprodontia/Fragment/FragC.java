package com.example.saprodontia.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.saprodontia.Activities.BaseFragment;
import com.example.saprodontia.Adapter.ItemAdapter;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.View.MyProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class FragC extends BaseFragment {

    List<FileInfo> infos ;
    ItemAdapter mItemAdapter;
    MyReceiver myReceiver;
    RecyclerView recycle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_pager,container,false);
        infos = new ArrayList<>();
        recycle = (RecyclerView) view.findViewById(R.id.recycle_item);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        recycle.setItemAnimator(new DefaultItemAnimator());
        mItemAdapter = new ItemAdapter(getContext(),infos);
        recycle.setAdapter(mItemAdapter);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DOWNLOAD_TASK_INIT_DATA");
        intentFilter.addAction("DOWNLOAD_TASK_PROGRESS");
        intentFilter.addAction("DOWNLOAD_TASK_DONE");
        getContext().registerReceiver(myReceiver,intentFilter);

    }

    @Override
    protected void notifyAdapter() {
        mItemAdapter.notifyDataSetChanged();
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
             String act = intent.getAction();

            if(act.equals("DOWNLOAD_TASK_INIT_DATA")){
                FileInfo info = new FileInfo();
                String name = intent.getStringExtra("name");
                long initSize = (long)Double.parseDouble(intent.getStringExtra("max"));
                info.setName(name);
                info.setInitSize(initSize);
                infos.add(info);
                mItemAdapter.notifyItemInserted(infos.size());
                LogUtil.e(name);
            }

            if(act.equals("DOWNLOAD_TASK_PROGRESS")){

                String taskName  = intent.getStringExtra("taskName");

                for(int i = 0 ; i < infos.size() ; i++){
                    if(infos.get(i).getName().equals(taskName)){
                        long progress = intent.getLongExtra("progress",0);
                        infos.get(i).setProgress(progress);
                        mItemAdapter.notifyDataSetChanged();
                        break;
                    }
                }

            }

            if(act.equals("DOWNLOAD_TASK_DONE")){
                String taskName  = intent.getStringExtra("taskName");

                for(int i = 0 ; i < infos.size() ; i++){
                    if(infos.get(i).getName().equals(taskName)){
                        infos.remove(i);
                        mItemAdapter.notifyDataSetChanged();
                    }
                }
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(myReceiver);
    }
}

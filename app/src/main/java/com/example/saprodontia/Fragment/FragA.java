package com.example.saprodontia.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saprodontia.Activities.BaseFragment;
import com.example.saprodontia.Adapter.ItemAdapter;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.View.RecycleDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class FragA extends BaseFragment {

    List<FileInfo> infos ;
    ItemAdapter mItemAdapter;
    FragAReceiver myReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_pager,container,false);
        infos = new ArrayList<>();
        RecyclerView recycle = (RecyclerView) view.findViewById(R.id.recycle_item);

        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mItemAdapter = new ItemAdapter(this,infos);
        recycle.setAdapter(mItemAdapter);
        recycle.addItemDecoration(new RecycleDecoration());


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myReceiver = new FragAReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DOWNLOAD_TASK_INIT_DATA");
        intentFilter.addAction("DOWNLOAD_TASK_PROGRESS");
        getContext().registerReceiver(myReceiver,intentFilter);
    }

    class FragAReceiver extends BroadcastReceiver{

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
                mItemAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(myReceiver);
    }
}

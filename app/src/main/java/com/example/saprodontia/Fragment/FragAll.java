package com.example.saprodontia.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saprodontia.Activities.BaseFragment;
import com.example.saprodontia.Adapter.ItemAdapter;
import com.example.saprodontia.Application.App;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.Models.UpLoadModel;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.View.MyProgressBar;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/25.
 */

public class FragAll extends BaseFragment {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private UpLoadModel upLoadModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        upLoadModel = UpLoadModel.getInstance();
        upLoadModel.setOnTaskFinishLishtener(new UpLoadModel.onTaskFinishLishtener() {
            @Override
            public void onTaskFinish(FileInfo sendDatas) {
                List<FileInfo> uploadedDatas = ((App)(App.getContext().getApplicationContext())).getUploadedDatas();
                uploadedDatas.add(0,sendDatas);
                adapter.notifyItemInserted(0);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_pager,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);
        adapter = new ItemAdapter(getContext(),((App)getContext().getApplicationContext()).getUploadedDatas());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    protected void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }
}

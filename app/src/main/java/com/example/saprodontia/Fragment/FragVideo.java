package com.example.saprodontia.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saprodontia.Activities.BaseFragment;
import com.example.saprodontia.Adapter.Adapter;
import com.example.saprodontia.Models.ContentModle;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class FragVideo extends BaseFragment {

    private Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContentModle = new ContentModle(getContext());
        View view = inflater.inflate(R.layout.item_pager,container,false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);

        adapter = new Adapter(getContext(),mContentModle.getVideosFile());

        mContentModle.setmOnVideoDataChangedListener(new ContentModle.OnVideoDataChangedListener() {
            @Override
            public void onDataChange() {
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    protected void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }
}

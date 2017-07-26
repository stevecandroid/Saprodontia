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
import com.example.saprodontia.Models.AppInfoModle;
import com.example.saprodontia.Models.ContentModle;
import com.example.saprodontia.R;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class FragApp extends BaseFragment {

    private AppInfoModle mContentModle;
    private  Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContentModle = new AppInfoModle(getContext());
        View view = inflater.inflate(R.layout.item_pager,container,false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);

          adapter = new Adapter(getContext(),mContentModle.initSimAppInfos());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mContentModle.setmOnDataChangeListener(new AppInfoModle.OnDataChangeListener() {
           @Override
           public void onDataChange() {
               adapter.notifyDataSetChanged();
           }
       });

        return view;
    }

    @Override
    protected void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }
}

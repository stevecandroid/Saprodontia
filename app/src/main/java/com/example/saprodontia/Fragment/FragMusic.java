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
import com.example.saprodontia.R;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class FragMusic extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mContentModle = new ContentModle(getContext());

        View view = inflater.inflate(R.layout.item_pager,container,false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);

        final Adapter adapter = new Adapter(getContext(),mContentModle.getMusicInfos());

        mContentModle.setmOnMusicDataChangedListener(new ContentModle.OnMusicDataChangedListener() {
            @Override
            public void onDataChanged() {
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}

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

public class FragDocu extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentModle = new ContentModle(getContext());

        View view = inflater.inflate(R.layout.item_pager,container,false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);

        final Adapter adapter = new Adapter(getContext(),mContentModle.getFileInfos());

       mContentModle.setmOnFileDataChangedListener(new ContentModle.OnFileDataChangedListener() {
           @Override
           public void onDataChanged(int position) {
               adapter.notifyDataSetChanged();
           }
       });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}

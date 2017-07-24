package com.example.saprodontia.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saprodontia.Activities.BaseFragment;
import com.example.saprodontia.Adapter.ItemAdapter;
import com.example.saprodontia.R;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class FragUpLoading extends BaseFragment {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_pager,container,false);
//        adapter = new ItemAdapter();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);
//        recyclerView.setAdapter(new ItemAdapter(this,));


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }
}

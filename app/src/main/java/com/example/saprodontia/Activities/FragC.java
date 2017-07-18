package com.example.saprodontia.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saprodontia.R;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class FragC extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_pager,container,false);

        RecyclerView recycle = (RecyclerView) view.findViewById(R.id.recycle_item);
//        recycle.setLayoutManager();
//        recycle.setAdapter();


        return view;
    }
}

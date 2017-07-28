package com.example.saprodontia.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saprodontia.Adapter.PicFolderAdapter;
import com.example.saprodontia.Models.ContentModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.ThumbUtils;
import com.example.saprodontia.View.RecycleDecoration;

import java.io.File;

/**
 * Created by 铖哥 on 2017/7/26.
 */

public class FragPicFolder extends Fragment {

    ContentModle contentModle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_pager,container,false);

        contentModle = new ContentModle(getContext());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecycleDecoration());
        recyclerView.setAdapter(new PicFolderAdapter(contentModle.getImagesFolder()));

        return view;
    }


}

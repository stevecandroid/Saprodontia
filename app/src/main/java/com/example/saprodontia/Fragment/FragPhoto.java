package com.example.saprodontia.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saprodontia.Activities.BaseFragment;
import com.example.saprodontia.Adapter.Adapter;
import com.example.saprodontia.Adapter.PhotoAdapter;
import com.example.saprodontia.Models.ContentModle;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class FragPhoto extends BaseFragment {

    private PhotoAdapter photoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContentModle = new ContentModle(getContext());

        View view = inflater.inflate(R.layout.item_pager,container,false);

        mContentModle.setmOnImageDataChangedListener(new ContentModle.OnImageDataChangedListener() {
            @Override
            public void onDataChanged() {

            }
        });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);

        photoAdapter = new PhotoAdapter(mContentModle.getImagesFolder(),getContext());

        photoAdapter.setOnScrollToBottomListener(new PhotoAdapter.OnScrollToBottomListener() {
            @Override
            public void onBottom(int pos, int length) {
                int vPos = ((GridLayoutManager)(recyclerView.getLayoutManager())).findLastVisibleItemPosition();
                if(vPos == pos || pos == vPos-1){
                    recyclerView.smoothScrollToPosition(pos+1);
                }
            }
        });

        photoAdapter.setOnSendDataChangeListener(new PhotoAdapter.OnSendDataChangeListener() {
            @Override
            public void onSendDataChange(List<FileInfo> list) {

            }
        });

        recyclerView.setAdapter(photoAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (recyclerView.getAdapter().getItemViewType(position)==1){
                    return 3;
                }else {
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);

        return view;
    }

    @Override
    protected void notifyAdapter() {
        photoAdapter.notifyDataSetChanged();
    }


}

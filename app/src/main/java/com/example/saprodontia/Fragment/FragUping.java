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
 * Created by 铖哥 on 2017/7/24.
 */

public class FragUping extends BaseFragment {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private UpLoadModel upLoadModel;
    MyProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        upLoadModel = UpLoadModel.getInstance();
        upLoadModel.setTaskStateChangeListener(new UpLoadModel.onTaskStateChangeListener() {
            @Override
            public void onTaskStart(List<FileInfo> readyFile) {
            }

            @Override
            public void onPercentChanged(String key, double progress) {

                for(int i = 0 ; i < recyclerView.getLayoutManager().getChildCount() ;i++) {

                    View view = recyclerView.getLayoutManager().findViewByPosition(i);

                    progressBar = (MyProgressBar) view.findViewById(R.id.progress_bar);

                    if ( key.equals(  ((TextView)view.findViewById(R.id.tv_appname)).getText())){
                        progressBar.setProgress(progress);
                        if(progress == 1.0){
                            progressBar.setProgress(0);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onSingleTaskFinish(FileInfo fileInfo) {

                List<FileInfo> uploadingDatas = ((App)(App.getContext().getApplicationContext())).getUploadingDatas();
                for(int i = 0 ; i < uploadingDatas.size() ; i++){
                    if(fileInfo.getName().equals(uploadingDatas.get(i).getName())){
                        uploadingDatas.remove(i);
                        break;
                    }

                }

                adapter.notifyDataSetChanged();
            }

        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_pager,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);
        adapter = new ItemAdapter(getContext(),((App)getContext().getApplicationContext()).getUploadingDatas());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    protected void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }


}

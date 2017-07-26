//package com.example.saprodontia.Fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.saprodontia.Activities.BaseFragment;
//import com.example.saprodontia.Adapter.ItemAdapter;
//import com.example.saprodontia.Application.App;
//import com.example.saprodontia.Models.FileInfo;
//import com.example.saprodontia.Models.UpLoadModel;
//import com.example.saprodontia.R;
//import com.example.saprodontia.Utils.LogUtil;
//import com.example.saprodontia.View.MyProgressBar;
//
//import java.util.List;
//
///**
// * Created by 铖哥 on 2017/7/25.
// */
//
//public class FragUped extends BaseFragment {
//
//    private RecyclerView recyclerView;
//    private ItemAdapter adapter;
//    private UpLoadModel upLoadModel;
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        upLoadModel = UpLoadModel.getInstance();
//        upLoadModel.setOnTaskFinishLishtener(new UpLoadModel.onTaskFinishLishtener() {
//            @Override
//            public void onTaskFinish(FileInfo datas) {
//                LogUtil.e("ON TASK FINSIH");
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.item_pager,container,false);
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_item);
//        adapter = new ItemAdapter(getContext(),((App)getContext().getApplicationContext()).getUploadedDatas());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        return view;
//    }
//
//    @Override
//    protected void notifyAdapter() {
//        adapter.notifyDataSetChanged();
//    }
//}

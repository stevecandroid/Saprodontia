//package com.example.saprodontia.Adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import java.util.List;
//
///**
// * Created by 铖哥 on 2017/7/16.
// */
//
//public class AppAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
//
//    private List<T> mDatas ;
//    private int layoutId;
//    private Context context;
//    private LayoutInflater inflater;
//
//    public AppAdapter(Context context , List<T> mDatas, int layoutId) {
//        this.mDatas = mDatas;
//        this.layoutId = layoutId;
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//    }
//
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return ViewHolder.getInstance(context,layoutId,parent);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}

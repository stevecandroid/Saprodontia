//package com.example.saprodontia.Adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.HashMap;
//
///**
// * Created by 铖哥 on 2017/7/16.
// */
//
//public class ViewHolder extends RecyclerView.ViewHolder {
//
//    private HashMap<Integer , View> views;
//    private Context context;
//    private View itemView;
//
//    private ViewHolder(Context context , View itemView) {
//        super(itemView);
//        this.context = context;
//        this.itemView = itemView;
//    }
//
//    public static ViewHolder getInstance(Context context , int layoutId , ViewGroup parent){
//
//        View itemView = LayoutInflater.from(context).inflate(layoutId,parent);
//        return new ViewHolder(context,itemView);
//
//    }
//
//    public <T extends View> T getView(int viewId){
//
//        View view = views.get(viewId);
//        if(view == null){
//            view = itemView.findViewById(viewId);
//            views.put(viewId,view);
//        }
//
//        return (T) view;
//
//
//    }
//
//
//
//}

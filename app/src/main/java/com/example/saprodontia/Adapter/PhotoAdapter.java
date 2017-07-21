package com.example.saprodontia.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saprodontia.Application.App;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;

import java.io.File;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/20.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FileInfo> infos;
    private Context context;
    private List<FileInfo> sendDatas;
    private OnSendDataChangeListener onSendDataChangeListener;
    private OnScrollToBottomListener onScrollToBottomListener;
    private AnimHandler handler;


    public PhotoAdapter(List<FileInfo> parentInfos , Context context) {
        this.context = context;
        this.infos = parentInfos;
        App app = (App) context.getApplicationContext();
        sendDatas = app.getSenDatas();
        handler = new AnimHandler();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 1){
            return new ViewHolderOne(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_one,parent,false));
        }else{
            return new ViewHolderTwo(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_two,parent,false));
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if(infos.get(position).getType()==1){
            final ViewHolderOne holderOne = ((ViewHolderOne)holder);

            if(infos.get(position).isExpand()){
                holderOne.imageArrow.setImageDrawable(context.getDrawable(R.drawable.arrowdown));
            }else{
                holderOne.imageArrow.setImageDrawable(context.getDrawable(R.drawable.arrow));

            }

            TextView tv =  holderOne.textView;
            tv.setText(infos.get(position).getName());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final long start = System.currentTimeMillis();
                    List<FileInfo> childs = infos.get(position).getChilds();
                    int childSize = 0;

                    if(childs!=null){
                        childSize=childs.size();
                    }

                    LogUtil.e(position +"POS");

                    if(!infos.get(position).isExpand()) {

                        if(childs!=null) {
                            infos.addAll(position + 1, childs);
                        }
                        infos.get(position).setExpand(true);

                            if(onScrollToBottomListener!=null)
                                onScrollToBottomListener.onBottom(position,infos.size());
                    }else{
                        infos.subList(position + 1 , position + 1+childSize).clear();
                        infos.get(position).setExpand(false);

                    }

                    notifyDataSetChanged();

                }
            });
        }else if(infos.get(position).getType()==2){
            final ViewHolderTwo holderTwo = ((ViewHolderTwo)holder);
            Glide.with(context).load(infos.get(position).getLocation()).override(200,200).centerCrop().into( holderTwo.imageView);

            holderTwo.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.e(position +"POS");
                    if(!holderTwo.checkPhoto.isChecked()) {
                        sendDatas.add(infos.get(position));
                        holderTwo.checkPhoto.setChecked(true);
                        holderTwo.checkPhoto.setVisibility(View.VISIBLE);
                        onSendDataChangeListener.onSendDataChange(sendDatas);
                    }else{
                        sendDatas.remove(infos.get(position));
                        holderTwo.checkPhoto.setChecked(false);
                        holderTwo.checkPhoto.setVisibility(View.INVISIBLE);
                        onSendDataChangeListener.onSendDataChange(sendDatas);
                    }
                }
            });

            if(belong(sendDatas,position)){
                holderTwo.checkPhoto.setChecked(true);
                holderTwo.checkPhoto.setVisibility(View.VISIBLE);
            }else{
                holderTwo.checkPhoto.setChecked(false);
                holderTwo.checkPhoto.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    private class ViewHolderOne extends  RecyclerView.ViewHolder{

        TextView textView ;
        ImageView imageArrow;

        ViewHolderOne(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageArrow = (ImageView) itemView.findViewById(R.id.image_arrow);
        }
    }

    private class ViewHolderTwo extends RecyclerView.ViewHolder{

        ImageView imageView ;
        CheckBox checkPhoto;

        ViewHolderTwo(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            checkPhoto = (CheckBox) itemView.findViewById(R.id.check_photo);
        }
    }



    @Override
    public int getItemViewType(int position) {
        return infos.get(position).getType();
    }

    private boolean belong(List<FileInfo> sendDatas, int pos) {
        String str = infos.get(pos).getLocation();
        for (int i = 0; i < sendDatas.size(); i++) {
            if (str.equals(sendDatas.get(i).getLocation()))
                return true;
        }
        return false;
    }

    public interface OnSendDataChangeListener{
        void onSendDataChange(List<FileInfo> list);
    }

    public void setOnSendDataChangeListener(OnSendDataChangeListener onSendDataChangeListener) {
        this.onSendDataChangeListener = onSendDataChangeListener;
    }

    public interface OnScrollToBottomListener{
        void onBottom(int pos,int length);
    }

    public void setOnScrollToBottomListener(OnScrollToBottomListener onScrollToBottomListener) {
        this.onScrollToBottomListener = onScrollToBottomListener;
    }

    class AnimHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            notifyDataSetChanged();
        }
    }
}

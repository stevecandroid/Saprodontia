package com.example.saprodontia.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.MathUtil;
import com.example.saprodontia.View.MyProgressBar;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    private List<FileInfo> mDatas;
    private Context context;

    public ItemAdapter(Context context, List<FileInfo> datas) {
        mDatas = datas;
        this.context  =  context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transfer,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_appsize.setText(MathUtil.bytoKbOrMb(mDatas.get(position).getInitSize()));
        holder.tv_appname.setText(mDatas.get(position).getName());
        if(mDatas.get(position).getBitmap()!=null){
            holder.image_app.setImageBitmap(mDatas.get(position).getBitmap());
        }else if(mDatas.get(position).getIcon()!=null){
            holder.image_app.setImageDrawable(mDatas.get(position).getIcon());
        }else if(mDatas.get(position).getName().contains("jpg")){
            Glide.with(context).load(mDatas.get(position).getLocation()).into(holder.image_app);
        }
//        holder.progress_bar.setMax((int) mDatas.get(position).getInitSize());
//        holder.progress_bar.setProgress(mDatas.get(position).getProgress());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image_app;
        TextView tv_appname ;
        TextView tv_appsize;
        MyProgressBar progress_bar;

        public ViewHolder(View itemView) {
            super(itemView);
            image_app = (ImageView) itemView.findViewById(R.id.image_app);
            tv_appname = (TextView) itemView.findViewById(R.id.tv_appname);
            tv_appsize = (TextView) itemView.findViewById(R.id.tv_appsize);
            progress_bar = (MyProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }


}

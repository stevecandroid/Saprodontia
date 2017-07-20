package com.example.saprodontia.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saprodontia.Activities.BaseFragment;
import com.example.saprodontia.Activities.FragC;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.MathUtil;
import com.example.saprodontia.View.MyProgressBar;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Fragment fragment;
    private List<FileInfo> mDatas;

    public ItemAdapter(Fragment fragment , List<FileInfo> datas) {
        this.fragment = fragment;

        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_pager,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_appsize.setText(MathUtil.bytoKbOrMb(mDatas.get(position).getInitSize()));
        holder.tv_appname.setText(mDatas.get(position).getName());
        holder.progress_bar.setMax((int) mDatas.get(position).getInitSize());
        holder.progress_bar.setProgress((int) mDatas.get(position).getProgress());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_appname ;
        TextView tv_appsize;
        MyProgressBar progress_bar;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_appname = (TextView) itemView.findViewById(R.id.tv_appname);
            tv_appsize = (TextView) itemView.findViewById(R.id.tv_appsize);
            progress_bar = (MyProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }
}

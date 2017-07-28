package com.example.saprodontia.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saprodontia.Activities.PicFragment;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.SearchUtil;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/26.
 */

public class PicFolderAdapter extends RecyclerView.Adapter<PicFolderAdapter.PicFolderViewHolder> {

    private List<FileInfo> fileInfos;
    private Context context;

    public PicFolderAdapter(List<FileInfo> fileInfos,Context context) {
        this.fileInfos = fileInfos;
        this.context = context;
    }



    @Override
    public PicFolderViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_folder,parent,false);
        final PicFolderViewHolder picFolderViewHolder = new PicFolderViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicFragment p = new PicFragment();
                Bundle bundle = new Bundle();
                bundle.putString("path",fileInfos.get(picFolderViewHolder.getAdapterPosition()).getLocation());
                p.setArguments(bundle);
                FragmentManager fm = ((AppCompatActivity)(context)).getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.addToBackStack(null);

                transaction.replace(R.id.con,p);
                transaction.commit();
            }
        });

        return picFolderViewHolder;
    }

    @Override
    public void onBindViewHolder(PicFolderViewHolder holder, int position) {
            holder.tv_count.setText(SearchUtil.getFileCountInFolder(fileInfos.get(position).getLocation())+"");
            LogUtil.e(fileInfos.get(position).getLocation());
            holder.tv_folderName.setText(fileInfos.get(position).getName());
            Glide.with(context).load(SearchUtil.getFirstFilePath(fileInfos.get(position).getLocation())).override(600 ,600).into(holder.im_first);
    }

    @Override
    public int getItemCount() {
        return fileInfos.size();
    }

    public class  PicFolderViewHolder extends RecyclerView.ViewHolder{
        TextView tv_count ;
        TextView tv_folderName;
        ImageView im_first;
        LinearLayout ll_bt;

        public PicFolderViewHolder(View itemView) {
            super(itemView);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_folderName = (TextView) itemView.findViewById(R.id.tv_folder_name);
            im_first = (ImageView) itemView.findViewById(R.id.imge_first);
            ll_bt = (LinearLayout) itemView.findViewById(R.id.ll_bt);
        }
    }


}

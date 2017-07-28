package com.example.saprodontia.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<FileInfo> mDatas;

    private ArrayList<FileInfo> sendDatas;
    private App app ;
    private onSenDatasChangedListener mOnSenDatasChangedListener;


    public Adapter(Context context, List<FileInfo> mDatas) {
        this.mDatas = mDatas;
        app = (App) context.getApplicationContext();
        sendDatas = app.getSenDatas();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_appname.setText(mDatas.get(position).getName());
        holder.tv_appsize.setText(mDatas.get(position).getSize());

        if(mDatas.get(position).getBitmap()==null){
            holder.image_app.setImageDrawable(mDatas.get(position).getIcon());
        }else{
            holder.image_app.setImageBitmap(mDatas.get(position).getBitmap());
        }


        if (belong(sendDatas, position)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(true);
                    sendDatas.add(mDatas.get(position));

                    if(mOnSenDatasChangedListener!=null)
                    mOnSenDatasChangedListener.onDataChanged(sendDatas);

                } else {

                    holder.checkBox.setChecked(false);
                    sendDatas.remove(mDatas.get(position));

                    if(mOnSenDatasChangedListener!=null)
                        mOnSenDatasChangedListener.onDataChanged(sendDatas);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_app;
        TextView tv_appname;
        TextView tv_appsize;
        Button bt_check;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            image_app = (ImageView) itemView.findViewById(R.id.image_app);
            tv_appname = (TextView) itemView.findViewById(R.id.tv_appname);
            tv_appsize = (TextView) itemView.findViewById(R.id.tv_appsize);
            bt_check = (Button) itemView.findViewById(R.id.bt_check);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }

    private boolean belong(List<FileInfo> sendDatas, int pos) {
        String str = mDatas.get(pos).getLocation();
        for (int i = 0; i < sendDatas.size(); i++) {
            if (str.equals(sendDatas.get(i).getLocation()))
                return true;
        }
        return false;
    }

    public interface onSenDatasChangedListener{
        void onDataChanged(List<FileInfo> fileInfos);
    }

    public void setmOnSenDatasChangedListener(onSenDatasChangedListener mOnSenDatasChangedListener) {
        this.mOnSenDatasChangedListener = mOnSenDatasChangedListener;
    }
}

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

import com.example.saprodontia.Activities.SendActivity;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.Models.SocketModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.MathUtil;
import com.example.saprodontia.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

      private  List<FileInfo> mDatas;
      private  SocketModle socketModle ;
      private  ArrayList<FileInfo> sendDatas;

    public Adapter(Context context , List<FileInfo> mDatas){
        this.mDatas = mDatas;
        socketModle = new SocketModle(context);
        sendDatas = new ArrayList<>();

        ((SendActivity)context).setOnSendListener(new SendActivity.OnSendListener() {
            @Override
            public void send() {
                ToastUtil.showToast("SENDDING!!!");
                socketModle.shareFile(sendDatas);
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.tv_appname.setText( mDatas.get(position).getName());
            holder.image_app.setImageDrawable(mDatas.get(position).getIcon());
            holder.tv_appsize.setText(mDatas.get(position).getSize());


        if(belong(sendDatas,position)){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        holder.bt_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!holder.checkBox.isChecked()) {
                        holder.checkBox.setChecked(true);
                        sendDatas.add(mDatas.get(position));
                    }else{
                        holder.checkBox.setChecked(false);
                        sendDatas.remove(mDatas.get(position));
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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
            checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);
        }

    }

    private boolean belong(List<FileInfo> sendDatas,int pos){
        String str = mDatas.get(pos).getLocation();
       for(int i = 0 ; i < sendDatas.size() ; i ++){
           if(str.equals(sendDatas.get(i).getLocation()))
               return true;
       }
        return false;
    }


}

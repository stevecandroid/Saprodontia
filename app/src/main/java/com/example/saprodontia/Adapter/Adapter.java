package com.example.saprodontia.Adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saprodontia.Activities.SendActivity;
import com.example.saprodontia.Models.AppInfo;
import com.example.saprodontia.Models.SocketModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.MathUtil;
import com.example.saprodontia.Utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

      private  List<AppInfo> mDatas;
      private  List<String> checkPos;
      private  SocketModle socketModle ;
      private  ArrayList<AppInfo> sendDatas;

    public Adapter(Context context , List<AppInfo> mDatas){
        this.mDatas = mDatas;
        checkPos = new ArrayList<>();
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

            final float size;
            holder.tv_appname.setText( mDatas.get(position).getName());
            holder.image_app.setImageDrawable(mDatas.get(position).getIcon());

        if( ( size = mDatas.get(position).getSize()) >= 1024){
            holder.tv_appsize.setText(MathUtil.keepTwoDecimals(size/1024.f) + " MB");
        }else{
            holder.tv_appsize.setText(size + " KB");
        }

        if(belong(checkPos,position)){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        holder.bt_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!holder.checkBox.isChecked()) {
                        holder.checkBox.setChecked(true);
                        checkPos.add(mDatas.get(position).getLocation());
                        sendDatas.add(mDatas.get(position));
                    }else{
                        holder.checkBox.setChecked(false);
                        checkPos.remove(Integer.valueOf(position));
                        sendDatas.remove(mDatas.get(position));
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

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

    private boolean belong(List<String> list,int pos){
        String str = mDatas.get(pos).getLocation();
        for(String s : list){
            if( s.equals(str) ){
                return true;
            }
        }
        return false;
    }


}

package com.example.saprodontia.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.ImageLoader;
import com.example.saprodontia.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by 铖哥 on 2017/7/28.
 */

public class CouldPicAdapter extends RecyclerView.Adapter<CouldPicAdapter.CloudHolder> {

    private List<String> pictures;
    private Context context;
    private List<String> downDatas;
    private ImageLoader imageLoader;

    public CouldPicAdapter(Context context , List<String> datas) {
        this.pictures = datas;
        downDatas = new ArrayList<>();
        imageLoader = new ImageLoader(context);
    }

    @Override
    public CloudHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view;
        final CloudHolder holder;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_two,parent,false);
        holder = new CloudHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.check_photo).setVisibility(View.VISIBLE);
                if( (  !((CheckBox) view.findViewById(R.id.check_photo)).isChecked() )) {
                    ((CheckBox) view.findViewById(R.id.check_photo)).setChecked(true);
                    downDatas.add(pictures.get(holder.getAdapterPosition()));
                }else{
                    ((CheckBox) view.findViewById(R.id.check_photo)).setChecked(false);
                    downDatas.remove(pictures.get(holder.getAdapterPosition()));
                }

            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(CloudHolder holder, int position) {
        if(belong(downDatas,position)){
            holder.checkBox.setChecked(true);
            holder.checkBox.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setChecked(false);
            holder.checkBox.setVisibility(GONE);
        }

       imageLoader.loadBitmap(pictures.get(position),50,50).into(holder.image_head);

    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    class CloudHolder extends RecyclerView.ViewHolder{

        ImageView image_head;
        CheckBox checkBox;


        public CloudHolder(View itemView) {
            super(itemView);
            image_head = (ImageView) itemView.findViewById(R.id.imageView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_photo);
        }


    }

    private boolean belong(List<String> downDatas, int pos) {
        String str = pictures.get(pos);
        for (int i = 0; i < downDatas.size(); i++) {
            if (str.equals(downDatas.get(i)));
                return true;
        }
        return false;
    }
}

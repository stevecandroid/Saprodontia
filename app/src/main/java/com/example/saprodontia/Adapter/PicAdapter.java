package com.example.saprodontia.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.saprodontia.Application.App;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by 铖哥 on 2017/7/26.
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicHolder> {


    private Context context;
    private List<FileInfo> pictures;
    private App app;
    private List<FileInfo> senDatas;


    public PicAdapter(List<FileInfo> pictures) {
        this.pictures = pictures;
        app = ((App)App.getContext());
        senDatas = app.getSenDatas();

    }

    @Override
    public PicHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();

        final View view;
        final PicHolder holder;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_two,parent,false);
        holder = new PicHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.check_photo).setVisibility(View.VISIBLE);
                if( (  !((CheckBox) view.findViewById(R.id.check_photo)).isChecked() )) {
                    ((CheckBox) view.findViewById(R.id.check_photo)).setChecked(true);
                    senDatas.add(pictures.get(holder.getAdapterPosition()));
                }else{
                    ((CheckBox) view.findViewById(R.id.check_photo)).setChecked(false);
                    senDatas.remove(pictures.get(holder.getAdapterPosition()));
                }

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final PicHolder holder, final int position) {
        Glide.with(context).load(pictures.get(position).getLocation()).into(holder.image_head);
        if(belong(senDatas,position)){
            holder.checkBox.setChecked(true);
            holder.checkBox.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setChecked(false);
            holder.checkBox.setVisibility(GONE);
        }

//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!holder.checkBox.isChecked()){
//                    senDatas.add(pictures.get(position));
//                    holder.checkBox.setChecked(true);
//                }else{
//                    senDatas.remove(pictures.remove(pictures.get(position)));
//                    holder.checkBox.setChecked(false);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    class PicHolder extends RecyclerView.ViewHolder{

        ImageView image_head;
        CheckBox checkBox;


        public PicHolder(View itemView) {
            super(itemView);
            image_head = (ImageView) itemView.findViewById(R.id.imageView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_photo);
        }


    }


    public interface onLongClickListener{
        void onLongClick(View view , int position);
    }

    private boolean belong(List<FileInfo> sendDatas, int pos) {
        String str = pictures.get(pos).getLocation();
        for (int i = 0; i < sendDatas.size(); i++) {
            if (str.equals(sendDatas.get(i).getLocation()))
                return true;
        }
        return false;
    }


}

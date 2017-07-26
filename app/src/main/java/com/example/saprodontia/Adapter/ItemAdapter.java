package com.example.saprodontia.Adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saprodontia.Models.AppInfoModle;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.MathUtil;
import com.example.saprodontia.Utils.ThumbUtils;
import com.example.saprodontia.View.MyProgressBar;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    private List<FileInfo> mDatas;
    private Context context;
    private BitmapFactory.Options options;

    public ItemAdapter(Context context, List<FileInfo> datas) {
        mDatas = datas;
        this.context  =  context;
        options = new BitmapFactory.Options();
        options.inSampleSize  = 4;
        options.inScaled = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transfer,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

            FileInfo fileInfo = mDatas.get(position);
            String name = fileInfo.getName();


            holder.tv_appsize.setText(MathUtil.bytoKbOrMb(fileInfo.getInitSize()));
            holder.tv_appname.setText(fileInfo.getName());

            if (fileInfo.getBitmap() != null) {
                holder.image_app.setImageBitmap(fileInfo.getBitmap());
            } else if (fileInfo.getIcon() != null) {
                holder.image_app.setImageDrawable(fileInfo.getIcon());
            } else if (name.endsWith("jpg") || name.endsWith(".gif") ) {
                Glide.with(context).load(fileInfo.getLocation()).into(holder.image_app);
            } else if (name.endsWith("doc")){
                holder.image_app.setImageDrawable(context.getDrawable(R.drawable.word));
            } else if (name.endsWith("xls")){
                holder.image_app.setImageDrawable(context.getDrawable(R.drawable.excel));
            } else if(name.endsWith("pdf")){
                holder.image_app.setImageDrawable(context.getDrawable(R.drawable.pdf));
            } else if (name.endsWith("ppt")){
                holder.image_app.setImageDrawable(context.getDrawable(R.drawable.powerpoint));
            } else if (name.endsWith("mp4")){
                LogUtil.e("IDDIDIDIDI =" + fileInfo.getId());
                Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), fileInfo.getId(), MediaStore.Video.Thumbnails.MINI_KIND, options);
                if(bitmap!=null)
                holder.image_app.setImageBitmap(ThumbUtils.cutBitmap( bitmap,200,200 ));
            } else if (name.endsWith("apk")){
                holder.image_app.setImageDrawable(AppInfoModle.getApkIcon(fileInfo.getLocation(),context));
            } else if (name.endsWith("mp3")){
                holder.image_app.setImageDrawable(context.getDrawable(R.drawable.music));
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

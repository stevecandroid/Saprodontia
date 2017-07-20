package com.example.saprodontia.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * Created by 铖哥 on 2017/7/19.
 */

public class ThumbUtils {

    public static Bitmap getBitmapThumbnails(String path){

        BitmapFactory.Options ops = new BitmapFactory.Options();
        Bitmap init = BitmapFactory.decodeFile(path,ops);
        int side = init.getWidth() > init.getHeight()?init.getHeight():init.getWidth();

        return Bitmap.createScaledBitmap( Bitmap.createBitmap(init,0,0,side,side),100,100, true);

    }

    public static Bitmap cutBitmap(Bitmap bm , int width , int height){

        BitmapFactory.Options ops = new BitmapFactory.Options();
        int side = bm.getWidth() > bm.getHeight()?bm.getHeight():bm.getWidth();
        return Bitmap.createScaledBitmap( Bitmap.createBitmap(bm,0,0,side,side),width,height, true);
    }



}

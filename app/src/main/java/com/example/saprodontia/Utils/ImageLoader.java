package com.example.saprodontia.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.saprodontia.Constant.Constant;
import com.example.saprodontia.R;
import com.example.saprodontia.libcore.io.DiskLruCache;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by 铖哥 on 2017/7/28.
 */

public class ImageLoader {

    public static final int MAX_DISK_CACHE = 1024 * 1024 * 200;
    public static final int MAX_MEMORY_CACHE = (int) (Runtime.getRuntime().maxMemory()/1024);


    private Context mContext;
    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache diskCache;
    private ExecutorService pool;

    public ImageLoader(Context context) {
        mContext = context;
        pool = Executors.newCachedThreadPool();
        try {
            LogUtil.e("MAX MENMORY = " + MAX_MEMORY_CACHE/8 + "KB");
            diskCache = DiskLruCache.open(new File(Constant.tempDir), 1, 1, MAX_DISK_CACHE);
            memoryCache = new LruCache<String,Bitmap>(MAX_MEMORY_CACHE/8){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight() /1024;
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            Result result = (Result) msg.obj;
            LogUtil.e("LOAD IN TO IMAGEVIEW " + result.imageView.getTag().toString() + "   " + result.url);
            if(result.imageView.getTag().toString().equals(result.url)) {
                result.imageView.setImageBitmap(result.bitmap);
            }else{
                //nothing // TODO: 2017/7/29
            }
        }
    };

    public Bitmap loadBitmap(String name, int reqW, int reqH) {

        Bitmap temp = memoryCache.get(hashKeyFromUrl(name));
        if (temp != null) {
            LogUtil.e("LOAD FROM RAM" + name);
            return temp;
        }


        temp = loadBitmapFromDisk(name,reqW,reqH);
        if( temp != null){
            LogUtil.e("LOAD FROM DISK" + name );
            return temp;
        }

        LogUtil.e("LOAD FROM NETWROK" + name);
        return loadFromHttp(name, reqW, reqH);

    }

    public void bindBitmap(final String name , final ImageView imageView , final int reqW , final int reqH){
        Bitmap temp = memoryCache.get(hashKeyFromUrl(name));
        imageView.setTag(hashKeyFromUrl(name));
        if(temp!=null) {
            LogUtil.e("LOAD FROM CACHE");
            mHandler.obtainMessage(0, new Result(hashKeyFromUrl(name),imageView, temp)).sendToTarget();
            return;
        }

        class Loader implements Runnable{
            @Override
            public void run() {

                memoryCache.put(hashKeyFromUrl(name), loadBitmap(name,reqW,reqH));
                Bitmap bitmap = memoryCache.get(hashKeyFromUrl(name));
                if(bitmap != null) mHandler.obtainMessage(0,new Result(hashKeyFromUrl(name),imageView,bitmap)).sendToTarget();
            }
        }
        pool.submit(new Loader());
    }

    private Bitmap loadFromHttp(final String name, int reqW, int reqH) {

        DiskLruCache.Editor editor = null;
        try {

            editor = diskCache.edit(hashKeyFromUrl(name));
            OutputStream os = editor.newOutputStream(0);
            URL url = new URL(getUrl(name));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();

            int len = 0 ;
            byte[] buf = new byte[1024];
            while((len=is.read(buf)) != -1){
                os.write(buf,0,len);
            }

            editor.commit();
            diskCache.flush();
            os.flush();
            os.close();
            conn.disconnect();
            return loadBitmapFromDisk(name,reqW,reqH);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("CATCH EXCEPTION");
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

//        while (!future.isDone()) {
////            File file = new File(Constant.tempDir + "/" + name + ".0");
////            File file2 = new File(Constant.tempDir + "/" + name);
////            file.renameTo(file2);
////            DiskLruCache.Editor editor = null;
////
////
////            try {
////                editor = diskCache.edit(name);
////                return decodeStream(, reqW, reqH);
////            } catch (Exception e) {
////                e.printStackTrace();
////                return null;
////            }
//            return loadBitmapFromDisk(name,reqW,reqH);
////            return BitmapFactory.decodeStream(new FileInputStream(file2));
//        }


    }

    public Bitmap loadBitmapFromDisk(String name , int reqW, int  reqH){

        Bitmap bitmap = null;
        try {
            DiskLruCache.Snapshot snapshot = diskCache.get(hashKeyFromUrl(name));
            if (snapshot != null) {
                InputStream is = snapshot.getInputStream(0);
                 bitmap =  (decodeStream(is, reqW, reqH));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }


    private String getUrl(String name) {

        try {
            String encodeFileName = URLEncoder.encode(name, "utf-8");
            String finalUrl = Constant.baseLink + encodeFileName;
            LogUtil.e(finalUrl);
            return finalUrl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Bitmap decodeStream(InputStream is, int reqW, int reqH) {
        try {
            FileDescriptor fd = ((FileInputStream) is).getFD();
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fd, null, o);
            int sampleSize = calculateInSampleSize(o, reqW, reqH);
            o.inSampleSize = sampleSize;
            o.inJustDecodeBounds = false;
            return BitmapFactory.decodeFileDescriptor(fd, null, o);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

//        LogUtil.e( (b==null )+"ASDASD");
    }

//    private Bitmap decodeInputStream(InputStream is, int reqW, int reqH){
//        try {
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(is);
//            int sampleSize = calculateInSampleSize(o, reqW, reqH);
//            o.inSampleSize = sampleSize;
//            o.inJustDecodeBounds = false;
//            return BitmapFactory.decodeStream(is,)
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
            e.printStackTrace();
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

//    class Task implements Runnable {
//        String names;
//
//        public Task(String names) {
//            this.names  = names;
//        }
//
//        @Override
//        public void run() {
//        }
//
//
//    }

//    public class ImageViewLoader {
//        Bitmap bitmap;
//        ImageView imageView ;
//
//        public ImageViewLoader(Bitmap bitmap ) {
//            this.bitmap = bitmap;
//        }
//
//        public void into(ImageView imageView) {
////            imageView.setImageBitmap(bitmap);
//            this.imageView = imageView;
//            mHandler.obtainMessage(1,this).sendToTarget();
//        }
//    }

    public class Result{
        public ImageView imageView;
        public Bitmap bitmap;
        public String url;


        public Result(String url,ImageView imageView, Bitmap bitmap) {
            this.imageView = imageView;
            this.bitmap = bitmap;
            this.url = url;
        }
    }

}

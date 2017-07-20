package com.example.saprodontia.Models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.MathUtil;
import com.example.saprodontia.Utils.ThumbUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class ContentModle {

    private Context mContext;
    private ContentResolver mContentResolver;
    private List<FileInfo> fileInfos;
    private List<FileInfo> videoInfos;
    private List<FileInfo> imageInfos;
    private List<FileInfo> musicInfos;

    private App app;

    private OnVideoDataChangedListener mOnVideoDataChangedListener;
    private OnImageDataChangedListener mOnImageDataChangedListener;
    private OnFileDataChangedListener mOnFileDataChangedListener;
    private OnMusicDataChangedListener mOnMusicDataChangedListener;

    public void setmOnVideoDataChangedListener(OnVideoDataChangedListener listener) {
        this.mOnVideoDataChangedListener = listener;
    }

    public void setmOnImageDataChangedListener(OnImageDataChangedListener listener) {
        this.mOnImageDataChangedListener = listener;
    }

    public void setmOnFileDataChangedListener(OnFileDataChangedListener listener) {
        this.mOnFileDataChangedListener = listener;
    }

    public void setmOnMusicDataChangedListener(OnMusicDataChangedListener listener) {
        this.mOnMusicDataChangedListener = listener;
    }

    public ContentModle(Context context) {
        mContext = context;
        app = (App) context.getApplicationContext();
        mContentResolver = mContext.getContentResolver();
        fileInfos = app.getFileInfos();
        videoInfos = app.getVideoInfos();
        imageInfos = app.getImageInfos();
        musicInfos = app.getMusicInfos();

    }

    public List<FileInfo> getImagesFile() {


        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE, MediaStore.Images.ImageColumns.TITLE, MediaStore.Images.ImageColumns._ID},
                null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        if (cursor != null && cursor.moveToFirst())
            do {
                FileInfo info = new FileInfo();
                info.setLocation(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
                info.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)));
                info.setInitSize(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE))));
                info.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)));
                imageInfos.add(info);
            } while (cursor.moveToNext());

        cursor.close();
        return imageInfos;

    }

    public List<FileInfo> getVideosFile() {
        if(!app.isExecp())
        new ViedoLoadTask().execute();
        app.setExecp(true);
        return videoInfos;

    }

    public List<FileInfo> getFileInfos() {
        if(!app.isExecd())
        new FileLoadTask().execute();
        app.setExecd(true);
        return fileInfos;
    }

    public List<FileInfo> getMusicInfos(){
        if(!app.isExecm())
        new MusicLoadtask().execute();
        app.setExecm(true);
        return musicInfos;
    }

    private class MusicLoadtask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            int t = 0 ;

            String[] projection = new String[]{MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE};

            Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,null,null,MediaStore.Audio.Media.DATE_MODIFIED + " desc");
            if (cursor != null && cursor.moveToFirst())
                do {
                    FileInfo info = new FileInfo();
                    Long initSize = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));

                    info.setInitSize(initSize);
                    info.setSize(MathUtil.bytoKbOrMb(initSize));
                    info.setName(cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))));
                    info.setIcon(mContext.getDrawable(R.drawable.music));
                    info.setLocation(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));

                    musicInfos.add(info);

                    if (t % 10 == 0 || t < 10)
                        publishProgress();

                    t++;

               }while (cursor.moveToNext());
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mOnMusicDataChangedListener.onDataChanged();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mOnMusicDataChangedListener.onDataChanged();
            super.onProgressUpdate(values);
        }
    }

    private class FileLoadTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            int t =  0;

            String[] projection = new String[]{MediaStore.Files.FileColumns.DATA,
                    MediaStore.Files.FileColumns.MIME_TYPE, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.TITLE};

            String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                    + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                    + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                    + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";

            String[] selectionArgs = new String[]{"application/msword", "application/pdf", "application/vnd.ms-powerpoint", "application/vnd.ms-excel"};

            Cursor cursor = mContentResolver.query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_MODIFIED + " desc");

            if (cursor != null && cursor.moveToFirst())
                do {
                    FileInfo info = new FileInfo();
                    String location = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    String format = location.substring(location.indexOf('.'), location.length());
                    Long initSize = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)));
                    info.setInitSize(initSize);
                    info.setSize(MathUtil.bytoKbOrMb(initSize));
                    info.setLocation(location);
                    info.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE)) + format);

                    if (format.equals(".xls")) {
                        info.setIcon(mContext.getDrawable(R.drawable.excel));
                    } else if (format.equals(".doc")) {
                        info.setIcon(mContext.getDrawable(R.drawable.word));
                    } else if (format.equals(".pdf")) {
                        info.setIcon(mContext.getDrawable(R.drawable.pdf));
                    } else {
                        info.setIcon(mContext.getDrawable(R.drawable.powerpoint));
                    }

                    fileInfos.add(info);

                    if (t % 10 == 0 || t < 10)
                        publishProgress();
                    t++;

                } while (cursor.moveToNext());

            cursor.close();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mOnFileDataChangedListener.onDataChanged();
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mOnFileDataChangedListener.onDataChanged();
            super.onPostExecute(aVoid);
        }
    }

    private class ViedoLoadTask extends AsyncTask<Void, Void, Void> {

        BitmapFactory.Options ops;

        public ViedoLoadTask() {
            ops = new BitmapFactory.Options();
            ops.inSampleSize = 4;
            ops.inScaled = true;
        }

        @Override
        protected Void doInBackground(Void... params) {

            int t = 0;

            Cursor cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns.DISPLAY_NAME,
                            MediaStore.Video.VideoColumns.SIZE, MediaStore.Video.VideoColumns._ID}, null, null, MediaStore.Video.VideoColumns.DATE_MODIFIED + "  desc");

            if (cursor != null && cursor.moveToFirst())
                do {

                    FileInfo info = new FileInfo();
                    String location = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
                    Long id = Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID)));

                    info.setLocation(location);
                    info.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)));
                    info.setInitSize(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE))));
                    info.setSize(MathUtil.bytoKbOrMb(info.getInitSize()));
                    info.setBitmap(ThumbUtils.cutBitmap(MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id, MediaStore.Video.Thumbnails.MINI_KIND, ops), 200, 200));
                    videoInfos.add(info);

                    if (t % 10 == 0 || t < 10)
                        publishProgress();

                    t++;
                } while (cursor.moveToNext());

            if (cursor != null)
                cursor.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mOnFileDataChangedListener.onDataChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mOnVideoDataChangedListener.onDataChange();
            super.onProgressUpdate(values);
        }
    }

    public interface OnVideoDataChangedListener {
        void onDataChange();
    }

    public interface OnImageDataChangedListener {
        void onDataChanged();
    }

    public interface OnFileDataChangedListener {
        void onDataChanged();
    }

    public interface OnMusicDataChangedListener{
        void onDataChanged();
    }


}

package com.example.saprodontia.Models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class ContentModle {

    private Context mContext;
    private List<FileInfo> fileInfos;
    ContentResolver mContentResolver;

    public ContentModle(Context context) {
        mContext = context;
        fileInfos = new ArrayList<>();
        mContentResolver = mContext.getContentResolver();
        ;
    }

    public List<FileInfo> getImagesFile() {


        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE, MediaStore.Images.ImageColumns.TITLE},
                null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        if (cursor != null)
            cursor.moveToFirst();
        do {
            FileInfo info = new FileInfo();
            info.setLocation(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
            info.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)));
            info.setInitSize(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE))));
            fileInfos.add(info);
        } while (cursor.moveToNext());

        cursor.close();
        return fileInfos;

    }

    public List<FileInfo> getVideosFile() {

        Cursor cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.VideoColumns.SIZE, MediaStore.Video.VideoColumns.DISPLAY_NAME,
                        MediaStore.Video.VideoColumns.SIZE}, null, null, MediaStore.Video.VideoColumns.TITLE + "  desc");

        if (cursor != null)
            cursor.moveToFirst();
        do {
            FileInfo info = new FileInfo();
            info.setLocation(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)));
            info.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)));
            info.setInitSize(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE))));
            fileInfos.add(info);
        } while (cursor.moveToNext());

        cursor.close();
        return fileInfos;

    }

    public List<FileInfo> getFileInfos() {

        List<FileInfo> fileInfos = new ArrayList<>();
        String[] projection = new String[]{MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.MIME_TYPE, MediaStore.Files.FileColumns.SIZE,MediaStore.Files.FileColumns.TITLE};

        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";

        String[] selectionArgs = new String[]{"application/msword", "application/pdf", "application/vnd.ms-powerpoint", "application/vnd.ms-excel"};

        Cursor cursor = mContentResolver.query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_MODIFIED + " desc");

        if (cursor != null)
            cursor.moveToFirst();
        do {
            FileInfo info = new FileInfo();
            String location = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            String format = location.substring(location.indexOf('.'), location.length());
                info.setInitSize(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE))));
                info.setLocation(location);
                info.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE)) + format);
                fileInfos.add(info);

        } while (cursor.moveToNext());


        cursor.close();
        return fileInfos;
    }


}

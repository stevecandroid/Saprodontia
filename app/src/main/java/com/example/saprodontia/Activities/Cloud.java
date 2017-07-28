package com.example.saprodontia.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.saprodontia.Adapter.CouldPicAdapter;
import com.example.saprodontia.Adapter.PicAdapter;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.QiniuUtils;
import com.example.saprodontia.View.RecycleDecoration;

import java.util.ArrayList;
import java.util.List;

public class Cloud extends AppCompatActivity {

    private List<String> datas;
    private QiniuUtils qiniu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        datas = new ArrayList<>();

        qiniu = new QiniuUtils();

        new Thread(new Runnable() {
            @Override
            public void run() {
                datas = qiniu.getImageSourceKey();
                Cloud.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(new GridLayoutManager(Cloud.this,3));
                        recyclerView.setAdapter(new CouldPicAdapter(Cloud.this,datas));
                        recyclerView.addItemDecoration(new RecycleDecoration());
                    }
                });

            }
        }).start();



    }
}

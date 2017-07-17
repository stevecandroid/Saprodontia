package com.example.saprodontia.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.saprodontia.Adapter.Adapter;
import com.example.saprodontia.Models.AppInfoModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    private OnSendListener mOnSendListener;
    private AppInfoModle mAppInfoModle;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mAppInfoModle = new AppInfoModle();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_all);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, mAppInfoModle.initSimAppInfos());
        recyclerView.setAdapter(adapter);

        RadioButton radio_photo = (RadioButton) findViewById(R.id.radio_photo);
        RadioButton radio_app = (RadioButton) findViewById(R.id.radio_app);
        RadioButton radio_viedo = (RadioButton) findViewById(R.id.radio_video);
        RadioButton radio_docu = (RadioButton) findViewById(R.id.radio_docu);
        RadioButton radio_another = (RadioButton) findViewById(R.id.radio_another);
        Button bt_ensure = (Button)findViewById(R.id.bt_ensure);
        bt_ensure.setOnClickListener(this);
        radio_another.setOnClickListener(this);
        radio_docu.setOnClickListener(this);
        radio_viedo.setOnClickListener(this);
        radio_app.setOnClickListener(this);
        radio_photo.setOnClickListener(this);

        mAppInfoModle.setmOnDataChangeListener(new AppInfoModle.OnDataChangeListener() {
            @Override
            public void onDataChange() {
                adapter.notifyDataSetChanged();
            }
        });

        radio_app.setChecked(true);




//        AppInfoModle modleAt = new AppInfoModle();
//        List<ApplicationInfo> infos = AppInfoModle.get();
//        ApplicationInfo i = infos.get(1);
//
//        for(ApplicationInfo info : infos){
//            LogUtil.e(info.loadLabel(getPackageManager()));
//        }
//        List<AppInfo> t = AppInfoModle.getSimAppinfos();


    }

    public void setOnSendListener(OnSendListener listener){
        mOnSendListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_send:{
                mOnSendListener.send();
            }
        }
    }

    public interface OnSendListener{
        void send();
    }
}

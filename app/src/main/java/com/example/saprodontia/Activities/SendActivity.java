package com.example.saprodontia.Activities;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.saprodontia.Adapter.MyPagerAdapter;
import com.example.saprodontia.Application.App;
import com.example.saprodontia.Fragment.FragApp;
import com.example.saprodontia.Fragment.FragDocu;
import com.example.saprodontia.Fragment.FragMusic;
import com.example.saprodontia.Fragment.FragPhoto;
import com.example.saprodontia.Fragment.FragVideo;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.Models.UpLoadModel;
import com.example.saprodontia.Models.WifiModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SendActivity extends BaseActivity implements View.OnClickListener {

    private WifiModle socketModle;
    private ViewPager viewPager;
    private BottomSheetDialog bsDialog;
    private UpLoadModel mUpLoadModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);


        socketModle = new WifiModle(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mUpLoadModel = UpLoadModel.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        initDialog();
        initAdapter();
        FloatingActionButton bt_ensure = (FloatingActionButton) findViewById(R.id.bt_ensure);
        bt_ensure.setOnClickListener(this);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();


        List<FileInfo> fileInfos = DataSupport.findAll(FileInfo.class);
        for(FileInfo fileInfo : fileInfos){
            LogUtil.e(fileInfo.getName());
        }



        tabLayout.getTabAt(0).setText("图片");
        tabLayout.getTabAt(1).setText("应用");
        tabLayout.getTabAt(2).setText("文档");
        tabLayout.getTabAt(3).setText("视频");
        tabLayout.getTabAt(4).setText("音乐");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ensure: {

                bsDialog.show();

                break;

            }
        }
    }

    private void initAdapter(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragPhoto());
        fragments.add(new FragApp());
        fragments.add(new FragDocu());
        fragments.add(new FragVideo());
        fragments.add(new FragMusic());
        MyPagerAdapter mySendPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(mySendPagerAdapter);

    }

    private void initDialog(){
        bsDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog,null,false);
        TextView tv_send = (TextView) view.findViewById(R.id.tv_send);
        TextView tv_rece = (TextView) view.findViewById(R.id.tv_rece);
        TextView tv_up = (TextView) view.findViewById(R.id.tv_up);

        tv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/7/24

                List<FileInfo> tempList = new ArrayList<FileInfo>();
                List<FileInfo> sendDatas = ((App)(App.getContext())).getSenDatas();
                tempList.addAll(sendDatas);
                sendDatas.clear();
                sendBroadcast(new Intent("SEND_DATA_CHANGE"));
                mUpLoadModel.upLoadFile(tempList);
                bsDialog.dismiss();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/7/24
                ToastUtil.showToast("SEND");
                socketModle.shareFile(((App)(getApplicationContext())).getSenDatas());
                bsDialog.dismiss();
                sendBroadcast(new Intent("SEND_DATA_CHANGE"));
            }
        });

        tv_rece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/7/24
                ToastUtil.showToast("RECE");
                bsDialog.dismiss();
            }
        });

        bsDialog.setContentView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private long start = 0;
    @Override
    public void onBackPressed() {

        ToastUtil.showToast("再按一次退出程序");
        if(System.currentTimeMillis()-start > 1500 ){
            start = System.currentTimeMillis();
        }else{
            finish();
        }

    }

}

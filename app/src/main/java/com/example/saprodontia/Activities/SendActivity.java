package com.example.saprodontia.Activities;

import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import com.example.saprodontia.Models.WifiModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    private WifiModle socketModle;
    private ViewPager viewPager;
    private BottomSheetDialog bsDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        initDialog();
        socketModle = new WifiModle(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        FloatingActionButton bt_ensure = (FloatingActionButton) findViewById(R.id.bt_ensure);
        bt_ensure.setOnClickListener(this);
        tabLayout.setupWithViewPager(viewPager);
        initAdapter();

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
                ToastUtil.showToast("UPLOAD");
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

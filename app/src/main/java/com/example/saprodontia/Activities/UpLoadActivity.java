package com.example.saprodontia.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.saprodontia.Adapter.MyPagerAdapter;
import com.example.saprodontia.Fragment.FragApp;
import com.example.saprodontia.Fragment.FragDocu;
import com.example.saprodontia.Fragment.FragMusic;
import com.example.saprodontia.Fragment.FragPhoto;
import com.example.saprodontia.Fragment.FragVideo;
import com.example.saprodontia.Models.UpLoadModel;
import com.example.saprodontia.R;

import java.util.ArrayList;
import java.util.List;

public class UpLoadActivity extends BaseActivity {

    private UpLoadModel upLoadModel;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FloatingActionButton bt_ensure = (FloatingActionButton) findViewById(R.id.bt_ensure);
        bt_ensure.setVisibility(View.GONE);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initAdapter();
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("上传中");
        tabLayout.getTabAt(1).setText("上传完成");
    }

    private void initAdapter(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragPhoto());
        fragments.add(new FragApp());
        MyPagerAdapter mySendPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(mySendPagerAdapter);

    }
}

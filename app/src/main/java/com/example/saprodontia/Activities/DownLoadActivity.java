package com.example.saprodontia.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.saprodontia.Adapter.MyPagerAdapter;
import com.example.saprodontia.Fragment.FragAll;
import com.example.saprodontia.Fragment.FragDowding;
import com.example.saprodontia.Fragment.FragUping;
import com.example.saprodontia.R;

import java.util.ArrayList;
import java.util.List;

public class DownLoadActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FloatingActionButton bt_ensure = (FloatingActionButton) findViewById(R.id.bt_ensure);
//        bt_ensure.setVisibility(View.GONE);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initAdapter();
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("下载中");
        tabLayout.getTabAt(1).setText("下载完成");
    }


    private void initAdapter(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragDowding());
        fragments.add(new FragAll());
        MyPagerAdapter mySendPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(mySendPagerAdapter);
    }
}

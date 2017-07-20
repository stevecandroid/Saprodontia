package com.example.saprodontia.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.saprodontia.Adapter.MyPagerAdapter;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class DownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);

        List<Fragment> fraglist = new ArrayList<>();
        fraglist.add(new FragC());
        fraglist.add(new FragA());
        final ViewPager pager_two = (ViewPager) findViewById(R.id.pager_two);
        pager_two.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fraglist));
        tabLayout.setupWithViewPager(pager_two);

        tabLayout.getTabAt(0).setText("正在下载");
        tabLayout.getTabAt(1).setText("已完成");



    }
}

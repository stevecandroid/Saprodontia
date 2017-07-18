package com.example.saprodontia.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saprodontia.Adapter.MyPagerAdapter;
import com.example.saprodontia.R;

import java.util.ArrayList;
import java.util.List;

public class DownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        List<Fragment> fraglist = new ArrayList<>();
        fraglist.add(new FragC());
        fraglist.add(new FragC());
        fraglist.add(new FragC());
        ViewPager pager_two = (ViewPager) findViewById(R.id.pager_two);
        pager_two.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fraglist));
    }
}

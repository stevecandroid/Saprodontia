package com.example.saprodontia.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 铖哥 on 2017/7/18.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fraglist;

    public MyPagerAdapter(FragmentManager fm , List<Fragment> fraglist) {
        super(fm);
        this.fraglist = fraglist;
    }

    @Override
    public Fragment getItem(int position) {
        return fraglist.get(position);
    }

    @Override
    public int getCount() {
        return fraglist.size();
    }

}

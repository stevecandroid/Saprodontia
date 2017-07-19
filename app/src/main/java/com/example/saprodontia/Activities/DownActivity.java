package com.example.saprodontia.Activities;

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

        final RadioButton radio_all = (RadioButton) findViewById(R.id.radio_all);
        final RadioButton radio_down = (RadioButton) findViewById(R.id.radio_down);

        radio_down.setChecked(true);

        List<Fragment> fraglist = new ArrayList<>();
        fraglist.add(new FragC());
        fraglist.add(new FragA());
        final ViewPager pager_two = (ViewPager) findViewById(R.id.pager_two);
        pager_two.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fraglist));


        pager_two.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0 ){
                    radio_down.setChecked(true);
                }else if(position == 1){
                    radio_all.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radio_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager_two.setCurrentItem(1);
            }
        });

        radio_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager_two.setCurrentItem(0);
            }
        });
    }
}

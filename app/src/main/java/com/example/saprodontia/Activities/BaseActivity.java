package com.example.saprodontia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saprodontia.R;
import com.example.saprodontia.Utils.ToastUtil;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.download:
                startActivity(new Intent(BaseActivity.this,Cloud.class));
                // TODO: 2017/7/24
                break;

            case R.id.upload :
                startActivity(new Intent(BaseActivity.this,UpLoadActivity.class));
                // TODO: 2017/7/24
                break;
        }
        return true;
    }
    
}

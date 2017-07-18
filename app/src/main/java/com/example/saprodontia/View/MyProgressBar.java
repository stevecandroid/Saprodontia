package com.example.saprodontia.View;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 铖哥 on 2017/7/17.
 */

public class MyProgressBar extends View {

    private int max;
    private  int progress;

    public MyProgressBar(Context context) {
        super(context);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

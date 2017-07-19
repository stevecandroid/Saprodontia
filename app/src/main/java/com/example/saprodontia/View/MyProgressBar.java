package com.example.saprodontia.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.saprodontia.R;

/**
 * Created by 铖哥 on 2017/7/17.
 */

public class MyProgressBar extends View {

    private int max = 1;
    private  long progress = 1;
    private TypedArray typedArray ;
    private Paint mPaint;
    private boolean enable = false;

    public MyProgressBar(Context context) {
        super(context);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        mPaint.setColor(typedArray.getColor(R.styleable.MyProgressBar_color, Color.argb(160 ,233, 182, 199)));
        mPaint.setStyle((Paint.Style.FILL));
        mPaint.setAntiAlias(true);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(enable)
        canvas.drawRect(0,0,progress*getWidth()/max,getHeight(),mPaint);

    }

    public int getMax() {

        return max;

    }

    public void setMax(int max) {
        enable = true;
        this.max = max;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
        invalidate();
    }
}

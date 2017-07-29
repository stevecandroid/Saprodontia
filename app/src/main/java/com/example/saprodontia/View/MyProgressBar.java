package com.example.saprodontia.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;

/**
 * Created by 铖哥 on 2017/7/17.
 */

public class MyProgressBar extends View {

    private double max = 1.0;
    private volatile Double destProgress = 0.0;
    private TypedArray typedArray ;
    private Paint mPaint;


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
        super.  onDraw(canvas);
            canvas.drawRect(0, 0, (float) ((destProgress * getWidth() +0.0 )/( max + 0.0)), getHeight(), mPaint);

    }


    public void setMax(double max) {
        this.max = max;
    }


    public void setProgress(double destProgress) {
            this.destProgress = destProgress;
        postInvalidate();
    }




}

package com.example.saprodontia.View;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 铖哥 on 2017/7/19.
 */

public class RecycleDecoration extends RecyclerView.ItemDecoration {

    Paint mPaint;



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        mPaint = new Paint();
        mPaint.setColor(Color.argb(255,217, 217, 217));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int count = parent.getChildCount();
        for(int i = 0 ; i < count ;i++){
            View view = parent.getChildAt(i);
            c.drawRect(view.getLeft(),view.getTop(), view.getRight(),view.getBottom(),mPaint);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,parent.getWidth(),10);
        super.getItemOffsets(outRect, view, parent, state);
    }
}

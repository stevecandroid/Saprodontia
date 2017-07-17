package com.example.saprodontia.View;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by 铖哥 on 2017/7/13.
 */

public class MLine extends LinearLayout {

    private RecyclerView recyclerView;
    private int downY;
    private int deafultY = 0;
    private long start;
    private MyHandler handler;



    public MLine(Context context) {
        super(context);


    }

    public MLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handler = new MyHandler();


    }

    public MLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public MLine(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        for (int i = 0; recyclerView == null && i < getChildCount(); i++) {
            if (getChildAt(i) instanceof RecyclerView) {
                recyclerView = (RecyclerView) getChildAt(i);
                break;
            }
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                downY = (int) ev.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {

                if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstCompletelyVisibleItemPosition() == 0
                        && (ev.getY() - downY) > 10) {

                    if (deafultY == 0) {
                        deafultY = (int) ev.getY();
                    }

                    scrollTo(0, (int) (deafultY - ev.getY()) / 3);
                    return true;
                } else if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition() ==
                        (recyclerView.getAdapter().getItemCount() - 1)
                        && (ev.getY() - downY) < -10) {

                    if (deafultY == 0) {
                        deafultY = (int) ev.getY();
                    }

                    scrollTo(0, (int) (deafultY - ev.getY()) / 3);
                    return true;

                } else {
                    break;
                }

            }

            case MotionEvent.ACTION_UP: {
                start = System.currentTimeMillis();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int temp = getScrollY();
                        long cur = start;

                        while (cur - start < 150) {
                            Message m = new Message();
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            cur = System.currentTimeMillis();
                            double precent = (1.0f - ((cur - start) / 150.0f));
                            m.arg1 = (int) (precent * temp);
                            handler.sendMessage(m);
                        }
                    }
                }).start();
                deafultY = 0;
                break;
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            scrollTo(0, msg.arg1);
        }
    }

}

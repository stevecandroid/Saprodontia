package com.example.saprodontia.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;

/**
 * Created by 铖哥 on 2017/7/24.
 */

public class FABavior extends FloatingActionButton.Behavior {

    public FABavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        LogUtil.e(dyConsumed);
        LogUtil.e(dyUnconsumed);

        if ( (dyConsumed > 0 && child.getVisibility()==View.VISIBLE ) ) {
            hide(child);
        }else if( dyConsumed < 0 && child.getVisibility()!=View.VISIBLE){
            show(child);
        }

    }

    private void show(final FloatingActionButton fab){
        Animation animation = AnimationUtils.loadAnimation(fab.getContext(),R.anim.show_rotate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fab.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(animation);
    }

    private void hide(final FloatingActionButton fab){
        Animation animation = AnimationUtils.loadAnimation(fab.getContext(),R.anim.hide_rotate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(animation);
    }

}

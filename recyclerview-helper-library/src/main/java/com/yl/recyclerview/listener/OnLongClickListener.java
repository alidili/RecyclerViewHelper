package com.yl.recyclerview.listener;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Custom delay long click listener.
 * <p>
 * Created by yangle on 2017/11/22.
 * Website：http://www.yangle.tech
 */

public abstract class OnLongClickListener implements View.OnTouchListener {

    private int x, y, downX, downY;
    private Timer timer;
    // Default long click delay 200ms.
    private long delay = 200;

    public OnLongClickListener() {
    }

    public OnLongClickListener(long delay) {
        this.delay = delay;
    }

    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent) {
        x = (int) motionEvent.getX();
        y = (int) motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Record coordinate
                downX = x;
                downY = y;

                // Delay response
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Offset 10dp
                                float offsetX = Math.abs(x - downX);
                                float offsetY = Math.abs(y - downY);
                                float offsetLevel = dp2px(view.getContext(), 10);
                                if (offsetX <= offsetLevel && offsetY <= offsetLevel) {
                                    onLongClickListener();
                                    timer = null;
                                }
                            }
                        });
                    }
                }, delay);
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                if (timer != null) {
                    timer.cancel();
                }
                break;
        }
        return true;
    }

    /**
     * Callback method for long click event.
     */
    public abstract void onLongClickListener();

    /**
     * dp to px
     *
     * @param context Context
     * @param dp      value/dp
     * @return value/px
     */
    private float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}

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
public abstract class OnCustomClickListener implements View.OnTouchListener {

    private int mX, mY, mDownX, mDownY;
    private Timer mTimer;
    // Default long click delay 200ms.
    private long mDelay = 200;

    public OnCustomClickListener() {
    }

    public OnCustomClickListener(long delay) {
        this.mDelay = delay;
    }

    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent) {
        mX = (int) motionEvent.getX();
        mY = (int) motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Record coordinate
                mDownX = mX;
                mDownY = mY;

                // Delay response
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Offset 10dp
                                float offsetX = Math.abs(mX - mDownX);
                                float offsetY = Math.abs(mY - mDownY);
                                float offsetLevel = dp2px(view.getContext(), 10);
                                if (offsetX <= offsetLevel && offsetY <= offsetLevel) {
                                    onLongClickListener(view);
                                    mTimer = null;
                                }
                            }
                        });
                    }
                }, mDelay);
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                if (mTimer != null) {
                    mTimer.cancel();
                    onClickListener(view);
                }
                break;
        }
        return true;
    }

    /**
     * Callback method for long click event.
     *
     * @param view View
     */
    public abstract void onLongClickListener(View view);

    /**
     * Callback method for click event.
     *
     * @param view View
     */
    public abstract void onClickListener(View view);

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

package com.yl.recyclerview.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * Interface definition for a callback to be invoked when a touch event is
 * dispatched to this view. The callback will be invoked before the touch
 * event is given to the view.
 * <p>
 * Created by yangle on 2018/11/29.
 * Website：http://www.yangle.tech
 */
public interface OnItemTouchListener {

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param view     The view the touch event has been dispatched to.
     * @param event    The MotionEvent object containing full information about the event.
     * @param position The view position the touch event has been dispatched to.
     * @return True if the listener has consumed the event, false otherwise.
     */
    boolean onItemTouch(View view, MotionEvent event, int position);
}

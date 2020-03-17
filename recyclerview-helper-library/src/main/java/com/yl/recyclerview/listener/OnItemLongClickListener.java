package com.yl.recyclerview.listener;

import android.view.View;

/**
 * Interface definition for a callback to be invoked when a view has been clicked and held..
 * <p>
 * Created by yangle on 2018/11/29.
 * Website：http://www.yangle.tech
 */
public interface OnItemLongClickListener {

    /**
     * Called when a view has been clicked and held.
     *
     * @param view     The view that was clicked and held.
     * @param position The view position that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    boolean onItemLongClick(View view, int position);
}

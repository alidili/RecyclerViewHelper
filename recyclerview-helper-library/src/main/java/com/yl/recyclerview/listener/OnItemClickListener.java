package com.yl.recyclerview.listener;

import android.view.View;

/**
 * Interface definition for a callback to be invoked when a view is clicked.
 * <p>
 * Created by yangle on 2018/11/29.
 * Website：http://www.yangle.tech
 */
public interface OnItemClickListener {

    /**
     * Called when a view has been clicked.
     *
     * @param view     The view that was clicked.
     * @param position The view position that was clicked.
     */
    void onItemClick(View view, int position);
}

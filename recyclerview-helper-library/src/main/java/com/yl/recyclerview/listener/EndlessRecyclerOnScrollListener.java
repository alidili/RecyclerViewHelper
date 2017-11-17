package com.yl.recyclerview.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView slip listener
 * <p>
 * Created by yangle on 2017/10/26.
 * Website：http://www.yangle.tech
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    // Used to mark whether it is sliding up
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // When not sliding
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // Get the last fully displayed item position
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            // To determine whether to slide to the last item, and is sliding up
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                // Load More
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // > 0 indicates that it is sliding up, <= 0 means stop or slide down
        isSlidingUpward = dy > 0;
    }

    /**
     * Load more callbacks
     */
    public abstract void onLoadMore();
}

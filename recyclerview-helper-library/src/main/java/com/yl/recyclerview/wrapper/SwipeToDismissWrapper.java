package com.yl.recyclerview.wrapper;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.recyclerview.helper.ItemSwipeCallback;

import java.util.List;

/**
 * An adapter can make {@link RecyclerView} swipe-to-dismiss.
 * <p>
 * Created by yangle on 2017/12/18.
 * Website：http://www.yangle.tech
 */
public class SwipeToDismissWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemSwipeCallback.ItemDismissListener {

    // Origin adapter
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    // Data list
    private List<?> mDataList;
    // A listener for a dismissal event.
    private ItemSwipeCallback.ItemDismissListener mItemDismissListener;

    public SwipeToDismissWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, List<?> dataList) {
        this.mAdapter = adapter;
        this.mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public void onItemDismiss(int position) {
        if (mItemDismissListener != null) {
            mItemDismissListener.onItemDismiss(position);
        } else {
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * Attach to RecyclerView for swipe-to-dismiss.
     *
     * @param recyclerView RecyclerView
     */
    public void attachToRecyclerView(RecyclerView recyclerView) {
        attachToRecyclerView(recyclerView, LinearLayout.VERTICAL);
    }

    /**
     * Attach to RecyclerView for swipe-to-dismiss.
     *
     * @param recyclerView RecyclerView
     * @param orientation  LinearLayout.HORIZONTAL or LinearLayout.VERTICAL
     */
    public void attachToRecyclerView(RecyclerView recyclerView, int orientation) {
        ItemTouchHelper.Callback callback = new ItemSwipeCallback(this, orientation);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Set a listener for a dismissal event.
     *
     * @param itemDismissListener {@link ItemSwipeCallback.ItemDismissListener}
     */
    public void setItemDismissListener(ItemSwipeCallback.ItemDismissListener itemDismissListener) {
        this.mItemDismissListener = itemDismissListener;
    }
}

package com.yl.recyclerview.wrapper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;

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
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    // Data list
    private List<?> list;
    // A listener for a dismissal event.
    private ItemSwipeCallback.ItemDismissListener itemDismissListener;

    public SwipeToDismissWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, List<?> list) {
        this.adapter = adapter;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        adapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public void onItemDismiss(int position) {
        if (itemDismissListener != null) {
            itemDismissListener.onItemDismiss(position);
        } else {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * Attach to RecyclerView for swipe-to-dismiss.
     *
     * @param recyclerView RecyclerView
     */
    public void attachToRecyclerView(RecyclerView recyclerView) {
        ItemTouchHelper.Callback callback = new ItemSwipeCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Set a listener for a dismissal event.
     *
     * @param itemDismissListener {@link ItemSwipeCallback.ItemDismissListener}
     */
    public void setItemDismissListener(ItemSwipeCallback.ItemDismissListener itemDismissListener) {
        this.itemDismissListener = itemDismissListener;
    }
}

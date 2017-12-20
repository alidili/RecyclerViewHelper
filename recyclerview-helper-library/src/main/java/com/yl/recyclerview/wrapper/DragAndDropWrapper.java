package com.yl.recyclerview.wrapper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;

import com.yl.recyclerview.helper.ItemMoveCallback;
import com.yl.recyclerview.listener.OnLongClickListener;

import java.util.Collections;
import java.util.List;

/**
 * An adapter can make {@link RecyclerView} basic drag & drop.
 * <p>
 * Created by yangle on 2017/11/17.
 * Website：http://www.yangle.tech
 */

public class DragAndDropWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemMoveCallback.ItemMoveListener {

    // Origin adapter
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    // Data list
    private List<?> mDataList;
    // ItemTouchHelper
    private ItemTouchHelper mItemTouchHelper;
    // Default long click delay 200ms.
    private long mDelay = 200;

    public DragAndDropWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, List<?> dataList) {
        this.mAdapter = adapter;
        this.mDataList = dataList;
    }

    public DragAndDropWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, List<?> dataList,
                              long delay) {
        this.mAdapter = adapter;
        this.mDataList = dataList;
        this.mDelay = delay;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        mAdapter.onBindViewHolder(holder, position);

        // Custom delay long click listener.
        // The item root view must be clickable.
        holder.itemView.setOnTouchListener(new OnLongClickListener(mDelay) {
            @Override
            public void onLongClickListener() {
                if (mItemTouchHelper != null) {
                    mItemTouchHelper.startDrag(holder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * Attach to RecyclerView for drag & drop.
     *
     * @param recyclerView RecyclerView
     */
    public void attachToRecyclerView(RecyclerView recyclerView) {
        attachToRecyclerView(recyclerView, false);
    }

    /**
     * Attach to RecyclerView for drag & drop.
     *
     * @param recyclerView RecyclerView
     * @param isFreedom    true: item can move up|down|left|right
     *                     false: item can move up|down
     */
    public void attachToRecyclerView(RecyclerView recyclerView, boolean isFreedom) {
        ItemTouchHelper.Callback callback = new ItemMoveCallback(this, isFreedom);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }
}

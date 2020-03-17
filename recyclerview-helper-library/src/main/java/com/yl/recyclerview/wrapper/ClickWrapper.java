package com.yl.recyclerview.wrapper;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.yl.recyclerview.listener.OnItemClickListener;
import com.yl.recyclerview.listener.OnItemLongClickListener;
import com.yl.recyclerview.listener.OnItemTouchListener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * An adapter that support {@link OnItemClickListener}、{@link OnItemLongClickListener} and
 * {@link OnItemTouchListener}.
 * <p>
 * Created by yangle on 2018/11/29.
 * Website：http://www.yangle.tech
 */
public class ClickWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Origin adapter
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mLongClickListener;
    private OnItemTouchListener mTouchListener;

    public ClickWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        mAdapter.onBindViewHolder(holder, position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position);
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mLongClickListener.onItemLongClick(v, position);
                }
            });
        }
        if (mTouchListener != null) {
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return mTouchListener.onItemTouch(v, event, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     *
     * @param onItemClickListener The callback that will run
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    /**
     * Register a callback to be invoked when this view is clicked and held. If this view is not
     * long clickable, it becomes long clickable.
     *
     * @param onLongClickListener The callback that will run
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
    }

    /**
     * Register a callback to be invoked when a touch event is sent to this view.
     *
     * @param onTouchListener the touch listener to attach to this view
     */
    public void setOnItemTouchListener(OnItemTouchListener onTouchListener) {
        this.mTouchListener = onTouchListener;
    }
}

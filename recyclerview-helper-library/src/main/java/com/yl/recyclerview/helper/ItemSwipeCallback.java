package com.yl.recyclerview.helper;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

/**
 * An implementation of {@link ItemTouchHelper.Callback} that enables swipe-to-dismiss.
 * <p>
 * Created by yangle on 2017/12/18.
 * Website：http://www.yangle.tech
 */

public class ItemSwipeCallback extends ItemTouchHelper.Callback {

    private ItemDismissListener mItemDismissListener;

    public ItemSwipeCallback(ItemDismissListener itemDismissListener) {
        this.mItemDismissListener = itemDismissListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags.
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        // NO OP
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // Notify the adapter of the dismissal.
        mItemDismissListener.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * Interface to listen for a dismissal event from a {@link ItemTouchHelper.Callback}.
     */
    public interface ItemDismissListener {

        /**
         * Called when an item has been dismissed by a swipe.
         *
         * @param position The position of the item dismissed.
         */
        void onItemDismiss(int position);
    }
}

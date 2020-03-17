package com.yl.recyclerview.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An implementation of {@link ItemTouchHelper.Callback} that enables basic drag & drop.
 * <p>
 * Created by yangle on 2017/11/17.
 * Website：http://www.yangle.tech
 */
public class ItemMoveCallback extends ItemTouchHelper.Callback {

    private ItemMoveListener mItemMoveListener;
    // true: item can move up|down|left|right
    // false: item can move up|down
    private boolean mIsFreedom;

    public ItemMoveCallback(ItemMoveListener itemMoveListener) {
        this.mItemMoveListener = itemMoveListener;
    }

    public ItemMoveCallback(ItemMoveListener itemMoveListener, boolean isFreedom) {
        this.mItemMoveListener = itemMoveListener;
        this.mIsFreedom = isFreedom;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager.
        int swipeFlags = 0;
        int dragFlags;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            if (mIsFreedom) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        // Notify the adapter of the move.
        mItemMoveListener.onItemMove(viewHolder, target, viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // NO OP
    }

    /**
     * Interface to listen for a move event from a {@link ItemTouchHelper.Callback}.
     */
    public interface ItemMoveListener {

        /**
         * Called when an item has been dragged far enough to trigger a move.
         *
         * @param viewHolder   The ViewHolder which is being dragged by the user.
         * @param target       The ViewHolder over which the currently active item is being
         *                     dragged.
         * @param fromPosition The start position of the moved item.
         * @param toPosition   Then resolved position of the moved item.
         * @return True if the item was moved to the new adapter position.
         */
        boolean onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target,
                           int fromPosition, int toPosition);
    }
}

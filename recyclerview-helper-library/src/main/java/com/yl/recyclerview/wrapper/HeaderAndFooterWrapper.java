package com.yl.recyclerview.wrapper;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Add header view and footer view.
 * <p>
 * Created by yangle on 2017/10/27.
 * Website：http://www.yangle.tech
 */
public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Origin adapter
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    // Header view type
    private final int TYPE_HEADER = 1000;
    // Footer view type
    private final int TYPE_FOOTER = 2000;
    // Header view list
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    // Footer view list
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    // GridLayoutManager, header view occupy a row, default is true.
    private boolean mIsMergeHeader = true;
    // GridLayoutManager, footer view occupy a row, default is true.
    private boolean mIsMergeFooter = true;

    public HeaderAndFooterWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return mHeaderViews.keyAt(position);

        } else if (isFooterView(position)) {
            return mFooterViews.keyAt(position - getHeaderViewCount() - getItemViewCount());
        }
        return mAdapter.getItemViewType(position - getHeaderViewCount());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view by the view type.
        if (mHeaderViews.get(viewType) != null) {
            return new HeaderAndFooterViewHolder(mHeaderViews.get(viewType));

        } else if (mFooterViews.get(viewType) != null) {
            return new HeaderAndFooterViewHolder(mFooterViews.get(viewType));
        }
        return mAdapter.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position) || isFooterView(position)) {
            return;
        }
        mAdapter.onBindViewHolder(holder, position - getHeaderViewCount());
    }

    @Override
    public int getItemCount() {
        return getItemViewCount() + getHeaderViewCount() + getFooterViewCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        // GridLayoutManager
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // If the current position is header view or footer view, the item occupy two cells,
                    // Normal item occupy a cell.
                    int itemViewType = getItemViewType(position);
                    if (itemViewType == TYPE_HEADER && mIsMergeHeader) {
                        return gridManager.getSpanCount();
                    }
                    if (itemViewType == TYPE_FOOTER && mIsMergeFooter) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /**
     * Header view and footer view holder.
     */
    private class HeaderAndFooterViewHolder extends RecyclerView.ViewHolder {

        HeaderAndFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Is't a header view.
     *
     * @param position Current view position
     * @return true: header view
     */
    public boolean isHeaderView(int position) {
        return position < getHeaderViewCount();
    }

    /**
     * Is't a footer view.
     *
     * @param position Current view position
     * @return true: footer view
     */
    public boolean isFooterView(int position) {
        return position >= getItemViewCount() + getHeaderViewCount();
    }

    /**
     * Get item view count.
     *
     * @return item view count
     */
    public int getItemViewCount() {
        return mAdapter.getItemCount();
    }

    /**
     * Get header view count.
     *
     * @return header view count
     */
    public int getHeaderViewCount() {
        return mHeaderViews.size();
    }

    /**
     * Get footer view count.
     *
     * @return footer view count
     */
    public int getFooterViewCount() {
        return mFooterViews.size();
    }

    /**
     * Add header view.
     *
     * @param view View
     */
    public void addHeaderView(View view) {
        mHeaderViews.append(getHeaderViewCount() + TYPE_HEADER, view);
    }

    /**
     * Add header view.
     *
     * @param view          View
     * @param isMergeHeader GridLayoutManager, header view occupy a row, default is true.
     */
    public void addHeaderView(View view, boolean isMergeHeader) {
        this.mIsMergeHeader = isMergeHeader;
        mHeaderViews.append(getHeaderViewCount() + TYPE_HEADER, view);
    }

    /**
     * Add footer view.
     *
     * @param view View
     */
    public void addFooterView(View view) {
        mFooterViews.append(getFooterViewCount() + TYPE_FOOTER, view);
    }

    /**
     * Add footer view.
     *
     * @param view          View
     * @param isMergeFooter GridLayoutManager, footer view occupy a row, default is true.
     */
    public void addFooterView(View view, boolean isMergeFooter) {
        this.mIsMergeFooter = isMergeFooter;
        mFooterViews.append(getFooterViewCount() + TYPE_FOOTER, view);
    }
}

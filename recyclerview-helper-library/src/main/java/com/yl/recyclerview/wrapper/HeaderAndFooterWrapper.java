package com.yl.recyclerview.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Add header view and footer view
 * Created by yangle on 2017/10/27.
 * <p>
 * Website：http://www.yangle.tech
 * <p>
 * GitHub：https://github.com/alidili
 * <p>
 * CSDN：http://blog.csdn.net/kong_gu_you_lan
 * <p>
 * JianShu：http://www.jianshu.com/u/34ece31cd6eb
 */

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Origin adapter
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    // Header view type
    private final int TYPE_HEADER = 1000;
    // Footer view type
    private final int TYPE_FOOTER = 2000;
    // Header view list
    private SparseArrayCompat<View> headerViews = new SparseArrayCompat<>();
    // Footer view list
    private SparseArrayCompat<View> footerViews = new SparseArrayCompat<>();

    public HeaderAndFooterWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return headerViews.keyAt(position);

        } else if (isFooterView(position)) {
            return footerViews.keyAt(position - getHeaderViewCount() - getItemViewCount());
        }
        return adapter.getItemViewType(position - getHeaderViewCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view by the view type
        if (headerViews.get(viewType) != null) {
            return new HeaderAndFooterViewHolder(headerViews.get(viewType));

        } else if (footerViews.get(viewType) != null) {
            return new HeaderAndFooterViewHolder(footerViews.get(viewType));
        }
        return adapter.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position) || isFooterView(position)) {
            return;
        }
        adapter.onBindViewHolder(holder, position - getHeaderViewCount());
    }

    @Override
    public int getItemCount() {
        return getItemViewCount() + getHeaderViewCount() + getFooterViewCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        // GridLayoutManager
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // If the current position is header view or footer view, the item occupy two cells
                    // Normal item occupy a cell
                    int itemViewType = getItemViewType(position);
                    return itemViewType == TYPE_HEADER || itemViewType == TYPE_FOOTER ?
                            gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * Header view and footer view holder
     */
    private class HeaderAndFooterViewHolder extends RecyclerView.ViewHolder {

        HeaderAndFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Is header view
     *
     * @param position Current view position
     * @return true: header view
     */
    public boolean isHeaderView(int position) {
        return position < getHeaderViewCount();
    }

    /**
     * Is footer view
     *
     * @param position Current view position
     * @return true: footer view
     */
    public boolean isFooterView(int position) {
        return position >= getItemViewCount() + getHeaderViewCount();
    }

    /**
     * Get item view count
     *
     * @return item view count
     */
    public int getItemViewCount() {
        return adapter.getItemCount();
    }

    /**
     * Get header view count
     *
     * @return header view count
     */
    public int getHeaderViewCount() {
        return headerViews.size();
    }

    /**
     * Get footer view count
     *
     * @return footer view count
     */
    public int getFooterViewCount() {
        return footerViews.size();
    }

    /**
     * Add header view
     *
     * @param view View
     */
    public void addHeaderView(View view) {
        headerViews.append(getHeaderViewCount() + TYPE_HEADER, view);
    }

    /**
     * Add footer view
     *
     * @param view View
     */
    public void addFooterView(View view) {
        footerViews.append(getFooterViewCount() + TYPE_FOOTER, view);
    }
}

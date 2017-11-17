package com.yl.recyclerview.wrapper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yl.recyclerview.R;

/**
 * Pull up to load more
 * <p>
 * Created by yangle on 2017/10/26.
 * Website：http://www.yangle.tech
 */

public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Context
    private Context context;
    // Origin adapter
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    // General view
    private final int TYPE_ITEM = 1;
    // Footer view
    private final int TYPE_FOOTER = 2;
    // The current loading state, the default is loading complete
    private int loadingState = 2;
    // Loading
    public final int LOADING = 1;
    // Loading done
    public final int LOADING_COMPLETE = 2;
    // Loading end
    public final int LOADING_END = 3;
    // Loading view
    private View loadingView;
    // Loading end view
    private View loadingEndView;
    // Loading view height
    private int loadingViewHeight;

    public LoadMoreWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        // The last item is set to footer view
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get context
        this.context = parent.getContext();

        // Create view by the view type
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_footer, parent, false);
            return new LoadMoreViewHolder(view);
        } else {
            return adapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;

            // Set loading view height
            loadMoreViewHolder.rlLoadingFooter.removeAllViews();
            if (loadingViewHeight > 0) {
                RelativeLayout.LayoutParams params =
                        (RelativeLayout.LayoutParams) loadMoreViewHolder.rlLoadingFooter.getLayoutParams();
                params.height = loadingViewHeight;
                loadMoreViewHolder.rlLoadingFooter.setLayoutParams(params);
            }

            // Display loading view
            switch (loadingState) {
                case LOADING: // Loading
                    if (loadingView != null) {
                        // Custom loading view
                        loadMoreViewHolder.rlLoadingFooter.addView(loadingView);
                    } else {
                        // Default loading view
                        View loadingView = View.inflate(context, R.layout.layout_loading, null);
                        loadMoreViewHolder.rlLoadingFooter.addView(loadingView);
                    }
                    break;

                case LOADING_COMPLETE: // Loading done
                    // TODO
                    break;

                case LOADING_END: // Loading end
                    if (loadingEndView != null) {
                        // Custom loading end view
                        loadMoreViewHolder.rlLoadingFooter.addView(loadingEndView);
                    } else {
                        // Default loading end view
                        View loadingEndView = View.inflate(context, R.layout.layout_loading_end, null);
                        loadMoreViewHolder.rlLoadingFooter.addView(loadingEndView);
                    }
                    break;

                default:
                    break;
            }
        } else {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 1;
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
                    // If the current position is footer view, then the item occupy two cells
                    // Normal item occupy a cell
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * Loading holder
     */
    private class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlLoadingFooter;

        LoadMoreViewHolder(View itemView) {
            super(itemView);
            rlLoadingFooter = itemView.findViewById(R.id.rl_loading_footer);
        }
    }

    /**
     * Set the pull-up state
     *
     * @param loadingState 0.Loading 1.Loading done 2.Loading end
     */
    public void setLoadState(int loadingState) {
        this.loadingState = loadingState;
        notifyDataSetChanged();
    }

    /**
     * Set loading view
     *
     * @param view Loading view
     */
    public void setLoadingView(View view) {
        this.loadingView = view;
    }

    /**
     * Set loading end view
     *
     * @param view Loading end view
     */
    public void setLoadingEndView(View view) {
        this.loadingEndView = view;
    }

    /**
     * Set loading view height
     *
     * @param height Height/px
     */
    public void setLoadingViewHeight(int height) {
        this.loadingViewHeight = loadingViewHeight;
    }
}

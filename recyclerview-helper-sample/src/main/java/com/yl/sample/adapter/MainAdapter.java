package com.yl.sample.adapter;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.sample.R;

import java.util.List;

/**
 * Index adapter.
 * <p>
 * Created by yangle on 2017/11/16.
 * Website：http://www.yangle.tech
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;
    private OnItemClickListener mItemClickListener;

    public MainAdapter(List<String> itemList) {
        this.mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_common, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MainHolder mainHolder = (MainHolder) holder;
        mainHolder.tvItem.setText(mItemList.get(position));

        // Set item click listener
        if (mItemClickListener != null) {
            mainHolder.cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(mainHolder.cvItem, mainHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    private class MainHolder extends RecyclerView.ViewHolder {

        CardView cvItem;
        TextView tvItem;

        MainHolder(View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.cv_item);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }

    /**
     * Set item click listener.
     *
     * @param onItemClickListener Click callback interface
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    /**
     * Click callback interface.
     */
    public interface OnItemClickListener {
        /**
         * Click callback method.
         *
         * @param view     Current view
         * @param position Click position
         */
        void onItemClick(View view, int position);
    }
}

package com.yl.sample.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.sample.R;

import java.util.List;

/**
 * Index adapter
 * Created by yangle on 2017/11/16.
 * <p>
 * Website：http://www.yangle.tech
 * <p>
 * GitHub：https://github.com/alidili
 * <p>
 * CSDN：http://blog.csdn.net/kong_gu_you_lan
 * <p>
 * JianShu：http://www.jianshu.com/u/34ece31cd6eb
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> itemList;
    private OnItemClickListener onItemClickListener;

    public MainAdapter(List<String> itemList) {
        this.itemList = itemList;
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
        mainHolder.tvItem.setText(itemList.get(position));

        // Set item click listener
        if (onItemClickListener != null) {
            mainHolder.cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(mainHolder.cvItem, mainHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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
     * Set item click listener
     *
     * @param onItemClickListener Click callback interface
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Click callback interface
     */
    public interface OnItemClickListener {
        /**
         * Click callback method
         *
         * @param view     Current view
         * @param position Click position
         */
        void onItemClick(View view, int position);
    }
}

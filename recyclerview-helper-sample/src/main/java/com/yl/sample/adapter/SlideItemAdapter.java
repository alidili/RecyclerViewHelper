package com.yl.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.yl.recyclerview.widget.SlideItemView;
import com.yl.sample.R;
import com.yl.sample.utils.DensityUtils;

import java.util.List;

/**
 * SlideItem adapter.
 * <p>
 * Created by yangle on 2018/1/4.
 * Website：http://www.yangle.tech
 */
public class SlideItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> mDataList;

    public SlideItemAdapter(List<String> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_slide_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvItem.setText(mDataList.get(position));
        recyclerViewHolder.slideItemView.setSlideLimit(DensityUtils.dp2px(mContext, 50));
        recyclerViewHolder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewHolder.slideItemView.reset();
                Toast.makeText(mContext, mDataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewHolder.tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mDataList.get(position) + " TOP", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mDataList.get(position) + " DELETE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        SlideItemView slideItemView;
        TextView tvItem;
        TextView tvTop;
        TextView tvDelete;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            slideItemView = itemView.findViewById(R.id.slide_item);
            tvItem = itemView.findViewById(R.id.tv_item);
            tvTop = itemView.findViewById(R.id.tv_top);
            tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }
}

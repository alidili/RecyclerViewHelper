package com.yl.sample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yl.recyclerview.widget.SuperDividerItemDecoration;
import com.yl.sample.R;
import com.yl.sample.adapter.SlideItemAdapter;
import com.yl.sample.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * SlideItem sample.
 * <p>
 * Created by yangle on 2019/1/4.
 * Website：http://www.yangle.tech
 */
public class SlideItemActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SlideItemAdapter mSlideItemAdapter;
    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);

        // Toolbar replace ActionBar
        setSupportActionBar(toolbar);

        // Simulate get data
        getData();
        mSlideItemAdapter = new SlideItemAdapter(mDataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        SuperDividerItemDecoration dividerItemDecoration = new SuperDividerItemDecoration(this,
                linearLayoutManager);
        dividerItemDecoration.setDividerHeight(DensityUtils.dp2px(this, 10));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mSlideItemAdapter);
    }

    /**
     * Simulate get data.
     */
    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            mDataList.add(String.valueOf(letter));
            letter++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.layout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SuperDividerItemDecoration dividerItemDecoration = null;
        switch (item.getItemId()) {
            case R.id.liner_layout:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                dividerItemDecoration = new SuperDividerItemDecoration(this, linearLayoutManager);
                break;

            case R.id.grid_layout:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                mRecyclerView.setLayoutManager(gridLayoutManager);
                dividerItemDecoration = new SuperDividerItemDecoration(this, gridLayoutManager);
                break;
        }
        if (dividerItemDecoration != null) {
            dividerItemDecoration.setDividerHeight(DensityUtils.dp2px(this, 10));
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            mRecyclerView.removeItemDecorationAt(0);
        }
        mDataList.clear();
        getData();
        mRecyclerView.setAdapter(mSlideItemAdapter);
        return super.onOptionsItemSelected(item);
    }
}

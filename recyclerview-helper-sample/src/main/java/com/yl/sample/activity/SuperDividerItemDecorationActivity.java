package com.yl.sample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yl.recyclerview.widget.SuperDividerItemDecoration;
import com.yl.sample.R;
import com.yl.sample.adapter.DividerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView divider sample.
 * <p>
 * Created by yangle on 2018/11/27.
 * Website：http://www.yangle.tech
 */
public class SuperDividerItemDecorationActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDataList = new ArrayList<>();
    private DividerAdapter mDividerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divider);

        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);

        // Toolbar replace ActionBar
        setSupportActionBar(toolbar);

        // Simulate get data
        getData();
        mDividerAdapter = new DividerAdapter(mDataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        SuperDividerItemDecoration dividerItemDecoration = new SuperDividerItemDecoration(this,
                linearLayoutManager);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.custom_bg_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mDividerAdapter);
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
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false);
                mRecyclerView.setLayoutManager(gridLayoutManager);
                dividerItemDecoration = new SuperDividerItemDecoration(this, gridLayoutManager);
                break;
        }
        if (dividerItemDecoration != null) {
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.custom_bg_divider));
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            mRecyclerView.removeItemDecorationAt(0);
        }
        mDataList.clear();
        getData();
        mRecyclerView.setAdapter(mDividerAdapter);
        return super.onOptionsItemSelected(item);
    }
}

package com.yl.sample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.yl.sample.R;
import com.yl.sample.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Header view and footer view sample.
 * <p>
 * Created by yangle on 2017/11/14.
 * Website：http://www.yangle.tech
 */
public class HeaderAndFooterViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
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
        CommonAdapter commonAdapter = new CommonAdapter(mDataList);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
        addHeaderAndFooterView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
    }

    /**
     * Add header view and footer view.
     */
    private void addHeaderAndFooterView() {
        // Add header view
        View headerView = View.inflate(this, R.layout.layout_header_footer, null);
        TextView headerItem = headerView.findViewById(R.id.tv_item);
        headerItem.setText("HeaderView");
        mHeaderAndFooterWrapper.addHeaderView(headerView);

        // Add footer view
        View footerView = View.inflate(this, R.layout.layout_header_footer, null);
        TextView footerItem = footerView.findViewById(R.id.tv_item);
        footerItem.setText("FooterView");
        mHeaderAndFooterWrapper.addFooterView(footerView);
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
        switch (item.getItemId()) {
            case R.id.liner_layout:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.grid_layout:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
        }
        mDataList.clear();
        getData();
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
        return super.onOptionsItemSelected(item);
    }
}

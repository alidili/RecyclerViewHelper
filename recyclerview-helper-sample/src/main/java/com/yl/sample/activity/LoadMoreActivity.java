package com.yl.sample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yl.recyclerview.listener.OnScrollListener;
import com.yl.recyclerview.wrapper.LoadMoreWrapper;
import com.yl.sample.R;
import com.yl.sample.adapter.CommonAdapter;
import com.yl.sample.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Pull down to refresh sample.
 * Pull up to load more.
 * <p>
 * Created by yangle on 2017/10/26.
 * Website：http://www.yangle.tech
 */
public class LoadMoreActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);

        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);

        // Toolbar replace ActionBar
        setSupportActionBar(toolbar);

        // Set the refresh view color
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        // Simulate get data
        getData();
        CommonAdapter commonAdapter = new CommonAdapter(mDataList);
        mLoadMoreWrapper = new LoadMoreWrapper(commonAdapter);
        //customLoadingView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mLoadMoreWrapper);

        // Set the pull-down refresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh data
                mDataList.clear();
                getData();
                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_COMPLETE);

                // Delay 1s close
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // Set the load more listener
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onLoadMore() {
                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING);

                if (mDataList.size() < 52) {
                    // Simulate get network data，delay 1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // Show loading end
                    mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    /**
     * Custom loading view.
     */
    private void customLoadingView() {
        // Custom loading view
        ProgressBar progressBar = new ProgressBar(this);
        mLoadMoreWrapper.setLoadingView(progressBar);

        // Custom loading end view
        TextView textView = new TextView(this);
        textView.setText("End");
        mLoadMoreWrapper.setLoadingEndView(textView);

        // Custom loading height
        mLoadMoreWrapper.setLoadingViewHeight(DensityUtils.dp2px(this, 50));
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
        mLoadMoreWrapper.setLoadState(mLoadMoreWrapper.LOADING_COMPLETE);
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        return super.onOptionsItemSelected(item);
    }
}

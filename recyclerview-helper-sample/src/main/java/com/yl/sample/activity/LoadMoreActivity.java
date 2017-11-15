package com.yl.sample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yl.recyclerview.listener.EndlessRecyclerOnScrollListener;
import com.yl.recyclerview.wrapper.LoadMoreWrapper;
import com.yl.sample.R;
import com.yl.sample.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Pull down to refresh
 * Pull up to load more
 * Created by yangle on 2017/10/26.
 * <p>
 * Website：http://www.yangle.tech
 * <p>
 * GitHub：https://github.com/alidili
 * <p>
 * CSDN：http://blog.csdn.net/kong_gu_you_lan
 * <p>
 * JianShu：http://www.jianshu.com/u/34ece31cd6eb
 */

public class LoadMoreActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);

        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Toolbar replace ActionBar
        setSupportActionBar(toolbar);

        // Set the refresh view color
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        // Simulate get data
        getData();
        CommonAdapter commonAdapter = new CommonAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(commonAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);

        // Set the pull-down refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh data
                dataList.clear();
                getData();
                loadMoreWrapper.notifyDataSetChanged();

                // Delay 1s close
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // Set the load more listener
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (dataList.size() < 52) {
                    // Simulate get network data，delay 1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // Show load end
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            dataList.add(String.valueOf(letter));
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
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.grid_layout:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
        }
        dataList.clear();
        getData();
        recyclerView.setAdapter(loadMoreWrapper);
        return super.onOptionsItemSelected(item);
    }
}

package com.yl.sample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.recyclerview.listener.OnItemClickListener;
import com.yl.recyclerview.listener.OnItemLongClickListener;
import com.yl.recyclerview.listener.OnItemTouchListener;
import com.yl.recyclerview.wrapper.ClickWrapper;
import com.yl.sample.R;
import com.yl.sample.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ClickWrapper sample.
 * <p>
 * Created by yangle on 2018/11/29.
 * Website：http://www.yangle.tech
 */
public class ClickActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ClickWrapper mClickWrapper;
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
        mClickWrapper = new ClickWrapper(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mClickWrapper);

        // Item click listener.
        mClickWrapper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ClickActivity.this, "ItemClick " + mDataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // Item long click listener.
        mClickWrapper.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                Toast.makeText(ClickActivity.this, "ItemLongClick " + mDataList.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Item touch listener.
        mClickWrapper.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public boolean onItemTouch(View view, MotionEvent event, int position) {
                return false;
            }
        });
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
        mRecyclerView.setAdapter(mClickWrapper);
        return super.onOptionsItemSelected(item);
    }
}

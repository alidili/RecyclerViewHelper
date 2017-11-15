package com.yl.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yl.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.yl.sample.R;
import com.yl.sample.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Header view and footer view
 * Created by yangle on 2017/11/14.
 * <p>
 * Website：http://www.yangle.tech
 * <p>
 * GitHub：https://github.com/alidili
 * <p>
 * CSDN：http://blog.csdn.net/kong_gu_you_lan
 * <p>
 * JianShu：http://www.jianshu.com/u/34ece31cd6eb
 */

public class HeaderAndFooterViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer);

        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Toolbar replace ActionBar
        setSupportActionBar(toolbar);

        // Simulate get data
        getData();
        CommonAdapter commonAdapter = new CommonAdapter(dataList);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(headerAndFooterWrapper);
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
        recyclerView.setAdapter(headerAndFooterWrapper);
        return super.onOptionsItemSelected(item);
    }
}

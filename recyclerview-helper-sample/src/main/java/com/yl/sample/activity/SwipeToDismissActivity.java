package com.yl.sample.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yl.recyclerview.helper.ItemSwipeCallback;
import com.yl.recyclerview.wrapper.SwipeToDismissWrapper;
import com.yl.sample.R;
import com.yl.sample.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Swipe to dismiss sample.
 * <p>
 * Created by yangle on 2017/12/19.
 * Website：http://www.yangle.tech
 */

public class SwipeToDismissActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeToDismissWrapper swipeToDismissWrapper;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);

        // Toolbar replace ActionBar
        setSupportActionBar(toolbar);

        // Simulate get data
        getData();
        CommonAdapter commonAdapter = new CommonAdapter(dataList);
        swipeToDismissWrapper = new SwipeToDismissWrapper(commonAdapter, dataList);
        swipeToDismissWrapper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(swipeToDismissWrapper);

        // Set a listener for a dismissal event.
        swipeToDismissWrapper.setItemDismissListener(new ItemSwipeCallback.ItemDismissListener() {
            @Override
            public void onItemDismiss(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SwipeToDismissActivity.this);
                builder.setMessage("Do you want to delete this item ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataList.remove(position);
                                swipeToDismissWrapper.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                swipeToDismissWrapper.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    /**
     * Simulate get data.
     */
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
        recyclerView.setAdapter(swipeToDismissWrapper);
        return super.onOptionsItemSelected(item);
    }
}

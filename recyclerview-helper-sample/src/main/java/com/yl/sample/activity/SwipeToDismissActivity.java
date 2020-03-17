package com.yl.sample.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class SwipeToDismissActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SwipeToDismissWrapper mSwipeToDismissWrapper;
    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        mSwipeToDismissWrapper = new SwipeToDismissWrapper(commonAdapter, mDataList);
        mSwipeToDismissWrapper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSwipeToDismissWrapper);

        // Set a listener for a dismissal event.
        mSwipeToDismissWrapper.setItemDismissListener(new ItemSwipeCallback.ItemDismissListener() {
            @Override
            public void onItemDismiss(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SwipeToDismissActivity.this);
                builder.setMessage("Do you want to delete this item ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDataList.remove(position);
                                mSwipeToDismissWrapper.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSwipeToDismissWrapper.notifyDataSetChanged();
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
        mRecyclerView.setAdapter(mSwipeToDismissWrapper);
        return super.onOptionsItemSelected(item);
    }
}

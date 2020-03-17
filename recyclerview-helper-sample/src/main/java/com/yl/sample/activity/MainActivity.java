package com.yl.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yl.sample.R;
import com.yl.sample.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Index
 * <p>
 * Created by yangle on 2017/10/26.
 * Website：http://www.yangle.tech
 */
public class MainActivity extends BaseActivity implements MainAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // Toolbar replace ActionBar
        setSupportActionBar(toolbar);

        // Item list
        List<String> itemList = new ArrayList<>();
        itemList.add("Pull up to load more");
        itemList.add("HeaderView / FooterView");
        itemList.add("Drag & Drop");
        itemList.add("Swipe to dismiss");
        itemList.add("SlideItemView");
        itemList.add("Divider item decoration");
        itemList.add("Click / LongClick / Touch");

        MainAdapter mainAdapter = new MainAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);

        // Set item click listener
        mainAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = null;
        switch (position) {
            case 0: // Pull up to load more
                intent = new Intent(this, LoadMoreActivity.class);
                break;

            case 1: // HeaderView / FooterView
                intent = new Intent(this, HeaderAndFooterViewActivity.class);
                break;

            case 2: // Drag & Drop
                intent = new Intent(this, DragAndDropActivity.class);
                break;

            case 3: // Swipe to dismiss
                intent = new Intent(this, SwipeToDismissActivity.class);
                break;

            case 4: // SlideItemView
                intent = new Intent(this, SlideItemActivity.class);
                break;

            case 5: // Divider item decoration
                intent = new Intent(this, SuperDividerItemDecorationActivity.class);
                break;

            case 6: // Click / LongClick / Touch
                intent = new Intent(this, ClickActivity.class);
                break;

            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}

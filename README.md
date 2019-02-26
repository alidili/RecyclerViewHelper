![Banner](https://github.com/alidili/RecyclerViewHelper/blob/master/screenshots/RecyclerViewHelper.png)

[![Travis](https://travis-ci.com/alidili/RecyclerViewHelper.svg?branch=master)](https://travis-ci.com/alidili/RecyclerViewHelper)
[![Jitpack](https://jitpack.io/v/alidili/RecyclerViewHelper.svg)](https://jitpack.io/#alidili/RecyclerViewHelper)
[![APK](https://img.shields.io/badge/APK%20download-1.97MB-blue.svg)](https://github.com/alidili/RecyclerViewHelper/raw/master/RecyclerViewHelper.apk)
[![API](https://img.shields.io/badge/API-16%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![LICENSE](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://github.com/alidili/RecyclerViewHelper/blob/master/LICENSE)

:zap: A library to make RecyclerView more easy :zap:

**[中文](https://www.jianshu.com/p/827769fc0290)**

## Dependency

Add this in your root build.gradle file:

``` groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Then, add the library to your module build.gradle:

``` groovy
dependencies {
    implementation 'com.github.alidili:RecyclerViewHelper:latest.release.here'
}
```

## Pull up to load more

![Pull up to load more](https://github.com/alidili/RecyclerViewHelper/blob/master/screenshots/PullUpToLoadMore.gif)

``` java
CommonAdapter commonAdapter = new CommonAdapter(mDataList);
mLoadMoreWrapper = new LoadMoreWrapper(commonAdapter);
customLoadingView();
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mLoadMoreWrapper);

// Set the load more listener
mRecyclerView.addOnScrollListener(new OnScrollListener() {
	@Override
	public void onLoadMore() {
		// TODO
	}
});
```

**Custom loading view**

``` java
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
```

## HeaderView / FooterView

![HeaderView / FooterView](https://github.com/alidili/RecyclerViewHelper/blob/master/screenshots/HeaderFooterView.gif)

``` java
CommonAdapter commonAdapter = new CommonAdapter(mDataList);
mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
addHeaderAndFooterView();
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
```

**Add header view and footer view**

``` java
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
```

## Drag & Drop

![Drag & Drop](https://github.com/alidili/RecyclerViewHelper/blob/master/screenshots/DragAndDrop.gif)

``` java
CommonAdapter commonAdapter = new CommonAdapter(mDataList);
// Default response time 200ms
mDragAndDropWrapper = new DragAndDropWrapper(commonAdapter, mDataList, 200);
mDragAndDropWrapper.attachToRecyclerView(mRecyclerView, true);
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mDragAndDropWrapper);
```

## Swipe to dismiss

![Swipe to dismiss](https://github.com/alidili/RecyclerViewHelper/blob/master/screenshots/SwipeToDismiss.gif)

``` java
CommonAdapter commonAdapter = new CommonAdapter(mDataList);
mSwipeToDismissWrapper = new SwipeToDismissWrapper(commonAdapter, mDataList);
mSwipeToDismissWrapper.attachToRecyclerView(mRecyclerView);
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mSwipeToDismissWrapper);

// Set a listener for a dismissal event.
mSwipeToDismissWrapper.setItemDismissListener(new ItemSwipeCallback.ItemDismissListener() {
	@Override
	public void onItemDismiss(final int position) {
		// TODO
	}
});
```

## SlideItemView

![SlideItemView](https://github.com/alidili/RecyclerViewHelper/blob/master/screenshots/SlideItemView.gif)

``` xml
<?xml version="1.0" encoding="utf-8"?>
<com.yl.recyclerview.widget.SlideItemView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/slide_item"
    android:layout_width="match_parent"
    android:layout_height="40dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">

        // Item

        <LinearLayout
            android:layout_width="140dp"
            android:layout_height="match_parent"
            android:orientation="horizontal">

            // Function button

        </LinearLayout>

    </LinearLayout>

</com.yl.recyclerview.widget.SlideItemView>
```

## Divider item decoration

![Divider item decoration](https://github.com/alidili/RecyclerViewHelper/blob/master/screenshots/DividerItemDecoration.gif)

``` java
mDividerAdapter = new DividerAdapter(mDataList);
LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
mRecyclerView.setLayoutManager(linearLayoutManager);
SuperDividerItemDecoration dividerItemDecoration = new SuperDividerItemDecoration(this, linearLayoutManager);
dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.custom_bg_divider));
mRecyclerView.addItemDecoration(dividerItemDecoration);
mRecyclerView.setAdapter(mDividerAdapter);
```

## Click / LongClick / Touch

``` java
CommonAdapter commonAdapter = new CommonAdapter(mDataList);
mClickWrapper = new ClickWrapper(commonAdapter);
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mClickWrapper);

mClickWrapper.setOnItemClickListener(new OnItemClickListener() {
	@Override
	public void onItemClick(View view, int position) {
		// TODO
	}
});
mClickWrapper.setOnItemLongClickListener(new OnItemLongClickListener() {
	@Override
	public boolean onItemLongClick(View view, int position) {
		// TODO
		return true;
	}
});
mClickWrapper.setOnItemTouchListener(new OnItemTouchListener() {
	@Override
	public boolean onItemTouch(View view, MotionEvent event, int position) {
		// TODO
		return false;
	}
});
```

## ProGuard

If you are using ProGuard you might need to add the following option:

``` xml
-keep class com.yl.recyclerview.** {
    *;
}
```

## License

```
Copyright (C) 2019 YangLe

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
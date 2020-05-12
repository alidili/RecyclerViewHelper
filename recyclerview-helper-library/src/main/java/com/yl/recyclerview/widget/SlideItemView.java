package com.yl.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * SlideItem is a {@link HorizontalScrollView} that can be used as {@link RecyclerView} item view.
 *
 * <p>
 * Created by yangle on 2019/1/4.
 * Website：http://www.yangle.tech
 */
public class SlideItemView extends HorizontalScrollView {

    private int mFunctionViewWidth;
    private int mSlideLimit;
    private boolean mIsExpansion;

    public SlideItemView(Context context) {
        this(context, null);
    }

    public SlideItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setFillViewport(true);
        this.setHorizontalScrollBarEnabled(false);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        // The SlideItem can only have one child view of type ViewGroup.
        ViewGroup rootView = null;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof ViewGroup) {
                rootView = (ViewGroup) view;
                break;
            }
        }
        if (rootView == null) {
            return;
        }

        int rootLayoutChildCount = rootView.getChildCount();
        if (rootLayoutChildCount < 2) {
            return;
        }
        View contentView = rootView.getChildAt(0);
        View functionView = rootView.getChildAt(1);
        int width = getMeasuredWidth();
        mFunctionViewWidth = functionView.getMeasuredWidth();
        if (mSlideLimit == 0) {
            mSlideLimit = mFunctionViewWidth / 2;
        }

        // SlideItem（ViewGroup（ViewGroup ViewGroup））
        rootView.layout(rootView.getLeft(), rootView.getTop(),
                rootView.getLeft() + width + mFunctionViewWidth, rootView.getBottom());
        contentView.layout(contentView.getLeft(), contentView.getTop(),
                contentView.getLeft() + width, contentView.getBottom());
        functionView.layout(contentView.getLeft() + width, contentView.getTop(),
                contentView.getLeft() + width + mFunctionViewWidth, contentView.getBottom());

        // Set content area width
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = width;
        contentView.setLayoutParams(layoutParams);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mIsExpansion) {
                    // slide to right
                    if (getScrollX() < mFunctionViewWidth - mSlideLimit) {
                        mIsExpansion = false;
                        smoothScrollTo(0, 0);
                    } else {
                        smoothScrollTo(mFunctionViewWidth, 0);
                    }
                } else {
                    // slide to left
                    if (getScrollX() > mSlideLimit) {
                        mIsExpansion = true;
                        smoothScrollTo(mFunctionViewWidth, 0);
                    } else {
                        smoothScrollTo(0, 0);
                    }
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * Set slide range.
     *
     * @param slideLimit 0~mFunctionViewWidth
     */
    public void setSlideLimit(int slideLimit) {
        this.mSlideLimit = slideLimit;
    }

    /**
     * Smooth scroll to (0, 0)
     */
    public void reset() {
        mIsExpansion = false;
        smoothScrollTo(0, 0);
    }
}

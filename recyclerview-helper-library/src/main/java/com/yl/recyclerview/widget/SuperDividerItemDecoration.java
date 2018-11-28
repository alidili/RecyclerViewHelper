package com.yl.recyclerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * SuperDividerItemDecoration is a {@link RecyclerView.ItemDecoration} that can be used as a divider
 * between items of {@link LinearLayoutManager} or {@link GridLayoutManager}.
 * It supports both {@link #HORIZONTAL} and {@link #VERTICAL} orientations.
 * <p>
 * Created by yangle on 2018/11/27.
 * Website：http://www.yangle.tech
 */

public class SuperDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "SuperDividerItem";
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with
     * {@link LinearLayoutManager} or {@link GridLayoutManager}.
     *
     * @param context       Current context, it will be used to access resources.
     * @param layoutManager {@link RecyclerView.LayoutManager}. Should be {@link LinearLayoutManager}
     *                      or {@link GridLayoutManager}.
     */
    public SuperDividerItemDecoration(Context context, RecyclerView.LayoutManager layoutManager) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null || layoutManager == null) {
            return;
        }
        a.recycle();
        if (layoutManager instanceof GridLayoutManager) {
            setOrientation(((GridLayoutManager) layoutManager).getOrientation());
        } else if (layoutManager instanceof LinearLayoutManager) {
            setOrientation(((LinearLayoutManager) layoutManager).getOrientation());
        }
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager or {@link GridLayoutManager}} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            return;
        }
        if (layoutManager instanceof GridLayoutManager) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);

        } else if (layoutManager instanceof LinearLayoutManager) {
            if (mOrientation == VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        if (mDivider == null) {
            return;
        }

        canvas.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isLastRow(child, parent)) {
                continue;
            }

            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int left = mBounds.left;
            final int right = mBounds.right;
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null || mDivider == null) {
            return;
        }

        canvas.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isLastColumn(child, parent)) {
                continue;
            }

            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int top = mBounds.top;
            final int bottom = mBounds.bottom;
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (isLastRow(view, parent)) {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else if (isLastColumn(view, parent)) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }

        } else if (layoutManager instanceof LinearLayoutManager) {
            if (mOrientation == VERTICAL && !isLastRow(view, parent)) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else if (!isLastColumn(view, parent)) {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }

    private boolean isLastRow(View view, RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return false;
        }

        int childCount = adapter.getItemCount();
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            if (mOrientation == VERTICAL) {
                childCount = childCount - childCount % spanCount;
                return position >= childCount;
            }
            return (position + 1) % spanCount == 0;

        } else if (layoutManager instanceof LinearLayoutManager) {
            return position + 1 == childCount;
        }
        return false;
    }

    private boolean isLastColumn(View view, RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return false;
        }

        int childCount = adapter.getItemCount();
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            if (mOrientation == VERTICAL) {
                return (position + 1) % spanCount == 0;
            }
            childCount = childCount - childCount % spanCount;
            return position >= childCount;

        } else if (layoutManager instanceof LinearLayoutManager) {
            return position + 1 == childCount;
        }
        return false;
    }
}

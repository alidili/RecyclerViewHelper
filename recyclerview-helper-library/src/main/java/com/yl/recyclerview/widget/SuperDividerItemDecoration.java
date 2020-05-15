package com.yl.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.recyclerview.R;

/**
 * SuperDividerItemDecoration is a {@link RecyclerView.ItemDecoration} that can be used as a divider
 * between items of {@link LinearLayoutManager} or {@link GridLayoutManager}.
 * It supports both {@link #HORIZONTAL} and {@link #VERTICAL} orientations.
 * <p>
 * Created by yangle on 2018/11/27.
 * Website：http://www.yangle.tech
 */
public class SuperDividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private Drawable mDivider;
    private int mDividerWidth;
    private int mDividerHeight;

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
        mDivider = context.getResources().getDrawable(R.drawable.bg_divider);
        if (mDivider == null || layoutManager == null) {
            return;
        }
        mDividerWidth = mDivider.getIntrinsicWidth();
        mDividerHeight = mDivider.getIntrinsicHeight();
        if (layoutManager instanceof GridLayoutManager) {
            setOrientation(((GridLayoutManager) layoutManager).getOrientation());
        } else if (layoutManager instanceof LinearLayoutManager) {
            setOrientation(((LinearLayoutManager) layoutManager).getOrientation());
        }
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
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
        mDividerWidth = mDivider.getIntrinsicWidth();
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * Sets width for this divider.
     *
     * @param dividerWidth Width that should be used to {@link LinearLayoutManager}
     *                     or {@link GridLayoutManager}.
     */
    public void setDividerWidth(int dividerWidth) {
        if (dividerWidth < 0) {
            return;
        }
        this.mDividerWidth = dividerWidth;
    }

    /**
     * Sets height for this divider.
     *
     * @param dividerHeight Height that should be used to {@link LinearLayoutManager}
     *                      or {@link GridLayoutManager}.
     */
    public void setDividerHeight(int dividerHeight) {
        if (dividerHeight < 0) {
            return;
        }
        this.mDividerHeight = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            return;
        }
        if (layoutManager instanceof GridLayoutManager) {
            drawVerticalLine(c, parent);
            drawHorizontalLine(c, parent);

        } else if (layoutManager instanceof LinearLayoutManager) {
            if (mOrientation == VERTICAL) {
                drawHorizontalLine(c, parent);
            } else {
                drawVerticalLine(c, parent);
            }
        }
    }

    /**
     * Draw a horizontal line.
     *
     * @param canvas Canvas
     * @param parent RecyclerView
     */
    private void drawHorizontalLine(Canvas canvas, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null || mDivider == null) {
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
            int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            // Horizontal GridLayout display in the left, margin top or bottom mDividerHeight / 2.
            if (mOrientation == HORIZONTAL && layoutManager instanceof GridLayoutManager) {
                bottom += mDividerHeight / 2;
            }
            final int top = bottom - mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    /**
     * Draw a vertical line.
     *
     * @param canvas Canvas
     * @param parent RecyclerView
     */
    private void drawVerticalLine(Canvas canvas, RecyclerView parent) {
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

            layoutManager.getDecoratedBoundsWithMargins(child, mBounds);
            final int top = mBounds.top;
            final int bottom = mBounds.bottom;
            int right = mBounds.right + Math.round(child.getTranslationX());
            // Vertical GridLayout display in the top, margin left or right mDividerWidth / 2.
            if (mOrientation == VERTICAL && layoutManager instanceof GridLayoutManager) {
                right += mDividerWidth / 2;
            }
            final int left = right - mDividerWidth;
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
            if (mOrientation == VERTICAL) {
                // Vertical GridLayout display in the top, margin left or right mDividerWidth / 2.
                if (isLastRow(view, parent)) {
                    outRect.set(mDividerWidth / 2, 0, mDividerWidth / 2, 0);
                } else {
                    outRect.set(mDividerWidth / 2, 0, mDividerWidth / 2, mDividerHeight);
                }
            } else {
                // Horizontal GridLayout display in the left, margin top or bottom mDividerHeight / 2.
                if (isLastColumn(view, parent)) {
                    outRect.set(0, mDividerHeight / 2, 0, mDividerHeight / 2);
                } else {
                    outRect.set(0, mDividerHeight / 2, mDividerWidth, mDividerHeight / 2);
                }
            }

        } else if (layoutManager instanceof LinearLayoutManager) {
            if (isLastRow(view, parent)) {
                outRect.set(0, 0, 0, 0);
            } else {
                if (mOrientation == VERTICAL) {
                    outRect.set(0, 0, 0, mDividerHeight);
                } else {
                    outRect.set(0, 0, mDividerWidth, 0);
                }
            }
        }
    }

    /**
     * Determine if it is the last row.
     *
     * @param view   ChildView
     * @param parent RecyclerView
     * @return true is the last row.
     */
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

    /**
     * Determine if it is the last column.
     *
     * @param view   ChildView
     * @param parent RecyclerView
     * @return true is the last column.
     */
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
                return (position + 1) % spanCount == 0 || position == childCount - 1;
            }
            childCount = childCount - childCount % spanCount;
            return position >= childCount;

        } else if (layoutManager instanceof LinearLayoutManager) {
            return position + 1 == childCount;
        }
        return false;
    }
}

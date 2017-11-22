package com.yl.sample.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Unit conversion utils.
 * <p>
 * Created by yangle on 2017/11/16.
 * Website：http://www.yangle.tech
 */

public class DensityUtils {

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dp      dp值
     * @return px值
     */
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param sp      sp值
     * @return px值
     */
    public static int sp2px(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }
}

package com.spriteapp.utillibrary;

import android.content.Context;

/**
 * Screen attributes util
 * Created by kuangxiaoguo on 2017/12/13.
 */

public class ScreenUtil {

    /**
     * Get device width.
     *
     * @param context You can use your application context.
     * @return Screen width.
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Get devices height.
     *
     * @param context You can use your application context.
     * @return Screen height.
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * Dimension to pixels.
     *
     * @param context You can use your application context.
     * @param dp      The incoming dimension value.
     * @return Pixels value.
     */
    public static int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * Pixels to dimension.
     *
     * @param context You can use your application context.
     * @param px      The incoming pixels value.
     * @return Dimension value.
     */
    public static int px2dp(Context context, float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }
}

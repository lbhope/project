package com.zero.materialdesign.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class DimenUtils {

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;
    private static DisplayMetrics mMetrics = Resources.getSystem().getDisplayMetrics();

    private static float applyDimension(int unit, float value) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, mMetrics);
            case PX_TO_DP:
                return value / mMetrics.density;
            case PX_TO_SP:
                return value / mMetrics.scaledDensity;
        }
       return 0;
    }

    public static int dp2px(float value) {
        return (int) applyDimension(DP_TO_PX, value);
    }

    public static int sp2px(float value) {
        return (int) applyDimension(SP_TO_PX, value);
    }

    public static int px2dp(float value) {
        return (int) applyDimension(PX_TO_DP, value);
    }

    public static int px2sp(float value) {
        return (int) applyDimension(PX_TO_SP, value);
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int getScreenWidth() {
        return mMetrics.widthPixels;
    }


    public static int getScreenHeight() {
        return mMetrics.heightPixels;
    }



    public static float getDensity() {
        return mMetrics.density;
    }

    public static float getScaleDensity() {
        return mMetrics.scaledDensity;
    }

    /**
     * 获取statusbar的高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static void updateLayoutMargin(View view, int l, int t, int r, int b) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (params instanceof ViewGroup.MarginLayoutParams) {
            updateMargin(view, (ViewGroup.MarginLayoutParams) params, l, t, r, b);
        }
    }

    public static void updateBottomMargin(View view, int b) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) params;
            updateLayoutMargin(view, layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, b);
        }
    }

    private static void updateMargin(View view, ViewGroup.MarginLayoutParams params, int l, int t,
                                     int r, int b) {
        if (view == null)
            return;
        if (l != -3)
            params.leftMargin = l;
        if (t != -3)
            params.topMargin = t;
        if (r != -3)
            params.rightMargin = r;
        if (b != -3)
            params.bottomMargin = b;
        view.setLayoutParams(params);
    }

    public static void updateLayout(View view, int w, int h) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (w != -3)
            params.width = w;
        if (h != -3)
            params.height = h;
        view.setLayoutParams(params);
    }


}

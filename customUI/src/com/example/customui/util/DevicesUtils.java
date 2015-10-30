package com.example.customui.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DevicesUtils {

	public static int mScreenHeight;
	public static int mScreenWidth;
	public static float mDensity;

	public static void init(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		mScreenWidth = outMetrics.widthPixels;
		mDensity = outMetrics.density;
	}

}

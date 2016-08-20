package com.example.zzl.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ToolUtils {
	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return metrics;
	}
}

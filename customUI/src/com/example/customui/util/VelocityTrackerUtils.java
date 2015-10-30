package com.example.customui.util;

import android.view.MotionEvent;
import android.view.VelocityTracker;
/**
 * 加速度工具类
 * @author libin
 *
 */
public class VelocityTrackerUtils {

	public static VelocityTracker mVelocityTracker;

	/**
	 * 获取加速度
	 * 
	 * @return
	 */
	public static int[] getVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		return new int[] { (int) mVelocityTracker.getXVelocity(),
				(int) mVelocityTracker.getYVelocity() };
	}

	/**
	 * 释放资源
	 */
	public static void recycleVelocity() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	/**
	 * 初始化加速度检测器
	 * 
	 * @param event
	 */
	public static void obtainVelocity(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

}

package com.example.customui.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.customui.util.DevicesUtils;
import com.example.customui.util.Logger;

/**
 * 垂直方向ViewPager
 * 
 * @author libin
 * 
 */
public class VerticalLinearLayout extends ViewGroup {

	private int mScreenHeight;
	private int mTouchSlop;

	public VerticalLinearLayout(Context context) {
		super(context);
		init();
	}

	public VerticalLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mScreenHeight = DevicesUtils.mScreenHeight;

		// 初始化一个最小滑动距离
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int count = getChildCount();
		for (int i = 0; i < count; ++i) {
			View childView = getChildAt(i);
			measureChild(childView, widthMeasureSpec, mScreenHeight);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (!changed) {
			return;
		}
		int count = getChildCount();
		// 设置主布局的高度
		MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
		lp.height = mScreenHeight * count;
		setLayoutParams(lp);
		Logger.getLogger()
				.i("main left: " + getLeft() + " " + getTop() + "  "
						+ getBottom());
		for (int i = 0; i < count; ++i) {
			View childView = getChildAt(i);
			childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
			Logger.getLogger().i(
					"top: " + i * mScreenHeight + " bottom: " + (i + 1)
							* mScreenHeight);
		}
	}

	private boolean isScroll = false;
	private int firstY = 0;
	private int downY;

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// switch (ev.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// firstY = (int) ev.getY();
	// break;
	// case MotionEvent.ACTION_MOVE:
	// int dis = (int) (ev.getY()-firstY);
	// if (Math.abs(dis)>mTouchSlop) {
	// isScroll = true;
	// }
	// break;
	// case MotionEvent.ACTION_UP:
	//
	// break;
	//
	// default:
	// break;
	// }
	// return super.dispatchTouchEvent(ev);
	// }

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// if (!isScroll) {
		// return super.onTouchEvent(ev);
		// }
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int curY = (int) ev.getY();
			int dis = (int) (curY - downY);
			int scrollY = getScrollY();
			
			scrollBy(0, dis);
			Logger.getLogger().i("dis: " + dis+" scrollY:"+scrollY);
			downY = curY;
			break;
		case MotionEvent.ACTION_UP:
			break;
 
		}
		postInvalidate();
		return true;
	}
}

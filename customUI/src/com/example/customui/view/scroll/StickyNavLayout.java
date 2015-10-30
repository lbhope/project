package com.example.customui.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

public class StickyNavLayout extends LinearLayout {
 

	private int mTopViewHeight;
	private ViewGroup mInnerScrollView;
	private boolean isTopHidden = false;

	private OverScroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mTouchSlop;
	private int mMaximumVelocity, mMinimumVelocity;

	private float mLastY;
	private boolean mDragging;

	private boolean isInControl = false;

	public StickyNavLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
		
		mScroller = new OverScroller(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mMaximumVelocity = ViewConfiguration.get(context)
				.getScaledMaximumFlingVelocity();
		mMinimumVelocity = ViewConfiguration.get(context)
				.getScaledMinimumFlingVelocity();
		System.out.println("---------StickyNavLayout---------------");
	}
  
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			float dy = y - mLastY;
 
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			float dy = y - mLastY;
			if (Math.abs(dy) > mTouchSlop) {
				mDragging = true;
				return true;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mDragging = false;
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

 

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastY = y;
			return true;
		case MotionEvent.ACTION_MOVE:
			float dy = y - mLastY;
			Log.e("TAG", "dy = " + dy + " , y = " + y + " , mLastY = " + mLastY);
			scrollBy(0, (int) -dy);
			mLastY = y;
			break;
		case MotionEvent.ACTION_CANCEL:
			 
			break;
		case MotionEvent.ACTION_UP:
			 
			break;
		}
		invalidate();
		return true;
	}

//	public void fling(int velocityY) {
//		mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
//		invalidate();
//	}

//	@Override
//	public void scrollTo(int x, int y) {
//		if (y < 0) {
//			y = 0;
//		}
//		if (y > mTopViewHeight) {
//			y = mTopViewHeight;
//		}
//		if (y != getScrollY()) {
//			super.scrollTo(x, y);
//		}
//
//		isTopHidden = getScrollY() == mTopViewHeight;
//
//	}

 
 
}
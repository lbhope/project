package com.example.customui.scroll;

import com.example.customui.util.DensityUtil;
import com.example.customui.util.Logger;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

public class MyScrollViewSlop extends ViewGroup {
	public MyScrollViewSlop(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public MyScrollViewSlop(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private Context context;

	/**
	 * 手指按下X的坐标
	 */
	private int downY;
	/**
	 * 手指按下Y的坐标
	 */
	private int downX;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	private int screenHeight;

	/**
	 * 滑动类
	 */
	private Scroller mScroller;

	private static final int SNAP_VELOCITY = 600;
	/**
	 * 速度追踪对象
	 */
	private VelocityTracker velocityTracker;

	/**
	 * 认为是用户滑动的最小距离
	 */
	private int mTouchSlop;

	private int mCurScreen=0;

	private boolean isSlide;

	private int densityDpi;
	
	private boolean leftMenuIsShow = false;

	private void init() {
		mScroller = new Scroller(context);
		screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		densityDpi = getResources().getDisplayMetrics().densityDpi;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		if (count > 0 && changed) {
			for (int i = 0; i < count; i++) {
				getChildAt(i).layout(i * screenWidth, 0, (i + 1) * screenWidth,
						screenHeight);
			}
		}
		scrollTo(mCurScreen * screenWidth, 0);
	}

//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent event) {
//	    float x=0;
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_MOVE:
//			 int distance = (int) (event.getX()-x);
//			 if (distance>mTouchSlop) {
//				
//			 }
//			break;
//		case MotionEvent.ACTION_DOWN:
//			x = event.getX();
//			break;
//		case MotionEvent.ACTION_CANCEL:
//		case MotionEvent.ACTION_UP:
// 
//			break;
//		}
//		return super.onInterceptHoverEvent(event);
//	}
	 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			addVelocityTracker(event);
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			downX = (int) event.getX();
			downY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) (downX - event.getX());
			downX = (int) event.getX();
			// 移动
			scrollBy(deltaX, 0);
			break;
		case MotionEvent.ACTION_UP:
			// 向右滑动
			int velocity = getScrollVelocity();
			if (velocity > 0) {
				Logger.getLogger().i("向右  滑动 ...before "+mCurScreen);
				snapToScreen(mCurScreen-1);
				Logger.getLogger().i("向右  滑动 ...after "+mCurScreen);
			} else if(velocity<0) {
				Logger.getLogger().i("向左滑动...before "+mCurScreen);
				snapToScreen(mCurScreen+1);
				Logger.getLogger().i("向左滑动...after "+mCurScreen);
			}else {
				snapToDestination();
			}
			recycleVelocityTracker();
		default:
			break;
		}
		return true;
	}
	
	private void snapToScreen(int whichScreen) { 
		// 选取合适的位置，防止越界
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		//getScrollX
		if (getScrollX() != (whichScreen * getWidth())) {
			final int delta = whichScreen * getWidth() - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0,
						Math.abs(delta));
			mCurScreen = whichScreen;
			invalidate();
		}
		
	}
	
	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);
	}
	private void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}
	// 在执行了invalidate()方法之后，程序便会执行computeScroll()方法
	@Override
	public void computeScroll() {
		// 判断动画是否完成,返回true表示动画没有完成
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), 0);
			postInvalidate();
		}
	}

	/**
	 * 添加用户的速度跟踪器
	 * 
	 * @param event
	 */
	private void addVelocityTracker(MotionEvent event) {
		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}

		velocityTracker.addMovement(event);
	}

	/**
	 * 移除用户速度跟踪器
	 */
	private void recycleVelocityTracker() {
		if (velocityTracker != null) {
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}

	/**
	 * 获取X方向的滑动速度,大于0向右滑动，反之向左
	 * 
	 * @return
	 */
	private int getScrollVelocity() {
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getXVelocity();
		return velocity;
	}

}

package com.example.customui.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.customui.util.Logger;

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
	 * ��ָ����X�����
	 */
	private int downY;
	/**
	 * ��ָ����Y�����
	 */
	private int downX;
	/**
	 * ��Ļ���
	 */
	private int screenWidth;
	private int screenHeight;

	/**
	 * ������
	 */
	private Scroller mScroller;

	private static final int SNAP_VELOCITY = 600;
	/**
	 * �ٶ�׷�ٶ���
	 */
	private VelocityTracker velocityTracker;

	/**
	 * ��Ϊ���û���������С����
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
			// �ƶ�
			scrollBy(deltaX, 0);
			break;
		case MotionEvent.ACTION_UP:
			// ���һ���
			int velocity = getScrollVelocity();
			if (velocity > 0) {
				Logger.getLogger().i("����  ���� ...before "+mCurScreen);
				snapToScreen(mCurScreen-1);
				Logger.getLogger().i("����  ���� ...after "+mCurScreen);
			} else if(velocity<0) {
				Logger.getLogger().i("���󻬶�...before "+mCurScreen);
				snapToScreen(mCurScreen+1);
				Logger.getLogger().i("���󻬶�...after "+mCurScreen);
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
		// ѡȡ���ʵ�λ�ã���ֹԽ��
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
	// ��ִ����invalidate()����֮�󣬳�����ִ��computeScroll()����
	@Override
	public void computeScroll() {
		// �ж϶����Ƿ����,����true��ʾ����û�����
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), 0);
			postInvalidate();
		}
	}

	/**
	 * ����û����ٶȸ�����
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
	 * �Ƴ��û��ٶȸ�����
	 */
	private void recycleVelocityTracker() {
		if (velocityTracker != null) {
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}

	/**
	 * ��ȡX����Ļ����ٶ�,����0���һ�������֮����
	 * 
	 * @return
	 */
	private int getScrollVelocity() {
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getXVelocity();
		return velocity;
	}

}

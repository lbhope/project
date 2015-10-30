package com.example.customui.view.scroll;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.customui.util.Logger;

/**
 * 边界检测、加速度检测(eg：DrawerLayout边界触发拉出) 回调Drag
 * Release（eg：DrawerLayout部分，手指抬起，自动展开/收缩）
 * 移动到某个指定的位置(eg:点击Button，展开/关闭Drawerlayout)
 * 
 * @author libin
 * 
 */
public class ViewDragHelperLayout extends LinearLayout {

	private ViewDragHelper mDragger;

	private View mDragView;
	private View mAutoBackView;
	private View mEdgeTrackerView;

	private Point mAutoBackOriginPos = new Point();

	public ViewDragHelperLayout(Context context) {
		super(context);
		init();
	}

	public ViewDragHelperLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		/**
		 * 创建实例需要3个参数，第一个就是当前的ViewGroup，第二个sensitivity，主要用于设置touchSlop:
		 * helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));
		 */
		mDragger = ViewDragHelper.create(this, 1.0f,
				new ViewDragHelper.Callback() {

					// 返回true，则表示可以捕获该view
					@Override
					public boolean tryCaptureView(View child, int pointerId) {
						// return true;
						// mEdgeTrackerView禁止直接移动
						return child == mDragView || child == mAutoBackView;
					}

					// 水平方向边界进行控制，left , top 分别为即将移动到的位置,如果返回固定的值，表示只能水平或垂直移动
					@Override
					public int clampViewPositionHorizontal(View child,
							int left, int dx) {
						// final int leftBound = getPaddingLeft();
						// final int rightBound = getWidth()
						// - mDragView.getWidth() - leftBound;
						//
						// final int newLeft = Math.min(Math.max(left,
						// leftBound),
						// rightBound);
						Logger.getLogger().i(
								"--clampViewPositionHorizontal--->> " + left
										+ " " + dx);
						return left;
					}

					// 垂直方向边界进行控制
					@Override
					public int clampViewPositionVertical(View child, int top,
							int dy) {
						Logger.getLogger().i(
								"--clampViewPositionVertical--->> " + top + " "
										+ dy);
						return top;
					}

					// 手指释放的时候回调
					@Override
					public void onViewReleased(View releasedChild, float xvel,
							float yvel) {
						// mAutoBackView手指释放时可以自动回去
						if (releasedChild == mAutoBackView) {
							//调用settleCapturedViewAt回到某个位置，其内部调用mScroller.startScroll，因此重写computeScroll
							mDragger.settleCapturedViewAt(mAutoBackOriginPos.x,
									mAutoBackOriginPos.y);
							invalidate();
						}
					}

					// 在边界拖动时回调，该方法可以绕过tryCaptureView，所以tryCaptureView没有返回true也可以移动
					@Override
					public void onEdgeDragStarted(int edgeFlags, int pointerId) {
						mDragger.captureChildView(mEdgeTrackerView, pointerId);
					}
					
					//解决子View如果消耗事件就无法移动
					@Override
					public int getViewHorizontalDragRange(View child) {
						return getMeasuredWidth() - child.getMeasuredWidth();
					}

					@Override
					public int getViewVerticalDragRange(View child) {
						return getMeasuredHeight() - child.getMeasuredHeight();
					}
				});
		
		
		mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
	}

	// ViewDragHelper中拦截和处理事件时，需要会回调CallBack
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDragger.processTouchEvent(event);
		return true;
	}

	@Override
	public void computeScroll() {
		if (mDragger.continueSettling(true)) {
			invalidate();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return mDragger.shouldInterceptTouchEvent(event);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		Logger.getLogger().i("--onLayout--->> ");
		mAutoBackOriginPos.x = mAutoBackView.getLeft();
		mAutoBackOriginPos.y = mAutoBackView.getTop();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Logger.getLogger().i("--onSizeChanged--->> ");
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		Logger.getLogger().i("--onFinishInflate--->> ");
		mDragView = getChildAt(0);
		mAutoBackView = getChildAt(1);
		mEdgeTrackerView = getChildAt(2);
	}
	
	
//	  [ main: ViewDragHelperLayout.java:138 ] - --onSizeChanged--->> 
//	 [ main: ViewDragHelperLayout.java:131 ] - --onLayout--->> 
//	 [ main: ViewDragHelperLayout.java:131 ] - --onLayout--->> 
//	 [ main: ViewDragHelperLayout.java:117 ] - --computeScroll--->> 

}

package com.example.customui.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Scroller;

public class MyScrollViewGesture extends ViewGroup {
	public MyScrollViewGesture(Context context) {
		super(context);
		this.ctx = context;
		init();
	}
	public MyScrollViewGesture(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		init();
	}

	private Context ctx;
	private GestureDetector gestureDetector;
	private Scroller scroller;
	/**
	 * 是否发生快速滑动
	 */
	private boolean isFling = false;
	/**
	 * 当前屏幕显示的子view的下标
	 */
	private int curId;
	
	

	private void init() {
		scroller = new Scroller(ctx);
		gestureDetector = new GestureDetector(ctx, new OnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			/**
			 * 正常滑动屏幕时的回调方法
			 */
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				scrollBy((int) distanceX, 0);
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			/**
			 * 发生快速滑动时的回调方法 
			 */
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				isFling = true;
				if (velocityX > 0 && curId > 0) { // 快速向右滑
					moveToDest(curId - 1);
				} else if (velocityX < 0 && curId < getChildCount()) {
					moveToDest(curId + 1);
				} else {
					moveToDest();
				}
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});

	}

	/**
	 * 对子view进行排列布局
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.layout(0 + i * getWidth(), 0, getWidth() + i * getWidth(),
					getHeight());
		}
	}
	

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if (!isFling) {
				moveToDest();
			}
			isFling = false;
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 将viewGroup的内容移动到适当的位置上
	 */
	private void moveToDest() {
		int destId = (getWidth() / 2 + getScrollX()) / getWidth();
		moveToDest(destId);

	}

	/**
	 * 将指定下标的子view移动到屏幕上来
	 * 
	 * @param destId
	 */
	public void moveToDest(int destId) {
		if (destId >= getChildCount() - 1) {
			destId = getChildCount() - 1;
		}
		curId = destId;
		if (changedListener != null) {
			changedListener.changedTo(curId);
		}
		int distance = destId * getWidth() - getScrollX();
		// scrollBy(distance, 0);
		// scroller.startScroll(getScrollX(), 0, distance, 0);
		//开始移动
		scroller.startScroll(getScrollX(), 0, distance, 0, Math.abs(distance));
		invalidate(); // 刷新视图 会执行 computeScroll（） 这个方法
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), 0);
			invalidate();
		}
	}

	public IPageChangedListener getChangedListener() {
		return changedListener;
	}

	public void setChangedListener(IPageChangedListener changedListener) {
		this.changedListener = changedListener; 
	}

	private IPageChangedListener changedListener;

	public interface IPageChangedListener {
		void changedTo(int pageId);
	}

}

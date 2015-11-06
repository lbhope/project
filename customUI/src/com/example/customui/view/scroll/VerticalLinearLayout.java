package com.example.customui.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.customui.util.DevicesUtils;
import com.example.customui.util.Logger;
import com.example.customui.util.VelocityTrackerUtils;

/**
 * 垂直方向ViewPager
 * 
 * @author libin
 * 
 */
public class VerticalLinearLayout extends ViewGroup {

	private int mScreenHeight;
	private int mTouchSlop;
	/** 
	 * x轴：负-向左滚动；>0:向右滚动
	 * y轴：负-向上滚动；>0:向下滚动
     * 滚动的辅助类 
     */  
    private Scroller mScroller;  

    private int downY;
    /** 
     * 手指按下时的getScrollY 
     */  
    private int mScrollStart;  
    /** 
     * 手指抬起时的getScrollY 
     */  
    private int mScrollEnd;  
    /** 
     * 是否正在滚动 
     */  
    private boolean isScrolling;  
    /** 
     * 加速度检测 
     */  
    private VelocityTracker mVelocityTracker; 
    /**
     * y轴上的加速度
     */
    private int yVelocity=600;
    /** 
     * 记录当前页 
     */  
    private int currentPage = 0;  
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
		mScroller = new Scroller(getContext());
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

	 

 

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		 // 如果当前正在滚动，调用父类的onTouchEvent  
        if (isScrolling)  
            return super.onTouchEvent(ev);  
 
        VelocityTrackerUtils.obtainVelocity(ev);
        
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mScrollStart = getScrollY();
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			int curY = (int) ev.getY();
			int dy = (int) (curY - downY);
			int scrollY = getScrollY();
			
			//向上运动 dy<0,向下dy>0。 getScrollY()等于0到达顶部
			 // 已经到达顶端，下拉多少，就往上滚动多少  
			if (dy < 0 && scrollY + dy < 0) {
				dy = -scrollY;
			}
			//滚动到底部：scrollY+mScreenHeight=getHeight()
			// 已经到达底部，上拉多少，就往下滚动多少
			if (dy > 0 && scrollY + dy > getHeight() - mScreenHeight) {
				dy = getHeight() - mScreenHeight - scrollY;
			}
			
            //大于0向上滑动，小于0向下
			scrollBy(0, dy);
			Logger.getLogger().i("dis: " + dy+" scrollY:"+scrollY+" vH:"+getHeight()+" sH:"+mScreenHeight);
			downY = curY;
			break;
		case MotionEvent.ACTION_UP:
			mScrollEnd = getScrollY();
			int disY = mScrollEnd - mScrollStart; 
			System.out.println("---->>>> "+mScreenHeight+" "+disY);
			if (disY>0) {//向上滑动
				if (isScrollPre(disY)) {//下一页
					mScroller.startScroll(0, getScrollY(), 0, mScreenHeight-disY);
				}else {
					mScroller.startScroll(0, getScrollY(), 0, -disY);
				}
			}
			if(disY<0){//向下滚动
				if (isScrollPre(disY)) {//上一页
					mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight-disY);
				}else {
					mScroller.startScroll(0, getScrollY(), 0, -disY);
				}
			}
            postInvalidate();  
            VelocityTrackerUtils.recycleVelocity();
			isScrolling = true;
			break;
 
		}
		postInvalidate();
		return true;
	}
	
	//判断是否向上滑，滚动到上一页
	private boolean isScrollPre(int disY){
		return Math.abs(disY) > mScreenHeight*1/2 || VelocityTrackerUtils.getYVelocity()>yVelocity ;
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		} else {
			int position = getScrollY() / mScreenHeight;
			Log.e("xxx", position + "," + currentPage);
//			if (position != currentPage) {
//				if (mOnPageChangeListener != null) {
//					currentPage = position;
//					mOnPageChangeListener.onPageChange(currentPage);
//				}
//			}
			isScrolling = false;
		}
  
	}
	
	
}

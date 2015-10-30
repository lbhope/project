package com.example.customui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.customui.util.Logger;

public class SwipeListView extends ListView {
	/**
	 * 手势识别
	 */
	private GestureDetector mGestureDetector;
	/**
	 * 滑动类
	 */
	private Scroller mScroller;
	/**
	 * 需要移动的view
	 */
	private View currentMoveView;
	
	private int downX;

	private int downY;

	/**
	 * 将手指按下时的坐标转换成listview的item的位置
	 */
	private int slidePosition;

	/**
	 * 是否在滑动
	 */
	private boolean isSlide;
	//快速滑动
//	private boolean isFling;
 
	private int mTouchSlop;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * listview item
	 */
	private ViewGroup viewGroup;
	
	private static final int SNAP_VELOCITY = 600;  
	public SwipeListView(Context context) {
		super(context);
		init(context);
	}

	public SwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		
	}
	private void init(Context context){
		mScroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();  
		screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		mGestureDetector = new GestureDetector(context,new OnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				if (isSlide && currentMoveView!=null) {
					if (listener!=null) {
						listener.scroll(currentMoveView.getScrollX(), viewGroup);
					}
					currentMoveView.scrollBy((int)distanceX, 0);
				}
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				 
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
//				if (isSlide && (e2.getX()-e1.getX())<0) {
//					isFling = true;
//					Logger.getLogger().i("==============> 快速滑动"+(e2.getX()-e1.getX()));
//				}
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				Logger.getLogger().i("--------dispatchTouchEvent down--------> ");
				if (!mScroller.isFinished()  || isSlide) {
					return super.dispatchTouchEvent(event);
				}
				downX = (int) event.getX();
				downY = (int) event.getY();
				Logger.getLogger().i("-------------downX= "+downX);
				slidePosition = pointToPosition(downX, downY);
				// 无效的position, 不做任何处理;isSlide防止多个手指按下时影响isFling的值，导致被删除
				if (slidePosition == AdapterView.INVALID_POSITION) {
					return super.dispatchTouchEvent(event);
				}
				// 获取我们点击的item view
			   viewGroup = (ViewGroup) getChildAt(slidePosition - getFirstVisiblePosition());
			    //需要一定的view
				currentMoveView = viewGroup.getChildAt(1);
				break;
			}
			case MotionEvent.ACTION_MOVE:{
				int x = (int) (event.getX()-downX);
				int y = (int) (event.getY() -downY);
//				Logger.getLogger().i("-------------x= "+x+" y= "+y+" mTouchSlop:"+mTouchSlop);
				if (Math.abs(x)>mTouchSlop && Math.abs(x)>Math.abs(y) && x<0 ) {
					isSlide = true;
				}
				break;
			}
			
		}
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		if (isSlide) {
			if (!mScroller.isFinished()) {
				return true;
			}
			switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:{
					downX = (int) ev.getX();
					break;
				}
				case MotionEvent.ACTION_CANCEL: 
				case MotionEvent.ACTION_UP:{
					/*if (isFling || isRemoveItem()) {
						if (isFling && this.listener!=null ) {
		            		Logger.getLogger().i("滑动删除。。");
							this.listener.removeItem(slidePosition);
						}else*/
					if (isRemoveItem() && this.listener!=null) {
						//向左滚动
						this.listener.removeItem(slidePosition);
						mScroller.startScroll(currentMoveView.getScrollX(), 0,screenWidth, 0,Math.abs(300));
					}else {
						//向右滚动
						int delta = currentMoveView.getScrollX();
						mScroller.startScroll(currentMoveView.getScrollX(), 0,-delta, 0,Math.abs(500));
					}
					postInvalidate();
					
				}
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	
	@Override
	public void computeScroll() {
		// 调用startScroll的时候scroller.computeScrollOffset()返回true，  
        if (mScroller.computeScrollOffset()) {  
            // 让ListView item根据当前的滚动偏移量进行滚动  
            currentMoveView.scrollTo(mScroller.getCurrX(), 0);  
            postInvalidate();  
            // 滚动动画结束的时候调用回调接口  
            if (mScroller.isFinished()) {  
            	currentMoveView.scrollTo(0, 0);
            	if (this.listener!=null && isSlide) {
            		this.listener.scrollFinish();
            	}
            	isSlide = false;
            }  
        }  
	}

	private boolean isRemoveItem(){
		if (currentMoveView.getScrollX()>screenWidth/2) {
			return true;
		}
		return false;
	}
	
	private RemoveItemListViewListener listener;
	public void setRemoveItemListViewListener(RemoveItemListViewListener listener){
		this.listener = listener;
	}
	public interface RemoveItemListViewListener{
		public void removeItem(int pos);
		public void scroll(float scrollX,View view);
		public void scrollFinish();
	}

}

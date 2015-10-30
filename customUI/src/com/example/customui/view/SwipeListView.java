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
	 * ����ʶ��
	 */
	private GestureDetector mGestureDetector;
	/**
	 * ������
	 */
	private Scroller mScroller;
	/**
	 * ��Ҫ�ƶ���view
	 */
	private View currentMoveView;
	
	private int downX;

	private int downY;

	/**
	 * ����ָ����ʱ������ת����listview��item��λ��
	 */
	private int slidePosition;

	/**
	 * �Ƿ��ڻ���
	 */
	private boolean isSlide;
	//���ٻ���
//	private boolean isFling;
 
	private int mTouchSlop;
	/**
	 * ��Ļ���
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
//					Logger.getLogger().i("==============> ���ٻ���"+(e2.getX()-e1.getX()));
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
				// ��Ч��position, �����κδ���;isSlide��ֹ�����ָ����ʱӰ��isFling��ֵ�����±�ɾ��
				if (slidePosition == AdapterView.INVALID_POSITION) {
					return super.dispatchTouchEvent(event);
				}
				// ��ȡ���ǵ����item view
			   viewGroup = (ViewGroup) getChildAt(slidePosition - getFirstVisiblePosition());
			    //��Ҫһ����view
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
		            		Logger.getLogger().i("����ɾ������");
							this.listener.removeItem(slidePosition);
						}else*/
					if (isRemoveItem() && this.listener!=null) {
						//�������
						this.listener.removeItem(slidePosition);
						mScroller.startScroll(currentMoveView.getScrollX(), 0,screenWidth, 0,Math.abs(300));
					}else {
						//���ҹ���
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
		// ����startScroll��ʱ��scroller.computeScrollOffset()����true��  
        if (mScroller.computeScrollOffset()) {  
            // ��ListView item���ݵ�ǰ�Ĺ���ƫ�������й���  
            currentMoveView.scrollTo(mScroller.getCurrX(), 0);  
            postInvalidate();  
            // ��������������ʱ����ûص��ӿ�  
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

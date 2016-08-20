package com.example.zzl.scroll;

import com.example.zzl.R;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ZoomListView extends ListView {
	
	private ImageView mHeaderImageView;
	private   int originalImageHeight=0;//原来图片高度
	private static final String TAG="ZoomListView";
	private View headerView;
	
	private int haflImageHeight;
	
	public ZoomListView(Context context) {
		super(context);
	}

	public ZoomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	public void setHeaderView(int layout){
		headerView = View.inflate(getContext(), layout, null);
		mHeaderImageView = (ImageView) headerView.findViewById(R.id.iv_bg_header);
		addHeaderView(headerView);
		headerView.measure(0, 0);
		originalImageHeight = mHeaderImageView.getMeasuredHeight();
		Log.i(TAG, "originalImageHeight: "+originalImageHeight);
		haflImageHeight = (int) ((originalImageHeight - getContext().getResources().getDisplayMetrics().density*100)/2);
		 
		
	}
	
	/**
	 * 滑动过渡时会触发此方法
	 * deltaY: 向下滑动小于0，向上滑动大于0
	 */
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		Log.i(TAG, "deltaX: "+deltaX+" deltaY: "+deltaY+" scrollY: "+scrollY);
		if (deltaY<0) {
			//向下滑动，图片放大，高度增加
			mHeaderImageView.getLayoutParams().height = mHeaderImageView.getHeight()-deltaY;
			mHeaderImageView.requestLayout();
		}else {
			//将图片拉大后，手指不抬起，向上慢慢滑动，需要将图片的高度变小
			if (mHeaderImageView.getHeight()>originalImageHeight) {
				mHeaderImageView.getLayoutParams().height =mHeaderImageView.getHeight()-deltaY;
				mHeaderImageView.requestLayout();
			}
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (this.scrollChangeList!=null) {
			this.scrollChangeList.onScroll(headerView.getTop(), haflImageHeight);
		}
		
		if (headerView.getTop()<0 && mHeaderImageView.getHeight()>originalImageHeight) {
			//header.getTop() 滑动出去的距离小于0
			Log.i(TAG, "onScrollChanged: "+mHeaderImageView.getHeight() +" originalImageHeight "+originalImageHeight+" top: "+headerView.getTop());
			mHeaderImageView.getLayoutParams().height = mHeaderImageView.getHeight()+headerView.getTop();
			headerView.layout(headerView.getLeft(), 0, headerView.getRight(), headerView.getBottom());
			mHeaderImageView.requestLayout();
		}
		
		
		
		
//		Log.i(TAG, "top: "+headerView.getTop()+" imgH: "+mHeaderImageView.getHeight()+"" +
//				" oldt: "+oldt+" t: "+t);

		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			ZoomTranslateAnim anim = new ZoomTranslateAnim(mHeaderImageView.getHeight());
			anim.setDuration(500);
			mHeaderImageView.startAnimation(anim);
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	private  class ZoomTranslateAnim extends Animation{
		private int currentImageHeight;//当前图片高度
		private int diffImageHeight;//拉大的高度
		public ZoomTranslateAnim(int currentImageHeight) {
			this.currentImageHeight = currentImageHeight;
			this.diffImageHeight = currentImageHeight-originalImageHeight;
		}
 
		/*
		 * interpolatedTime 范围0到1，原来到现在
		 */
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			if (this.currentImageHeight>originalImageHeight) {
				//interpolatedTime  1--->0
				mHeaderImageView.getLayoutParams().height = (int) (currentImageHeight-diffImageHeight*interpolatedTime);
				mHeaderImageView.requestLayout();
			}
			
			super.applyTransformation(interpolatedTime, t);
		}
		
	}
	
	
	private ScrollChangeList scrollChangeList;
	public void setScrollChangeList(ScrollChangeList changeList){
		this.scrollChangeList = changeList;
	}
 
	public interface ScrollChangeList{
		public void onScroll(int y, int half);
	}
	
}

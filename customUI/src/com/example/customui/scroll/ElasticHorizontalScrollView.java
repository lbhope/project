package com.example.customui.scroll;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;
/**
 * http://blog.sina.com.cn/s/blog_6f561cc30101b7fe.html
 * 
 * http://www.2cto.com/kf/201402/279066.html
 * 
 * http://blog.csdn.net/andylao62/article/details/41213015
 * @author libin
 *
 */
public class ElasticHorizontalScrollView extends HorizontalScrollView {
	
	 //目的是达到一个延迟的效果
    private static final float MOVE_FACTOR = 0.7f;
    
    private View view;  
    private Rect normal = new Rect();  
    private float x;  
    
    private int mTouchSlop;
    private Scroller mScroller;
    //滚动的动画有问题，直接就返回了
    private int scrollTime = 200;
    private boolean isSlide=false;
    
    public ElasticHorizontalScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init(context);
        
    }  
  
    public ElasticHorizontalScrollView(Context context) {  
        super(context);  
        init(context);
    }  
    
    private void init(Context context){
    	mScroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
  
    @Override  
    protected void onFinishInflate() {  
        if (getChildCount() > 0) {  
        	view = getChildAt(0);  
        }  
        super.onFinishInflate();  
    }  
    
 
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	 int action = ev.getAction();  
         switch (action) {  
         case MotionEvent.ACTION_DOWN:  
             x = ev.getX();  
             break;  
         case MotionEvent.ACTION_UP:  

             break;  
         case MotionEvent.ACTION_MOVE:  
        	 float curX= ev.getX();
        	 float disX = curX -x;
             if (Math.abs(disX)>mTouchSlop) {
            	 isSlide = true;
			 }
             break;  
         }  
    	return super.dispatchTouchEvent(ev);
    }
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {
    	if (!isSlide) {
			return super.onTouchEvent(ev);  
		}
    	commOnTouchEvent(ev);  
        return super.onTouchEvent(ev);  
    }  
	  /**
	   * 
	   * @param ev
	   */
    private void commOnTouchEvent(MotionEvent ev) {  
        int action = ev.getAction();  
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
        	if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
            x = ev.getX();  
            break;  
        case MotionEvent.ACTION_UP:  
            if (isSlide) {
            	
//            	System.out.println("---left: "+view.getLeft()+" n: "+normal.left+" sc: "+getScrollX()
//            			+" vright:"+view.getRight()+" vsc:"+view.getScrollX());
//            	System.out.println("  "+ view.getWidth() +" "+ getWidth()+" " + getScrollX()+" "+mTouchSlop);
            	if (isScrollLeft()) {
            		mScroller.startScroll(0, 0,-view.getLeft(), 0, scrollTime);
				}else if (isScrollRight()) {
					mScroller.startScroll(view.getRight(), 0,getScrollX(), 0, scrollTime);
				}
				invalidate();
			}
            isSlide = false;
            break;  
        case MotionEvent.ACTION_MOVE:
    		final float preX = x;  
            float nowX = ev.getX();  
            int distanceX = (int) (preX - nowX);  
            scrollBy(distanceX, 0);  
            x = nowX;  
			if ((isScrollLeft()) || (isScrollRight())) {
				 int disatnce = (int) (distanceX * MOVE_FACTOR);
				view.layout(view.getLeft() - disatnce,view.getTop(), view.getRight()
						- disatnce, view.getBottom());
//    	            System.out.println("-------------->"+disX+" left: "+view.getLeft()+" scrollX: "+getScrollX());
			}
            break;  
  
        default:  
            break;  
        }  
    }  
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    	super.onLayout(changed, l, t, r, b);
    	normal.set(view.getLeft(), view.getTop(), view
                .getRight(), view.getBottom());
    }
 
   
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			System.out.println("  "+mScroller.getCurrX());
			scrollTo(mScroller.getCurrX(), 0);
			postInvalidate();
			if (mScroller.isFinished()) {
				isSlide = false;
				view.layout(0, normal.top, normal.right, normal.bottom);
			}
		}
	}
    
//	private boolean isNeedMove() {  
//        int offset = view.getMeasuredWidth() - getWidth();  
//        int scrollX = getScrollX();  
//        if (scrollX == 0 || offset == scrollX)  
//            return true;  
//        return false;  
//    }  
	
    /**
     * 判断是否滚动到左边
     */
	private boolean isScrollLeft() {
		return getScrollX() == 0 || view.getWidth() < getWidth()+getScrollX();
	}
     
    /**
     * 判断是否滚动到右边
     */
    private boolean isScrollRight() {
        return  view.getWidth() <= getWidth() + getScrollX()+mTouchSlop;
    }
}  
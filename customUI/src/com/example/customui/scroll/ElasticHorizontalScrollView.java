package com.example.customui.scroll;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;
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
    private static final float MOVE_FACTOR = 1f;
    
    private View view;  
    private Rect normal = new Rect();  
    private float x;  
    
    private int mTouchSlop;
    private OverScroller mScroller;
    
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
    	mScroller = new OverScroller(context);
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
            x = ev.getX();  
            break;  
        case MotionEvent.ACTION_UP:  
            if (isSlide) {
            	System.out.println("---left: "+view.getLeft()+" n: "+normal.left+" sc: "+getScrollX());
            	if (isScrollLeft()) {
            		mScroller.startScroll(getScrollX(), 0,-view.getLeft(), 0, 200);
				}else if (isScrollRight()) {
					mScroller.startScroll(getScrollX(), 0,view.getLeft(), 0, 200);
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
        	
//        	float curX= ev.getX();
//       	    float disX = curX -x; 
			if ((isScrollLeft()) || (isScrollRight())) {
//				int disatnce = (int) (disX * MOVE_FACTOR);
//	       	    scrollBy(-(int)disX, 0);  
				view.layout(view.getLeft() - distanceX,view.getTop(), view.getRight()
						- distanceX, view.getBottom());
//	            System.out.println("-------------->"+disX+" left: "+view.getLeft()+" scrollX: "+getScrollX());
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
    	System.out.println("--------->> "+normal.left+" "+view.getLeft());
    }
 
  
   
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), 0);
			postInvalidate();
			if (mScroller.isFinished()) {
				System.out.println(normal);
				view.layout(0, normal.top, normal.right, normal.bottom);
			}
		}
	}
    
	private boolean isNeedMove() {  
        int offset = view.getMeasuredWidth() - getWidth();  
        int scrollX = getScrollX();  
        if (scrollX == 0 || offset == scrollX)  
            return true;  
        return false;  
    }  
	
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
        return  view.getWidth() <= getWidth() + getScrollX();
    }
}  
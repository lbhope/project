package com.example.customui.scroll;

import com.example.customui.util.Logger;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MySlideView extends ViewGroup{  
  
    private View content;  
      
    private View menu;  
      
    private boolean isMenuShow = false;  
      
    public MySlideView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        scroller = new Scroller(context);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        menu = getChildAt(0);  
        content = getChildAt(1);  
        //根据提供的大小值和模式创建测量值，并计算menu的位置  
        menu.measure(MeasureSpec.makeMeasureSpec(menu.getLayoutParams().width, MeasureSpec.EXACTLY)  
                , heightMeasureSpec);  
          
        content.measure(widthMeasureSpec, heightMeasureSpec);  
    }  
      
    /** 
     * 设置view的位置 
     */  
    @Override  
    protected void onLayout(boolean changed, int l, int t, int r, int b) {  
        System.out.println("layout:::"+getChildCount());  
          
        menu.layout(0-menu.getMeasuredWidth(), 0, 0, b);  
          
        content.layout(0, 0, r, b);  
    }  
      
    private int firstX;  
  
    private int lastX;  
      
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        super.onTouchEvent(event);  
          
        switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            firstX = lastX = (int) event.getX();  
            Logger.getLogger().i("menu width :::: "+menu.getWidth()+" lastX:"+lastX);
            
            break;  
        case MotionEvent.ACTION_MOVE:  
            int disX = (int) (lastX - event.getX());  
            lastX = (int) event.getX();  
            int nextScrollX = getScrollX()+disX; // 可能的，下一个 mScrollX 的值  
            //[ main: MySlideView.java:66 ] - getScrollX:::: -485   disX::: -14 n: -499 g:388
            Logger.getLogger().i("getScrollX:::: "+getScrollX()+"   disX::: "+disX+" n: "+nextScrollX+" g:"+event.getX());
//            if(getScrollX()<=0 && getScrollX()>=-menu.getWidth()){  
            if( nextScrollX >= -menu.getWidth() && nextScrollX <=0){  
                scrollBy(disX, 0);  
            }  
              
            break;  
        case MotionEvent.ACTION_UP:  
            int curScrollX = getScrollX();  
            if(curScrollX > -menu.getWidth()/2){  
                isMenuShow = false;  
            }else{  
                isMenuShow = true;  
            }  
            flushState();  
            break;  
        }  
        return true;   
    }  
  
    private void flushState() {  
        int distance = 0;  
        if(!isMenuShow){  
//          scrollTo(0,0);  
        	//不显示左边菜单，需要菜单向左滚动隐藏，向左滚动distance要大于0，当前到左边的距离是getScrollX()
            distance = 0-getScrollX();  
        }else{  
//          scrollTo(-menu.getWidth(),0);  
        	//显示菜单，要向右滚动distance小于0，要滚动的距离是菜单的宽度减去当前的偏移量，右边为负最左边的坐标是-menu.getWidth()
            distance = -menu.getWidth()-getScrollX();  
            Logger.getLogger().i("-----------------------> distance "+distance);
        }  
        scroller.startScroll(getScrollX(), 0, distance, 0);  
        invalidate();  
          
    }  
    /** 
     * 滚动，子View滚动 
     */  
    @Override  
    public void computeScroll() {  
        if(scroller.computeScrollOffset()){  
            scrollTo(scroller.getCurrX(),0);  
            invalidate();  
        }  
    }  
    private Scroller scroller ;  
  
    /** 
     * 切换状态 
     */  
    public void changeState() {  
        isMenuShow = !isMenuShow;  
        flushState();  
    }  
  
}  
package com.example.zzl.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ContainerLinearLayout extends LinearLayout{
	public ContainerLinearLayout(Context context) {
		super(context);
	}
	public ContainerLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public String tag = "ContainerLinearLayout-->> ";
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		System.out.println(tag+" onInterceptTouchEvent "+super.onInterceptTouchEvent(ev) );
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		System.out.println(tag+" dispatchTouchEvent "+super.dispatchTouchEvent(ev) );
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		System.out.println(tag+" onTouchEvent "+super.onTouchEvent(event) );
		return super.onTouchEvent(event);
	}
}

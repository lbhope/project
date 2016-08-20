package com.example.zzl.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

public class ContainerButton extends Button {
	public ContainerButton(Context context) {
		super(context);
	}

	public ContainerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public String tag = "ContainerButton-->> ";

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		System.out.println(tag + " dispatchTouchEvent "+ super.dispatchTouchEvent(ev));
		return super.dispatchTouchEvent(ev);
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("ContainerButton onTouchEvent ACTION_DOWN");
			return false;
		}
		System.out.println(tag + " onTouchEvent " + super.onTouchEvent(event));
		return super.onTouchEvent(event);
	}
}

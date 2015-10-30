package com.example.customui.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class ZhiHuTextView extends TextView {
	public ZhiHuTextView(Context context) {
		super(context);
	}

	public ZhiHuTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ZhiHuTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean act = super.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d("TAG", "MyTextView----ACTION_DOWN-------" + act);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d("TAG", "MyTextView----ACTION_MOVE-------" + act);
			break;
		}

		return act;
	}
}

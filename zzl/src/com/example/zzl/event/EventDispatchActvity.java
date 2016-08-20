package com.example.zzl.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.example.zzl.R;

public class EventDispatchActvity extends Activity {

	private ContainerButton btnButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.event_dispatch);
		btnButton = (ContainerButton) findViewById(R.id.btn);
		//down -> move -> up
		
//		btnButton.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					System.out.println("onTouch ACTION_DOWN in activity ");
//					return  false;
//				case MotionEvent.ACTION_MOVE:
//					System.out.println("onTouch ACTION_MOVE in activity ");
//					return true;
//				case MotionEvent.ACTION_UP:
//					System.out.println("onTouch ACTION_UP in activity ");
//					return false;
//				}
//				return true;
//			}
//		});
		btnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println(" click in activity ");
			}
		});
		btnButton.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				System.out.println(" onlong click in activity ");
				return false;
			}
		});
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
//		System.out.println("EventDispatchActvity -->  dispatchTouchEvent  " +super.dispatchTouchEvent(ev));
		return false;
	}
	
	
	
}

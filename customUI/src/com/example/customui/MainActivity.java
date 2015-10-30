package com.example.customui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity implements OnClickListener {

	private Button mRadioButton;
	private Button mEditButton1;
	private Button mScroolButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paint_canvas_view);
//		setContentView(R.layout.my_slide_view);
//		setContentView(R.layout.activity_main);

//		mRadioButton = (Button) findViewById(R.id.btn_radio);
//		mEditButton1 = (Button) findViewById(R.id.btn_textedit1);
//		mScroolButton = (Button) findViewById(R.id.btn_scroll_layout);
//
//		mRadioButton.setOnClickListener(this);
//		mEditButton1.setOnClickListener(this);
//		mScroolButton.setOnClickListener(this);
		
		 

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	public void onClick(View v) {
		Intent intent1 = new Intent(MainActivity.this,
				RadioButtonActivity.class);
		switch (v.getId()) {
		
		case R.id.btn_radio:
			intent1.putExtra("type", "radio");
			break;
		case R.id.btn_textedit1:
			intent1.putExtra("type", "textedit");
			break;
		case R.id.btn_scroll_layout:
			intent1.putExtra("type", "launch");
			break;
		}
		startActivity(intent1);
	}
	

}

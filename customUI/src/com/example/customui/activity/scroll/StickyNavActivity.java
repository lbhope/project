package com.example.customui.activity.scroll;

import android.os.Bundle;

import com.example.customui.BaseActivity;
import com.example.customui.R;

public class StickyNavActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_scroll_vertical_linearlayout);
//		setContentView(R.layout.view_scroll_horizontal_layout);
		System.out.println("---------StickyNavActivity-----onCreate->>>>>>>>>> ???");
	}
	/**
	 * 配置文件
	 * 
	 *  android:configChanges="keyboardHidden|orientation"
	 *  android:screenOrientation="landscape"
	 *  
	 *  onCreate
	 *  不设置：执行一次
	 *  SCREEN_ORIENTATION_PORTRAIT：执行两次
	 *  SCREEN_ORIENTATION_LANDSCAPE：执行一次
	 */
	
}

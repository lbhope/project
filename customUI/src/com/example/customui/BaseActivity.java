package com.example.customui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
//		CUIApplication.getInstance().addActivity(this);
	}
	
	public void show(String msg){
		Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
		
	}
	
	
}
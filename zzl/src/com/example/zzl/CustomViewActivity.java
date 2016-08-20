package com.example.zzl;

import android.app.Activity;
import android.os.Bundle;

public class CustomViewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int layout = getIntent().getIntExtra("layout", -1);
		if (layout>0) {
			setContentView(layout);
		}
		
	}
}

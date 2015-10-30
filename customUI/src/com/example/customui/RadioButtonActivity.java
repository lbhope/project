package com.example.customui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.customui.R;
import com.example.customui.view.MyRadioButton;

public class RadioButtonActivity extends BaseActivity {
	private MyRadioButton myRadioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
 
		String typeStr = getIntent().getStringExtra("type");
		if (typeStr.equals("radio")) {
			setContentView(R.layout.radio);

			myRadioButton = (MyRadioButton) findViewById(R.id.rb_radio);

			myRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
				
					show("" + myRadioButton.getValue());
				}
			});
		}else if (typeStr.equals("textedit")){
			setContentView(R.layout.textedit);
		}else if (typeStr.equals("launch")){
			setContentView(R.layout.launch_scroll_layout);
		}
	}
}

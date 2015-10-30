package com.example.customui.view;

import com.example.customui.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class MyRadioButton extends RadioButton implements OnCheckedChangeListener {
	private String mValue;

	public MyRadioButton(Context context) {

		super(context);
	}

	public MyRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public String getValue() {
		return this.mValue;
	}

	public void setValue(String value) {
		this.mValue = value;
	}

	public MyRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyRadioButton);
		this.mValue = a.getString(R.styleable.MyRadioButton_value);
//		invalidate();
		a.recycle();
		setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	}
 

}
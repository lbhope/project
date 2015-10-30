package com.example.customui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customui.R;

public class TextEditView2 extends LinearLayout {

	public TextEditView2(Context context) {
		this(context, null);
	}

	public TextEditView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		int resouceId = -1;
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.EditTextView);
		TextView tv = new TextView(context);
		EditText et = new EditText(context);
		int N = typeArray.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = typeArray.getIndex(i);
			switch (attr) {
			case R.styleable.EditTextView_Oriental://Ã¶¾Ù
				resouceId = typeArray.getInt(R.styleable.EditTextView_Oriental,
						0);
				this.setOrientation(resouceId == 1 ? LinearLayout.HORIZONTAL
						: LinearLayout.VERTICAL);
				break;
			case R.styleable.EditTextView_Text:
				resouceId = typeArray.getResourceId(
						R.styleable.EditTextView_Text, 0);
				tv.setText(resouceId > 0 ? typeArray.getResources().getText(
						resouceId) : typeArray
						.getString(R.styleable.EditTextView_Text));
				break;
			}
		}
		addView(tv);
		addView(et);
		typeArray.recycle();
	}
}

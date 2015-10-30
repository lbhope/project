package com.example.customui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextEditView1 extends LinearLayout {

	private String Text = "";

	public TextEditView1(Context context) {
		this(context, null);
	}

	public TextEditView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		int resouceId = -1;
		TextView tv = new TextView(context);
		EditText et = new EditText(context);
		this.setOrientation(LinearLayout.VERTICAL);
		//»ñÈ¡ÊôÐÔid
		resouceId = attrs.getAttributeResourceValue(null, "Text", 0);
		if (resouceId > 0) {
			Text = context.getResources().getText(resouceId).toString();
		} else {
			Text = "";
		}
		tv.setText(Text);
		addView(tv);
		addView(et, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}
}
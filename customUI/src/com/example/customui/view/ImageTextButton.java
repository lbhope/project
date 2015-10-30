package com.example.customui.view;

import com.example.customui.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageTextButton extends LinearLayout {

	private ImageButton mButton;
	private TextView mTextView;

	public ImageTextButton(Context context) {
		this(context, null);
	}

	public ImageTextButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(
				R.layout.button_text_image, this, true);
		mButton = (ImageButton) view.findViewById(R.id.btn1);
		mTextView = (TextView) view.findViewById(R.id.text1);

		//修改自定义控件实现代码，以获取xml中定义的属性的值
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ImageTextButton);
		CharSequence text = a.getText(R.styleable.ImageTextButton_android_text);
		if (text != null)
			mTextView.setText(text);
		Drawable drawable = a.getDrawable(R.styleable.ImageTextButton_android_src);
		if (drawable != null)
			mButton.setImageDrawable(drawable);
		a.recycle();
	}

	public void setButtonImageResource(int resId) {
		mButton.setImageResource(resId);
	}

	public void setTextViewText(String text) {
		mTextView.setText(text);
	}

}

package com.example.customui.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customui.R;

public class ImageViewText extends LinearLayout  {
	private Context context;
	private LayoutInflater inflater;
	private ImageView mImageView;
	private TextView mTextView;
	
	private View view;

	public ImageViewText(Context context) {
		super(context);
		initView(context,null);
	}

	//在父控件中添加android:clickable=“true” android:focusable=“true”，而在子控件中添加android:duplicateParentState=“true”子控件就能获得父控件的点击事件
	public ImageViewText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.widget_image_text, null);

		mImageView = (ImageView) view.findViewById(R.id.iv_button_icon);
		mTextView = (TextView) view.findViewById(R.id.tv_button_text);
		
		this.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
		this.setOrientation(HORIZONTAL);
		this.setPadding(5, 5, 5, 5);

		view.setClickable(true);
		view.setFocusable(true);
		view.setOnClickListener(ocl);
		view.setOnTouchListener(otl);
 
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ImageViewText);
		float textSize = a.getDimension(R.styleable.ImageViewText_textSize, 14);
		textSize = textSize/3;
		String text = a.getString(R.styleable.ImageViewText_text);
		float textMarginLeft = a.getDimension(R.styleable.ImageViewText_textMargin, 10);
		float image_size = a.getDimension(R.styleable.ImageViewText_image_size, 10);
		
		mTextView.setText(text);
		mTextView.setTextSize(textSize);
 
		LayoutParams params  = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.leftMargin = (int) textMarginLeft;
		mTextView.setLayoutParams(params);
		mImageView.getLayoutParams().height=(int) image_size;
		mImageView.getLayoutParams().width=(int) image_size;
		mImageView.setImageResource(R.drawable.home);
		 
		 
		a.recycle();// 
		view.setBackgroundColor(Color.GRAY);
		addView(view,new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
 
	}

	public OnClickListener ocl = new OnClickListener() {
		@Override
		public void onClick(View v) {
//			listener.onClick(v);
		}
	};
	

	/**
	 * 设置颜色
	 */
	public OnTouchListener otl = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				view.setBackgroundColor(context.getResources().getColor(R.color.blue_shallow));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				view.setBackgroundColor(Color.GRAY);
			}
			return false;
		}
	};
	
	public OnClickListenerView listener;
	
	public void setOnClickListener(OnClickListenerView listenerView){
		this.listener = listenerView;
	}
	
	public interface OnClickListenerView{
		public void onClick(View v);
	}

}

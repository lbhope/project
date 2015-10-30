package com.example.customui.image;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customui.R;

public class ImageViewText2 extends LinearLayout {
	private ImageView mImageView;
	private TextView mTextView;

	private View view;

	public ImageViewText2(Context context) {
		super(context);
		initView(context, null);
	}

	// 在父控件中添加android:clickable=“true”
	// android:focusable=“true”，而在子控件中添加android:duplicateParentState=“true”子控件就能获得父控件的点击事件
	public ImageViewText2(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		 
		mImageView = new ImageView(context);
		mTextView = new TextView(context);
		view = this;
		view.setBackgroundColor(Color.GRAY);
		this.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//		this.setPadding(5, 5, 5, 5);

		view.setClickable(true);
		view.setFocusable(true);
		view.setOnClickListener(ocl);
//		view.setOnTouchListener(otl);

		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ImageViewText);
		float textSize = typedArray.getDimension(R.styleable.ImageViewText_textSize, 14);
		textSize = textSize/3;
		String text = typedArray.getString(R.styleable.ImageViewText_text);
		float textMarginLeft = typedArray.getDimension(R.styleable.ImageViewText_textMargin, 10);
		float image_size = typedArray.getDimension(R.styleable.ImageViewText_image_size, 10);

		int pressed = typedArray.getColor(R.styleable.ImageViewText_state_pressed, Color.BLACK);
		int normal = typedArray.getColor(R.styleable.ImageViewText_state_normal, Color.BLACK);
		int selected = typedArray.getColor(R.styleable.ImageViewText_state_selected, Color.BLACK);
		 

		int background = typedArray.getResourceId(R.styleable.ImageViewText_view_background, 0);
		int image_src = typedArray.getResourceId(R.styleable.ImageViewText_image_src, 0);
		if (image_src!=0) {
			mImageView.setBackgroundResource(image_src);
		}
		if (background!=0) {
			view.setBackgroundResource(background);
		}
		
		
		String text_direction = typedArray.getString(R.styleable.ImageViewText_text_direction);
		mTextView.setText(text);

		mTextView.setTextSize(textSize);
		mTextView.setTextColor(createColorStateList(normal,pressed,selected));

		LayoutParams imageLayoutParams = new LayoutParams((int) image_size,
				(int) image_size);
  
		mImageView.setLayoutParams(imageLayoutParams);

		typedArray.recycle();//
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		if (TextDirection.LEFT.equals(text_direction)) {
			this.setOrientation(HORIZONTAL);
			params.rightMargin = (int) textMarginLeft;
			mTextView.setLayoutParams(params);
			addView(mTextView);
			addView(mImageView);
			
		} else if (TextDirection.RIGHT.equals(text_direction)) {
			this.setOrientation(HORIZONTAL);
			params.leftMargin = (int) textMarginLeft;
			mTextView.setLayoutParams(params);
			addView(mImageView);
			addView(mTextView);
		} else if (TextDirection.TOP.equals(text_direction)) {
			this.setOrientation(VERTICAL);
			params.bottomMargin = (int) textMarginLeft;
			mTextView.setLayoutParams(params);
			addView(mTextView);
			addView(mImageView);
		} else if (TextDirection.BOTTOM.equals(text_direction)) {
			this.setOrientation(VERTICAL);
			params.topMargin = (int) textMarginLeft;
			mTextView.setLayoutParams(params);
			addView(mImageView);
			addView(mTextView);
		}
	}

	public OnClickListener ocl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (listener != null) {
				listener.onClick(v);
			}
		}
	};
	
	public void setImage(int resId){
		mImageView.setImageResource(resId);
	}

//	public OnTouchListener otl = new OnTouchListener() {
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			if (event.getAction() == MotionEvent.ACTION_DOWN) {
//				view.setBackgroundColor(context.getResources().getColor(
//						R.color.blue_shallow));
//			} else if (event.getAction() == MotionEvent.ACTION_UP) {
//				view.setBackgroundColor(Color.GRAY);
//			}
//			return false;
//		}
//	};

	public OnClickListenerView listener;

	public void setOnClickListener(OnClickListenerView listenerView) {
		this.listener = listenerView;
	}

	public interface OnClickListenerView {
		public void onClick(View v);
	}

	/** 对TextView设置不同状态时其文字颜色。 */
	private ColorStateList createColorStateList(int normal, int pressed,
			int selected) {
		int[] colors = new int[] { pressed,  selected, normal };
		int[][] states = new int[3][];
		states[0] = new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled };
		states[1] = new int[] { android.R.attr.selectable,
				android.R.attr.state_focused };
		states[2] = new int[] {};
		 
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}

	/** 设置Selector。 */
	public static StateListDrawable newSelector(Context context, int idNormal,
			int idPressed, int idFocused, int idUnable) {
		StateListDrawable bg = new StateListDrawable();
		Drawable normal = idNormal == -1 ? null : context.getResources()
				.getDrawable(idNormal);
		Drawable pressed = idPressed == -1 ? null : context.getResources()
				.getDrawable(idPressed);
		Drawable focused = idFocused == -1 ? null : context.getResources()
				.getDrawable(idFocused);
		Drawable unable = idUnable == -1 ? null : context.getResources()
				.getDrawable(idUnable);
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, focused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, focused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
		return bg;
	}

	public class TextDirection {
		public static final String LEFT = "left";
		public static final String TOP = "top";
		public static final String RIGHT = "right";
		public static final String BOTTOM = "bottom";
	}
}

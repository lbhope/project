package com.example.zzl.viewgroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zzl.R;

/**
 * 百分比布局
 * 
 * @author libin
 * 
 */
public class PercentLinearLayout extends LinearLayout {
	private static final String REGEX_PERCENT = "^(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)%([s]?[wh]?)$";
	
	public PercentLinearLayout(Context context) {
		super(context);
	}

	public PercentLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private static final String TAG = "PercentLinearLayout";

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
		int tmpHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
				heightMode);

		int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
		int tmpWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,
				widthMode);
		
		// 处理ScrollView
		if (heightMode == MeasureSpec.UNSPECIFIED && getParent() != null
				&& (getParent() instanceof ScrollView)) {
			int baseHeight = 0;
			Context context = getContext();
			if (context instanceof Activity) {
				Activity act = (Activity) context;
				//获取 PhoneWindow类中DecorView(继承FrameLayout)的根View的高度
				int measuredHeight = act.findViewById(android.R.id.content).getMeasuredHeight();
				baseHeight = measuredHeight;
			} else {
				baseHeight = getScreenHeight();
			}
			tmpHeightMeasureSpec = MeasureSpec.makeMeasureSpec(baseHeight,heightMode);
		}
		
		int width = View.MeasureSpec.getSize(tmpWidthMeasureSpec);
		int height = View.MeasureSpec.getSize(tmpHeightMeasureSpec);
		 
		Log.d(TAG, "width = " + width + " , height = " + height);
		//获取子View并设置其宽度和高度
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
			LayoutParams params = (LayoutParams) view.getLayoutParams();
			float widthp = 0;
			float heightp = 0;
			if (params instanceof PercentLinearLayout.LayoutParams) {
				widthp = params.w;
				heightp = params.h;
			}
			if (widthp != 0) {
				params.width = (int) (width * widthp);
			}
			if (heightp != 0) {
				params.height = (int) (height * heightp);
			}
			
			//设置文字大小
			if (params.hasParcentTextSize) {
				supportTextSize(width,height,view,params.parcentTextSize);
			}

		}
		 
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 该方法在LayoutInflater类的inflate方法中调用
	 */
	@Override
	public android.widget.LinearLayout.LayoutParams generateLayoutParams(
			AttributeSet attrs) {
		Log.i(TAG, "generateLayoutParams method execute ... ");
		return new PercentLinearLayout.LayoutParams(getContext(), attrs);
	}

	public static class LayoutParams extends LinearLayout.LayoutParams {
		public float h;
		public float w;
		public String parcentTextSize ;
		public boolean hasParcentTextSize=false;

		public LayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.ParcentLayout);

			w = a.getFloat(R.styleable.ParcentLayout_parcentWidth, -1);
			h = a.getFloat(R.styleable.ParcentLayout_parcentHeight, -1);
			parcentTextSize = a.getString(R.styleable.ParcentLayout_parcentTextSize);
			if (w < 0) {
				w = 0;
			}
			if (w > 1) {
				w = 1;
			}
			h = h < 0 ? 0 : h;
			h = h > 1 ? 1 : h;
			
			if (!TextUtils.isEmpty(parcentTextSize)) {
				hasParcentTextSize = true;
			}
			a.recycle();
			
		}

	}

	/**
	 * 设置文字大小
	 * @param widthHint 宽度
	 * @param heightHint 高度
	 * @param view
	 * @param percentStr 百分比字符串(20%w)
	 */
	private void supportTextSize(int widthHint, int heightHint, View view,
			String percentStr) {
		Pattern p = Pattern.compile(REGEX_PERCENT);
		Matcher matcher = p.matcher(percentStr);
		if (!matcher.matches()) {
			throw new RuntimeException(
					"the value of layout_xxxPercent invalid! ==>" + percentStr);
		}
		int len = percentStr.length();
		// extract the float value
		String floatVal = matcher.group(1);

		float percent = Float.parseFloat(floatVal) / 100f;
		
		int base = 0;
		if (percentStr.endsWith("w")) {
			base = view.getMeasuredWidth();
		} else if (percentStr.endsWith("h")) {
			base = view.getMeasuredHeight();
		}
		
		

		float textSize = (int) (base * percent);

		System.out.println("ss "+textSize);
		
		// Button 和 EditText 是TextView的子类
		if (view instanceof TextView) {
			((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		}
	}
	
	
	
	private int getScreenHeight() {
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

}

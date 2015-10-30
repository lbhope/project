package com.example.customui.widget.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class PaintTextView extends View {

	private Paint mPaint;
	private Rect mBound;
	private String mTitle="TextView";
	private int mTitleTextSize=22;

	public PaintTextView(Context context) {
		super(context);
		init();
	}

	public PaintTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setTextSize(mTitleTextSize);
		// mPaint.setColor(mTitleTextColor);
		mBound = new Rect();
		mPaint.getTextBounds(mTitle, 0,mTitle.length(), mBound);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

		mPaint.setColor(Color.RED);
		canvas.drawText("TextView", (getMeasuredWidth() - mBound.width()) / 2,
				(getMeasuredHeight()/2 - mBound.centerY()), mPaint);
		super.onDraw(canvas);
	}

	/**
	 *  EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
		AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
		UNSPECIFIED：表示子布局想要多大就多大，很少使用
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width;
		int height;
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {//WARP_CONTENT
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBound);
			float textWidth = mBound.width();
			int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
			width = desired;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBound);
			float textHeight = mBound.height();
			int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
			height = desired;
		}

		setMeasuredDimension(width, height);
	}
}

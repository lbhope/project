package com.example.customui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScllorTabView extends View {

	private int mTabNum, mCurrentNum;
	private float mWidth, mTabWidth, mOffset;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int mBeginColor;
	private int mEndColor;
	LinearGradient gradient;

	public ScllorTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setTabNum(int n) {
		mTabNum = n;
	}

	public void setCurrentNum(int n) {
		mCurrentNum = n;
		mOffset = 0;
	}

	public void setOffset(int position, float offset) {
		if (offset == 0) {
			return;
		}
		mCurrentNum = position;
		mOffset = offset;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mTabWidth == 0) {
			mWidth = getWidth();
			mTabWidth = mWidth / mTabNum;
		}
		mPaint.setColor(android.R.color.holo_green_light);
		// ���λ�ú�ƫ���������㻬������λ��
		float left = (mCurrentNum + mOffset) * mTabWidth;
		final float right = left + mTabWidth;
		final float top = getPaddingTop();
		final float bottom = getHeight() - getPaddingBottom();
 
		// if (gradient == null) {
//		LinearGradient gradient = new LinearGradient(left, getHeight(), right,
//				getHeight(), mBeginColor, mEndColor, Shader.TileMode.CLAMP);
//		mPaint.setShader(gradient);
		// }
		canvas.drawRect(left, top, right, bottom, mPaint);
	}

	public void setSelectedColor(int color, int color2) {
		mBeginColor = color;
		mEndColor = color2;

	}
}
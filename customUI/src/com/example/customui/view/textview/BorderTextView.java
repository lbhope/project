package com.example.customui.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderTextView extends TextView{
	private Paint mPaint;
	private int sroke_width = 5;

	public BorderTextView(Context context) {
		super(context);
		init();
	}

	public BorderTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BorderTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(sroke_width);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(Color.GREEN);
	}

	private int distance = 20;
	private int distanceWhite = 15;

	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		// canvas.drawLine(0, 0, this.getWidth() - sroke_width, 0, mPaint);
		// canvas.drawLine(0, 0, 0, this.getHeight() - sroke_width, mPaint);
		// canvas.drawLine(this.getWidth() - sroke_width, 0, this.getWidth() -
		// sroke_width, this.getHeight() - sroke_width, mPaint);
		int count = width / (distance + distanceWhite) * 2 + 2;
		int preIndex = 0;
		for (int i = 0; i < count; i++) {
			if (i % 2 == 0) {
				mPaint.setColor(Color.parseColor("#FA7777"));
				canvas.drawLine(preIndex, height - sroke_width, preIndex
						+ distance, height - sroke_width, mPaint);
				preIndex += distance;
			} else {
				mPaint.setColor(Color.WHITE);
				canvas.drawLine(preIndex, height - sroke_width, preIndex
						+ distanceWhite, height - sroke_width, mPaint);
				preIndex += distanceWhite;
			}
		}
		super.onDraw(canvas);
	}
}

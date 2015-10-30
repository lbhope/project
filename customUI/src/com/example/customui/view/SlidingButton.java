package com.example.customui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.customui.R;

/**
 * 可以自由拽托的控件
 * 
 * @author libin
 * 
 */
public class SlidingButton extends TextView {
	private float curX;
	private float curY;
	private Paint paint;
	private Bitmap bitmap;
	private boolean isPicked;
	/** * 记录点击的位置和上次所在位置的偏移 */
	private float offSetX = 0;
	/** * 记录点击的位置和上次所在位置的偏移 */
	private float offSetY = 0;

	public SlidingButton(Context context) {
		super(context);
		initLabelView();
	}

	public SlidingButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initLabelView();
	}

	public SlidingButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLabelView();
	}

	private final void initLabelView() {
		paint = new Paint();
		paint.setColor(0xFF888888);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (event.getX() >= curX
					&& event.getX() <= curX + bitmap.getWidth()
					&& event.getY() >= curY
					&& event.getY() <= curY + bitmap.getHeight()) {
				offSetX = event.getX() - curX;
				offSetY = event.getY() - curY;
				invalidate();
				isPicked = true;
			} else {
				isPicked = false;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isPicked) {
				curX = event.getX() - offSetX;
				curY = event.getY() - offSetY;
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isPicked) {
//				curX = event.getX() - offSetX;
//				curY = event.getY() - offSetY;
//				TranslateAnimation tranforAni = new TranslateAnimation(curX, 0,
//						curY, 0);
//				tranforAni.setDuration(1000);
//				this.startAnimation(tranforAni);

				// invalidate();
			}
			break;
		default:
			break;

		}
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		MeasureSpec.getSize(widthMeasureSpec);
		MeasureSpec.getSize(heightMeasureSpec);
		// setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas!=null) {
			canvas.drawBitmap(bitmap, curX, curY, paint);
		}
//		super.onDraw(canvas);
		
	}
}

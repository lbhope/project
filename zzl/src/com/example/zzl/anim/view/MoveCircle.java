package com.example.zzl.anim.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.zzl.anim.evaluator.PositionEvaluator;
import com.example.zzl.utils.ToolUtils;

public class MoveCircle extends View {
	
	private int radius=30;
	private Paint mPaint;
	
	private PointF currentPointF;
	private PointF lastPointF;
	private int halfHeight;
	private int width;
	
//	private Paint mLinePaint;
	
	
	public MoveCircle(Context context) {
		super(context);
		init();
	}

	public MoveCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
//		mLinePaint = new Paint();
//		mLinePaint.setAntiAlias(true);
//		mLinePaint.setColor(Color.RED);
		halfHeight = ToolUtils.getDisplayMetrics(getContext()).heightPixels/2;
		width = ToolUtils.getDisplayMetrics(getContext()).widthPixels;
		currentPointF = new PointF(radius,halfHeight);
//		lastPointF = currentPointF;
		startAnimator();
	}
 
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(currentPointF.x, currentPointF.y, radius, mPaint);
//		canvas.save();
//		canvas.drawLine(lastPointF.x, lastPointF.y, currentPointF.x, currentPointF.y, mLinePaint);
//		canvas.restore();
//		lastPointF = currentPointF;
		super.onDraw(canvas);
	}
	
	
	/**
	 * 启动动画
	 */
    private void startAnimator() {
        ValueAnimator animator = ValueAnimator.ofObject(
                new PositionEvaluator(),new PointF(radius, halfHeight),new PointF(width-radius, halfHeight));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            	currentPointF = (PointF) animation.getAnimatedValue();
            	Log.i("tag"," onAnimationUpdate x: "+currentPointF.x+"   y: "+currentPointF.y);
                invalidate();
            }
        });
        // 设置加速插值器AccelerateInterpolator()
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setStartDelay(200);//推迟启动200毫秒
        animator.setDuration(10 * 1000).start();
    } 
    
    
	
}

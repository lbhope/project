package com.example.customui.widget.testview;

import com.example.customui.R;
import com.example.customui.util.DensityUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleVoiceView extends View {

	public CircleVoiceView(Context context) {
		super(context);
		init();
	}

	public CircleVoiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 第一圈的颜色
	 */
	private int mFirstColor=Color.GRAY;

	/**
	 * 第二圈的颜色
	 */
	private int mSecondColor=Color.GREEN;
	/**
	 * 圈的宽度
	 */
	private int mCircleWidth;
	/**
	 * 画笔
	 */
	private Paint mPaint;
	/**
	 * 当前进度
	 */
	private int mCurrentCount = 3;

	/**
	 * 中间的图片
	 */
	private Bitmap mImage;
	/**
	 * 每个块块间的间隙
	 */
	private int mSplitSize=20;
	/**
	 * 个数
	 */
	private int mCount=10;

	private Rect mRect;

	private void init() {
		mCircleWidth = DensityUtil.dip2px(getContext(), 10);
		mImage = BitmapFactory.decodeResource(getResources(), R.drawable.like);
		
		mPaint = new Paint();
		mRect = new Rect();
		mPaint.setAntiAlias(true); // 消除锯齿
		mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
		//BUTT SQUARE
		mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头
		mPaint.setStyle(Paint.Style.STROKE); // 设置空心
		
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int centre = getWidth() / 2; // 获取圆心的x坐标
		int centerY = getHeight()/2;
		int radius = centre - mCircleWidth / 2;// 半径
		
		drawOval(canvas,centre,radius);
		
	    /** 
         * 计算内切正方形的位置 
         */  
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径  
        /** 
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2 
         */  
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;  
        /** 
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2 
         */  
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;  
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);  
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);  
  
        /** 
         * 如果图片比较小，那么根据图片的尺寸放置到正中心 
         */  
        if (mImage!=null && mImage.getWidth() < Math.sqrt(2) * relRadius)  
        {  
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);  
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);  
            mRect.right = (int) (mRect.left + mImage.getWidth());  
            mRect.bottom = (int) (mRect.top + mImage.getHeight());  
  
        }  
        // 绘图  
        canvas.drawBitmap(mImage, null, mRect, mPaint);  
		super.onDraw(canvas);
	}
	
	private void drawOval(Canvas canvas, int centre, int radius) {
		/**
		 * 根据需要画的个数以及间隙计算每个块块所占的比例*360
		 */
		float itemSize = (360 * 1.0f - mCount * mSplitSize) / mCount;

		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

		mPaint.setColor(mFirstColor); // 设置圆环的颜色
		for (int i = 0; i < mCount; i++) {
			/**
			 *  oval :指定圆弧的外轮廓矩形区域。
				startAngle: 圆弧起始角度，单位为度。
				sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
				useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
				paint: 绘制圆弧的画板属性，如颜色，是否填充等。
			 */
			canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false,
					mPaint); // 根据进度画圆弧
		}
		 
		
		mPaint.setColor(mSecondColor); // 设置圆环的颜色
		for (int i = 0; i < mCurrentCount; i++) {
			canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false,
					mPaint); // 根据进度画圆弧
		}
		 
	}

}

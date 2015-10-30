package com.example.customui.view;

 

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.customui.R;

public class ImageTextView extends View implements OnClickListener {
	
	private Paint mImagePaint;
	private Paint mTextPaint;
	private int mTextSize=16;
	private Bitmap imageBitmap;  
	private Context context; 
	private int viewHeight;
	private DisplayMetrics displayMetrics;
	private int imageTop;
	private int textTop;
	 
	public ImageTextView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}
	/**
	 * 初始化数据
	 */
	private void init() {
		displayMetrics = getDisplayMetrics();
		mImagePaint = new Paint();
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setTextSize(mTextSize*displayMetrics.density);
//		
		mTextPaint.setAntiAlias(true); // 打开抗矩齿  
		imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home); 
		//设置背景颜色
		setBackground(getResources().getDrawable(R.drawable.item_background_selector));
		//设置点击事件
		setOnClickListener(this);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewHeight = getHeight();
		imageTop = (viewHeight - imageBitmap.getHeight())/2;
		//是文字在垂直方向上居中
		textTop = (int) ((viewHeight - getFontHeight(mTextPaint))/2-mTextPaint.getFontMetrics().top);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(imageBitmap, 10, imageTop, mImagePaint);
		canvas.drawText("打开",imageBitmap.getWidth()+20, textTop, mTextPaint);
	}
	
	
	/**
	 * 获取手机分辨率
	 * @return
	 */
	public DisplayMetrics getDisplayMetrics() {   
        DisplayMetrics dm = new DisplayMetrics();   
        //取得DisplayMetrics对象方法一  
        dm = context.getApplicationContext().getResources().getDisplayMetrics();   
        //取得DisplayMetrics对象方法二  
//      ((Activity)cx).getWindowManager().getDefaultDisplay().getMetrics(dm);  
        return dm;   
     } 
	
	/**
	 * 获取字体的高度
	 * @param paint
	 * @return
	 */
	private int getFontHeight(Paint paint){
		FontMetrics fm = paint.getFontMetrics();
		return (int)Math.ceil(fm.descent - fm.ascent);  
	}

	/**
	 * 改变字体颜色
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTextPaint.setColor(Color.BLUE);
			break;
		case MotionEvent.ACTION_UP:
			mTextPaint.setColor(Color.BLACK);
			break;
		default:
			break;
		}
		invalidate();
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onClick(View v) {
		 
	}
	 
}

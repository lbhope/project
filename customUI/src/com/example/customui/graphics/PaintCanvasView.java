package com.example.customui.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class PaintCanvasView extends View {
	private Paint mPaint;
	private float width;
	private float height;
	
	
	public PaintCanvasView(Context context) {
		super(context);
		init();
	}

	public PaintCanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GREEN);//设置画笔颜色
		mPaint.setStyle(Style.FILL);//设置画笔风格，空心或实心
		mPaint.setStrokeWidth(10);//画笔宽度
		mPaint.setAlpha(100);//透明度
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setTextSize(setTextSize(20));//字体大小
		width = getResources().getDisplayMetrics().widthPixels;
		height = getResources().getDisplayMetrics().heightPixels;

	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawColor(Color.WHITE); //设置画布颜色
		
//		canvas.clipRect(0, 0, 200, 200);//设置裁剪区
		
		//绘制矩形
		canvas.save();//锁定画布
		canvas.translate(50, 50);//使当前的坐标移动到50，50
		canvas.rotate(45.0f);//旋转画布
		canvas.drawRect(new Rect(50, 50, 80, 80),mPaint);
		
		canvas.restore();//解除画布锁定
		
		mPaint.setColor(Color.RED);
		canvas.drawRect(new Rect(50, 50, 100, 100),mPaint);
		
		//绘制三角形
		Path path = new Path();
		path.moveTo(100, 100);
		path.lineTo(150, 150);
		path.lineTo(50, 150);
		path.close();
		path.setFillType(Path.FillType.WINDING);
		
		mPaint.setColor(Color.BLUE);
		canvas.drawPath(path, mPaint);
		
		//绘制弧形字符串
		float r = getDip(100);
		mPaint.setColor(Color.RED);
	    canvas.save();
	    canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);
	    canvas.drawCircle(0, 0, r, mPaint);  
	    Path arcPath = new Path();  
	    arcPath.addArc(new RectF(-r, -r, r, r), -180, 180);  
	    mPaint.setTextAlign(Align.CENTER);  
		mPaint.setColor(Color.CYAN);
		canvas.drawTextOnPath("android", arcPath, 0, 50,mPaint);
		canvas.restore();
	}
	
	
	
    public float setTextSize(float size) {
        Context c = getContext();
        Resources r;

        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

       float textsize = TypedValue.applyDimension(
       		TypedValue.COMPLEX_UNIT_SP, size, r.getDisplayMetrics());  
       return textsize;
    }
    public float getDip(float size) {
    	Context c = getContext();
    	Resources r;
    	
    	if (c == null)
    		r = Resources.getSystem();
    	else
    		r = c.getResources();
    	
    	float textsize = TypedValue.applyDimension(
    			TypedValue.COMPLEX_UNIT_DIP, size, r.getDisplayMetrics());  
    	return textsize;
    }
}

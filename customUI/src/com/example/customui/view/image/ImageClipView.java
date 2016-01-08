package com.example.customui.view.image;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
/**
 * http://bbs.csdn.net/topics/390954941?page=1
 * 
 * http://blog.sina.com.cn/s/blog_474928c90101dkvf.html
 * @author libin
 *
 */
public class ImageClipView extends View {
 
    private Bitmap leftImage;
    private Bitmap rightImage;
    private Bitmap downImage;
    private Matrix leftMatrix;
    private Matrix rightMatrix;
    private Matrix downMatrix;
    private float padding = 10;
    private Path left;
    private Path right;
    private Path down;
    private Paint paint;
     
    private int state = -1;
    private final int START = 1;
     
    public ImageClipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
 
    public ImageClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public ImageClipView(Context context) {
        super(context);
    }
 
    private void init(){
        try {
            leftImage = BitmapFactory.decodeStream(getContext().getAssets().open("clip_image.png"));
            rightImage = BitmapFactory.decodeStream(getContext().getAssets().open("right.png"));
            downImage = BitmapFactory.decodeStream(getContext().getAssets().open("down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        paint = new Paint();
        paint.setAntiAlias(true);
        initMatrix();
        initPath();
    }
     
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(leftImage != null){
            canvas.save();
            canvas.clipPath(left);
            canvas.drawBitmap(leftImage, leftMatrix, paint);
            canvas.restore();
            canvas.save();
            canvas.clipPath(right);
            canvas.drawBitmap(rightImage, rightMatrix, paint);
            canvas.restore();
            canvas.save();
            canvas.clipPath(down);
            canvas.drawBitmap(downImage, downMatrix, paint);
            canvas.restore();
        }
    }
     
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(state > 0){
            return;
        }
        state = START;
        init();
    }
     
    private void initMatrix(){
        leftMatrix = new Matrix();
        rightMatrix = new Matrix();
        downMatrix = new Matrix();
         
        float w = getWidth();
        float h = getHeight();
         
        //第一个图片
        float scale = 1;
        float scaleX = w / leftImage.getWidth();
        float scaleY = h / leftImage.getHeight();
        scale = scaleX > scaleY ? scaleX : scaleY;
        leftMatrix.setScale(scale, scale);
        //第二个图片
        scaleX = w / rightImage.getWidth();
        scaleY = h / rightImage.getHeight();
        scale = scaleX > scaleY ? scaleX : scaleY;
        rightMatrix.setScale(scale, scale);
        //第三个图片
        scaleX = w / downImage.getWidth();
        scaleY = h / downImage.getHeight();
        scale = scaleX > scaleY ? scaleX : scaleY;
        downMatrix.setScale(scale, scale);
         
    }
     
    private void initPath(){
         
        float cpad = padding / 2;//padding = 10
         
        float w = getWidth();
        float h = getHeight();
        float bx = w / 2;
        float by = h / 2;
         
        left = new Path();
        right = new Path();
        down = new Path();
         
        left.moveTo(padding, padding);
        left.lineTo(bx - cpad, padding);
        left.lineTo(bx - cpad, by);
        left.lineTo(padding, h - 1.7f * padding);
        left.lineTo(padding, padding);
        left.close();
         
        right.moveTo(bx + cpad, padding);
        right.lineTo(w - padding, padding);
        right.lineTo(w - padding, h - 1.7f * padding);
        right.lineTo(bx + cpad, by);
        right.lineTo(bx + cpad, padding);
        right.close();
         
        down.moveTo(bx, by + 0.7f * padding);
        down.lineTo(padding * 1.41f, h - padding);
        down.lineTo(w - padding* 1.41f, h - padding);
        down.lineTo(bx, by + 0.7f * padding);
        down.close();
         
    }
     
}
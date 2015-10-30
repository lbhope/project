package com.example.customui.view.textview;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.customui.R;
import com.example.customui.util.DensityUtil;
/**
 * 富文本textview
 * @author libin
 *
 */
public class RichTextView extends  TextView {

	public RichTextView(Context context) {
		super(context);
	}

	public RichTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RichTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (!TextUtils.isEmpty(text)) {
			super.setText(replace(text), type);
		} else {
			super.setText(text, type);
		}
	}

	private Pattern buildPattern() {
		StringBuilder patternString = new StringBuilder();
//				DTApplication.getInstance().getmRichText().size() * 3);
//		patternString.append('(');
//		for (int i = 0; i < DTApplication.getInstance().getmRichText().size(); i++) {
//			String s = DTApplication.getInstance().getmRichText().get(i);
//			patternString.append(Pattern.quote(s));
//			patternString.append('|');
//		}
//		patternString.replace(patternString.length() - 1,
//				patternString.length(), ")");
		return Pattern.compile(patternString.toString());
	}

	private CharSequence replace(CharSequence text) {
		try {
			SpannableStringBuilder builder = new SpannableStringBuilder(text);
			Pattern pattern = buildPattern();
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
//				if (DTApplication.getInstance().getmRichTextPic().containsKey(matcher.group())) {
//					int id = DTApplication.getInstance().getmRichTextPic().get(matcher.group());
//					if (id==R.mipmap.like_h_red_small) {
//						BitmapFactory.Options options = new BitmapFactory.Options();
//					    options.inSampleSize = 2;
//						Bitmap bitmap = BitmapFactory.decodeResource(
//								getResources(), id,options);
//						if (bitmap != null) {
//							ImageSpan span = new ImageSpan(getContext(),bitmap);
//							builder.setSpan(span, matcher.start(), matcher.end(),
//									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//						}
//					}else {
//						ImageSpan span = new ImageSpan(getContext(), drawableToBitmap(90));
//						builder.setSpan(span, matcher.start(), matcher.end(),
//								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//					}
//				}
			}
			return builder;
		} catch (Exception e) {
			return text;
		}
	}
	
	private Bitmap drawableToBitmap(int number) {
        int width = DensityUtil.dip2px(getContext(), 25);
		Bitmap bitmap = Bitmap
				.createBitmap(width, width, Bitmap.Config.RGB_565);
		
        Canvas canvas = new Canvas(bitmap);
        Rect targetRect = new Rect(0, 0, width,width);  
        //将图片画在bitmap的画布上
        Paint mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        canvas.drawARGB(0, 255, 255, 255);
		canvas.drawCircle(width / 2, width / 2, width / 2, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, targetRect, targetRect, mPaint);
        
//        Drawable drawable =  getResources().getDrawable(R.drawable.oval_yellow);
//        //设置drawable图片的边缘，以改变图片的大小
//        drawable.setBounds(0, 0, width, width);
//        drawable.draw(canvas);
        
        //画文�?
        mPaint.setTextAlign(Paint.Align.CENTER);  
        mPaint.setColor(Color.RED);
//        mPaint.setTextSize(getResources().getDimension(R.dimen.font_body_12));
        String text = ""+number;  
        FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();  
        int baseline = targetRect.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;  
        canvas.drawText(text, targetRect.centerX(), baseline, mPaint);  
        return bitmap;
	}
}

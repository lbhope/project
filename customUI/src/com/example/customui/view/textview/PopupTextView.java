package com.example.customui.view.textview;

import com.example.customui.util.Logger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopupTextView extends TextView {
	private static final String TAG = "PopupTextView";
	private static final boolean IS_DEBUG = false;
	/**
	 * x of anchor triangle in the popup
	 */
	private float mTriangleX;
	/**
	 * border color
	 */
	private int mBorderColor = 0xff1fc38f;
	/**
	 * border width
	 */
	private int mBorderWidth = 2;
	/**
	 * background color
	 */
	private int mBgColor = 0xffffffff;
	/**
	 * background color in dark mode
	 */
	private int mBgColorDark = 0xff999999;
	/**
	 * anchor height
	 */
	private float mAnchorHeight = 20;
	/**
	 * anchor width
	 */
	private float mAnchorWidth = 30;
	/**
	 * If content under anchor
	 */
	private boolean mShowDown = true;
	/**
	 * Below items for draw
	 */
	private ShapeDrawable mBorderDrawable;
	private Path mBorderPath;
	private ShapeDrawable mBgDrawable;
	private Path mBgPath;
	private int mWidth;
	private int mHeight;
	/**
	 * Keep a record of original padding.
	 */
	private int mPadding;
	/**
	 * Is night mode.
	 */
	private boolean mIsNightMode;
	/**
	 * anchor x, y in screen
	 */
	private int mAnchorYUp;
	private int mAnchorYDown;
	private int mAnchorX;

	/**
	 * screen height & width
	 */
	private int mScreenHeight;
	private int mScreenWidth;
	private float mDensity;
	private PopupWindow mPopupWindow;
	private Context mCtx;
	/**
	 * Touch listener
	 */
	private OnTouchListener mOnTouchListener;
	private boolean mDismissAfterTouch = true;
	/**
	 * The minimum margin to left or right.
	 */
	private int TRIANGLE_MINIMUM_MARGIN = 10;

	public PopupTextView(Context context) {
		super(context);
		setFocusable(true);
		init(context);
	}

	public PopupTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PopupTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context c) {
		mCtx = c;
		mPadding = getPaddingBottom();
		DisplayMetrics dm = c.getResources().getDisplayMetrics();
		mScreenHeight = dm.heightPixels;
		mScreenWidth = dm.widthPixels;
		mDensity = dm.scaledDensity;
	}

	/**
	 * Show as pop up window
	 */
	public void show()
    {
        if (mPopupWindow != null)
        {
            mPopupWindow.dismiss();
        }
 
        if (IS_DEBUG)
        {
            Logger.getLogger().d("mAnchorX=" + mAnchorX +  "mWidth= "+ mWidth +  "mHeight= "+ mHeight);
        }
 
        mPopupWindow = new PopupWindow(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (mOnTouchListener != null)
        {
            mPopupWindow.setTouchInterceptor(new OnTouchListener()
            {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1)
                {
                    mOnTouchListener.onTouch(arg0, arg1);
                    if (mDismissAfterTouch && arg1.getAction() == MotionEvent.ACTION_UP)
                    {
                        mPopupWindow.dismiss();
                    }
                    return false;
                }
            });
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
 
        int popX = 0, popY = 0;
        if (mWidth <= 0 || mHeight <= 0)
        {
            // The first time we showthe pop up window out of the screen to get
            // the size of itself.
            popX = mScreenWidth;
            popY = mScreenHeight;
        }
        else
        {
            // The second time we calculate the pop up window's right position.
            Point pos = getLayoutValue();
            popX = pos.x;
            popY = pos.y;
            mTriangleX = mAnchorX - pos.x;
            mTriangleX = Math.max(mTriangleX, TRIANGLE_MINIMUM_MARGIN);
            mTriangleX = Math.min(mTriangleX, mWidth - TRIANGLE_MINIMUM_MARGIN - mAnchorWidth);
        }
        mPopupWindow.showAtLocation(this, Gravity.LEFT | Gravity.TOP, popX, popY);
    }

	/**
	 * Calculate the pop up window's right position.
	 * 
	 * @return
	 */
	private Point getLayoutValue() {
		int x = mAnchorX - mWidth / 2;
		if (x < 10 * mDensity) {
			x = (int) (10 * mDensity);
		} else if (x + mWidth > mScreenWidth - 10 * mDensity) {
			x = (int) (mScreenWidth - mWidth - 10 * mDensity);
		}
		boolean showDown = mAnchorYDown + mHeight < mScreenHeight
				|| mAnchorYDown <= mScreenHeight / 2;
		setShowDown(showDown);
		int y = showDown ? mAnchorYDown : mAnchorYUp - mHeight;
		return new Point(x, y);
	}

	/**
	 * Init drawble path.
	 * 
	 * @param width
	 * @param height
	 */
	private void initPath(int width, int height) {
		mBorderPath = new Path();
		mBgPath = new Path();

		if (mShowDown) {
			/**
			 * ....|<----------------width-------->|
			 * 
			 * ....|<--archorX------>|
			 * 
			 * ....................2
			 * 
			 * ..................../ (anchor)
			 * 
			 * ....0/7-------------1 3-----------4...........----
			 * 
			 * ....|...............................|.............|
			 * 
			 * ....|...............................|.............height
			 * 
			 * ....|...............................|.............|
			 * 
			 * ....6-------------------------------5............---
			 */
			PointF[] borderPoints = new PointF[] {
					new PointF(0, mAnchorHeight),
					new PointF(mTriangleX - mAnchorWidth / 2, mAnchorHeight),
					new PointF(mTriangleX, 0),
					new PointF(mTriangleX + mAnchorWidth / 2, mAnchorHeight),
					new PointF(width, mAnchorHeight),
					new PointF(width, height), new PointF(0, height),
					new PointF(0, mAnchorHeight), };
			mBorderPath = createLIneToPath(borderPoints);

			PointF[] bgPoints = new PointF[] {
					new PointF(borderPoints[0].x + mBorderWidth,
							borderPoints[0].y + mBorderWidth),
					new PointF(borderPoints[1].x + mBorderWidth,
							borderPoints[1].y + mBorderWidth),
					new PointF(borderPoints[2].x, borderPoints[2].y
							+ mBorderWidth),
					new PointF(borderPoints[3].x - mBorderWidth,
							borderPoints[3].y + mBorderWidth),
					new PointF(borderPoints[4].x - mBorderWidth,
							borderPoints[4].y + mBorderWidth),
					new PointF(borderPoints[5].x - mBorderWidth,
							borderPoints[5].y - mBorderWidth),
					new PointF(borderPoints[6].x + mBorderWidth,
							borderPoints[6].y - mBorderWidth),
					new PointF(borderPoints[7].x + mBorderWidth,
							borderPoints[7].y + mBorderWidth), };
			mBgPath = createLIneToPath(bgPoints);
		} else {
			/**
			 * 0/7-----------------------------1
			 * 
			 * |...............................|
			 * 
			 * |...............................|
			 * 
			 * 6------------------5..3---------2
			 * 
			 * ..................../
			 * 
			 * ....................4
			 */
			PointF[] borderPoints = new PointF[] {
					new PointF(0, 0),
					new PointF(width, 0),
					new PointF(width, height - mAnchorHeight),
					new PointF(mTriangleX + mAnchorWidth / 2, height
							- mAnchorHeight),
					new PointF(mTriangleX, height),
					new PointF(mTriangleX - mAnchorWidth / 2, height
							- mAnchorHeight),
					new PointF(0, height - mAnchorHeight), new PointF(0, 0), };
			mBorderPath = createLIneToPath(borderPoints);

			PointF[] bgPoints = new PointF[] {
					new PointF(borderPoints[0].x + mBorderWidth,
							borderPoints[0].y + mBorderWidth),
					new PointF(borderPoints[1].x - mBorderWidth,
							borderPoints[1].y + mBorderWidth),
					new PointF(borderPoints[2].x - mBorderWidth,
							borderPoints[2].y - mBorderWidth),
					new PointF(borderPoints[3].x - mBorderWidth,
							borderPoints[3].y - mBorderWidth),
					new PointF(borderPoints[4].x, borderPoints[4].y
							- mBorderWidth),
					new PointF(borderPoints[5].x + mBorderWidth,
							borderPoints[5].y - mBorderWidth),
					new PointF(borderPoints[6].x + mBorderWidth,
							borderPoints[6].y - mBorderWidth),
					new PointF(borderPoints[7].x + mBorderWidth,
							borderPoints[7].y + mBorderWidth), };
			mBgPath = createLIneToPath(bgPoints);
		}
	}

	private Path createLIneToPath(PointF[] points) {
		Path path = new Path();
		if (points != null && points.length > 1) {
			path.moveTo(points[0].x, points[0].y);
			for (int i = 1; i < points.length; i++) {
				path.lineTo(points[i].x, points[i].y);
			}
		}
		path.close();
		return path;
	}

	public int getAnchorYUp() {
		return mAnchorYUp;
	}

	public void setAnchorYUp(int mAnchorYUp) {
		this.mAnchorYUp = mAnchorYUp;
	}

	public int getAnchorYDown() {
		return mAnchorYDown;
	}

	public void setAnchorYDown(int mAnchorYDown) {
		this.mAnchorYDown = mAnchorYDown;
	}

	public int getAnchorX() {
		return mAnchorX;
	}

	public void setAnchorX(int anchorX) {
		this.mAnchorX = anchorX;
	}

	public void setOnTouchListener(OnTouchListener l) {
		mOnTouchListener = l;
	}

	public void setDismissAfterTouch(boolean dismissAfterTouch) {
		mDismissAfterTouch = dismissAfterTouch;
	}

	public boolean getDismissAfterTouch() {
		return mDismissAfterTouch;
	}

	public void setShowDown(boolean showDown) {
		mShowDown = showDown;
		if (mShowDown) {
			setPadding(getPaddingLeft(), (int) mAnchorHeight + mPadding,
					getPaddingRight(), mPadding);
		} else {
			setPadding(getPaddingLeft(), mPadding, getPaddingRight(),
					(int) mAnchorHeight + mPadding);
		}
	}

	public void setNightMode(boolean isNightMode) {
		mIsNightMode = isNightMode;
	}

	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
		if (IS_DEBUG) {
			Logger.getLogger()
					.i(" w= " + w + " h= " + h + "oldw= " + oldw + "oldh= "
							+ oldh);
		}
        mWidth = w;
        mHeight = h;
        show();
    }

	@Override
	protected void onDraw(Canvas canvas) {
		initPath(mWidth, mHeight);
		mBorderDrawable = new ShapeDrawable(new PathShape(mBorderPath, mWidth,
				mHeight));
		mBorderDrawable.getPaint().setColor(mBorderColor);
		mBgDrawable = new ShapeDrawable(new PathShape(mBgPath, mWidth, mHeight));
		int bgColor = mBgColor;
		if (mIsNightMode) {
			bgColor = mBgColorDark;
		}
		mBgDrawable.getPaint().setColor(bgColor);

		int x = 0;
		int y = 0;
		mBorderDrawable.setBounds(x, y, x + mWidth, y + mHeight);
		mBorderDrawable.draw(canvas);
		mBgDrawable.setBounds(x, y, x + mWidth, y + mHeight);
		mBgDrawable.draw(canvas);
		super.onDraw(canvas);
	}
}
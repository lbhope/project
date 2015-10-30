package com.example.customui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;

public class GestureLockView extends View {

	private static final String TAG = "GestureLockView";

	/**
	 * GestureLockView������״̬
	 */
	enum Mode {
		STATUS_NO_FINGER, STATUS_FINGER_ON, STATUS_FINGER_UP;
	}

	/**
	 * GestureLockView�ĵ�ǰ״̬
	 */
	private Mode mCurrentStatus = Mode.STATUS_NO_FINGER;

	/**
	 * ���
	 */
	private int mWidth;
	/**
	 * �߶�
	 */
	private int mHeight;
	/**
	 * ��Բ�뾶
	 */
	private int mRadius;
	/**
	 * ���ʵĿ��
	 */
	private int mStrokeWidth = 2;

	/**
	 * Բ������
	 */
	private int mCenterX;
	private int mCenterY;
	private Paint mPaint;

	/**
	 * ��ͷ��С������ߵ�һ�볤�� = mArrawRate * mWidth / 2 ��
	 */
	private float mArrowRate = 0.333f;
	private int mArrowDegree = -1;
	private Path mArrowPath;
	/**
	 * ��Բ�İ뾶 = mInnerCircleRadiusRate * mRadus
	 * 
	 */
	private float mInnerCircleRadiusRate = 0.3F;

	/**
	 * �ĸ���ɫ�������û��Զ��壬��ʼ��ʱ��GestureLockViewGroup����
	 */
	private int mColorNoFingerInner;
	private int mColorNoFingerOutter;
	private int mColorFingerOn;
	private int mColorFingerUp;

	public GestureLockView(Context context, int colorNoFingerInner,
			int colorNoFingerOutter, int colorFingerOn, int colorFingerUp) {
		super(context);
		this.mColorNoFingerInner = colorNoFingerInner;
		this.mColorNoFingerOutter = colorNoFingerOutter;
		this.mColorFingerOn = colorFingerOn;
		this.mColorFingerUp = colorFingerUp;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mArrowPath = new Path();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);

		// ȡ���Ϳ��е�Сֵ
		mWidth = mWidth < mHeight ? mWidth : mHeight;
		mRadius = mCenterX = mCenterY = mWidth / 2;
		mRadius -= mStrokeWidth / 2;

		// ���������Σ���ʼʱ�Ǹ�Ĭ�ϼ�ͷ���ϵ�һ�����������Σ��û����ƽ����󣬸���������GestureLockView������Ҫ��ת���ٶ�
		float mArrowLength = mWidth / 2 * mArrowRate;
		mArrowPath.moveTo(mWidth / 2, mStrokeWidth + 2);
		mArrowPath.lineTo(mWidth / 2 - mArrowLength, mStrokeWidth + 2
				+ mArrowLength);
		mArrowPath.lineTo(mWidth / 2 + mArrowLength, mStrokeWidth + 2
				+ mArrowLength);
		mArrowPath.close();
		mArrowPath.setFillType(Path.FillType.WINDING);

	}

	@Override
	protected void onDraw(Canvas canvas) {

		switch (mCurrentStatus) {
		case STATUS_FINGER_ON:

			// ������Բ
			mPaint.setStyle(Style.STROKE);
			mPaint.setColor(mColorFingerOn);
			mPaint.setStrokeWidth(2);
			canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
			// ������Բ
			mPaint.setStyle(Style.FILL);
			canvas.drawCircle(mCenterX, mCenterY, mRadius
					* mInnerCircleRadiusRate, mPaint);
			break;
		case STATUS_FINGER_UP:
			// ������Բ
			mPaint.setColor(mColorFingerUp);
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeWidth(2);
			canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
			// ������Բ
			mPaint.setStyle(Style.FILL);
			canvas.drawCircle(mCenterX, mCenterY, mRadius
					* mInnerCircleRadiusRate, mPaint);

			drawArrow(canvas);

			break;

		case STATUS_NO_FINGER:

			// ������Բ
			mPaint.setStyle(Style.FILL);
			mPaint.setColor(mColorNoFingerOutter);
			canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
			// ������Բ
			mPaint.setColor(mColorNoFingerInner);
			canvas.drawCircle(mCenterX, mCenterY, mRadius
					* mInnerCircleRadiusRate, mPaint);
			break;

		}

	}

	/**
	 * ���Ƽ�ͷ
	 * 
	 * @param canvas
	 */
	private void drawArrow(Canvas canvas) {
		if (mArrowDegree != -1) {
			mPaint.setStyle(Paint.Style.FILL);

			canvas.save();
			canvas.rotate(mArrowDegree, mCenterX, mCenterY);
			canvas.drawPath(mArrowPath, mPaint);

			canvas.restore();
		}

	}

	/**
	 * ���õ�ǰģʽ���ػ����
	 * 
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.mCurrentStatus = mode;
		invalidate();
	}

	public void setArrowDegree(int degree) {
		this.mArrowDegree = degree;
	}

	public int getArrowDegree() {
		return this.mArrowDegree;
	}
}

package com.example.customui.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.customui.R;
import com.example.customui.view.GestureLockView.Mode;
 
/** 
 * http://blog.csdn.net/lmj623565791/article/details/36236113
 * �����n*n��GestureLockView,ÿ��GestureLockView����mMarginBetweenLockView�� 
 * ������GestureLockView����������mMarginBetweenLockView����߾� 
 *  
 * ����GestureLockView�ı߳���n*n���� n * mGestureLockViewWidth + ( n + 1 ) * 
 * mMarginBetweenLockView = mWidth ; �ã�mGestureLockViewWidth = 4 * mWidth / ( 5 
 * * mCount + 1 ) ע��mMarginBetweenLockView = mGestureLockViewWidth * 0.25 ; 
 *  
 * @author zhy 
 *  
 */  
public class GestureLockViewGroup extends RelativeLayout  
{  
  
    private static final String TAG = "GestureLockViewGroup";  
    /** 
     * �������е�GestureLockView 
     */  
    private GestureLockView[] mGestureLockViews;  
    /** 
     * ÿ�����ϵ�GestureLockView�ĸ��� 
     */  
    private int mCount = 4;  
    /** 
     * �洢�� 
     */  
    private int[] mAnswer = { 0, 1, 2, 5, 8 };  
    /** 
     * �����û�ѡ�е�GestureLockView��id 
     */  
    private List<Integer> mChoose = new ArrayList<Integer>();  
  
    private Paint mPaint;  
    /** 
     * ÿ��GestureLockView�м�ļ�� ����Ϊ��mGestureLockViewWidth * 25% 
     */  
    private int mMarginBetweenLockView = 30;  
    /** 
     * GestureLockView�ı߳� 4 * mWidth / ( 5 * mCount + 1 ) 
     */  
    private int mGestureLockViewWidth;  
  
    /** 
     * GestureLockView����ָ������״̬����Բ����ɫ 
     */  
    private int mNoFingerInnerCircleColor = 0xFF939090;  
    /** 
     * GestureLockView����ָ������״̬����Բ����ɫ 
     */  
    private int mNoFingerOuterCircleColor = 0xFFE0DBDB;  
    /** 
     * GestureLockView��ָ������״̬����Բ����Բ����ɫ 
     */  
    private int mFingerOnColor = 0xFF378FC9;  
    /** 
     * GestureLockView��ָ̧���״̬����Բ����Բ����ɫ 
     */  
    private int mFingerUpColor = 0xFFFF0000;  
  
    /** 
     * ��� 
     */  
    private int mWidth;  
    /** 
     * �߶� 
     */  
    private int mHeight;  
  
    private Path mPath;  
    /** 
     * ָ���ߵĿ�ʼλ��x 
     */  
    private int mLastPathX;  
    /** 
     * ָ���ߵĿ�ʼλ��y 
     */  
    private int mLastPathY;  
    /** 
     * ָ���µĽ���λ�� 
     */  
    private Point mTmpTarget = new Point();  
  
    /** 
     * ����Դ��� 
     */  
    private int mTryTimes = 4;  
    /** 
     * �ص��ӿ� 
     */  
    private OnGestureLockViewListener mOnGestureLockViewListener;  
  
    public GestureLockViewGroup(Context context, AttributeSet attrs)  
    {  
        this(context, attrs, 0);  
    }  
  
    public GestureLockViewGroup(Context context, AttributeSet attrs,  
            int defStyle)  
    {  
        super(context, attrs, defStyle);  
        /** 
         * ��������Զ���Ĳ����ֵ 
         */  
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,  
                R.styleable.GestureLockViewGroup, defStyle, 0);  
        int n = a.getIndexCount();  
  
        for (int i = 0; i < n; i++)  
        {  
            int attr = a.getIndex(i);  
            switch (attr)  
            {  
            case R.styleable.GestureLockViewGroup_color_no_finger_inner_circle:  
                mNoFingerInnerCircleColor = a.getColor(attr,  
                        mNoFingerInnerCircleColor);  
                break;  
            case R.styleable.GestureLockViewGroup_color_no_finger_outer_circle:  
                mNoFingerOuterCircleColor = a.getColor(attr,  
                        mNoFingerOuterCircleColor);  
                break;  
            case R.styleable.GestureLockViewGroup_color_finger_on:  
                mFingerOnColor = a.getColor(attr, mFingerOnColor);  
                break;  
            case R.styleable.GestureLockViewGroup_color_finger_up:  
                mFingerUpColor = a.getColor(attr, mFingerUpColor);  
                break;  
            case R.styleable.GestureLockViewGroup_count:  
                mCount = a.getInt(attr, 3);  
                break;  
            case R.styleable.GestureLockViewGroup_tryTimes:  
                mTryTimes = a.getInt(attr, 5);  
            default:  
                break;  
            }  
        }  
  
        a.recycle();  
  
        // ��ʼ������  
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mPaint.setStyle(Paint.Style.STROKE);  
        // mPaint.setStrokeWidth(20);  
        mPaint.setStrokeCap(Paint.Cap.ROUND);  
        mPaint.setStrokeJoin(Paint.Join.ROUND);  
        // mPaint.setColor(Color.parseColor("#aaffffff"));  
        mPath = new Path();  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
  
        mWidth = MeasureSpec.getSize(widthMeasureSpec);  
        mHeight = MeasureSpec.getSize(heightMeasureSpec);  
  
        // Log.e(TAG, mWidth + "");  
        // Log.e(TAG, mHeight + "");  
  
        mHeight = mWidth = mWidth < mHeight ? mWidth : mHeight;  
  
        // setMeasuredDimension(mWidth, mHeight);  
  
        // ��ʼ��mGestureLockViews  
        if (mGestureLockViews == null)  
        {  
            mGestureLockViews = new GestureLockView[mCount * mCount];  
            // ����ÿ��GestureLockView�Ŀ��  
            mGestureLockViewWidth = (int) (4 * mWidth * 1.0f / (5 * mCount + 1));  
            //����ÿ��GestureLockView�ļ��  
            mMarginBetweenLockView = (int) (mGestureLockViewWidth * 0.25);  
            // ���û��ʵĿ��ΪGestureLockView����Բֱ����΢С�㣨��ϲ���Ļ�������裩  
            mPaint.setStrokeWidth(mGestureLockViewWidth * 0.29f);  
  
            for (int i = 0; i < mGestureLockViews.length; i++)  
            {  
                //��ʼ��ÿ��GestureLockView  
                mGestureLockViews[i] = new GestureLockView(getContext(),  
                        mNoFingerInnerCircleColor, mNoFingerOuterCircleColor,  
                        mFingerOnColor, mFingerUpColor);  
                mGestureLockViews[i].setId(i + 1);  
                //���ò�����Ҫ�Ƕ�λGestureLockView���λ��  
                RelativeLayout.LayoutParams lockerParams = new RelativeLayout.LayoutParams(  
                        mGestureLockViewWidth, mGestureLockViewWidth);  
  
                // ����ÿ�еĵ�һ����������λ��Ϊǰһ�����ұ�  
                if (i % mCount != 0)  
                {  
                    lockerParams.addRule(RelativeLayout.RIGHT_OF,  
                            mGestureLockViews[i - 1].getId());  
                }  
                // �ӵڶ��п�ʼ������Ϊ��һ��ͬһλ��View������  
                if (i > mCount - 1)  
                {  
                    lockerParams.addRule(RelativeLayout.BELOW,  
                            mGestureLockViews[i - mCount].getId());  
                }  
                //�����������ϵı߾�  
                int rightMargin = mMarginBetweenLockView;  
                int bottomMargin = mMarginBetweenLockView;  
                int leftMagin = 0;  
                int topMargin = 0;  
                /** 
                 * ÿ��View��������߾�͵���߾� ��һ�е�������߾� ��һ�е�������߾� 
                 */  
                if (i >= 0 && i < mCount)// ��һ��  
                {  
                    topMargin = mMarginBetweenLockView;  
                }  
                if (i % mCount == 0)// ��һ��  
                {  
                    leftMagin = mMarginBetweenLockView;  
                }  
  
                lockerParams.setMargins(leftMagin, topMargin, rightMargin,  
                        bottomMargin);  
                mGestureLockViews[i].setMode(Mode.STATUS_NO_FINGER);  
                addView(mGestureLockViews[i], lockerParams);  
            }  
  
            Log.e(TAG, "mWidth = " + mWidth + " ,  mGestureViewWidth = "  
                    + mGestureLockViewWidth + " , mMarginBetweenLockView = "  
                    + mMarginBetweenLockView);  
  
        }  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event)  
    {  
        int action = event.getAction();  
        int x = (int) event.getX();  
        int y = (int) event.getY();  
  
        switch (action)  
        {  
        case MotionEvent.ACTION_DOWN:  
            // ����  
            reset();  
            break;  
        case MotionEvent.ACTION_MOVE:  
            mPaint.setColor(mFingerOnColor);  
            mPaint.setAlpha(50);  
            GestureLockView child = getChildIdByPos(x, y);  
            if (child != null)  
            {  
                int cId = child.getId();  
                if (!mChoose.contains(cId))  
                {  
                    mChoose.add(cId);  
                    child.setMode(Mode.STATUS_FINGER_ON);  
                    if (mOnGestureLockViewListener != null)  
                        mOnGestureLockViewListener.onBlockSelected(cId);  
                    // ����ָ���ߵ����  
                    mLastPathX = child.getLeft() / 2 + child.getRight() / 2;  
                    mLastPathY = child.getTop() / 2 + child.getBottom() / 2;  
  
                    if (mChoose.size() == 1)// ��ǰ���Ϊ��һ��  
                    {  
                        mPath.moveTo(mLastPathX, mLastPathY);  
                    } else  
                    // �ǵ�һ����������ʹ��������  
                    {  
                        mPath.lineTo(mLastPathX, mLastPathY);  
                    }  
  
                }  
            }  
            // ָ���ߵ��յ�  
            mTmpTarget.x = x;  
            mTmpTarget.y = y;  
            break;  
        case MotionEvent.ACTION_UP:  
  
            mPaint.setColor(mFingerUpColor);  
            mPaint.setAlpha(50);  
            this.mTryTimes--;  
  
            // �ص��Ƿ�ɹ�  
            if (mOnGestureLockViewListener != null && mChoose.size() > 0)  
            {  
                mOnGestureLockViewListener.onGestureEvent(checkAnswer());  
                if (this.mTryTimes == 0)  
                {  
                    mOnGestureLockViewListener.onUnmatchedExceedBoundary();  
                }  
            }  
  
            Log.e(TAG, "mUnMatchExceedBoundary = " + mTryTimes);  
            Log.e(TAG, "mChoose = " + mChoose);  
            // ���յ�����λ��Ϊ��㣬��ȡ��ָ����  
            mTmpTarget.x = mLastPathX;  
            mTmpTarget.y = mLastPathY;  
  
            // �ı���Ԫ�ص�״̬ΪUP  
            changeItemMode();  
              
            // ����ÿ��Ԫ���м�ͷ��Ҫ��ת�ĽǶ�  
            for (int i = 0; i + 1 < mChoose.size(); i++)  
            {  
                int childId = mChoose.get(i);  
                int nextChildId = mChoose.get(i + 1);  
  
                GestureLockView startChild = (GestureLockView) findViewById(childId);  
                GestureLockView nextChild = (GestureLockView) findViewById(nextChildId);  
  
                int dx = nextChild.getLeft() - startChild.getLeft();  
                int dy = nextChild.getTop() - startChild.getTop();  
                // ����Ƕ�  
                int angle = (int) Math.toDegrees(Math.atan2(dy, dx)) + 90;  
                startChild.setArrowDegree(angle);  
            }  
            break;  
  
        }  
        invalidate();  
        return true;  
    }  
  
    private void changeItemMode()  
    {  
        for (GestureLockView gestureLockView : mGestureLockViews)  
        {  
            if (mChoose.contains(gestureLockView.getId()))  
            {  
                gestureLockView.setMode(Mode.STATUS_FINGER_UP);  
            }  
        }  
    }  
  
    /** 
     *  
     * ��һЩ��Ҫ������ 
     */  
    private void reset()  
    {  
        mChoose.clear();  
        mPath.reset();  
        for (GestureLockView gestureLockView : mGestureLockViews)  
        {  
            gestureLockView.setMode(Mode.STATUS_NO_FINGER);  
            gestureLockView.setArrowDegree(-1);  
        }  
    }  
    /** 
     * ����û����Ƶ������Ƿ���ȷ 
     * @return 
     */  
    private boolean checkAnswer()  
    {  
        if (mAnswer.length != mChoose.size())  
            return false;  
  
        for (int i = 0; i < mAnswer.length; i++)  
        {  
            if (mAnswer[i] != mChoose.get(i))  
                return false;  
        }  
  
        return true;  
    }  
      
    /** 
     * ��鵱ǰ����Ƿ���child�� 
     * @param child 
     * @param x 
     * @param y 
     * @return 
     */  
    private boolean checkPositionInChild(View child, int x, int y)  
    {  
  
        //�������ڱ߾࣬��x,y����������GestureLockView���ڲ��м��С�����У�����ͨ�����paddingʹ��x,y���뷶Χ����󣬻��߲�����padding  
        int padding = (int) (mGestureLockViewWidth * 0.15);  
  
        if (x >= child.getLeft() + padding && x <= child.getRight() - padding  
                && y >= child.getTop() + padding  
                && y <= child.getBottom() - padding)  
        {  
            return true;  
        }  
        return false;  
    }  
  
    /** 
     * ͨ��x,y��������GestureLockView 
     * @param x 
     * @param y 
     * @return 
     */  
    private GestureLockView getChildIdByPos(int x, int y)  
    {  
        for (GestureLockView gestureLockView : mGestureLockViews)  
        {  
            if (checkPositionInChild(gestureLockView, x, y))  
            {  
                return gestureLockView;  
            }  
        }  
  
        return null;  
  
    }  
  
    /** 
     * ���ûص��ӿ� 
     *  
     * @param listener 
     */  
    public void setOnGestureLockViewListener(OnGestureLockViewListener listener)  
    {  
        this.mOnGestureLockViewListener = listener;  
    }  
  
    /** 
     * ���⹫�����ô𰸵ķ��� 
     *  
     * @param answer 
     */  
    public void setAnswer(int[] answer)  
    {  
        this.mAnswer = answer;  
    }  
  
    /** 
     * �������ʵ����� 
     *  
     * @param boundary 
     */  
    public void setUnMatchExceedBoundary(int boundary)  
    {  
        this.mTryTimes = boundary;  
    }  
  
    @Override  
    public void dispatchDraw(Canvas canvas)  
    {  
        super.dispatchDraw(canvas);  
        //����GestureLockView�������  
        if (mPath != null)  
        {  
            canvas.drawPath(mPath, mPaint);  
        }  
        //����ָ����  
        if (mChoose.size() > 0)  
        {  
            if (mLastPathX != 0 && mLastPathY != 0)  {
                canvas.drawLine(mLastPathX, mLastPathY, mTmpTarget.x,  
                        mTmpTarget.y, mPaint);  
            }
        }  
  
    }  
  
    public interface OnGestureLockViewListener  
    {  
        /** 
         * ����ѡ��Ԫ�ص�Id 
         *  
         * @param position 
         */  
        public void onBlockSelected(int cId);  
  
        /** 
         * �Ƿ�ƥ�� 
         *  
         * @param matched 
         */  
        public void onGestureEvent(boolean matched);  
  
        /** 
         * �����Դ��� 
         */  
        public void onUnmatchedExceedBoundary();  
    }  
}  
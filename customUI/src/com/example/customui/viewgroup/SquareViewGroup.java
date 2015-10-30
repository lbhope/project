package com.example.customui.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class SquareViewGroup extends ViewGroup {
	
	
	
	public SquareViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SquareViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		try {
			/**
			 * ��ô�ViewGroup�ϼ�����Ϊ���Ƽ��Ŀ�͸ߣ��Լ�����ģʽ
			 */
			int widthMode = MeasureSpec.getMode(widthMeasureSpec);
			int heightMode = MeasureSpec.getMode(heightMeasureSpec);
			int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
			int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


			// ��������е�childView�Ŀ�͸�
			measureChildren(widthMeasureSpec, heightMeasureSpec);
			//���SquareViewGroup�Ŀ�����Բ���wrap_contentʱ���Ͳ��ø����ּ����ģʽ�ʹ�С
			if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
				setMeasuredDimension(sizeWidth, sizeHeight);
				return;
			}
 
			
			/**
			 * ��¼�����wrap_contentʱ���õĿ�͸�
			 */
			int width = 0;
			int height = 0;

			int cCount = getChildCount();

			int childWidth = 0;
			int childHeight = 0;
			MarginLayoutParams cParams = null;

			// ���ڼ����������childView�ĸ߶�
			int lHeight = 0;
			// ���ڼ����ұ�����childView�ĸ߶ȣ����ո߶�ȡ����֮���ֵ
			int rHeight = 0;

			// ���ڼ����ϱ�����childView�Ŀ��
			int tWidth = 0;
			// ���ڼ�����������childiew�Ŀ�ȣ����տ��ȡ����֮���ֵ
			int bWidth = 0;
			 
			/**
			 * ����childView����ĳ��Ŀ�͸ߣ��Լ����õ�margin���������Ŀ�͸ߣ���Ҫ����������warp_contentʱ
			 */
			for (int i = 0; i < cCount; i++) {
				View childView = getChildAt(i);
				if (childView == null) {
					return;
				}

				childWidth = childView.getMeasuredWidth();
				childHeight = childView.getMeasuredHeight();
				cParams = (MarginLayoutParams) childView.getLayoutParams();

				// ��������childView
				if (i == 0 || i == 1) {
					tWidth += childWidth + cParams.leftMargin
							+ cParams.rightMargin;
				}

				if (i == 2 || i == 3) {
					bWidth += childWidth + cParams.leftMargin
							+ cParams.rightMargin;
				}

				if (i == 0 || i == 2) {
					lHeight += childHeight + cParams.topMargin
							+ cParams.bottomMargin;
				}

				if (i == 1 || i == 3) {
					rHeight += childHeight + cParams.topMargin
							+ cParams.bottomMargin;
				}

			}

			width = Math.max(tWidth, bWidth);
			height = Math.max(lHeight, rHeight);

			width += (getPaddingLeft() + getPaddingRight());
			height += (getPaddingTop() + getPaddingBottom());
			/**
			 * ���SquareViewGroup��wrap_contentʱ����Ϊ���Ǽ����ֵ ,����ֱ������Ϊ�����������ֵ
			 * EXACTLY:
			 * �洢�����õ��Ŀ�Ⱥ͸߶�ֵ�����û����ôȥ���ᴥ���쳣IllegalStateException
			 */
			setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
					: width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
					: height);
		 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		try {
			int cCount = getChildCount();
			int cWidth = 0;
			int cHeight = 0;
			MarginLayoutParams cParams = null;
			int pl = 0, pt = 0, pr = 0, pb = 0;
			 pl = getPaddingLeft();
			 pt = getPaddingTop();
			 pr = getPaddingRight();
			 pb = getPaddingBottom();
			/**
			 * ��������childView�������͸ߣ��Լ�margin���в���
			 */
			
			for (int i = 0; i < cCount; i++) {
				View childView = getChildAt(i);
				cWidth = childView.getMeasuredWidth();
				cHeight = childView.getMeasuredHeight();
				cParams = (MarginLayoutParams) childView.getLayoutParams();
				 
				// ��������
				int cl = 0, ct = 0, cr = 0, cb = 0;
	 
				switch (i) {
				case 0:
					cl = cParams.leftMargin+pl;
					ct = cParams.topMargin+pt;
					break;
				case 1:
					cl = getWidth() - cWidth - cParams.leftMargin
							- cParams.rightMargin-pr;
					ct = cParams.topMargin+pt;
					break;
				case 2:
					cl = cParams.leftMargin+pl;
					ct = getHeight() - cHeight - cParams.bottomMargin-pb;
					break;
				case 3:
					cl = getWidth() - cWidth - cParams.leftMargin
							- cParams.rightMargin-pr;
					ct = getHeight() - cHeight - cParams.bottomMargin-pb;
					break;

				}
				cr = cl + cWidth;
				cb = cHeight + ct;
				childView.layout(cl, ct, cr, cb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {

		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	// ViewGroup�ܹ�֧��margin
	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new MarginLayoutParams(p);
	}
	
	

}

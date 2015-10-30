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
			 * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
			 */
			int widthMode = MeasureSpec.getMode(widthMeasureSpec);
			int heightMode = MeasureSpec.getMode(heightMeasureSpec);
			int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
			int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


			// 计算出所有的childView的宽和高
			measureChildren(widthMeasureSpec, heightMeasureSpec);
			//如果SquareViewGroup的宽高属性不是wrap_content时，就采用父布局计算的模式和大小
			if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
				setMeasuredDimension(sizeWidth, sizeHeight);
				return;
			}
 
			
			/**
			 * 记录如果是wrap_content时设置的宽和高
			 */
			int width = 0;
			int height = 0;

			int cCount = getChildCount();

			int childWidth = 0;
			int childHeight = 0;
			MarginLayoutParams cParams = null;

			// 用于计算左边两个childView的高度
			int lHeight = 0;
			// 用于计算右边两个childView的高度，最终高度取二者之间大值
			int rHeight = 0;

			// 用于计算上边两个childView的宽度
			int tWidth = 0;
			// 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
			int bWidth = 0;
			 
			/**
			 * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
			 */
			for (int i = 0; i < cCount; i++) {
				View childView = getChildAt(i);
				if (childView == null) {
					return;
				}

				childWidth = childView.getMeasuredWidth();
				childHeight = childView.getMeasuredHeight();
				cParams = (MarginLayoutParams) childView.getLayoutParams();

				// 上面两个childView
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
			 * 如果SquareViewGroup是wrap_content时设置为我们计算的值 ,否则：直接设置为父容器计算的值
			 * EXACTLY:
			 * 存储测量得到的宽度和高度值，如果没有这么去做会触发异常IllegalStateException
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
			 * 遍历所有childView根据其宽和高，以及margin进行布局
			 */
			
			for (int i = 0; i < cCount; i++) {
				View childView = getChildAt(i);
				cWidth = childView.getMeasuredWidth();
				cHeight = childView.getMeasuredHeight();
				cParams = (MarginLayoutParams) childView.getLayoutParams();
				 
				// 左上右下
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

	// ViewGroup能够支持margin
	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new MarginLayoutParams(p);
	}
	
	

}

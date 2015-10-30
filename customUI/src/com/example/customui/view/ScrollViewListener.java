package com.example.customui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/**
 * Android 仿美团网,大众点评购买框悬浮效果之修改版
 * http://blog.csdn.net/xiaanming/article/details/17761431
 * @author libin
 *
 */
public class ScrollViewListener extends ScrollView {
	private OnScrollListener onScrollListener;

	public ScrollViewListener(Context context) {
		this(context, null);
	}

	public ScrollViewListener(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollViewListener(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 设置滚动接口
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	@Override
	public int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (onScrollListener != null) {
			onScrollListener.onScroll(t);
		}
	}

	/**
	 * 
	 * 滚动的回调接口
	 * 
	 */
	public interface OnScrollListener {
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 * 
		 * @param scrollY
		 *            、
		 */
		public void onScroll(int scrollY);
	}

}

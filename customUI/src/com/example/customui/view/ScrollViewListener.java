package com.example.customui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/**
 * Android ��������,���ڵ������������Ч��֮�޸İ�
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
	 * ���ù����ӿ�
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
	 * �����Ļص��ӿ�
	 * 
	 */
	public interface OnScrollListener {
		/**
		 * �ص������� ����MyScrollView������Y�������
		 * 
		 * @param scrollY
		 *            ��
		 */
		public void onScroll(int scrollY);
	}

}

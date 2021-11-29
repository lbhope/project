package com.zero.materialdesign.base;

import android.view.View;
import android.view.ViewGroup;


public abstract class BaseTypeAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {


    public static final int TYPE_COMMOM = 1;

    public BaseTypeAdapter() {
        super(-1);
    }

    /**
     * 获取类型
     */
    protected int getType(int position, T data) {
        return TYPE_COMMOM;
    }

    /**
     * 创建View
     */
    protected abstract View createView(int type, ViewGroup parent);

    /**
     * 绑定数据
     */
    protected abstract void bindData(BaseViewHolder holder, T data, int position, int type);

    @Override
    protected int getDefItemViewType(int position) {
        return getType(position, getItem(position));
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(createView(viewType, parent));
    }

    @Override
    protected final void convert(BaseViewHolder helper, T item) {
        int position = helper.getLayoutPosition() - getHeaderLayoutCount();
        int type = getType(position, item);
        bindData(helper, item, position, type);
    }
}

package com.zero.materialdesign.base.loadmore;


import com.zero.materialdesign.R;


public class CustomLoaderView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.load_more_loading_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_loading_view;
    }

    public void setEndText(String endText){
        mEndText = endText;
    }
}
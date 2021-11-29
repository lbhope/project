package com.zero.materialdesign.base.loadmore;



import com.zero.materialdesign.R;
import com.zero.materialdesign.base.BaseViewHolder;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;


/**
 * Created by BlingBling on 2016/11/11.
 */

public abstract class LoadMoreView {

    protected String mEndText = "没有更多数据";

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean mLoadMoreEndGone = false;

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert(BaseViewHolder holder) {
        switch (getLoadMoreStatus()) {
            case STATUS_LOADING:
                showLoading(holder, true);
                showFail(holder, false);
                showEnd(holder, false);
                break;
            case STATUS_FAIL:
                showLoading(holder, false);
                showFail(holder, true);
                showEnd(holder, false);
                break;
            case STATUS_END:
                showLoading(holder, false);
                showFail(holder, false);
                showEnd(holder, true);
                break;
            case STATUS_DEFAULT:
                showLoading(holder, false);
                showFail(holder, false);
                showEnd(holder, false);
                break;
        }
    }

    private void showLoading(BaseViewHolder holder, boolean visible) {
        holder.setVisible(getLoadingViewId(), visible);
    }

    private void showFail(BaseViewHolder holder, boolean visible) {
        holder.setVisible(getLoadFailViewId(), visible);
    }

    private void showEnd(BaseViewHolder holder, boolean visible) {
        holder.setText(R.id.end_text,mEndText);
        holder.setVisible(getLoadEndViewId(), visible);
    }
    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone(){
        if(getLoadEndViewId()==0){
            return true;
        }
        return mLoadMoreEndGone;}

    /**
     * No more data is hidden
     * @return true for no more data hidden load more
     */
    @Deprecated
    public boolean isLoadEndGone(){return mLoadMoreEndGone;}

    /**
     * load more layout
     *
     * @return
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * loading view
     *
     * @return
     */
    protected abstract @IdRes
    int getLoadingViewId();

    /**
     * load fail view
     *
     * @return
     */
    protected abstract @IdRes int getLoadFailViewId();

    /**
     * load end view, you can return 0
     *
     * @return
     */
    protected abstract @IdRes int getLoadEndViewId();
}

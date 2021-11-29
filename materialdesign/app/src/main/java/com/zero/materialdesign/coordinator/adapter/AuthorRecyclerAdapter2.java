package com.zero.materialdesign.coordinator.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zero.materialdesign.R;
import com.zero.materialdesign.base.BaseTypeAdapter;
import com.zero.materialdesign.base.BaseViewHolder;
import com.zero.materialdesign.coordinator.bean.AuthorInfo;

public class AuthorRecyclerAdapter2 extends BaseTypeAdapter<AuthorInfo> {

    public static final int TYPE_TITLE = -1;

    @Override
    protected int getType(int position, AuthorInfo data) {
        AuthorInfo actor = (AuthorInfo) data;
//        Log.e("tagurl", "actor:" + actor);
        if (!TextUtils.isEmpty(actor.getNickName())) {
            return TYPE_COMMOM;
        }
        return TYPE_TITLE;
    }

    @Override
    protected View createView(int type, ViewGroup parent) {
        if (type == TYPE_TITLE) {
            return mLayoutInflater.inflate(R.layout.layout_title_bar, parent, false);
        } else {
            return mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        }
    }

    @Override
    protected void bindData(BaseViewHolder holder, AuthorInfo item, int position, int type) {
        if (type == TYPE_TITLE) {
        } else {
            ((ImageView) (holder.getView(R.id.iv_portrait))).setImageResource(item.getPortrait());
            ((TextView) (holder.getView(R.id.tv_nickname))).setText(item.getNickName());
            ((TextView) (holder.getView(R.id.tv_motto))).setText(item.getMotto());
        }
    }
}

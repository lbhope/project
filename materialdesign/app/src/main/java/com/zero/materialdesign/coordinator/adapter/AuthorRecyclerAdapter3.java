package com.zero.materialdesign.coordinator.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.zero.materialdesign.R;
import com.zero.materialdesign.base.BaseQuickAdapter;
import com.zero.materialdesign.base.BaseTypeAdapter;
import com.zero.materialdesign.base.BaseViewHolder;
import com.zero.materialdesign.coordinator.bean.AuthorInfo;

import java.util.List;

import androidx.annotation.Nullable;

public class AuthorRecyclerAdapter3 extends BaseQuickAdapter<AuthorInfo, BaseViewHolder> {

    public AuthorRecyclerAdapter3(@Nullable List<AuthorInfo> data) {
        super(R.layout.item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, AuthorInfo item) {
        ((ImageView) (holder.getView(R.id.iv_portrait))).setImageResource(item.getPortrait());
        ((TextView) (holder.getView(R.id.tv_nickname))).setText(item.getNickName());
        ((TextView) (holder.getView(R.id.tv_motto))).setText(item.getMotto());
    }
}

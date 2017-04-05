package com.chhd.cniaoshops.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chhd.cniaoshops.R;
import com.chhd.cniaoshops.ui.StatusEnum;
import com.chhd.per_library.util.UiUtils;

import java.util.List;

/**
 * Created by CWQ on 2017/3/26.
 */

public abstract class SimpleAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    private StatusEnum status = StatusEnum.ON_NORMAL;

    public SimpleAdapter(List<T> data) {
        super(data);
        openLoadAnimation();
    }

    public SimpleAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
        openLoadAnimation();
    }

    public void setCustomEmptyView() {
        if (getEmptyView() == null) {
            View emptyView = View.inflate(mContext, R.layout.view_empty, null);
            super.setEmptyView(emptyView);
        }
    }

    /**
     * 设置包含头部的空布局
     *
     * @param parent
     */
    public void setCustomEmptyView(RecyclerView parent) {
        if (getEmptyView() == null && status != StatusEnum.ON_EMPTY) {
            status = StatusEnum.ON_EMPTY;
            int height = parent.getHeight() - getHeaderLayout().getHeight() - UiUtils.getStatusBarHeight();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            View emptyView = View.inflate(mContext, R.layout.view_empty, null);
            TextView tvNone = (TextView) emptyView.findViewById(R.id.tv_none);
            TextView tvError = (TextView) emptyView.findViewById(R.id.tv_error);
            tvNone.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            emptyView.setLayoutParams(params);
            setHeaderAndEmpty(true);
            setEmptyView(emptyView);
        }
    }

    /**
     * 设置包含头部的空布局
     *
     * @param parent
     */
    public void setCustomEmptyView(RecyclerView parent, final View.OnClickListener networkErrorClickListener) {
        if (getEmptyView() == null && status != StatusEnum.ON_ERROR) {
            status = StatusEnum.ON_ERROR;
            int height = parent.getHeight() - getHeaderLayout().getHeight() - UiUtils.getStatusBarHeight();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            View emptyView = View.inflate(mContext, R.layout.view_empty, null);
            TextView tvNone = (TextView) emptyView.findViewById(R.id.tv_none);
            TextView tvError = (TextView) emptyView.findViewById(R.id.tv_error);
            tvNone.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            emptyView.setLayoutParams(params);
            setHeaderAndEmpty(true);
            setEmptyView(emptyView);
            if (networkErrorClickListener != null) {
                emptyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        networkErrorClickListener.onClick(v);
                    }
                });
            }
        }
    }
}

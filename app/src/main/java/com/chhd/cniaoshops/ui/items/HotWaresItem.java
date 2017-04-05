package com.chhd.cniaoshops.ui.items;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chhd.cniaoshops.R;
import com.chhd.cniaoshops.bean.ShoppingCart;
import com.chhd.cniaoshops.bean.Wares;
import com.chhd.cniaoshops.biz.CartBiz;
import com.chhd.cniaoshops.util.LoggerUtils;
import com.chhd.cniaoshops.util.ToastyUtils;
import com.chhd.per_library.util.UiUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by CWQ on 2017/3/15.
 */

public class HotWaresItem extends AbstractFlexibleItem<HotWaresItem.Holder> implements View.OnClickListener {

    private Wares wares;

    public HotWaresItem(Wares wares) {
        this.wares = wares;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.list_item_hot_wares;
    }

    @Override
    public Holder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new Holder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }


    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Holder holder, int position, List payloads) {
        holder.ivPic.setImageURI(Uri.parse(wares.getImgUrl()));
        holder.tvTitle.setText(wares.getName());
        holder.tvPrice.setText("" + wares.getPrice());
        holder.btnBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new CartBiz().put(convertData(wares));
        ToastyUtils.success(UiUtils.getString(R.string.add_shopping_cart_success));
    }

    class Holder extends FlexibleViewHolder {

        @BindView(R.id.iv_pic)
        SimpleDraweeView ivPic;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_buy)
        Button btnBuy;

        public Holder(View view, FlexibleAdapter adapter) {
            super(view, adapter);

            ButterKnife.bind(this, view);

        }

    }

    private ShoppingCart convertData(Wares wares) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(wares.getId());
        cart.setDescription(wares.getDescription());
        cart.setImgUrl(wares.getImgUrl());
        cart.setName(wares.getName());
        cart.setPrice(wares.getPrice());
        return cart;
    }
}

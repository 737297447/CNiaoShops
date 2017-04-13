package com.chhd.cniaoshops.ui.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chhd.cniaoshops.R;
import com.chhd.cniaoshops.bean.Menu;
import com.chhd.cniaoshops.ui.base.fragment.BaseFragment;
import com.chhd.cniaoshops.ui.decoration.SpaceItemDecoration;
import com.chhd.per_library.ui.base.SimpleHolder;
import com.chhd.per_library.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by CWQ on 2016/10/24.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_mine;
    }

    private List<Menu> menus = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] titles = UiUtils.getStringArray(R.array.menu_mine_title);
        int[] icons = getIcons();
        for (int i = 0; i < titles.length; i++) {
            menus.add(new Menu(icons[i], titles[i]));
        }

        recyclerView.setAdapter(new MineAdapter(menus));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(UiUtils.dp2px(0.5f)));
    }

    private int[] getIcons() {
        TypedArray ar = getResources().obtainTypedArray(R.array.menu_mine_icon);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);

        ar.recycle();
        return resIds;
    }

    class MineAdapter extends BaseQuickAdapter<Menu, BaseViewHolder> {

        public MineAdapter(List<Menu> data) {
            super(R.layout.list_item_menu, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Menu item) {
            Holder holder = new Holder(helper.itemView);
            holder.ivIcon.setImageResource(item.getIcon());
            holder.tvTitle.setText(item.getTitle());
        }

        class Holder extends SimpleHolder {

            @BindView(R.id.iv_icon)
            ImageView ivIcon;
            @BindView(R.id.tv_title)
            TextView tvTitle;

            public Holder(View itemView) {
                super(itemView);
            }
        }
    }
}

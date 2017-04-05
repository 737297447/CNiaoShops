package com.chhd.cniaoshops.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chhd.cniaoshops.global.Constant;

import butterknife.ButterKnife;

/**
 * Created by CWQ on 2017/3/21.
 */

public abstract class BaseFragment extends Fragment implements Constant {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResID(), container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    public abstract int getLayoutResID();

}

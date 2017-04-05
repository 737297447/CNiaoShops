package com.chhd.cniaoshops.ui.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chhd.cniaoshops.global.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements Constant {

    private List<Activity> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        ButterKnife.bind(this);

        activities.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        activities.remove(this);
    }

    public abstract int getLayoutResID();
}

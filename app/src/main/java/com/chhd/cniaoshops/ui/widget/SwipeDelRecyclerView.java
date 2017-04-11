package com.chhd.cniaoshops.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by CWQ on 2017/4/6.
 */

public class SwipeDelRecyclerView extends RecyclerView {

    public SwipeDelRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeDelRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeDelRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
            if (null != viewCache) {
                viewCache.smoothClose();
            }
        }
        return super.onTouchEvent(e);
    }
}

package com.chhd.per_library.util;

import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;

    private ToastUtil() {
    }

    public static void makeText(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(UiUtil.getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    public static void makeText(int resId) {
        if (toast == null) {
            toast = Toast.makeText(UiUtil.getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }
}

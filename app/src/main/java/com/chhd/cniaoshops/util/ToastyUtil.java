package com.chhd.cniaoshops.util;

import android.widget.Toast;

import com.chhd.per_library.util.UiUtil;

import es.dmoral.toasty.Toasty;

/**
 * Created by CWQ on 2017/3/21.
 */

public class ToastyUtil {

    private ToastyUtil() {
    }

    public static void success(CharSequence message) {
        Toasty.success(UiUtil.getContext(), message, Toast.LENGTH_LONG, true).show();
    }

    public static void success(int resId) {
        Toasty.success(UiUtil.getContext(), UiUtil.getString(resId), Toast.LENGTH_LONG, true).show();
    }

    public static void warning(CharSequence message) {
        Toasty.warning(UiUtil.getContext(), message, Toast.LENGTH_LONG, true).show();
    }

    public static void warning(int resId) {
        Toasty.warning(UiUtil.getContext(), UiUtil.getString(resId), Toast.LENGTH_LONG, true).show();
    }

    public static void error(CharSequence message) {
        Toasty.error(UiUtil.getContext(), message, Toast.LENGTH_LONG, true).show();
    }

    public static void error(CharSequence message, int duration) {
        Toasty.error(UiUtil.getContext(), message, duration, true).show();
    }

    public static void error(int resId) {
        Toasty.error(UiUtil.getContext(), UiUtil.getString(resId), Toast.LENGTH_LONG, true).show();
    }

}

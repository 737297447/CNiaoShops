package com.chhd.cniaoshops.http.bmob;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chhd.cniaoshops.global.Constant;
import com.chhd.cniaoshops.util.DialogUtil;
import com.chhd.cniaoshops.util.LoggerUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by CWQ on 2017/4/19.
 */

public abstract class SimpleUploadListener extends UploadFileListener implements Constant {

    private int delayMillis = DELAYMILLIS_FOR_RQUEST_FINISH;
    private long startTimeMillis;
    private Context progressDialog;
    private MaterialDialog dialog;

    public SimpleUploadListener(Context progressDialog) {
        this.progressDialog = progressDialog;
        onBefore();
    }

    public final void onBefore() {
        startTimeMillis = System.currentTimeMillis();
        if (progressDialog != null && progressDialog instanceof Activity) {
            dialog = DialogUtil.newProgressDialog(progressDialog);
            dialog.show();
        }
        before();
    }

    @Override
    public void done(final BmobException e) {
        long timeDif = getTimeDif();
        if (timeDif > delayMillis) {
            if (e == null) {
                success();
            } else {
                LoggerUtil.e(e);
                error(e);
            }
            onAfter();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (e == null) {
                        success();
                    } else {
                        LoggerUtil.e(e);
                        error(e);
                    }
                    onAfter();
                }
            }, delayMillis - timeDif);
        }
    }

    public final void onAfter() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        after();
    }

    private long getTimeDif() {
        return System.currentTimeMillis() - startTimeMillis;
    }

    protected void before() {

    }

    public abstract void success();

    protected void error(BmobException e) {
        BmobEx.handlerError(e);
}

    protected void after() {

    }
}

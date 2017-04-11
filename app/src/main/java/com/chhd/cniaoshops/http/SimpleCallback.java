package com.chhd.cniaoshops.http;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chhd.cniaoshops.R;
import com.chhd.cniaoshops.global.Config;
import com.chhd.cniaoshops.global.Constant;
import com.chhd.cniaoshops.util.DialogUtils;
import com.chhd.cniaoshops.util.LoggerUtils;
import com.chhd.cniaoshops.util.ToastyUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by CWQ on 2017/3/26.
 */

public abstract class SimpleCallback extends StringCallback implements Constant {

    private boolean isToastError = true;
    private int delayMillis = DELAYMILLIS_FOR_RQUEST_FINISH;
    private long startTimeMillis;
    private Context progressDialog;
    private MaterialDialog dialog;
    private HttpParams params;
    private String url;

    public SimpleCallback() {
    }

    public SimpleCallback(boolean isToastError) {
        this.isToastError = isToastError;
    }

    public SimpleCallback(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    public SimpleCallback(Context progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public final void onBefore(BaseRequest request) {
        super.onBefore(request);
        startTimeMillis = System.currentTimeMillis();
        if (progressDialog != null && progressDialog instanceof Activity) {
            dialog = DialogUtils.newProgressDialog(progressDialog);
            dialog.show();
        }
        url = request.getUrl();
        params = request.getParams();
        before(request);
    }

    @Override
    public final void onSuccess(final String s, final Call call, final Response response) {
        d(url, params, s);
        long timeDif = getTimeDif();
        if (timeDif > delayMillis) {
            success(s, call, response);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    success(s, call, response);
                }
            }, delayMillis - timeDif);
        }
    }

    @Override
    public final void onError(final Call call, final Response response, final Exception e) {
        super.onError(call, response, e);
        e(url, params, response, e);
        long timeDif = getTimeDif();
        if (timeDif > delayMillis) {
            error(call, response, e);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    error(call, response, e);
                }
            }, delayMillis - timeDif);
        }
    }

    @Override
    public final void onAfter(final String s, final Exception e) {
        super.onAfter(s, e);
        long timeDif = getTimeDif();
        if (timeDif > delayMillis) {
            after(s, e);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    after(s, e);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, delayMillis - timeDif);
        }
    }

    private long getTimeDif() {
        return System.currentTimeMillis() - startTimeMillis;
    }

    public void before(BaseRequest request) {

    }

    public abstract void success(String s, Call call, Response response);

    public void error(Call call, Response response, Exception e) {
        ToastyUtils.error(R.string.network_connect_fail);
    }

    public void after(String s, Exception e) {

    }

    /**
     * 打印OKGO请求成功信息
     *
     * @param url
     * @param params
     * @param json
     */
    private void d(String url, HttpParams params, String json) {
        if (Config.isDebug) {
            String paramsStr = params != null ? "params:\t" + formatParamsStr(params.toString()) + "\n\n" : "";
            String message =
                    "url:\t\t" + url
                            + "\n\n"
                            + paramsStr
                            + "json:\t" + json;
            Logger.d(message);
        }
    }


    /**
     * 打印OKGO请求失败信息
     *
     * @param url
     * @param params
     * @param throwable
     */
    private void e(String url, HttpParams params, Response response, Throwable throwable) {
        if (Config.isDebug) {
            String paramsStr = params != null ? "params:\t" + formatParamsStr(params.toString()) + "\n\n" : "";
            String responseCode = response != null ? "responseCode:\t" + response.code() + "\n\n" : "";
            String xml = response != null ? "xml:\t" + getXml(response) + "\n\n" : "";
            String message =
                    "url:\t\t" + url
                            + "\n\n"
                            + paramsStr
                            + responseCode
                            + xml
                            + ERROR;
            Logger.e(throwable, message);
        }
    }

    private String formatParamsStr(String params) {
        return params.replace("[", "").replace("]", "").replace("&", "\n" + "params:\t");
    }

    private String getXml(Response response) {
        try {
            return response.body().string();
        } catch (Exception e) {
            LoggerUtils.e(e);
        }
        return "";
    }

}

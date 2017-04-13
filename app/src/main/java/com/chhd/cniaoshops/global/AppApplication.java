package com.chhd.cniaoshops.global;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.chhd.cniaoshops.util.LoggerUtils;
import com.chhd.cniaoshops.util.ToastyUtils;
import com.chhd.per_library.global.BaseApplication;
import com.lzy.okgo.OkGo;
import com.squareup.leakcanary.LeakCanary;
import com.wanjian.cockroach.Cockroach;
import com.yanzhenjie.nohttp.NoHttp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * Created by CWQ on 2016/10/24.
 */
public class AppApplication extends BaseApplication implements Constant {

    public static boolean isHotRun = true;

    @Override
    public void onCreate() {
        super.onCreate();

        initHttpLib();

        com.orhanobut.logger.Logger.init(TAG).methodCount(3).methodOffset(1);

        if (Config.isDebug) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }

        Cockroach.install(exceptionHandler);

        ShareSDK.initSDK(this);
    }

    private void initHttpLib() {
        NoHttp.Config config = new NoHttp.Config()
                .setConnectTimeout(TIME_OUT)
                .setReadTimeout(TIME_OUT);
        NoHttp.initialize(this, config);

        if (Config.isDebug) {
            com.yanzhenjie.nohttp.Logger.setDebug(true);
            com.yanzhenjie.nohttp.Logger.setTag(TAG_NOHTTP);
        }

        OkGo
                .init(this);
        OkGo
                .getInstance()
                .setRetryCount(0)
                .setConnectTimeout(TIME_OUT)
                .setReadTimeOut(TIME_OUT)
                .setWriteTimeOut(TIME_OUT);

        if (Config.isDebug) {
            OkGo
                    .getInstance()
                    .debug(TAG_OKGO, Level.INFO, true);
        }


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG_OKHTTP_UTILS))
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private Cockroach.ExceptionHandler exceptionHandler = new Cockroach.ExceptionHandler() {

        // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

        @Override
        public void handlerException(final Thread thread, final Throwable throwable) {
            // 开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
            // 由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
            // 所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
            // new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                        if (Config.isDebug) {
                            LoggerUtils.e(throwable);
                            ToastyUtils.error("Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_LONG);
                        }
                    } catch (Throwable e) {

                    }
                }
            });
        }
    };
}

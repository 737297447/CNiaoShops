package com.chhd.cniaoshops.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chhd.cniaoshops.R;
import com.chhd.cniaoshops.bean.Wares;
import com.chhd.cniaoshops.biz.CartBiz;
import com.chhd.cniaoshops.ui.base.activity.BaseActivity;
import com.chhd.cniaoshops.ui.widget.ProgressView;
import com.chhd.cniaoshops.util.LoggerUtils;
import com.chhd.per_library.util.ToastUtils;

import butterknife.BindView;

public class WaresDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_view)
    ProgressView progressView;

    private String url = SERVER_URL + "wares/detail.html";
    private Wares wares;
    private WebAppInterface appInterface = new WebAppInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();

        wares = getIntent().getParcelableExtra("wares");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(appInterface, "appInterface");
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl(url);
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.wares_detail);
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            appInterface.showDetail(wares.getId());
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressView.setProgress(newProgress);
        }
    };

    @Override
    public int getLayoutResID() {
        return R.layout.activity_wares_detail;
    }

    private class WebAppInterface {

        @JavascriptInterface
        public void showDetail(long id) {
            webView.loadUrl(String.format("javascript:showDetail(%d)", id));
        }

        @JavascriptInterface
        public void addToCart(long id) {
            new CartBiz().put(wares);
        }

        @JavascriptInterface
        public void buy(long id) {
            ToastUtils.makeText(R.string.buy_now);
        }
    }
}

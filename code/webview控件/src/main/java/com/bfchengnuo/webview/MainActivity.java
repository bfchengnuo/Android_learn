package com.bfchengnuo.webview;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private final String url = "http://www.baidu.com";
    private ProgressDialog progd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        webView.loadUrl(url);
        /**
         * 优先使用缓存：
         * webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
         * 不用缓存：
         * webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
         */
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //WebViewClient帮助WebView去处理一些页面控制和请求通知
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //返回值为true就在本页面中打开网页不跳转
                return true;
            }
        });
        //设置支持js    负责管理一个web视图设置状态。
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置加载进度
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    //加载完成
                    webCompleted(newProgress);
                }else {
                    //加载中
                    webLoing(newProgress);
                }
            }
        });
    }

    private void webCompleted(int newProgress) {
        if (progd != null && progd.isShowing()){
            //关闭对话框
            progd.dismiss();
            progd = null;
        }
    }

    private void webLoing(int newProgress) {
        if (progd == null){
            progd = new ProgressDialog(MainActivity.this);
            progd.setTitle("加载中...");
            progd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progd.setProgress(newProgress);
            progd.show();
        }else {
            progd.setProgress(newProgress);
        }
    }

    //设置物理按键的响应
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();
                return true;
            }else {
                System.exit(0);

            }
        }
        return false;
    }
}

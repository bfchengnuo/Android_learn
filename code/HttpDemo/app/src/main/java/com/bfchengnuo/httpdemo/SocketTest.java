package com.bfchengnuo.httpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SocketTest extends Activity {

    private TextView mTvShow;
    private Button mBtnSend;
    private final String URL = "https://www.baidu.com";
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);

        mTvShow = (TextView) findViewById(R.id.tv_show);
        mBtnSend = (Button) findViewById(R.id.btn_send);

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new MyThread(URL,mHandler,mTvShow);
                thread.start();
            }
        });
    }


    private class MyThread extends Thread {

        private String mUrl;
        private Handler mHandler;
        private TextView mTextView;

        public MyThread(String url, Handler handler, TextView textView) {
            mUrl = url;
            mHandler = handler;
            mTextView = textView;
        }

        @Override
        public void run() {
            try {
                URL httpUrl = new URL(mUrl);

                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() != 200){
                    // TODO: 2017/3/4 0004
                    throw new RuntimeException("url获取失败");
                }

                // 创建一个集合存字符串，创建一个缓冲区读取字符流
                final StringBuilder sb = new StringBuilder();
                BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                while ((str = read.readLine()) != null){
                    sb.append(str);
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(sb.toString());
                        Log.d("lxc", "run: " + sb.toString());
                    }
                });

                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

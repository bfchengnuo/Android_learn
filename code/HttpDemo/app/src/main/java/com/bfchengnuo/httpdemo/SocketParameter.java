package com.bfchengnuo.httpdemo;

import android.os.Handler;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 冰封承諾Andy on 2017/3/4 0004.
 * 带参数的网络请求
 */

public class SocketParameter extends Thread {
    private String mUrl;
    private Handler mHandler;
    private TextView mTextView;

    public SocketPara(String url, Handler handler, TextView textView) {
        mUrl = url;
        mHandler = handler;
        mTextView = textView;
    }

    // 使用Get请求获取数据
    private void doGet(){
        try {
            // 如果提交中文，要进行转码操作
//            URLEncoder.encode("lalla","utf-8");
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);

            BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuilder sb = new StringBuilder();
            String str;
            while ((str = read.readLine()) != null){
                sb.append(str);
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(sb.toString());
                }
            });

            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 通过post进行请求
    private void doPost(){
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);

            OutputStream out = conn.getOutputStream();
            // 获得一个输出流，向服务器写数据
            String content = "key1=msn&key2=456";
            out.write(content.getBytes());
            out.flush();

            // 获取响应
            BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuilder sb = new StringBuilder();
            String str;
            while ((str = read.readLine()) != null){
                sb.append(str);
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(sb.toString());
                }
            });

            read.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doGet();
//        doPost();
    }

    // 对于非字符的处理，使用字节流，返回值待定
    private String isStream(){
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);

            InputStream in = conn.getInputStream();
            // 创建个字节流，将数据写入到这个流中
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            byte[] buffer = new byte[2*1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1){
                os.write(buffer,0,len);
            }

            in.close();
            String data = os.toString();  // 默认utf-8编码
            os.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

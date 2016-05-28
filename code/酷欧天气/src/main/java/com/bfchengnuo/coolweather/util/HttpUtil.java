package com.bfchengnuo.coolweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lvxue on 2016/5/21 0021.
 * 开启一个线程从服务器获取传入地址相关数据
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = bufr.readLine()) != null){
                        sb.append(line);
                    }
                    if (listener != null){
                        //回调onfinish方法
                        listener.onFinish(sb.toString());
                    }
                } catch (IOException e) {
                    if (listener != null){
                        //回调onerror方法
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}

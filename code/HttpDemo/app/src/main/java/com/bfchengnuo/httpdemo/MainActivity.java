package com.bfchengnuo.httpdemo;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetThread nt = new NetThread();
                nt.start();


            }
        });
    }
// 一定不要忘记获取网络访问的权限
    class NetThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            try {
                URL url = new URL("http://www.marschen.com/data1.html");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置连接超时
                //conn.setConnectTimeout(5000);
                //设置请求的类型
                conn.setRequestMethod("GET");
                //判断是否请求成功

                if (conn.getResponseCode() != 200){
                    throw new RuntimeException("请求url失败");
                }
                InputStream in = conn.getInputStream();
                BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
                String msg = bufr.readLine();
                Log.d("http","接收到的数据为"+msg);
               // Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                bufr.close();
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

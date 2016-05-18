package com.bfchengnuo.systemservertest;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doClick(View view) {
        switch (view.getId()){
            case R.id.checkIntent:
                if (isNetWorkConnected(MainActivity.this)){
                    Toast.makeText(MainActivity.this, "网络已经开启", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "网络未连接...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.checkWifi:
                WifiManager wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(MainActivity.this, "WIFI已经关闭", Toast.LENGTH_SHORT).show();
                }else {
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(MainActivity.this, "WIFI已经开启", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.volume:
                AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
                int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
                int current = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                Toast.makeText(MainActivity.this, "最大音量为："+max+"当前音量为："+current, Toast.LENGTH_SHORT).show();
                break;
            case R.id.time:
                Intent intent = new Intent(this,LongRunningService.class);
                startService(intent);
        }
    }

    private boolean isNetWorkConnected(Context context) {
        if (context != null){
            //获取一个网络链接服务
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null)
                return networkInfo.isAvailable();
        }
        return false;
    }
}

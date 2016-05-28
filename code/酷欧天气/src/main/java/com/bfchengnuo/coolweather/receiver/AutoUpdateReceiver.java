package com.bfchengnuo.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bfchengnuo.coolweather.service.AutoUpdateService;

/**
 * Created by lvxue on 2016/5/22 0022.
 * 每隔一段时间执行onReceive方法，onReceive方法再去启动服务实现定时更新
 * 广播接收者 当有广播来的时候会执行onReceive方法
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startActivity(i);
    }
}

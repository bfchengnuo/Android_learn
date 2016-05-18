package com.bfchengnuo.notificationtest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lvxue on 2016/5/11 0011.
 * 自定义的一个服务
 * 启动服务一定要在主文件中注册，四大组件的使用必须都要注册才能使用
 */
public class MyService extends Service{
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        Log.d("lxc","run");
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("测试前台服务");
        builder.setContentText("我是主内容区域");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置时间部分
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("lxc","Command--run");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}

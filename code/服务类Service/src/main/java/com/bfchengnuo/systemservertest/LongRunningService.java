package com.bfchengnuo.systemservertest;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

/**
 * Created by lvxue on 2016/5/12 0012.
 * 定义一个可以长期在后台执行定时任务的服务
 */
public class LongRunningService extends Service{
    //绑定服务的时候用来回传数据
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //每次启动都会回调此方法，如果没有创建过那么就先执行onCreate()方法
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("lxc","executed at "+ new Date().toString());
            }
        }).start();
        //使用Alarm机制控制后台定时服务
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 1000; //一分钟的毫秒值
        //系统运行至今所经历的毫秒值
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        //用来指定一个广播接收器
        Intent intent1 = new Intent(this,AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,intent1,0);
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);//第一个参数为工作的类型
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);//第一个参数为工作的类型

        return super.onStartCommand(intent, flags, startId);
    }
}

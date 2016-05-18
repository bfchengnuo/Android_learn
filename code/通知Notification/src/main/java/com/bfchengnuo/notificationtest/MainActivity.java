package com.bfchengnuo.notificationtest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button send1;
    private Button btn;
    private Intent bindIntent;
/*

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当与服务建立链接的时候调用

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //当服务链接失败的时候调用，例如服务被kill

        }
    };
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send1 = (Button) findViewById(R.id.send);
        btn  = (Button) findViewById(R.id.btn);
        send1.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                在最新的版本中官方建议用 Notification.Builder来创建 notification 实例
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("我是标题");
                builder.setContentText("我是主内容区域");
                builder.setContentInfo("我是补充内容部分");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setTicker("新消息");
                //设置时间部分
                builder.setWhen(System.currentTimeMillis());
                builder.setAutoCancel(true);  //触摸自动解除


                builder.setDefaults(Notification.DEFAULT_ALL); //设置默认震动铃声等


                //点击响应事件
                Intent intent = new Intent(this,Main2Activity.class);
                //第四个参数是指定pi的行为  如果已经存在就清理掉
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(pendingIntent); //绑定

                Notification notification = builder.build();
                manager.notify(1,notification);

                //已废弃
//                Notification notification = new Notification(R.mipmap.ic_launcher,"这是一条通知",System.currentTimeMillis());
                //给通知设置布局 显示内容
//                notification.setLatestEventInfo()
                break;
            case R.id.btn:
                bindIntent = new Intent(MainActivity.this,MyService.class);
//                bindService(bindIntent,null,BIND_AUTO_CREATE);
                startService(bindIntent);
                Log.d("lxc","onclick");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        stopService(bindIntent);
        super.onDestroy();
    }
}

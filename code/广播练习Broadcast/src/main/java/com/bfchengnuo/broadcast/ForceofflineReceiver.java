package com.bfchengnuo.broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by lvxue on 2016/5/2 0002.
 * 自定义广播接收器
 */
public class ForceofflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        //创建一个弹窗对象 对话框
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("警告");
        dialogBuilder.setMessage("您的账号异常，强制退出");
        //设置不能取消
        dialogBuilder.setCancelable(false);
        //设置弹窗按钮
        dialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();//销毁所有活动
                Intent intent1 = new Intent(context,MainActivity.class);
                //因为是在广播接收器里启动窗口的，所以要给intent设置一个标志
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        });

        //
        AlertDialog alertDialog = dialogBuilder.create();
        //设置alert的类型保证广播接收器可以正常的接收到
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}

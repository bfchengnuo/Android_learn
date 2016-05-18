package com.bfchengnuo.sms_test;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView from;
    private TextView content;

    private IntentFilter receiveFilter;  //起到过滤的作用？？
    private MessageReceiver messageReceiver; //短信接收者
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        from = (TextView) findViewById(R.id.from);
        content = (TextView) findViewById(R.id.content);
        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        receiveFilter.setPriority(1000);
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver,receiveFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一定别忘了卸载
        unregisterReceiver(messageReceiver);
    }

    public class MessageReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, "执行", Toast.LENGTH_SHORT).show();
            String format = intent.getStringExtra("format");
            Bundle bundle = intent.getExtras();
            //使用PDU密钥来提取一个SMS pdus数组  每一个pdu都代表一条短信
            //smsBundle.get("pdus")返回的实际类型是byte[][]，二位数组的每一个子数组就是一个pdu。
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0;i<messages.length;i++){
//                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i],format);
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            String address = messages[0].getOriginatingAddress(); //获取发送方的电话号码
            String text = "";
            for (SmsMessage message : messages){
                text += message.getMessageBody(); //获取短信内容
            }
            from.setText(address);
            content.setText(text);
        }
    }

}

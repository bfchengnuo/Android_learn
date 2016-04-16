package com.bfchengnuo.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Handler handler;
    private Handler handlerMain;
    private Handler handlerRunn = new Handler();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
//        button = (Button) findViewById(R.id.button);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new MyThread();
                thread.start();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UI线程向其他线程回传数据
                //一个handler对应一个looper对象，一个looper对象对应一个message，使用handler生成message
                //所生成的message对象的target就是这个handler对象
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        });


        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //消息队列中的消息被looper取出来以后先判断callback是否为空  如果不为空则执行run方法
                //如果为空才调用handlemessage方法
                TextThread tt = new TextThread();
                tt.start();
            }
        });


        WorkThread wk = new WorkThread();
        wk.start();
        handlerMain = new MyHandler();

    }

    class MyHandler extends Handler{
        //looper检测到有消息就会取出给handler处理，调用下面的方法
        @Override
        public void handleMessage(Message msg) {
//            打印一下线程的名字
            System.out.println("Handler---->"+Thread.currentThread().getName());
            String s = (String) msg.obj;
            textView.setText(s);
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            //打印下当前线程的名字
            System.out.println("MyThread---->"+Thread.currentThread().getName());
            //让线程睡眠两秒
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String s = "模拟数据";
            //获取消息队列，进行消息的添加
            Message msg = handlerMain.obtainMessage();
            //添加信息
            msg.obj = s;
            //发送消息
            handlerMain.sendMessage(msg);
        }
    }
//    ------------------------------------------------------

    class WorkThread extends Thread{
        @Override
        public void run() {
            //准备looper对象,创建了一个looper对象存在本线程中 以键值对的形式，键就是线程
            //ThreadLocal对象  静态，不管new多少个对象只存在一个
            //如果looper对象已经存在，则会报错，也就是一个线程不能创建两个对象
            Looper.prepare();
            //创建一个handler对象，构造函数就会获取当前线程的数据  获取到looper对象，其次再拿到消息队列
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
//                    System.out.println("WorkThread---->"+Thread.currentThread().getName());
                    Log.d("wark",Thread.currentThread().getName());
                    Toast.makeText(MainActivity.this,"收到回传的数据",Toast.LENGTH_SHORT).show();
                }
            };
            //不断的取出消息对象
            Looper.loop();

        }
    }

    class TextThread extends Thread{
        @Override
        public void run() {
            //runnable接口也是用来实现多线程的
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    //打印下当前的线程
                    Log.d("text",Thread.currentThread().getName());
                    Toast.makeText(MainActivity.this,"当前线程"+Thread.currentThread().getName(),Toast.LENGTH_SHORT).show();
                }
            };
            //实质是发送了一个message对象给消息队列，handler接收到后直接调用run方法而不开辟新的线程，相当于ISO中的代码块对象
            //其实是将r对象赋给了message的callback属性
            handlerRunn.post(r);
        }
    }

}

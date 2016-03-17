package bfchengnuo.moreactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button but ;
    @Override
    //OnCreate 声明周期的第一部
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but = (Button) findViewById(R.id.but);
        //设置按钮的监听事件
        but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //声明一个意图来启动窗体
        Intent intent = new Intent();
//        设置通过意图来打开窗体,
//        第一个是Context对象/类  它是activity的父类，第二个是要启动窗口的class文件
        intent.setClass(this,SecondActivity.class);
//        启动这个意图,把定义的意图传进去
        startActivity(intent);
        //以及activity的声明周期
    }
}

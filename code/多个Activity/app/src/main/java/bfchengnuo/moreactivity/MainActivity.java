package bfchengnuo.moreactivity;

import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        findViewById(R.id.but2).setOnClickListener(new TellOnclick());
        findViewById(R.id.but3).setOnClickListener(new DateOnclick());

    }

    @Override
    public void onClick(View v) {
        //安卓系统的内置动作  打开指定的网页
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }




/*
    @Override
    public void onClick(View v) {
        //写配置的xml文件中的名字 action
        Intent intent = new Intent("bfchengnuo.moreactivity.ACTION_START");
//        intent.addCategory("");
        startActivity(intent);
    }
    */

    /* 显式启动
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
    */

    class TellOnclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //安卓系统的内置动作  拨打电话
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        }
    }

    class DateOnclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String data = "这是传过来的数据";
            Intent intent =new Intent(MainActivity.this,SecondActivity.class);
            //通过putextra传送数据
            intent.putExtra("main",data);
            startActivity(intent);
        }
    }
//    ---------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //得到对象，创建菜单 第一个通过拿一个资源文件来创建，第二个添加到那个menu对象
        getMenuInflater().inflate(R.menu.main,menu);
//        (所处的组的名字/id，这项菜单的ID，排序的ID，内容)
        menu.add(0,3,2,"退出");
        //返回true表示运行显示出来
        return true;
    }
    //定义响应事件

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Toast.makeText(this,"你点击了add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove:
                Toast.makeText(this,"你点击了remove",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                finish();
                break;
        }
        return true;
    }
}

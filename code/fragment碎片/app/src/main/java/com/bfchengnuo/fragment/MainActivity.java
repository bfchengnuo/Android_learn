package com.bfchengnuo.fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //静态加载
        switch (checkedId) {
            case R.id.first: {
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
            }
            break;
            case R.id.second: {
                FragmentDongtai fd = new FragmentDongtai();  //new一个fragment对象
                //得到fragment的管理者
                FragmentManager fragmentManager = getFragmentManager();
                //开始一个事物
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragment事物添加一个fragment,
                //R.id.fragment为fragment显示的位置id,fragment2为需要添加的fragment;
                fragmentTransaction.add(R.id.line, fd);
                //增加回退效果，手机物理返回按键可以回退到上一个界面而不是直接退出
                fragmentTransaction.addToBackStack(null);
                //提交
                fragmentTransaction.commit();
            }
            break;
            case R.id.thrid:{
                Intent intent = new Intent(this,Main3Activity.class);
                startActivity(intent);
            }
        }
    }
}

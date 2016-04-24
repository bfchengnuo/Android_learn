package com.bfchengnuo.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener,MyfragmentData.MyListener{

    private Button btn;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btn = (Button) findViewById(R.id.btn);
        editText = (EditText) findViewById(R.id.edittext);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str = editText.getText().toString();
        MyfragmentData myfragmentData = new MyfragmentData();//
        //使用bundle传送数据
        Bundle bundle = new Bundle();
        bundle.putString("name",str);
        myfragmentData.setArguments(bundle);
        //创建一个管理者  以及开始一个事务
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.line,myfragmentData,"myfragment");
        //增加回退效果，手机物理返回按键可以回退到上一个界面而不是直接退出
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();//提交
        Toast.makeText(this,"向myfragmentdata发送数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show(String code) {
        Log.d("awsd","main run");
        Toast.makeText(Main3Activity.this,"接收的数据-"+code,Toast.LENGTH_SHORT).show();
    }
}

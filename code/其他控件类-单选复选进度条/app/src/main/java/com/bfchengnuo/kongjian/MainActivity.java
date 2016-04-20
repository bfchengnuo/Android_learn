package com.bfchengnuo.kongjian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radg;
    private RadioButton radbtn1;
    private RadioButton radbtn2;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private ProgressBar fistBar;
    private Button btn;
    private TextView textView;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radg = (RadioGroup) findViewById(R.id.radg);
        radbtn1 = (RadioButton) findViewById(R.id.radio1);
        radbtn2 = (RadioButton) findViewById(R.id.radio2);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        btn = (Button) findViewById(R.id.btn);
        fistBar = (ProgressBar) findViewById(R.id.fistbar);
        textView = (TextView) findViewById(R.id.textView);
        //fistBar.setMax(100);

        //一定是给单选按钮的组绑定监听器,因为组并不是按钮，所以监听器是特有的
        radg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //第一个 拿一个组被点击了，第二个 点击的那个按钮
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radbtn1.getId()){
                    Toast.makeText(MainActivity.this,"你点击了男",Toast.LENGTH_SHORT).show();
                }else if (checkedId == radbtn2.getId()){
                    Toast.makeText(MainActivity.this,"你点击了女",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //可以单独写一个实现点击事件的类，根据不同的ID做出不同的响应
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(MainActivity.this,"你选择了one",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"你取消了one",Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(MainActivity.this,"你选择了two",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"你取消了two",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0){
                    //设置可见
                    fistBar.setVisibility(View.VISIBLE);
                }else if (i <= 100){
                    //设置进度增加
                    fistBar.setProgress(i);
                    //设置第二进度
                    fistBar.setSecondaryProgress(i+10);
                }else {
                    Toast.makeText(MainActivity.this,"加载完毕！",Toast.LENGTH_SHORT).show();
                    return;
                }
//                i += 10;
                i = fistBar.getProgress() + 10;
                //设置文本显示
                textView.setText("第一进度"+(fistBar.getProgress()*100)/fistBar.getMax()+"% 第二进度"
                        +(fistBar.getSecondaryProgress()*100)/fistBar.getMax()+"%");
            }
        });

        //noinspection ConstantConditions
        findViewById(R.id.btnn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProgressActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.bfchengnuo.calc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//原来的继承的是AppCompatActivity
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btndian;
    Button btn_clear;//清除按钮
    Button btn_del;//删除按钮
    Button btn_plus;//加号
    Button btn_minus;//减号
    Button btn_multiply;//乘号
    Button btn_divide;//除号
    Button btn_equle;//等于
    EditText et_showview;//显示显示屏内容
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn0 = (Button) findViewById(R.id.button0);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btndian = (Button) findViewById(R.id.buttondian);
        btn_multiply = (Button) findViewById(R.id.buttoncheng);
        btn_clear = (Button) findViewById(R.id.buttonc);
        btn_del = (Button) findViewById(R.id.buttondel);
        btn_divide = (Button) findViewById(R.id.buttonchu);
        btn_equle = (Button) findViewById(R.id.buttondeng);
        btn_minus = (Button) findViewById(R.id.buttonjian);
        btn_plus = (Button) findViewById(R.id.buttonjia);

        et_showview = (EditText) findViewById(R.id.vtext);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btndian.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_equle.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // 获取显示屏的显示内容
        String str = et_showview.getText().toString();
        switch (v.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            case R.id.buttondian:
                // 将按的按键累加到显示屏
                // 运算第一次后要清空显示if
                if (flag){
                    str = "";
                    et_showview.setText("");
                    flag = false;
                }
                et_showview.setText(str + ((Button) v).getText());
                break;
            case R.id.buttoncheng:
            case R.id.buttonchu:
            case R.id.buttonjia:
            case R.id.buttonjian:
                if (flag){
                    et_showview.setText("");
                }

                et_showview.setText(str + " " + ((Button) v).getText() + " ");
                break;
            case R.id.buttonc:
                et_showview.setText("");
                break;
            case R.id.buttondel:
                //str != null &&
                if (!str.equals("")) {
                    et_showview.setText(str.substring(0, str.length() - 1));
                }
                break;
            case R.id.buttondeng:
                getResult();
                break;
        }

    }

    //计算结果
    private void getResult() {
        flag = true;
        //取显示器的内容
        String exp = et_showview.getText().toString();
        double r = 0; // 用来存储计算数值
        int space = exp.indexOf(' ');//搜索空格位,注意是单引号
        String s1 = exp.substring(0, space);//保存第一个运算数
        String op = exp.substring(space + 1, space + 2);//获取运算符
        String s2 = exp.substring(space + 3);//用于保存第二个运算数
        double arg1 = Double.parseDouble(s1);
        double arg2 = Double.parseDouble(s2);
        if (op.equals("+")) {
            r = arg1 + arg2;
        }else if (op.equals("-")) {
            r = arg1 - arg2;
        }else if (op.equals("*")) {
            r = arg1 * arg2;
        }else if (op.equals("/")) {
            if (arg2 == 0){
                r = 0;
            }
            else {
                r = arg1 / arg2;
            }
        }
        if (!s1.contains(".") && !s2.contains(".")){
            int res = (int) r;
            et_showview.setText(res + "");
        }else{
            et_showview.setText(r + "");
        }

    }
}

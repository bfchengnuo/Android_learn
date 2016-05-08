package com.bfchengnuo.filetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button btn;
    private EditText editText1;
    private CheckBox cb;
    //
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit);
        editText1 = (EditText) findViewById(R.id.edit1);
        btn = (Button) findViewById(R.id.btn);
        cb = (CheckBox) findViewById(R.id.checkbox);
        pref = getSharedPreferences("dataceshi",MODE_PRIVATE);

        boolean isCheck = pref.getBoolean("ischeck",false);
        if (isCheck){
            //如果复选框被选定
            String pwd = pref.getString("pwd","");
            editText1.setText(pwd);
            cb.setChecked(true);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useName = editText.getText().toString();
                String usePWD = editText1.getText().toString();
                if (useName.equals("admin") && usePWD.equals("1234")){
                    editor = pref.edit();
                    if (cb.isChecked()){
                        editor.putBoolean("ischeck",true);
                        editor.putString("pwd",usePWD);
                    }else {
                        editor.clear();
                    }
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        -------------------------------------------------------
        String inputText = load();
        //判断data文件是否是空值，这个方法如果是null或者""都返回true
        if (!TextUtils.isEmpty(inputText)){
            editText.setText(inputText);
            //将光标移动到结尾
            editText.setSelection(inputText.length());
            Toast.makeText(this,"成功载入数据",Toast.LENGTH_SHORT).show();
        }
    }

    private String load() {
        FileInputStream is;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = openFileInput("dataL");
            reader = new BufferedReader(new InputStreamReader(is));
            String lin ;
            while ((lin = reader.readLine()) != null){
                sb.append(lin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String data = editText.getText().toString();
        save(data);
    }

    private void save(String data) {
        FileOutputStream out;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("dataL",MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

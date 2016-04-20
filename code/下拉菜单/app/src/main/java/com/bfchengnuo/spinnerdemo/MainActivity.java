package com.bfchengnuo.spinnerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //创建数据源
    private List<String> list;
    private List<Map<String,Object>>list1;

    private TextView textView;
    private Spinner spinner;
    private Spinner spinner1;
    //定义一个适配器
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spacer1);

        list1 = new ArrayList<Map<String, Object>>();
        list = new ArrayList<>();
        list.add("刀剑神域");
        list.add("CLANNAD");
        list.add("loli");
        list.add("lxc");

        //为下拉列表定义适配器,将数据存入了适配器
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        //定义下拉菜单的样式
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //将适配器添加到下拉菜单
        spinner.setAdapter(adapter);
        //设置响应事件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("您选择的是"+adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("NONE");
            }
        });

//        ---------------------------------

     //   getData();



    }


    private void getData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image",R.mipmap.ic_launcher);
        map.put("text","SAO");
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("image",R.mipmap.ic_launcher);
        map1.put("text","LOli");

        list1.add(map);
        list1.add(map1);
    }

}

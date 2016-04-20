package com.bfchengnuo.gridviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ArrayList dataList;
    private GridView gridView;
    private SimpleAdapter adapter;

    //添加图标的ID数据
    private int[] icon = {R.drawable.address_book,R.drawable.calendar,R.drawable.camera,
    R.drawable.clock,R.drawable.games_control,R.drawable.messenger,R.drawable.ringtone,
    R.drawable.settings,R.drawable.speech_balloon,R.drawable.weather,R.drawable.world,
    R.drawable.youtube};
    //添加图标所对应的文字数据
    private String[] text = {"图书","日历","相机","时钟","游戏","联系人","音乐","设置","信息",
            "天气","文档","视频"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //定义数据源
        dataList = new ArrayList<Map<String,Object>>();

        gridView = (GridView) findViewById(R.id.gridview);

        //定义一个适配器
        adapter = new SimpleAdapter(this,getData(),R.layout.data,new String[]{"icon","text"},new int[]{R.id.icon,R.id.text});
        //加载适配器
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(this);
    }

    //添加数据的函数
    private ArrayList<Map<String,Object>> getData(){
        for (int i = 0;i<icon.length;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("icon",icon[i]);
            map.put("text",text[i]);
            dataList.add(map);
        }
        return dataList;
    }

    //设置表格的监听事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //position 传进来的标志 和下标相同
        Toast.makeText(this,"我是"+text[position],Toast.LENGTH_SHORT).show();
    }
}

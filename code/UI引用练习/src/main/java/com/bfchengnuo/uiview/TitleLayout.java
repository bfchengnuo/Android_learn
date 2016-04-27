package com.bfchengnuo.uiview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //通过LayoutInflater.from() 构造出一个Inflater对象
        //inflate()动态加载布局，第一个是加载布局文件ID 第二个是添加的父布局
        LayoutInflater.from(context).inflate(R.layout.title,this);

        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击了编辑按钮",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.bfchengnuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyFragment extends android.app.Fragment {

    //layout布局文件转换成View对象
    /**
     * resource:Fragment需要加载的布局文件
     * root：加载layout的父ViewGroup
     * attactToRoot：false，不返回父ViewGroup
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment,container,false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("静态加载F");

        return view;
    }
}

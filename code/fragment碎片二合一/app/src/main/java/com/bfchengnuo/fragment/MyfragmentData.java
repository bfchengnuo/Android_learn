package com.bfchengnuo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyfragmentData extends Fragment{


    public MyListener myListener;
    private String code = "hello，Activity!";
    //定义一个接口用来回传数据
    public interface MyListener{
        public void show(String code);
    }

    //当Fragment被添加到Activity时候会回调这个方法，并且只调用一次
    /*@Override
    public void onAttach(Context context) {
        Log.d("awsd","run");
        super.onAttach(context);
        myListener = (MyListener) context;
    }*/
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myListener = (MyListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Log.d("awsd","run");
        View view = inflater.inflate(R.layout.fragment2,container,false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        //接收数据
        String str = getArguments().get("name")+"";
        textView.setText(str);
        Toast.makeText(getActivity(),"成功接收数据",Toast.LENGTH_SHORT).show();
        //调用已经实现接口的activity的方法
        //myListener = (MyListener) container;
        myListener.show(code);
        return view;
    }

}

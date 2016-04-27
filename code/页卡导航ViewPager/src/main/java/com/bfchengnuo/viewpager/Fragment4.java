package com.bfchengnuo.viewpager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lvxue on 2016/4/24 0024.
 */
public class Fragment4 extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ceshi","4已经销毁");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view4,container,false);
    }
}

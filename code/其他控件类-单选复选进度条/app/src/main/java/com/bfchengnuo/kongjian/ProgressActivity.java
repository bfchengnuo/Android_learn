package com.bfchengnuo.kongjian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗口特征，第一个带进度  第二个不带进度，一定要在设置布局之前加载
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.progress);
        //设置进度的可见性
        setProgressBarVisibility(true);
        setProgressBarIndeterminateVisibility(true);
        //设置进度
        setProgress(80);


    }

}

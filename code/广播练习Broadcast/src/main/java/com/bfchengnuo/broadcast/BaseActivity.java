package com.bfchengnuo.broadcast;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by lvxue on 2016/5/2 0002.
 * 作为所有活动的父类
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

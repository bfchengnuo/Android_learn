package com.bfchengnuo.fragmentnews;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
}

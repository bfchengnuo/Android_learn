package com.bfchengnuo.gesturetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private GestureDetector mGestureDetector; //识别各种手势

    //已经实现了所有的方法
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        //当手指滑动的时候
        @Override //e1是起始位置，e2是结束为止
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > 50){
                Toast.makeText(MainActivity.this, "向左滑动", Toast.LENGTH_SHORT).show();
            }else if (e2.getX() - e1.getX() > 50){
                Toast.makeText(MainActivity.this, "向右滑动", Toast.LENGTH_SHORT).show();
            }
            Log.d("lxc","run");
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        mGestureDetector = new GestureDetector(new MyGestureListener());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);  //将MotionEvent发送出去
                return true; //要返回true
            }
        });
    }
}

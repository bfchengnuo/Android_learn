package com.bfchengnuo.mydemo.fragmentdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 冰封承諾Andy on 2016/8/23 0023.
 */
public class Mview extends View {

    private static final String TAG = "lxc";
    private int aa;
    private int bb;
    private int cc;
    private int lastX;
    private int lastY;

    public Mview(Context context) {
        super(context);
    }

    public Mview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Mview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean setData(int a, int b, int c) {
        aa = a;
        bb = b;
        cc = c;

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(TAG, "onMeasure: " + aa + bb);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            //手指按下
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            //手指移动
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;

                //将布局的位置进行重绘
                layout(getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);
                break;
        }

        return true;
    }
}

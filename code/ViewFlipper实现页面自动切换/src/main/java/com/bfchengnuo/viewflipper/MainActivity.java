package com.bfchengnuo.viewflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper flipper;
    private int[] src={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5};
    private float startX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //添加资源
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        for (int i = 0;i<src.length;i++){
            flipper.addView(getImageView(src[i]));
        }
        /*
        //加载动画
        flipper.setInAnimation(this,R.anim.left_in); // 进入动画
        flipper.setOutAnimation(this,R.anim.left_out); //滑出动画
        flipper.setFlipInterval(3000); //设置切换间隔时间 毫秒值
        flipper.startFlipping(); //开始轮播
        */
    }

    private ImageView getImageView(int i) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(i);
        return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            //手指落下的时候
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            //手指滑动的时候,会多次调用
            case MotionEvent.ACTION_MOVE:
//                //往右滑的时候
//                if (event.getX() - startX >100){
//                    flipper.setInAnimation(this,R.anim.left_in);
//                    flipper.setOutAnimation(this,R.anim.left_out);
//                    //显示下一张图
//                    flipper.showNext();
//                }
//                if (startX - event.getX() >100){
//                    flipper.setInAnimation(this,R.anim.right_in);
//                    flipper.setOutAnimation(this,R.anim.right_out);
//                    //显示上一张图
//                    flipper.showPrevious();
//                }
                break;
            case MotionEvent.ACTION_UP:
                //往右滑的时候
                if (startX - event.getX() >100){
                    flipper.setInAnimation(this,R.anim.right_in);
                    flipper.setOutAnimation(this,R.anim.right_out);
                    //显示下一张图
                    flipper.showNext();
                }
                if (event.getX() - startX >100){
                    flipper.setInAnimation(this,R.anim.left_in);
                    flipper.setOutAnimation(this,R.anim.left_out);
                    //显示上一张图
                    flipper.showPrevious();
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}

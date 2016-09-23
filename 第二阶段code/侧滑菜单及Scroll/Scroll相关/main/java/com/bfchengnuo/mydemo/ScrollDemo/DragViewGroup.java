package com.bfchengnuo.mydemo.ScrollDemo;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by 冰封承諾Andy on 2016/9/12 0012.
 * 自定义group，实现自定义滑动侧边栏样式
 */
public class DragViewGroup extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mMenuView,mMainView;
    private int mWidth;

    public DragViewGroup(Context context) {
        super(context);
        initView();
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this,callback);
    }

    /**
     * 加载完布局后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    /**
     * 组件大小改变时回调
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取侧滑菜单的宽度
        mWidth = mMenuView.getWidth();
    }

    /**
     * 触摸事件相关
     * 拦截 or 处理/消费
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传给ViewDragHelper处理
        mViewDragHelper.processTouchEvent(event);
        return true;
    }


    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        //何时开始检测触摸事件
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //当触摸的child是mMainView的时候开始检测
            return mMainView == child;
        }

        //处理水平方向的滑动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        //处理垂直方向的滑动，这里我们用不到，设置return 0
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        //拖动结束后调用
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //让抬起手指后 缓慢/平滑的移动到指定位置
            //mainViwe滑动距离小于500时关闭侧滑菜单
            if (mMainView.getLeft() < 500){
                mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }else {
                //打开菜单
                mViewDragHelper.smoothSlideViewTo(mMainView,300,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}

package com.bfchengnuo.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


/**
 * Created by 冰封承諾Andy on 2016/9/19 0019.
 * 自定义横向滚动View
 */
public class SlidingMenu extends HorizontalScrollView {

    private String TAG = "lxc";
    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mWidth;

    private int menuPading = 50;
    private boolean one;
    private int mMEnuWidth;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * 在xml里声明的时候调用,没有自定义属性
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 当使用了自定义属性的时候调用
     * 它并不会主动的被调用，需要在第二个构造函数中手动进行调用
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取窗口的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayManager = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayManager);
        mWidth = displayManager.widthPixels;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, R.style.AppTheme);
        for (int i = 0; i < a.getIndexCount(); i++) {
            switch (a.getIndex(i)) {
                case R.styleable.SlidingMenu_rightPading:
                    //获取自定义的值(px)，第二个参数为默认值
                    menuPading = a.getDimensionPixelSize(a.getIndex(i),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle(); //释放资源
        //将dp转换为px
//        menuPading = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
    }

    /**
     * 进行测量，会多次调用此方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!one) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);

            //菜单的宽度就是当前屏幕的宽度-设定的pading值
            mMEnuWidth = mMenu.getLayoutParams().width = mWidth - menuPading;
            mContent.getLayoutParams().width = mWidth;
            one = true;
        }
    }

    /**
     * 确定布局的位置，同样会调用多次,当发生改变的时候
     * 用来设置菜单的偏移，达到隐藏菜单的作用
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMEnuWidth, 0);
        }
    }

    /**
     * 触摸事件的判断
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //这里的getScrollX获取的其实就是隐藏部分的宽度
                int scroll = getScrollX();
                //隐藏部分大于一半的话就进行隐藏菜单
                if (scroll >= mMEnuWidth / 2) {
                    //平滑的完成滑动，基于Scroll实现
                    this.smoothScrollTo(mMEnuWidth, 0);
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true; //消费事件
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 抽屉式菜单效果,当滚动条变化的时候调用，不管是系统还是人为改变
     * l, t代表left, top，也就是水平方向/垂直方向的滚动偏移。而oldl, oldt就是滑动前的偏移量。
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float f = l * 1.0f / mMEnuWidth; //1~0的变化，l的偏移量是width~0，往左拉滚动条往左滑动
        //调用属性动画设置偏移
//        mMenu.setTranslationX(mMEnuWidth * f); //width~0的变化

        /**
         * 上面为抽屉式侧滑菜单
         * QQ5.0效果
         * 内容区域的缩放效果  1.0~0.7   0.7 + 0.3 * f(1~0)
         *
         * 菜单的缩放与透明度的变化
         * 缩放：0.7 ~1.0  对应：1.0 - f * 0.3
         * 透明度 0.6 ~ 1.0 对应：0.6 + 0.4 * (1- f)
         */
        float Rsacale = 0.7f + 0.3f * f;
        float leftScale = 1.0f - f * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - f);
        // 调用属性动画，设置菜单的缩放与透明度
        mMenu.setTranslationX(mMEnuWidth * f * 0.8f); //使一开始不完全显示菜单
        mMenu.setScaleX(leftScale);
        mMenu.setScaleY(leftScale);
        mMenu.setAlpha(leftAlpha);

        //设置缩放中心为左边的中心
        mContent.setPivotX(0);
        mContent.setPivotY(mContent.getHeight()/2);
        //设置缩放 x y
        mContent.setScaleX(Rsacale);
        mContent.setScaleY(Rsacale);
    }
}

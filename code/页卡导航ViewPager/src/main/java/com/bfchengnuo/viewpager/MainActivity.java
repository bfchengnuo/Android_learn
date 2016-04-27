package com.bfchengnuo.viewpager;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<String>title;
    private List<View>viewList;
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view1 = View.inflate(this,R.layout.view1,null);
        View view2 = View.inflate(this,R.layout.view2,null);
        View view3 = View.inflate(this,R.layout.view3,null);
        View view4 = View.inflate(this,R.layout.view4,null);

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
        fragmentList.add(new Fragment4());

        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        title = new ArrayList<String>();
        title.add("第一页");
        title.add("第二页");
        title.add("第三页");
        title.add("第四页");

        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.tab);

        pagerTabStrip.setTextColor(Color.RED);
        pagerTabStrip.setDrawFullUnderline(false);//去掉下划线

        /*
        //新建一个适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList,title);
        //绑定适配器
        viewPager.setAdapter(myPagerAdapter);*/

        //新建适配器
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(),title,fragmentList);
        //viewPager.setAdapter(myFragmentAdapter);

        MyFragmentAdapter2 myFragmentAdapter2 = new MyFragmentAdapter2(getSupportFragmentManager(),title,fragmentList);
        viewPager.setAdapter(myFragmentAdapter2);
        //设置监听器
        //setOnPageChangeListener已过时
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this,"这是第"+(position+1)+"页",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

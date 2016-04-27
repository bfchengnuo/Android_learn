package com.bfchengnuo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lvxue on 2016/4/24 0024.
 */
public class MyPagerAdapter extends PagerAdapter{
    private List<String> title;
    private List<View>viewList;


    public MyPagerAdapter(List<View> viewList,List<String>title){
        this.viewList = viewList;
        this.title = title;
    }

    //返回页卡的数量
    @Override
    public int getCount() {
        return viewList.size();
    }
    //view是否来自与对象
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //实例化一个页卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
    //销毁一个页卡
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
    //设置标题，必须设置了子标签
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}

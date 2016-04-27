package com.bfchengnuo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lvxue on 2016/4/24 0024.
 */
public class MyFragmentAdapter2 extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> title;

    public MyFragmentAdapter2(FragmentManager fm, List<String> title, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //设置标题
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
/*
下面的创建和销毁方法不用改写，能达到自动管理页卡的目的
自动的删除销毁 保留3个
 */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}

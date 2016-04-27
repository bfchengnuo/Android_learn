package com.bfchengnuo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lvxue on 2016/4/24 0024.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> title;

    public MyFragmentAdapter(FragmentManager fm,List<String>title,List<Fragment>fragmentList) {
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
}

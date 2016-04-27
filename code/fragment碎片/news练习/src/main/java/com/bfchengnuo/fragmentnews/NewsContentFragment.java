package com.bfchengnuo.fragmentnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lvxue on 2016/4/25 0025.
 * 新闻内容部分的碎片布局
 */
public class NewsContentFragment extends Fragment {
    private View view;

    //Fragment第一次绘制它的用户界面的时候，系统会调用此方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    //调用此方法刷新新闻内容的显示
    public void refresh(String newsTitle,String newsContent){
        //不可见的，新闻内容部分 总布局
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE); //设置为可见状态
        TextView newsTitleText = (TextView) view.findViewById(R.id.news_title);
        TextView newsContentText = (TextView) view.findViewById(R.id.news_content);
        //刷新新闻的标题和内容
        newsContentText.setText(newsContent);
        newsTitleText.setText(newsTitle);
    }
}

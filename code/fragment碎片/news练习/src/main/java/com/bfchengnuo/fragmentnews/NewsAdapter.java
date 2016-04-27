package com.bfchengnuo.fragmentnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lvxue on 2016/4/25 0025.
 * 创建一个自定义新闻适配器
 */
public class NewsAdapter extends ArrayAdapter {
    private int resourceId;
    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获得与在数据集中的指定位置相关联的数据项。适配器的数据源为news的集合
        News news = (News) getItem(position);
        View view;
        //判断是否有缓存，如果有直接取缓存数据，提高运行速度
        if (convertView == null){
            //从给定的XML文件获取View对象
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(R.id.news_title);
        textView.setText(news.getTitle());

        return view;
    }
}

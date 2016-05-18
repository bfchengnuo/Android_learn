package com.bfchengnuo.fragmentnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by lvxue on 2016/4/25 0025.
 * Fragment Activity之间的通信
 * Fragment —> Activity : getActivity()
 * Activity —> Fragment : getFragmentManager().findFragmentById()   //只有在activity_layout中注册了ID才能找到
 * Fragment1 — > Fragment2 : getActivity().getFragmentManager().findFragmentById()
 */
public class NewsContentActivity extends FragmentActivity{
    //这是最佳的启动方式，让别人看出启动新的activity需要那些参数
    public static void actionStart(Context context,String newsTitle,String newsContent){
        Intent intent = new Intent(context,NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //不显示标题
        setContentView(R.layout.news_content); //绑定布局
        String newsTitle = getIntent().getStringExtra("news_title");
        String newsContent = getIntent().getStringExtra("news_content");
        //android.support.v4.app.Fragment使用 (ListFragment)getSupportFragmentManager().findFragmentById(R.id.userList) 获得 ， 需要继承android.support.v4.app.FragmentActivity
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);

        newsContentFragment.refresh(newsTitle,newsContent);
    }
}

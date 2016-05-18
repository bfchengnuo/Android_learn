package com.bfchengnuo.fragmentnews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvxue on 2016/4/25 0025.
 * 这个碎片用来加载list列表/布局
 * 新闻标题的碎片
 */
public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView newsTitLeListView;
    private List<News> newsList;
    private NewsAdapter newsAdapter;
    private boolean isTwoPane;  //判断是否手机或者平板

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newsList = getNews(); //初始化新闻数据
        newsAdapter = new NewsAdapter(context,R.layout.news_item,newsList);//创建适配器,样式就是一个文本显示，文本显示数据源里的数据
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag,container, false);//获得碎片视图，listview
        newsTitLeListView = (ListView) view.findViewById(R.id.news_title_list_view);
        //给listview绑定适配器
        newsTitLeListView.setAdapter(newsAdapter);
        //设置listview的监听器 响应点击事件
        newsTitLeListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null){
            //可以找到布局的时候为双页模式
            isTwoPane = true;
        }else {
            //找不到布局文件的时候为单页模式
            isTwoPane = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news = newsList.get(position);
        if (isTwoPane){
            //如果是双页模式，刷新NewsContentFragment中的内容
            NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(),news.getContent());
        }else {
            //如果为单页模式,则直接启动NewsContentActivity
            //静态调用actionStart启动新的的activity，需要的参数更清晰
            NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
        }
    }
    //添加模拟数据源
    public List<News> getNews() {
        List<News> newsList = new ArrayList<News>();
        News news1 = new News();
        news1.setTitle("这是第一条新闻的标题");
        news1.setContent("这是第一条新闻的内容：《Re:从零开始的异世界生活》是长月达平所作的日本轻小说作品，插图由大塚真一郎绘画，本为投稿网站《成为小说家吧》连载的网络小说，当时笔名是鼠色猫，后来在2014年经由Media Factory出版书籍，中文版则由青文出版社发行。\n" +
                "\n" +
                "漫画版分别有マツセダイチ绘制，在《月刊Comic Alive》连载的《Re:从零开始的异世界生活 第一章 王都一日篇》和枫月诚绘画，《月刊BIG GANGAN》连载的《Re:从零开始的异世界生活 第二章 宅邸一周篇》。2015年7月19日发布改编为电视动画的消息[1]。2015年《这本轻小说真厉害！》票选排名第二名。[2]电视动画于2016年4月起播放。");
        newsList.add(news1);
        News news2 = new News();
        news2.setTitle("这是第二条新闻的标题");
        news2.setContent("走出便利店要回家的高中生菜月昴突然被召唤到异世界，并遭遇强盗迅速面临性命危机。这时，一名神秘银发美少女和猫精灵拯救了一筹莫展的他。以报恩为名义，昴自告奋勇要帮助少女找东西。然而，好不容易才掌握到线索，昴和少女却被不明人士攻击而殒命。\n" +
                "\n" +
                "本来应该就这样结束，但回过神来，昴却发现自己置身在第一次被召唤到这个异世界时所在的位置。“死亡回归”──无力的少年得到的唯一能力，是死后时间会倒转回到开始那一刻。跨越无数绝望，从死亡的命运中开拓未来！");
        newsList.add(news2);
        return newsList;
    }
}

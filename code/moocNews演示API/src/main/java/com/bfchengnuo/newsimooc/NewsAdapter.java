package com.bfchengnuo.newsimooc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lvxue on 2016/4/28 0028.
 * 自定义的适配器用来适配listview的数据
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<NewsBean> mList;
    //需要有一个转换布局成view的对象
    private LayoutInflater mInflater;
    //创建一个对象  避免每次都new 产生多个缓存器
    private ImageLoader mImageLoader;

    private int mStart,mEnd;
    public static String[] URLS;//用来保存所有的地址

    public NewsAdapter(Context context, List<NewsBean> mList){
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader();
        //获取、保存地址
        URLS = new String[mList.size()];
        for (int i = 0;i<mList.size();i++){
            URLS[i] = mList.get(i).newsIcoUrl;
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //文艺式写法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new viewHolder();
            convertView = mInflater.inflate(R.layout.item_layout,null);
            viewHolder.ivIco = (ImageView) convertView.findViewById(R.id.iv_ico);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (NewsAdapter.viewHolder) convertView.getTag();
        }
        viewHolder.ivIco.setImageResource(R.mipmap.ic_launcher);
       // new ImageLoader().showImageByThread(viewHolder.ivIco,mList.get(position).newsIcoUrl);
        mImageLoader.showImageByAsyncTask(viewHolder.ivIco,mList.get(position).newsIcoUrl);
        viewHolder.tvTitle.setText(mList.get(position).newsTitle);
        viewHolder.tvContent.setText(mList.get(position).newsContent);
        //设置url给后面设置图片判断缓存提供服务
        String murl = mList.get(position).newsIcoUrl;
        viewHolder.ivIco.setTag(murl);
        return convertView;
    }

    //当状态改变的时候才会被调用
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //处于停止状态，加载
        if (scrollState == SCROLL_STATE_IDLE){

        }else {
            //其他状态，停止加载活动
        }
    }
    //整个滑动中都会调用
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem+visibleItemCount; //开始的可见元素加上可见元素的数量
    }

    class viewHolder{
        public TextView tvTitle,tvContent;
        public ImageView ivIco;
    }
}

package com.bfchengnuo.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lvxue on 2016/4/24 0024.
 * 自定义适配器类
 */
public class MsgAdapter extends ArrayAdapter {

    private int resourceID;  //保存传入的样式主题

    public MsgAdapter(Context context, int resource, List<Msg> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }

    //这个方法在每个子项被滚动到屏幕内的时候调用
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //得到当前项的一个实例
        //因为listview每一行就是显示的一个list集合，所以就是获取一个msg的实例
        Msg msg = (Msg) getItem(position);
        View view;
        //创建一个方法用来保存listview中显示的聊天内容用到的各种控件，配合历史view，避免多长生成影响效率
        //将实例化的对象保存在方法中避免了每次都要获取对象
        ViewHolder viewHolder;
        //convertView用于将加载好的布局进行缓存，如果缓存为空则重新加载，如果缓存不为空则直接调用缓存的内容
        if (convertView == null){
            //获取XML文件 转换成view对象
            view = LayoutInflater.from(getContext()).inflate(resourceID,null);
            viewHolder = new ViewHolder();
            viewHolder.leftLaylout = (LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.reghtLaylout = (LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.leftTextView = (TextView) view.findViewById(R.id.left_msg);
            viewHolder.reghtTextView = (TextView) view.findViewById(R.id.right_msg);
            //将已经创建实例的空间通过view的方法保存起来
            view.setTag(viewHolder);
        }else {
            view = convertView;
            //读取view保存的实例对象
            viewHolder = (ViewHolder) view.getTag();
        }
        //判断消息的类型是发送还是接收
        if (msg.getType() == Msg.TYPE_READ){
            viewHolder.leftLaylout.setVisibility(View.VISIBLE);
            viewHolder.reghtLaylout.setVisibility(View.GONE);
            viewHolder.leftTextView.setText(msg.getContent());
        }else {
            viewHolder.leftLaylout.setVisibility(View.GONE);
            viewHolder.reghtLaylout.setVisibility(View.VISIBLE);
            viewHolder.reghtTextView.setText(msg.getContent());
        }
        return view;
    }

    private class ViewHolder {
        LinearLayout leftLaylout;
        LinearLayout reghtLaylout;
        TextView leftTextView;
        TextView reghtTextView;
    }
}

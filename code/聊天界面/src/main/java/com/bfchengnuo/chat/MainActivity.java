package com.bfchengnuo.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView msgListView;
    private EditText editText;
    private Button btn;
    private MsgAdapter msgAdapter;
    private List<Msg> msgList = new ArrayList<Msg>();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMsg(); //初始化消息列表
        msgListView = (ListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.edit);
        btn = (Button) findViewById(R.id.btn);
        //初始化自定义的适配器
        msgAdapter = new MsgAdapter(this,R.layout.msg_layout,msgList);
        //绑定适配器
        msgListView.setAdapter(msgAdapter);
        //发送按钮的点击事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框里的内容
                String sendText = editText.getText().toString();
                if (!"".equals(sendText)){
//                    如果不为空，就将消息添加到消息列表中
                    Msg msg = new Msg(sendText,Msg.TYPE_SEND);
                    msgList.add(msg);
                    //刷新列表
                    msgAdapter.notifyDataSetChanged();
                    //将msglist定位到最后一行
                    msgListView.setSelection(msgList.size());
                    editText.setText(""); //清空输入框
                }
            }
        });
    }

    private void initMsg() {
        Msg msg1 = new Msg("hello!",Msg.TYPE_READ);
        msgList.add(msg1);
    }
}

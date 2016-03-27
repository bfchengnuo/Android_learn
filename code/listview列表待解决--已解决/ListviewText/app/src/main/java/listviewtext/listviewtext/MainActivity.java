package listviewtext.listviewtext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//实现监听手指滑动的接口
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {

    private ListView listView;
//    定义数组适配器
    private ArrayAdapter<String> arr_ter;
//    定义简单适配器
    private SimpleAdapter simp_ter;
//    定义一个map集合用于简单适配器
    private List<Map<String,Object>> dateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        dateList = new ArrayList<Map<String,Object>>();


//        定义一个简单的数据源
        String[] ss = {"数据1","数据2","数据3","数据4"};
//       新建一个适配器     第一个为上下问对象，第二个列表布局文件这里引用安卓默认，第三个是数据源
        arr_ter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ss);
//        给视图绑定适配器
        //listView.setAdapter(arr_ter);

        /*
        * 设置简单适配器
        * 第一个context上下文对象
        * 第二个data 数据源，List<Map<String,Object>>是一个map所组成的list集合，每个map对应listView一行数据
        *   每一个map（键值对）中的键必须包含所有在form中指定的键
        * 第三个 resource 列表项的布局文件ID
        * 第四个 form map中的键名 (new String[]{布局文件的name})
        * 第五个 to 绑定视图中的ID，与form成对应关系 (new int[]{布局文件中ID})
        * */

//        simp_ter = new SimpleAdapter(this,dateList,R.layout.datelayout,new String[] {"src","text"},new int[] {R.id.text,R.id.src});

//        form中的键名和to中的ID一定要对应起来！！！就在这里被坑了  最后两个
        simp_ter = new SimpleAdapter(this,getDateList(),R.layout.datelayout,new String[] {"src","text"},new int[] {R.id.src,R.id.text});
        listView.setAdapter(simp_ter);
        // 设置ListView的元素被选中时的事件处理监听器
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
    }

    private List<Map<String,Object>> getDateList(){
        for (int i =0;i<20;i++){
            Map<String,Object>map = new HashMap<String, Object>();
//            向map集合中添加键值对
            map.put("src",R.drawable.ic_launcher);
            map.put("text","萝莉"+i);
//            将map集合添加到list中
            dateList.add(map);
        }

        return dateList;
    }

    @Override
    //列表内容被点击后事件
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        获取列表控件的信息，位置和内容
        String text = listView.getItemAtPosition(position)+"";
//        将获取的内容显示在屏幕,,上下文对象，文本内容，显示时间
        Toast.makeText(MainActivity.this,"位置："+position+"内容："+text,Toast.LENGTH_LONG).show();
    }

//    监听手势滑动
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        switch (scrollState){
            case SCROLL_STATE_FLING :
//                手指离开屏幕之前，由于惯性继续滑动
                Toast.makeText(MainActivity.this,"用力划了一下",Toast.LENGTH_LONG).show();
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("text","滚动添加的数据");
                map.put("src",R.drawable.demo);
                dateList.add(map); //将数据添加到数据源
//            将数据添加到简单适配器
//                不需要添加  否则会重置列表到顶部
               // listView.setAdapter(simp_ter);

//            更新UI
                simp_ter.notifyDataSetChanged();
                break;
            case SCROLL_STATE_IDLE :
//                已经停止滑动
                break;
            case SCROLL_STATE_TOUCH_SCROLL :
//                手指没有离开屏幕，视图正在滑动
                break;
        }



        /*  另一种方式

//        手指离开屏幕前，用力划了一下
        if(scrollState == SCROLL_STATE_FLING){
            Toast.makeText(MainActivity.this,"用力划了一下",Toast.LENGTH_LONG).show();

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("text","滚动添加的数据");
            map.put("src",R.drawable.demo);
            dateList.add(map); //将数据添加到数据源
//            将数据添加到简单适配器
            listView.setAdapter(simp_ter);
//            更新UI
            simp_ter.notifyDataSetChanged();
        }else if(scrollState == SCROLL_STATE_IDLE){
            //停止滚动时

        }else if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
            //正在滚动的时候
        }
    */

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}

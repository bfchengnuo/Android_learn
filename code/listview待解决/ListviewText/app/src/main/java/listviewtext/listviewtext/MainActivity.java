package listviewtext.listviewtext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

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
        simp_ter = new SimpleAdapter(this,getDateList(),R.layout.datelayout,new String[] {"src","text"},new int[] {R.id.text,R.id.src});
        listView.setAdapter(simp_ter);
    }

    private List<Map<String,Object>> getDateList(){
        for (int i =0;i<20;i++){
            Map<String,Object>map = new HashMap<String, Object>();
//            向map集合中添加键值对
            map.put("src",R.drawable.ic_launcher);
            map.put("text","haha");
//            将map集合添加到list中
            dateList.add(map);
        }

        return dateList;
    }
}

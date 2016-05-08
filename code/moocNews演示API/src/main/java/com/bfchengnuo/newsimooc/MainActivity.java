package com.bfchengnuo.newsimooc;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private static String url = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.mListView);

        //开启异步进程
        new NewsAsyncTask().execute(url);
    }

    //异步任务类
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        //执行在其他线程的耗时的网络加载
        @Override
        protected List<NewsBean> doInBackground(String... params) {

            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeen) {
            super.onPostExecute(newsBeen);
            //将加载好的newsBean集合设置到自定义的适配器
            NewsAdapter adapter = new NewsAdapter(MainActivity.this,newsBeen);
            mListView.setAdapter(adapter);
        }
    }

    //读取流返回json加密的字串
    private String readStream(InputStream is){
        InputStreamReader isr;
        String result = ""; //用于存储读取到的字串
        try {
            String line = null;
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null){
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Log.d("lxc","data"+result);
        return result;
    }

    //获取json数据的方法,将有用的数据添加到newsBean集合中
    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        try {
            //获取json加密后的字符串
            String jsonString = readStream(new URL(url).openStream());//与OPenC...getInPutStream相同
            JSONObject jsonObject;
            NewsBean newsBean;
            //用于解析加密的字串
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data"); //对应API，一个大的数据集合
            for (int i = 0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);//复用
                newsBean = new NewsBean();
                newsBean.newsIcoUrl = jsonObject.getString("picSmall"); //对应API
                newsBean.newsContent = jsonObject.getString("description");
                newsBean.newsTitle = jsonObject.getString("name");
                newsBeanList.add(newsBean);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }
}



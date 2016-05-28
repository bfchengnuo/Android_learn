package com.bfchengnuo.coolweather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bfchengnuo.coolweather.R;
import com.bfchengnuo.coolweather.db.CoolWeatherDB;
import com.bfchengnuo.coolweather.model.City;
import com.bfchengnuo.coolweather.model.County;
import com.bfchengnuo.coolweather.model.Province;
import com.bfchengnuo.coolweather.util.HttpCallbackListener;
import com.bfchengnuo.coolweather.util.HttpUtil;
import com.bfchengnuo.coolweather.util.Utilty;

import java.util.ArrayList;
import java.util.List;

public class ChooseAreaActivity extends AppCompatActivity {


    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CoolWeatherDB coolWeatherDB;
    private List<String> dataList = new ArrayList<>();
    //省市县列表
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    //选中的省市
    private Province selectedProvince;
    private City selectedCity;
    //选中的级别
    private int currentlevel;
    //判断是否是从weather跳过来的
    private boolean isFromWeatherActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity",false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //如果存储过天气信息并且不是从weather跳转过来的  直接跳转
        if (prefs.getBoolean("city_selected",false) && !isFromWeatherActivity){
            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //加载布局文件
        setContentView(R.layout.choose_area);

        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        coolWeatherDB = CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断选中的是不是省级，然后获取数据
                if (currentlevel == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(position);
                    queryCities();
                }else if (currentlevel == LEVEL_CITY){
                    selectedCity = cityList.get(position);
                    queryCounties();
                }else if (currentlevel == LEVEL_COUNTY){
                    String countyCode = countyList.get(position).getCountyCode();
                    Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                    intent.putExtra("county_code",countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        //加载省级数据
        queryProvinces();


    }


    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查到再去从服务器查询
     */
    private void queryProvinces() {
        provinceList = coolWeatherDB.loadProvince();
        if (provinceList.size() > 0){
            dataList.clear();
            for (Province province :
                    provinceList) {
                dataList.add(province.getProvinceName());
            }
            //刷新列表
            adapter.notifyDataSetChanged();
            listView.setSelection(0); //设置默认选中第一个
            titleText.setText("中国");
            currentlevel = LEVEL_PROVINCE;
        }else {
            queryFromServer(null,"province");
        }
    }

    /**
     * 查询全国所有的市，优先从数据库查询，如果没有查到再去从服务器查询
     * selectedProvince 选中的省
     */
    private void queryCities() {
        cityList = coolWeatherDB.loadCities(selectedProvince.getId());
        if (cityList.size() > 0){
            dataList.clear();
            for (City city :
                    cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentlevel = LEVEL_CITY;
        }else {
            queryFromServer(selectedProvince.getProvinceCode(),"city");
        }
    }

    /**
     * 查询全国所有的县，优先从数据库查询，如果没有查到再去从服务器查询
     */
    private void queryCounties() {
        countyList = coolWeatherDB.loadCounties(selectedCity.getId());
        if (countyList.size() > 0){
            dataList.clear();
            for (County county :
                    countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentlevel = LEVEL_COUNTY;
        }else {
            queryFromServer(selectedCity.getCityCode(),"county");
        }
    }

    private void queryFromServer(final String Code,final String type) {
        String address;
        if (!TextUtils.isEmpty(Code)){
            address = "http://www.weather.com.cn/data/list3/city"+Code+".xml";
        }else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        /**
         * HttpUtil.sendHttpRequest 来向服务器发生请求 ，开启了一个新线程
         * 响应的数据会回调到HttpCallbackListener的onFinish方法去，
         *      然后调用Utilty.handleProvincesResponse来解析返回的数据，并储存到数据库中
         */
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false; //结果
                if ("province".equals(type)){
                    //解析处理返回的省级数据  response---->服务器返回的数据
                    result = Utilty.handleProvincesResponse(coolWeatherDB,response);
                }else if ("city".equals(type)){
                    result = Utilty.handleCitiesResponse(coolWeatherDB,response,
                            selectedProvince.getId());
                }else if ("county".equals(type)){
                    result = Utilty.handleCountiesResponce(coolWeatherDB,response,
                            selectedCity.getId());
                }

                if (result){
                    //通过runOnUiThread回到主线程逻辑   ---> 在UI线程上运行指定的行动。
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    /**
     * 捕捉back按键 根据当前级别来判断是返回那个列表还是直接退出
     */
    @Override
    public void onBackPressed() {
        if (currentlevel == LEVEL_COUNTY){
            queryCities();
        }else if (currentlevel == LEVEL_CITY){
            queryProvinces();
        }else {
            if (isFromWeatherActivity){
                Intent intent = new Intent(this,WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }


    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("提示");
            progressDialog.setMessage("正在玩命的加载...");
            progressDialog.setCanceledOnTouchOutside(false);  //设置不可取消
        }
        progressDialog.show();
    }
}

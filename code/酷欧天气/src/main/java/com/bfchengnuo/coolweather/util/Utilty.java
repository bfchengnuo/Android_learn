package com.bfchengnuo.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.bfchengnuo.coolweather.db.CoolWeatherDB;
import com.bfchengnuo.coolweather.model.City;
import com.bfchengnuo.coolweather.model.County;
import com.bfchengnuo.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lvxue on 2016/5/21 0021.
 * 解析和处理服务器返回的数据
 */
public class Utilty {
    //解析处理返回的省级数据
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,
                                                               String responce){
        if (!TextUtils.isEmpty(responce)){
            String[] allProvinces = responce.split(",");
            if (allProvinces.length > 0){
                for (String p :
                        allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出的数据存到province表
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    //处理返回的市级数据
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
                                               String response,int provinceID){
        if (!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (allCities.length > 0){
                for (String c :
                        allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceID(provinceID);
                    //存储
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    //解析返回的县级数据
    public static boolean handleCountiesResponce(CoolWeatherDB coolWeatherDB,
                                                 String response,int cityID){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if (allCounties.length > 0){
                for (String c :
                        allCounties) {
                    String[] array = c.split("\\|");
                    County county =new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityID(cityID);
                    //存储
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的json数据  解析出保存到本地
     */
    public static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String pubkushTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,pubkushTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //将信息保存到sharedPreferences文件中
    private static void saveWeatherInfo(Context context, String cityName,
                                        String weatherCode, String temp1, String temp2,
                                        String weatherDesp, String pubkushTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        //创建文件
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather_code",weatherCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("publish_time",pubkushTime);
        editor.putString("current_date",sdf.format(new Date()));
        // apply没有返回值而commit返回boolean表明修改是否提交成功
        editor.apply();
    }
}

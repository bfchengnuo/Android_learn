package com.bfchengnuo.coolweather.model;

/**
 * Created by lvxue on 2016/5/21 0021.
 *   数据库所对应的实体类 市
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceID;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }
}

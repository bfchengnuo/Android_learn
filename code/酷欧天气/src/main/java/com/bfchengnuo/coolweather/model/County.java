package com.bfchengnuo.coolweather.model;

/**
 * Created by lvxue on 2016/5/21 0021.
 * 数据库所对应的实体类 县级
 */
public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityID;

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

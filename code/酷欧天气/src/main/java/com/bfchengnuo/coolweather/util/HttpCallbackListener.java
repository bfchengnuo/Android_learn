package com.bfchengnuo.coolweather.util;

/**
 * Created by lvxue on 2016/5/21 0021.
 * 此接口来回调服务的返回结果
 */
public interface HttpCallbackListener  {
    void onFinish(String response);

    void onError(Exception e);
}

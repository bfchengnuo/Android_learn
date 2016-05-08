package com.bfchengnuo.broadcast;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvxue on 2016/5/2 0002.
 * 用于管理所有的活动
 */
public class ActivityCollector {
    public static List<Activity> activityList = new ArrayList<Activity>(); //存储活动

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity : activityList){
            //判断是否退出状态，如果不是就强制退出
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}

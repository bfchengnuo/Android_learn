package com.bfchengnuo.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lvxue on 2016/5/14 0014.
 * 自定计算服务
 */
public class IRemoteService extends Service {
    //当服务被绑定的时候执行
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    //Ibinder可以直接new 接口
    private IBinder iBinder = new IMyAidlInterface.Stub(){

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d("lxc","成功接收远程传来的数据 a:"+a+"b:"+b);
            return a + b;
        }
    };
}

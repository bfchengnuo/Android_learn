package com.bfchengnuo.asynctaskd;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class ProgressBarD extends AppCompatActivity {

    private ProgressBar progressBar;
    private mAsyncTask mAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar_d);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAsyncTask = new mAsyncTask();
        mAsyncTask.execute();
    }


    //在Activity失去焦点的时候调用的方法
    @Override
    protected void onPause() {
        super.onPause();
        //只是发送了一个取消请求，将AsyncTask标记为cancel状态，但未真正取消线程的执行
        //实际上JAVA语音没办法粗暴地直接停止一个正在运行的线程
        //标记，true就表示继续完成任务，通常都是设置位true
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            mAsyncTask.cancel(true);
        }
    }

    class mAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //模拟进度显示
            for (int i = 0; i < 100; i++) {
                if (isCancelled())
                    break;
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
          //  Log.d("lxc",values[0].toString());
            super.onProgressUpdate(values);
            if (isCancelled())
                return;
            progressBar.setProgress(values[0]);

        }
    }
}

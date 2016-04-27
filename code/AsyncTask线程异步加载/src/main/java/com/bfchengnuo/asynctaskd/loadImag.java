package com.bfchengnuo.asynctaskd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class loadImag extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_imag);
        mProgressBar = (ProgressBar) findViewById(R.id.mprogressBar);
        mImageView = (ImageView) findViewById(R.id.image);
        String url = "http://www.zerodm.tv/wp-content/uploads/2016/04/20160411053434261-600x375.jpg";

        new myAsyncTask().execute(url);
    }


    //采用内部类的形式实现
    class myAsyncTask extends AsyncTask<String, Void, Bitmap> {
        //耗时部分执行前运行，主线程中
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置进度条显示
            mProgressBar.setVisibility(View.VISIBLE);
        }

        //耗时执行部分,只有此部分是运行在次线程中
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection urlConnection;
            InputStream ins;
            try {
                //获取网络连接对象
                urlConnection = new URL(url).openConnection();
                ins = urlConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(ins);
                //通过decodeStream解析输入流
                bitmap = BitmapFactory.decodeStream(bis);
                //关闭流
                ins.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        //处理结束后执行，在主线程中，可以操作UI
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //操作UI 设置图片
            mImageView.setImageBitmap(bitmap);
            mProgressBar.setVisibility(View.GONE); //隐藏进度条
        }
    }
}

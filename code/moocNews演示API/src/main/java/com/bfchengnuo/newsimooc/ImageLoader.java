package com.bfchengnuo.newsimooc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lvxue on 2016/4/28 0028.
 * 使用多线程的方法和AsyncTask方式异步加载
 * 用于处理图片的类，利用多线程防止卡顿
 * <p/>
 * <p/>
 * 关于判断身份
 * Bitmap-刷新ListView加载：
 * 问题是解决：缓存的图片对正确的图片加载时的影响~
 * 问题描述：图片在刷新显示的时候会刷新更改多次，并不是一次到位加载正确图片。
 * 问题分析：ListView会重用convertView,每一个ImageView并没有唯一的标识，多个LIstView中Item的加载在缓冲池中产生多个ImageView，在没有Tag的情况下，重用convertView就会导致新的图片在加载之前先显示旧的图片，根本原因是ListView中特定的Item没有显示正确的URL资源
 * 解决方案：方法1-设置Tag：在首次加载时就为ImageView的标签，标签为正确的URL（身份验证信息）：viewHolder.imageView.setTag(url)，在handler中加载图片时对标签进行判断，若符合，则加载。
 * 方法2-设置成员变量：在showImageByThread中仿照ImageView的方式对URL进行缓存，避免了网络下载时间不确定导致的持续性的混乱
 */
public class ImageLoader {
    private ImageView mImageView;
    private String mURL;

    private LruCache<String, Bitmap> mLruCache;

    public ImageLoader() {
        //获取最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存时调用，告诉系统当前存储的内容有多大
                return value.getByteCount();
            }
        };
    }

    //增加到缓存
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromURL(url) != null) {
            mLruCache.put(url, bitmap);
        }
    }

    //获取缓存数据
    private Bitmap getBitmapFromCache(String url) {
        //本身就是个map集合，url对应bitmap
        return mLruCache.get(url);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //判断身份信息
            if (mImageView.getTag().equals(mURL)) {
                //设置图片
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }

        }
    };

    public void showImageByThread(ImageView imageView, final String url) {
        //利用本地变量缓存，避免网络的不确定造成的显示错乱
        mImageView = imageView;
        mURL = url;
        new Thread() {
            //非主线程不能操作UI
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromURL(url);
                //在获取Message对象的时候是不能用 "new Message" 的方式来获取，而必须使用 Obtain()的方式来获取Message对象
                //它是静态方法,而且只有在spool = null 的情况下才会new出一个Message(),返回一个Message对象,如果在不为空的情况下,
                // Message的对象都是从Message对象池里面拿的实例从而重复使用的,这也为了Android中的Message对象能够更好的回收。
                Message msg = Message.obtain();
                msg.obj = bitmap;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    //解析URL，转换成一个Bitmap并且返回
    public Bitmap getBitmapFromURL(String stringUrl) {
        Bitmap bitmap;
        InputStream is = null;
        // Log.d("lxc",stringUrl);
        try {
            URL url = new URL(stringUrl);
            //创建一个连接,注意是http不是https

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Log.d("lxc", "run");
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            //释放资源连接
            connection.disconnect();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    //    ----------------------------------------------
    public void showImageByAsyncTask(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {
            //去下载图片
            new myAsyncTask(imageView).execute(url);
        }else {
            imageView.setImageBitmap(bitmap);
        }
    }

    //异步加载类
    private class myAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;
        private String mUrl;

        public myAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            mUrl = params[0];
            //将下载的图片保存在缓存中
            Bitmap bitmap = getBitmapFromURL(params[0]);
            if (bitmap != null){
                addBitmapToCache(mUrl,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (imageView.getTag().equals(mUrl)) {
                imageView.setImageBitmap(bitmap);
            }
        }

        //加载指定的图片
        private void loadImages(int start,int end){
            for (int i = start;i<end;i++){
                String url = NewsAdapter.URLS[i];
                Bitmap bitmap = getBitmapFromCache(url);
                if (bitmap == null) {
                    //去下载图片
                    new myAsyncTask(imageView).execute(url);
                }else {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}

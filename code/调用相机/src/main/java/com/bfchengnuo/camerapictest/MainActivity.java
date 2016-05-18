package com.bfchengnuo.camerapictest;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;
    private static final int CHOOSE_PHOTO = 3;
    private ImageView pic;
    private Uri imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pic = (ImageView) findViewById(R.id.pic);
        Intent intent = new Intent();
    }

    public void onCamera(View view) {
        switch (view.getId()) {
            case R.id.btnCare:
                //创建file对象  获取SD卡根目录
                File outPutImage = new File(Environment.getExternalStorageDirectory(), "test.jpg");
                try {
                    if (outPutImage.exists()) {
                        outPutImage.delete();  //如果存在就先删除
                    }
                    outPutImage.createNewFile();  //不存在就创建一个新的空文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUrl = Uri.fromFile(outPutImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
                startActivityForResult(intent,TAKE_PHOTO); //启动相机程序
                break;
            case R.id.btnPhoto:
                Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                intent1.setType("image/*");
                startActivityForResult(intent1,CHOOSE_PHOTO);  //打开相册
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //根据请求码确定是谁传过来的
        switch (requestCode){
            case TAKE_PHOTO:
                //判断返回结果  是否成功执行
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //用于安卓打开各种文件，第二个参数是一个MIME对应的是文件格式  本质是个MAP集合
                    intent.setDataAndType(imageUrl,"image/*");
                    intent.putExtra("scale",true); //设置缩放
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
                    //启动裁剪程序
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                        pic.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机的版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4以上系统使用
                        imageOnkitkat(data);
                    }else {
                        imageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    //4.4以下的版本 url没有加密 直接解析设置
    private void imageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    //4.4以上系统
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void imageOnkitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的url就通过 document的ID进行处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                //判断是否是media格式的，如果是还要在进行一次解析才能得到真正的数字ID
                String id = docID.split(":")[1];  //截取后半部分获取ID
                String selection = MediaStore.Images.Media._ID + "=" + id; //拼凑起新的地址ID
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果不是document类型的url 用普通的方式解析
            imagePath = getImagePath(uri,null);
        }

        displayImage(imagePath);
    }

    //设置图片
    private void displayImage(String imagePath) {
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            pic.setImageBitmap(bitmap);
        }
    }

    //获取图片路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过url和selection来获取真正的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);  //获取游标
        if (cursor != null){
            if (cursor.moveToNext()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}

package com.bfchengnuo.sharedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends Activity {

    private ImageView imageView;
    private Button btn;
    private TextView text;
    private Uri imageUrl;
    private final String APP_ID = "wx22e2ff8ec7a314c0";
    private IWXAPI api; //微信通信的接口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.beijing);
        btn = (Button) findViewById(R.id.btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items_list = {"本地图库", "打开相机"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("选择来源");
                builder.setItems(items_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //Toast.makeText(MainActivity.this, "点击了"+items_list[which], Toast.LENGTH_SHORT).show();

                        switch (which){
                            case 0:
                                // ACTION_PICK  从数据中选择一个子项目，并返回你所选中的项目
                                Intent intent = new Intent(Intent.ACTION_PICK,null);
                                //获取系统图库
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                                startActivityForResult(intent,1);
                                break;
                            case 1:
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
                                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
                                startActivityForResult(intent1,2); //启动相机程序
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();  //获取
                dialog.show();          //显示
            }
        });

        text = (EditText) findViewById(R.id.text);
        text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/test.ttf"));

        api = WXAPIFactory.createWXAPI(this,APP_ID,true); //获取实例
        api.registerApp(APP_ID); //注册
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weChatShare();
                btn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void weChatShare(){
        Bitmap bmp = screenshot();
        //初始化
        WXImageObject imageObject = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        //设置缩略图
        /*
        Bitmap slBmp = Bitmap.createScaledBitmap(bmp,120,150,true);
        bmp.recycle(); //释放图片所占的资源
        msg.thumbData = bmpToByteArray*/
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    private Bitmap screenshot(){
        //隐藏按钮
        btn.setVisibility(View.INVISIBLE);
        View view = getWindow().getDecorView();
        //启动绘图缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(); //调用截图功能
        return view.getDrawingCache();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            if (data != null){
                imageView.setImageURI(data.getData());
            }
        }else if (resultCode == RESULT_OK && requestCode == 2){
            Intent intent = new Intent("com.android.camera.action.CROP");
            //用于安卓打开各种文件，第二个参数是一个MIME对应的是文件格式  本质是个MAP集合
            intent.setDataAndType(imageUrl,"image/*");
            intent.putExtra("scale",true); //设置缩放
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
            //启动裁剪程序
            startActivityForResult(intent,3);
        }else if (resultCode == RESULT_OK && requestCode == 3){
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

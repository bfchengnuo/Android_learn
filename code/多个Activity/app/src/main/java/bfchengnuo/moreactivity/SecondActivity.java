package bfchengnuo.moreactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        绑定第二个布局文件，不要忘了去注册
        setContentView(R.layout.second);

        Intent intent = getIntent();
        String s = intent.getStringExtra("main");
        Toast.makeText(this,"接收到的数据为"+s,Toast.LENGTH_SHORT).show();
        Log.d("second",s);
    }

}

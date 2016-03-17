package bfchengnuo.moreactivity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by lvxue on 2016/3/17 0017.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        绑定第二个布局文件，不要忘了去注册
        setContentView(R.layout.second);
    }
}

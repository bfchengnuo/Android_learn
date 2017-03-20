package com.bfchengnuo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private long closeTime;
    private String[] yTexts = new String[] { "10k", "8k", "6k", "5k", "3k", "1k" };
    private String[] xTexts = new String[] { "0", "1", "2", "3", "4", "5", "6" };
    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = (MyView) findViewById(R.id.myView);
        myView.setyTexts(yTexts);
        myView.setxTexts(xTexts);
        int[] poins = new int[xTexts.length];
        for (int i = 0; i < xTexts.length; i++) {
            poins[i] = (int)(Math.random() * 6);
        }
        myView.setPoints(poins);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - closeTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            closeTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}

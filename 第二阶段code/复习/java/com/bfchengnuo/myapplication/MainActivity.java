package com.bfchengnuo.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private NavigationView mNavView;
    private DrawerLayout mDrawerLayout;
    private long closeTime;
    private String[] yTexts = new String[]{"10k","8k","6k","5k","3k","1k"};
    private String[] xTexts = new String[]{"0" , "1","2","3","4","5","6"};
    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        myView = (MyView) findViewById(R.id.myView);
        myView.setyTexts(yTexts);
        myView.setxTexts(xTexts);
        int[] poins = new int[xTexts.length];
        for (int i = 0; i < xTexts.length; i++) {
            poins[i] = (int)(Math.random() * 6);
        }
        myView.setPoints(poins);

        mNavView.getMenu().getItem(0).setChecked(true);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_1:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        setFragmentShow(new SocketFragment());
                        break;
                    case R.id.menu_2:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        setFragmentShow(new TimerFragment());
                        break;
                    case R.id.menu_3:
                        setFragmentShow(new MathFragment());
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_4:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        setFragmentShow(new PlayFragment());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            if (System.currentTimeMillis() - closeTime > 2000){
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                closeTime = System.currentTimeMillis();
            }else {
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
                System.exit(0);
            }
        }
    }

    private void setFragmentShow(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        // 设置标准的转场动画
       // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out);
        transaction.replace(R.id.fragment_show,fragment);
        transaction.commit();
    }
}

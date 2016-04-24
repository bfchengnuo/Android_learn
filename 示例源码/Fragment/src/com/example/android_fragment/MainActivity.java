package com.example.android_fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity implements OnCheckedChangeListener
 {

	private RadioGroup group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		group = (RadioGroup) findViewById(R.id.radiogroup);
		group.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		switch (checkedId) {
		case R.id.first: {
            Intent intent=new Intent(this,MainActivity2.class);
            startActivity(intent);
			break;

		}
		case R.id.second: {
            MyFragment2 fragment2=new MyFragment2();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.add(R.id.frame, fragment2);
            beginTransaction.addToBackStack(null);
            beginTransaction.commit();
			break;
		}
		case R.id.thrid: {
            Intent intent=new Intent(MainActivity.this,MainActivity3.class);
            startActivity(intent);

			break;
		}
		case R.id.fourth: {
			Intent intent=new Intent(MainActivity.this,MainActivity4.class);
            startActivity(intent);

			break;
		 }
		}
	}


	

	

}

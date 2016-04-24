package com.example.android_fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity3 extends Activity {

	private Button button;
	private Fragment frag;
	private boolean flag = true;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		init();
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction beginTransaction = fragmentManager
						.beginTransaction();
				
				if (flag) {
					MyFragment4 frag4=new MyFragment4();
					beginTransaction.replace(R.id.layout, frag4);
					flag=false;

				} else {
					MyFragment3 frag3=new MyFragment3();
					beginTransaction.replace(R.id.layout, frag3);
					flag=true;

				}
				
			   beginTransaction.commit();
			}
		});

	}

	private void init() {
		// TODO Auto-generated method stub

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction beginTransaction = fragmentManager
				.beginTransaction();
		MyFragment3 frag3 = new MyFragment3();
		beginTransaction.add(R.id.layout,frag3);
		beginTransaction.commit();
	}

}

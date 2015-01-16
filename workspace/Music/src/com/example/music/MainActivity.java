package com.example.music;



import android.os.Bundle;

import android.app.Activity;
import android.app.TabActivity;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		
	

	
		Resources resources=getResources();
		TabHost tabHost=getTabHost();
        TabHost.TabSpec spec; 

//		tabHost.setup();
		 spec=tabHost.newTabSpec("tab1").setIndicator("歌曲", resources.getDrawable(R.drawable.ic_launcher));
		tabHost.addTab(spec);
		
//		TabSpec spec2=tabHost.newTabSpec("tab2");
//		View view2=View.inflate(this, R.layout.tab_item2, null);
//		spec1.setIndicator(view2);
//		tabHost.addTab(spec2);
//
//		
//		TabSpec spec3=tabHost.newTabSpec("tab3");
//		View view3=View.inflate(this, R.layout.tab_item3, null);
//		spec1.setIndicator(view3);
//		tabHost.addTab(spec3);
//
//	
//		TabSpec spec4=tabHost.newTabSpec("tab4");
//		View view4=View.inflate(this, R.layout.tab_item4, null);
//		spec1.setIndicator(view4);
//		tabHost.addTab(spec4);
	}



}

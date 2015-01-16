package com.example.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.frag_02);
    	ImageView imageView=(ImageView) findViewById(R.id.imageView1);
    	imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=getIntent();
				Bundle bundle=new Bundle();
				String str1="bbbb";
				bundle.putString("str2", str1);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
    	
    }
}

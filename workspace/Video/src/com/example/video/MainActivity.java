package com.example.video;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	public static  SurfaceView scree;
	private CheckedTextView view;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent=new Intent(this,PlayService.class);
		startService(intent);
		
		scree=(SurfaceView)findViewById(R.id.surfaceView1);
		scree.getHolder().setFixedSize(176, 144);
		scree.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		scree.getHolder().setKeepScreenOn(true);

		scree.setOnClickListener(this);
		
		view=(CheckedTextView)findViewById(R.id.checkedTextView1);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
         int id=arg0.getId();
         Intent intentb=new Intent();
         intentb.setAction("com.ww.com");
         if (id==R.id.surfaceView1) {
             String tagString=scree.getTag().toString();
        if ("播放".equals(tagString)) {
        	intentb.putExtra("msg", 1);
	        scree.setTag("暂停");
	        view.setText("视频播放中");
          }else if ("暂停".equals(tagString)) {
        	  intentb.putExtra("msg", 2);
  	        scree.setTag("播放");
	        view.setText("点击屏幕继续播放");

		}

		} 
		sendBroadcast(intentb);
	}

}

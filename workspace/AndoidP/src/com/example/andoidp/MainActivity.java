package com.example.andoidp;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SharedPreferences sharedPreferences;
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		final Button btn1=(Button) findViewById(R.id.button1);
		final Button btn2=(Button) findViewById(R.id.button2);
		final Button btn4=(Button) findViewById(R.id.button4);

		final TextView tv1=(TextView) findViewById(R.id.textView1);
		final TextView tv2=(TextView) findViewById(R.id.textView2);

		final ImageView img1=(ImageView) findViewById(R.id.imageView1);
		 sharedPreferences=getSharedPreferences("sharedPreference1", Activity.MODE_PRIVATE);
  final  Button btn3=(Button) findViewById(R.id.button3);
  btn3.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MainActivity.this,SQLiteActivity.class);
		startActivity(intent);
	}
});
		btn1.setOnClickListener(new OnClickListener() {
			
			@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Date date=new Date();
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time=simpleDateFormat.format(date);
				tv2.setText(time);
				img1.setVisibility(View.VISIBLE);
				img1.setImageResource(R.drawable.logo_wechat);
				
				SharedPreferences.Editor editor=sharedPreferences.edit();
				editor.putBoolean("isTrue", true);
				editor.putString("text", "sharedPreference使用成功");
				editor.commit();
				
			}
		});
	
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//String text=sharedPreferences.getString("text", "");
				Map<String, ?> allMap=sharedPreferences.getAll();
				String text=(String) allMap.get("text");
				boolean zhen=(Boolean) allMap.get("isTrue");
				boolean isExist=sharedPreferences.contains("text");
				tv1.setText(text+zhen);
				img1.setVisibility(View.GONE);
				Toast.makeText(MainActivity.this, ""+isExist, 0).show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

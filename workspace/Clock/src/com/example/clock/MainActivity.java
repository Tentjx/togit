package com.example.clock;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvtime;
private ImageView hourImageView;
private ImageView minuteImageView;
private ImageView secongImageView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvtime = (TextView) findViewById(R.id.textView1);
          hourImageView=(ImageView) findViewById(R.id.hour);
          minuteImageView=(ImageView) findViewById(R.id.minute);
          secongImageView=(ImageView) findViewById(R.id.second);

         new TimeThread().start();
	}
	public void start(View v) {
		Animation animHour=AnimationUtils.loadAnimation(this, R.anim.hour_demo);
		Animation animMinute=AnimationUtils.loadAnimation(this, R.anim.minute_demo);
		Animation animSecond=AnimationUtils.loadAnimation(this, R.anim.second_demo);
		hourImageView.startAnimation(animHour);
		minuteImageView.startAnimation(animMinute);
		secongImageView.startAnimation(animSecond);

		

	}
	 class TimeThread extends Thread {
	        @Override
	        public void run() {
	            do {
	                try {
	                    Thread.sleep(1000);
	                    Message msg = new Message();
	                    msg.what = 1;  //消息(一个整型值)
	                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            } while (true);
	        }
	    }
	 
	 private Handler mHandler=new Handler(){
		 @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            switch (msg.what) {
	            case 1:
					SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            	Date date=new Date();
					String time=simpleDateFormat.format(date);
					
					tvtime.setText(time);
					
                    break;
                    default:
                    	break;
	            }
	            }
	 };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 
}

package com.example.m;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText inputPath;
	private Button playPause;
	private int temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inputPath = (EditText)findViewById(R.id.editText1);
		Intent intent = new Intent(this,PlayService.class);
		startService(intent);
		playPause = (Button)findViewById(R.id.button1);
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		MyPhoneListener listener = new MyPhoneListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	class MyPhoneListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			Intent intent = new Intent();
			intent.putExtra("musicPath", inputPath.getText().toString());
			intent.setAction("com.gemptc.action.PLAYCONTROLACTION");
			if(TelephonyManager.CALL_STATE_IDLE == state){
				intent.putExtra("control", 1);
			}else if(TelephonyManager.CALL_STATE_RINGING == state){
				intent.putExtra("control", 2);
			}
			if(temp != 0){
				sendBroadcast(intent);
			}
		}
	}
	
	public void click(View v){
		temp ++;//解决第一次播放
		int id = v.getId();
		Button btn = (Button)v;
		Intent intent = new Intent();
		intent.putExtra("musicPath", inputPath.getText().toString());
		intent.setAction("com.gemptc.action.PLAYCONTROLACTION");
		if(id == R.id.button1){//播放和暂停
			String label = btn.getText().toString();
			if("播放".equals(label)){
				intent.putExtra("control", 1);//播放
				btn.setText("暂停");
				
			}else if("暂停".equals(label)){
				intent.putExtra("control", 2);//暂停
				btn.setText("播放");
			}
		}else if(id == R.id.button2){//停止
			intent.putExtra("control", 3);//停止
			playPause.setText("播放");
		}else if(id == R.id.button3){//重播
			intent.putExtra("control", 4);//重播
			playPause.setText("暂停");
		}
		sendBroadcast(intent);
	}
}


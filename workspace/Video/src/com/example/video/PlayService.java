package com.example.video;

import java.io.IOException;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlayService extends Service {
	private MediaPlayer mediaPlayer;
	private boolean isPause;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mediaPlayer = new MediaPlayer();

		VideoReceiver videoReceiver = new VideoReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.ww.com");
		registerReceiver(videoReceiver, intentFilter);

	}

	class VideoReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			int msg = intent.getIntExtra("msg", 0);
			if (msg == 1) {
				if (isPause) {
					mediaPlayer.start();
				} else {
					try {
						mediaPlayer.reset();
						mediaPlayer
								.setAudioStreamType(AudioManager.STREAM_MUSIC);
						mediaPlayer.setDisplay(MainActivity.scree.getHolder());
						mediaPlayer
								.setDataSource("/storage/sdcard0/baidu/63.mp4");
						mediaPlayer.prepare();
						mediaPlayer.start();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
             isPause=false;
			}else if (msg==2) {
				mediaPlayer.pause();
				isPause=true;
			}

		}
	}
}

package com.example.m;


import java.io.IOException;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;

public class PlayService extends Service {
	private MediaPlayer player;
	private boolean isPause;//pause true  

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		player=  new MediaPlayer();
		ControlReceiver receiver = new ControlReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.gemptc.action.PLAYCONTROLACTION");
		registerReceiver(receiver, filter);
	}
	
	public void play(String musicPath){
		try {
			player.reset();
			player.setDataSource(musicPath);
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.prepareAsync();
			player.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					player.start();
				}
			});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class ControlReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			int control = intent.getIntExtra("control", 0);
			String musicPath = intent.getStringExtra("musicPath");
			if(control == 1){//播放
				if(isPause){
					player.start();
				}else{
					play(musicPath);
				}
				isPause = false;
			}else if(control == 2){//暂停
				if(player.isPlaying()){
					player.pause();
				}
				isPause = true;
			}else if(control == 3){//ֹͣ停止
				if(player.isPlaying()){
					player.stop();
				}
			}else if(control == 4){//重播
				if(player.isPlaying()){
					player.seekTo(0);
					player.start();
				}else{
					play(musicPath);
				}
			}
		}
	}
}

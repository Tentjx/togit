package com.example.service;

import java.io.IOException;

import com.example.fragment.PlayFragment;
import com.example.vo.MyConstent;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class PlayMusicService extends Service implements OnCompletionListener{

	private MediaPlayer mediaplaer;
//	private long duration;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if(mediaplaer!=null){
			mediaplaer.reset();
			mediaplaer.release();
			mediaplaer=null;
		}
		mediaplaer=new MediaPlayer();
		mediaplaer.setOnCompletionListener(this);
		Log.i("PlayMusicService", "已经启动！");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		int user_option=intent.getIntExtra("user_option", MyConstent.PLAY_MUSIC);
		Message msg=new Message();
		if(user_option==MyConstent.PLAY_MUSIC){
//			duration=intent.getIntExtra("duration", 0);
//			duration=Integer.MAX_VALUE;
			msg.what=1;
			PlayFragment.update.sendMessage(msg);
			PlayFragment.UpdatePlayState.handleMessage(msg);
			playmusic(intent.getStringExtra("music_uri"));
		}else if(MyConstent.PUASE_MUSIC==user_option){
			if(mediaplaer.isPlaying()){
				msg.what=0;
				PlayFragment.UpdatePlayState.handleMessage(msg);
				mediaplaer.pause();
			}else{
				msg.what=1;
				PlayFragment.UpdatePlayState.handleMessage(msg);
				mediaplaer.start();
			}
		}else if(MyConstent.FAST_FORWARD==user_option){
			mediaplaer.seekTo(intent.getIntExtra("fast_forward", 0));
		}
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		if(mediaplaer.getDuration()-mediaplaer.getCurrentPosition()<300){
			Intent intent=new Intent("com.example.waterworld.play.COMPLITION");
			sendOrderedBroadcast(intent, null);
			Toast.makeText(getApplicationContext(), "广播已发送", Toast.LENGTH_SHORT).show();
		}
	}

	private void playmusic(String music_uri){
		if(PlayFragment.lrc_view!=null)
			PlayFragment.lrc_view.hasLrc(music_uri.substring(0,music_uri.length()-4)+".lrc");
			try {
				mediaplaer.reset();
				mediaplaer.setDataSource(music_uri);
				mediaplaer.prepare();
				mediaplaer.start();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		new Thread(new SeekBarUpdateRunnable()).start();
	}
	
	class SeekBarUpdateRunnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int current=0;
			Message msg=new Message();
			while(mediaplaer!=null){
				try {
					Thread.sleep(100);
					current=mediaplaer.getCurrentPosition();
					msg.what=current;
					if(PlayFragment.seekbarhandler!=null)
						PlayFragment.seekbarhandler.handleMessage(msg);
//					Log.i("seekbarupdate", "正在运行！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	

	
}

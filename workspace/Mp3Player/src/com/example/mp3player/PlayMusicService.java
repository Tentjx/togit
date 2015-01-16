package com.example.mp3player;

import java.io.IOException;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;

public class PlayMusicService extends Service {
	static MediaPlayer mediaPlayer;
	public static boolean isPause;
	 public static int position;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mediaPlayer = new MediaPlayer();
		MusicControlBroadReceiver musicControlBroadReceiver = new MusicControlBroadReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.waterworld.action.PLAYMUSICBROAD");
		registerReceiver(musicControlBroadReceiver, intentFilter);
	
        PlayActivity.progressSekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				if (arg2) {
					mediaPlayer.seekTo(arg1);
					mediaPlayer.start();
				}
				
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub

				
			if (mediaPlayer.getDuration()==mediaPlayer.getCurrentPosition()) {

					PlayActivity.animation.cancel();
					Intent intentCompletionBroad = new Intent();
					intentCompletionBroad
							.setAction("com.waterworld.action.COMPLETIONBROAD");
					intentCompletionBroad.putExtra("completion", "completed");
					sendBroadcast(intentCompletionBroad);
				}
			}

		});
		
	}
  

	 class TimeThread extends Thread {
	        @Override
	        public void run() {
	            do {
	                try {
	                    Thread.sleep(100);
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
						 position = mediaPlayer.getCurrentPosition();
		            	 PlayActivity.progressSekbar.setProgress(
		            			 position);
		    			String currentTime = TimeFormat.changeTime(position);

		    			PlayActivity.currentTime.setText(currentTime);
					
	            	
                    break;
	         

                    default:
                    	break;
	            }
	            }
	 };

	class MusicControlBroadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int broadMessage = intent.getIntExtra("broadMessage", 0);
			String musicPath = intent.getStringExtra("musicPath");
			Uri musicUri = Uri.parse(musicPath);

			if (broadMessage == 1) {
				if (isPause) {
					mediaPlayer.start();
				} else {
					try {
						mediaPlayer.reset();
						mediaPlayer
								.setAudioStreamType(AudioManager.STREAM_MUSIC);
						mediaPlayer.setDataSource(getApplicationContext(),
								musicUri);
						mediaPlayer.prepareAsync();
						mediaPlayer
								.setOnPreparedListener(new OnPreparedListener() {

									@Override
									public void onPrepared(MediaPlayer arg0) {
										// TODO Auto-generated method stub
										mediaPlayer.start();
										new TimeThread().start();
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
				isPause = false;
			} else if (broadMessage == 2) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
				isPause = true;
			} else if (broadMessage == 5) {
				if (isPause || mediaPlayer.isPlaying()) {
					mediaPlayer.stop(); 
			
				}
				isPause = false;

			}
		}

	}
	

}

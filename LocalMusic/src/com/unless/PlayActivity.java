package com.unless;

import android.os.Bundle;
import android.os.Message;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;

import java.io.IOException;

import com.example.activity.MainActivity;
import com.example.activity.PlayMusicAcitvity;
import com.example.entity.Music;
import com.example.entity.TimeFormat;
import com.example.localmusic.R;
import com.lrc.LyricView;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayActivity extends Activity {
	private LyricView lyricView;
	private MediaPlayer mediaPlayer;
	private SeekBar seekBar;
	private String mp3Path;
	private int INTERVAL = 45;// 歌词每行的间隔
	private ImageView img_play;
	private int position;
	private String musicPath;
	private TextView tv_top_musicName;
	private TextView tv_totalTime;
	private TextView tv_currentTime;
	private ImageView img_pre;
	//private String mPath;
	private ImageView img_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.opyofplaying_music);
		lyricView = (LyricView) findViewById(R.id.mylrc);
		mediaPlayer = new MediaPlayer();
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent=getIntent();
		 position=intent.getIntExtra("position", 0);
		 musicPath=intent.getStringExtra("musicPath");
	     
		 ResetMusic(musicPath);

		//mp3Path = "/storage/sdcard0/baidu/痛快.mp3";
		//ResetMusic(mp3Path);
		SerchLrc();
		lyricView.SetTextSize();

		initView(position);
		
		img_play = (ImageView) findViewById(R.id.img_play);
        img_pre= (ImageView) findViewById(R.id.Img_pre);
        img_next = (ImageView) findViewById(R.id.Img_next);
         
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					mediaPlayer.seekTo(progress);
					String currentTime = TimeFormat.changeTime((int)mediaPlayer.getCurrentPosition());
	    			tv_currentTime.setText(currentTime);
					lyricView.setOffsetY(220 - lyricView.SelectIndex(progress)
							* (lyricView.getSIZEWORD() + INTERVAL - 1));

				}
			}
		});
		img_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mediaPlayer.isPlaying()) {
					// button.setText("播放");
					img_play.setImageResource(R.drawable.play_nomal);
					mediaPlayer.pause();
				} else {
					img_play.setImageResource(R.drawable.pause_nomal);
					mediaPlayer.start();
					lyricView.setOffsetY(220
							- lyricView.SelectIndex(mediaPlayer
									.getCurrentPosition())
							* (lyricView.getSIZEWORD() + INTERVAL - 1));

				}
			}
		});
		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						ResetMusic(musicPath);
						lyricView.SetTextSize();
						lyricView.setOffsetY(200);
						mediaPlayer.start();
					}
				});
		seekBar.setMax(mediaPlayer.getDuration());
		new Thread(new runable()).start();
	}

	public void SerchLrc() {
		String lrc = musicPath;
		lrc = lrc.substring(0, lrc.length() - 4).trim() + ".lrc".trim();
		LyricView.read(lrc);
		lyricView.SetTextSize();
		lyricView.setOffsetY(350);
	}

	public void ResetMusic(String path) {

		mediaPlayer.reset();
		try {

			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
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

	class runable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {

				try {
					Thread.sleep(100);
					if (mediaPlayer.isPlaying()) {
						lyricView.setOffsetY(lyricView.getOffsetY()
								- lyricView.SpeedLrc());
						lyricView.SelectIndex(mediaPlayer.getCurrentPosition());
						seekBar.setProgress(mediaPlayer.getCurrentPosition());
					//	tv_totalTime.setText(TimeFormat.changeTime(mediaPlayer.getCurrentPosition()));
						
						mHandler.post(mUpdateResults);
						
						 Message msg = new Message();
		                    msg.what = 1;  //消息(一个整型值)
		                    mHandler.sendMessage(msg);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler mHandler = new Handler(){
		private int cposition;

		@Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            switch (msg.what) {
	            case 1:
						 cposition = mediaPlayer.getCurrentPosition();
		            	
		    			String currentTime = TimeFormat.changeTime(cposition);

		    			tv_currentTime.setText(currentTime);
					
	            	
                  break;
	         

                  default:
                  	break;
	            }
	            }
	};
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			lyricView.invalidate(); // 更新视图
		}
	};
	

private void initView(int musicPosition) {
    tv_top_musicName=(TextView) findViewById(R.id.tv_prev_info);
    tv_totalTime=(TextView) findViewById(R.id.tv_totaltime);
    tv_currentTime=(TextView) findViewById(R.id.tv_currentime);

   Music music = (Music) MainActivity.musicList.get(musicPosition);
    musicPath = music.getMusicPath();
	String	musicName = music.getMusicName();
	//String	singer = music.getSinger();
	//int time = (int) music.getMusicTime();
	String totalTime = TimeFormat.changeTime((int) music.getMusicTime());
	//String album = music.getAlbum();

	tv_top_musicName.setText(musicName);
	tv_totalTime.setText(totalTime);
	tv_currentTime.setText("00:00");
	
	
}
}

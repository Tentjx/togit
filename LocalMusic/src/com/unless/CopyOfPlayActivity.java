package com.unless;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;

import java.io.IOException;
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

public class CopyOfPlayActivity extends Activity {
	private LyricView lyricView;
	private MediaPlayer mediaPlayer;
	private SeekBar seekBar;
	private String mp3Path;
	private int INTERVAL = 45;// 歌词每行的间隔
	private ImageView img_play;

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
		/*Intent intent=getIntent();
		int position=intent.getIntExtra("position", 0);
		String musicPath=intent.getStringExtra("musicPath");*/
		
		mp3Path = "/storage/sdcard0/baidu/痛快.mp3";
		ResetMusic(mp3Path);
		SerchLrc();
		lyricView.SetTextSize();

		img_play = (ImageView) findViewById(R.id.img_play);
		img_play.setImageResource(R.drawable.play_nomal);
		// button.setText("播放");

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
					img_play.setImageResource(R.drawable.pause_nomal);
					mediaPlayer.pause();
				} else {
					img_play.setImageResource(R.drawable.play_nomal);
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
						ResetMusic(mp3Path);
						lyricView.SetTextSize();
						lyricView.setOffsetY(200);
						mediaPlayer.start();
					}
				});
		seekBar.setMax(mediaPlayer.getDuration());
		new Thread(new runable()).start();
	}

	public void SerchLrc() {
		String lrc = mp3Path;
		lrc = lrc.substring(0, lrc.length() - 4).trim() + ".lrc".trim();
		LyricView.read(lrc);
		lyricView.SetTextSize();
		lyricView.setOffsetY(350);
	}

	public void ResetMusic(String path) {

		mediaPlayer.reset();
		try {

			mediaPlayer.setDataSource(mp3Path);
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
						mHandler.post(mUpdateResults);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler mHandler = new Handler();
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			lyricView.invalidate(); // 更新视图
		}
	};

}

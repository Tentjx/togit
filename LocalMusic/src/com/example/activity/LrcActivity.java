package com.example.activity;


import com.example.localmusic.R;
import com.example.service.PlayMusicSevice;
import com.lrc.LyricView;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LrcActivity extends Activity {

	private static LyricView lyricView;
	private int INTERVAL = 30;// 歌词每行的间隔
	private String mp3Path;
	private TextView textView;
	public static boolean isScroll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.newplay);
		Intent intent = getIntent();
		String musicName = intent.getStringExtra("musicName");
		 textView = (TextView) findViewById(R.id.tv_prev_info);
		 textView.setText(musicName);
		// mp3Path=intent.getStringExtra("musicPath");
		mp3Path = PlayMusicSevice.musicPath;
		lyricView = (LyricView) findViewById(R.id.mylrc);

		SerchLrc();
		lyricView.SetTextSize();

		// PlayMusicSevice.mediaPlayer.setOnCompletionListener(new
		// OnCompletionListener() {
		//
		// @Override
		// public void onCompletion(MediaPlayer arg0) {
		// // TODO Auto-generated method stub
		// SerchLrc();
		// lyricView.SetTextSize();
		// lyricView.setOffsetY(200);
		//
		//
		// }
		// });
		new Thread(new runable()).start();

		UpdateLrcBroadReceiver updateLrcBroadReceiver = new UpdateLrcBroadReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.wtwd.action.UPDATELRC");
		registerReceiver(updateLrcBroadReceiver, intentFilter);

		LinearLayout linearLayoutBack = (LinearLayout) findViewById(R.id.playactivity_linear_back);
		linearLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyricView.setOffsetY(300
				- lyricView.SelectIndex(PlayMusicSevice.mediaPlayer
						.getCurrentPosition())
				* (lyricView.getSIZEWORD() + INTERVAL - 1));
	}

	public void SerchLrc() {
		String lrc = mp3Path;
		lrc = lrc.substring(0, lrc.length() - 4).trim() + ".lrc".trim();
		LyricView.read(lrc);
		lyricView.SetTextSize();
		lyricView.setOffsetY(500);
	}

	class runable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {

				try {
					Thread.sleep(100);
					if (PlayMusicSevice.mediaPlayer.isPlaying()&&isScroll) {
						lyricView.setOffsetY(lyricView.getOffsetY()
								- lyricView.SpeedLrc());
						lyricView.SelectIndex(PlayMusicSevice.mediaPlayer
								.getCurrentPosition());
						// seekBar.setProgress(mediaPlayer.getCurrentPosition());
						mHandler.post(mUpdateResults);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Handler mHandler = new Handler();
	public static Runnable mUpdateResults = new Runnable() {
		public void run() {
			lyricView.invalidate(); // 更新视图
		}
	};

	class UpdateLrcBroadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String broadMsg = arg1.getStringExtra("update");
		    final String path = arg1.getStringExtra("musicPath");
			
		  
			mp3Path=path;
			if (broadMsg.equals("do")) {
				SerchLrc();
			    lyricView.SetTextSize();
				lyricView.setOffsetY(500);
				
		        String musicName = path;
			    musicName = musicName.substring(musicName.lastIndexOf("/")+1, musicName.length() - 4).trim();
				textView.setText(musicName);
			}
	
		}

	}
}

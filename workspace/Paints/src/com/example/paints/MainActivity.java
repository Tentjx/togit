package com.example.paints;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private LrcView mWordView;
	private List<Integer> mTimeList;
	private MediaPlayer mPlayer;

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPlayer.stop();
				finish();
			}
		});

		mWordView = (LrcView) findViewById(R.id.text);

		mPlayer = new MediaPlayer();
		mPlayer.reset();
		LrcHandle lrcHandler = new LrcHandle();
		try {
			lrcHandler.readLRC("/storage/sdcard/baidu/丁当-痛快.lrc");
			mTimeList = lrcHandler.getTime();
			mPlayer.setDataSource("/storage/sdcard/baidu/丁当-痛快.mp3");
			mPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		final Handler handler = new Handler();
		mPlayer.start();
		new Thread(new Runnable() {
			int i = 0;

			@Override
			public void run() {
				while (mPlayer.isPlaying()) {
					handler.post(new Runnable() {

						@Override
						public void run() {
							mWordView.invalidate();
						}
					});
					try {
						Thread.sleep((mTimeList.get(i + 1)
								- mTimeList.get(i)));
					} catch (InterruptedException e) {
					}
					i++;
					if (i == mTimeList.size() - 1) {
						mPlayer.stop();
						break;
					}
				}
			}
		}).start();
	}
}

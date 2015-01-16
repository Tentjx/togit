package com.jk.service;

import java.io.IOException;

import com.jk.impl.IMusic;
import com.jk.impl.onMusicOver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.opengl.Visibility;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class musicService extends Service {

	private MediaPlayer mp;
	private String path;
	private boolean f = false;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("onBind");

		path = intent.getStringExtra("path");
		System.out.println(path);
		return new MyBinder();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("onCreate");
		// ��������
		try {
			// �õ�ý�岥����
			mp = new MediaPlayer();

			// mp.pause();
			// mp.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		System.out.println("onStart");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestroy");

	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("onUnbind");
		return super.onUnbind(intent);
	}

	// ��ͣ
	public void pause() {
		mp.pause();
	}

	// ����
	public void resume() {
		mp.start();
	}

	class MyBinder extends Binder implements IMusic {
		// MyService getService(){
		// return MyService.this;
		// }
		private SeekBar seekbar;
		Handler handle = new Handler() {

		};
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// ���½�����
				if (mp.isPlaying()) {
					seekbar.setProgress(mp.getCurrentPosition());
					
				}
				handle.postDelayed(runnable, 500);
			}
		};

		public void dopause() {
			// ���õı���MyService����ͣ
			f=false;
			pause();
		}  

		public void doresume() {
			resume();
		}
		public void dorevmo(){
			handle.removeCallbacks(runnable);
		}
     
		public void init(SeekBar seekbar,final onMusicOver over) {
			System.out.println("*********init******");
			this.seekbar = seekbar;
			// ����
			mp.reset();
			
			// ���ò�����Դ
			try {
				mp.setDataSource(path);
				mp.prepare();
				seekbar.setMax(mp.getDuration());
				//�����϶��������ı��ʱ��ļ�������
				seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
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
						// fromUser  �Ƿ����û�����
						
						if(fromUser){
							mp.seekTo(progress);
							
						}
					}
				});
				// ���ý���
				mp.start();
			f=true;
				handle.post(runnable);
				mp.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						over.onMusicOver();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void dostart() {
			try {
				mp.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void dostop() {
			// TODO Auto-generated method stub
			if (mp != null && mp.isPlaying()) {
				mp.stop();
				mp.release();
				mp = null;
			}
		}

		public void doRset() {
			mp.release();
		}
	}

}

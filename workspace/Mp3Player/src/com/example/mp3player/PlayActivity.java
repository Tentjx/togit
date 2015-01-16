package com.example.mp3player;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayActivity extends Activity implements OnClickListener {
	public static ImageButton btn_play;
	private ImageButton btn_pervious;
	private ImageButton btn_next;
	private ImageButton btn_stop;
	private String musicPath;
	public static ImageView img_roate;
	public static Animation animation;
	private int musicId;
	public static SeekBar progressSekbar;
	private long musicTime;
	private Intent intentBroadCast;
	private LinearLayout linearLayout;
	public static TextView tv_totalTime ;
	public static TextView currentTime;

	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar aBar = getActionBar();
		aBar.hide();
		
		setContentView(R.layout.play_music);
		
		// -------接收歌曲信息并设置在界面上
		Intent intent = getIntent();
		musicId = intent.getIntExtra("musicId", 0);
		
		 linearLayout=(LinearLayout) findViewById(R.id.ll_seekbar);

		
		inintView(musicId);
         
		// ---------------初始化控件
		img_roate = (ImageView) findViewById(R.id.imageView1);
		btn_play = (ImageButton) findViewById(R.id.imgbtn_play);
		btn_play.setOnClickListener(this);
		btn_pervious = (ImageButton) findViewById(R.id.imgbtn_previous);
		btn_pervious.setOnClickListener(this);
		btn_next = (ImageButton) findViewById(R.id.imgbtn_next);
		btn_next.setOnClickListener(this);
		btn_stop = (ImageButton) findViewById(R.id.imgbtn_stop);
		btn_stop.setOnClickListener(this);
		
		
		// -------启动服务控制播放状态---------------
		Intent intentService = new Intent(this, PlayMusicService.class);
		startService(intentService);
	
		// -------初始化动画-------------
		animation = AnimationUtils.loadAnimation(PlayActivity.this,
				R.anim.music_is_playing);
		//seekbar------------------
		
		//---注册歌曲播放完成的广播接收者-------------
		CompletionBroadReceiver completionBroadReceiver=new CompletionBroadReceiver();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("com.waterworld.action.COMPLETIONBROAD");
		registerReceiver(completionBroadReceiver, intentFilter);
		//--------------------------------------
		
		ImageButton imgbtn_back=(ImageButton) findViewById(R.id.btn_back);
	    imgbtn_back.setOnClickListener(this);
		
		//-----------------音量--------------------

	}

	public void inintView(int musicId) {
		Music music = (Music) MainActivity.musiclListView
				.getItemAtPosition(musicId);
		musicPath = music.getMusicPath();
		String musicNames = music.getMusicName();

		String singer = null;
		String musicName = null;
		int musicIndex = musicNames.indexOf("-");
		if (musicIndex != -1) {
			singer = musicNames.substring(0, musicIndex);
			musicName = musicNames.substring(musicIndex + 1);
		} else {
			musicName = musicNames;
			singer = "未知演唱者";
		}
		// 总时间
		 musicTime = music.getTime();
		String totalTime = TimeFormat.changeTime((int) musicTime);

		TextView tv_singer = (TextView) findViewById(R.id.tv_singer);
		TextView tv_musicName = (TextView) findViewById(R.id.tv_musicName);
		tv_totalTime = (TextView) findViewById(R.id.tv_total_time);
        
		tv_singer.setText(singer);
		tv_musicName.setText(musicName);
		tv_totalTime.setText(totalTime);
		
		 currentTime=(TextView) findViewById(R.id.tv_currentTime);
		 currentTime.setText("00:00");
		progressSekbar=(SeekBar) findViewById(R.id.seekBar1);
		progressSekbar.setMax((int)musicTime);
		progressSekbar.setProgress(0);
		progressSekbar.invalidate();
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		intentBroadCast = new Intent();
		intentBroadCast.putExtra("musicPath", musicPath);
		intentBroadCast.setAction("com.waterworld.action.PLAYMUSICBROAD");

		if (id == R.id.imgbtn_play) {
			String tag = btn_play.getTag().toString();
			if ("播放".equals(tag)) {
				linearLayout.setVisibility(View.VISIBLE);
				intentBroadCast.putExtra("broadMessage", 1);
				img_roate.startAnimation(animation);
				btn_play.setImageResource(android.R.drawable.ic_media_pause);				
				btn_play.setTag("暂停");

			} else if ("暂停".equals(tag)) {
				intentBroadCast.putExtra("broadMessage", 2);
				animation.cancel();
				btn_play.setImageResource(android.R.drawable.ic_media_play);
				btn_play.setTag("播放");

			}

		} else if (id == R.id.imgbtn_previous) {
			linearLayout.setVisibility(View.VISIBLE);

			if (musicId == 0) {
				musicId = MainActivity.musicsList.size() - 1;

			} else {
				musicId -= 1;
			}
			inintView(musicId);
			intentBroadCast.putExtra("broadMessage", 1);
			intentBroadCast.putExtra("musicPath", musicPath);
			img_roate.startAnimation(animation);
			btn_play.setImageResource(android.R.drawable.ic_media_pause);
			btn_play.setTag("暂停");
			PlayMusicService.isPause = false;
		} else if (id == R.id.imgbtn_next) {
			linearLayout.setVisibility(View.VISIBLE);

			if (musicId == MainActivity.musicsList.size() - 1) {
				musicId = 0;
			} else {
				musicId += 1;
			}
			inintView(musicId);
			intentBroadCast.putExtra("broadMessage", 1);
			intentBroadCast.putExtra("musicPath", musicPath);
			img_roate.startAnimation(animation);
			btn_play.setImageResource(android.R.drawable.ic_media_pause);
			btn_play.setTag("暂停");
			PlayMusicService.isPause = false;
		} else if (id == R.id.imgbtn_stop) {
			intentBroadCast.putExtra("broadMessage", 5);
			animation.cancel();
			btn_play.setTag("播放");
			btn_play.setImageResource(android.R.drawable.ic_media_play);
			

		}else if (id==R.id.btn_back) {
		      Intent  intent=new Intent(PlayActivity.this,MainActivity.class);
	           startActivity(intent);
			    finish();
		}
		sendBroadcast(intentBroadCast);
	}

   
   
  class CompletionBroadReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent intent) {
		 String broadString=intent.getStringExtra("completion");
		 if ("completed".equals(broadString)) {
			 intentBroadCast=new Intent();
			 intentBroadCast.setAction("com.waterworld.action.PLAYMUSICBROAD");

			 if (musicId == MainActivity.musicsList.size() - 1) {
					musicId = 0;
				} else {
					musicId += 1;
				}
				inintView(musicId);
				intentBroadCast.putExtra("broadMessage", 1);
				intentBroadCast.putExtra("musicPath", musicPath);
				img_roate.startAnimation(animation);
				btn_play.setImageResource(android.R.drawable.ic_media_pause);
				btn_play.setTag("暂停");
				PlayMusicService.isPause = false;
				sendBroadcast(intentBroadCast);
		}
	}
	  
  }

  
  
  
 @Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	  if (keyCode==KeyEvent.KEYCODE_BACK) {
		  
		  Intent  intent=new Intent(PlayActivity.this,MainActivity.class);
          startActivity(intent);
          finish();
          
	}
	  return true;
 }
}

package com.unless;

import java.util.Random;

import com.example.activity.CurrentPlayList;
import com.example.activity.MainActivity;
import com.example.entity.Exitactivity;
import com.example.entity.Music;
import com.example.entity.TimeFormat;
import com.example.localmusic.R;
import com.example.service.PlayMusicSevice;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class play extends Activity implements OnClickListener {
	private int position = 0;
	private LinearLayout playactivity_linear_back;
	private TextView tv_musicing_singer;
	private TextView tv_musicing_album;
	private TextView tv_musicing_musicName;
	private TextView tv_totaltime;
	private TextView tv_music_lrc;
	public static TextView tv_currentime;
	public static SeekBar progressSeekBar;
	private ImageView img_pre;
	private ImageView img_play;
	private ImageView Img_next;
	private ImageView img_currentList;
	private ImageView img_repeat;
	private String musicPath;
	private ImageView img_img;
	private String musicName;
	private String singer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.playing_music);
		Exitactivity.getInstance().addActivity(this);

		SharedPreferences repeatSharedPreferences = play.this
				.getSharedPreferences("repeat", Context.MODE_PRIVATE);
		String repeat_state = repeatSharedPreferences.getString("repeat_state",
				"");
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);

		img_img = (ImageView) findViewById(R.id.img_img);
		img_img.setOnClickListener(this);
		img_currentList = (ImageView) findViewById(R.id.img_currentList);
		img_repeat = (ImageView) findViewById(R.id.img_repeat);

		img_pre = (ImageView) findViewById(R.id.Img_pre);
		img_play = (ImageView) findViewById(R.id.img_play);
		Img_next = (ImageView) findViewById(R.id.Img_next);

		img_currentList.setOnClickListener(this);
		img_repeat.setOnClickListener(this);
		img_pre.setOnClickListener(this);
		img_play.setOnClickListener(this);
		Img_next.setOnClickListener(this);

		if ("循环全部".equals(repeat_state)) {
			img_repeat.setImageResource(R.drawable.ic_mp_repeat_all_btn);
			img_repeat.setTag("循环全部");

		} else if ("单曲循环".equals(repeat_state)) {
			img_repeat.setImageResource(R.drawable.ic_mp_repeat_once_btn);
			img_repeat.setTag("单曲循环");

		} else if ("随机播放".equals(repeat_state)) {
			img_repeat.setImageResource(R.drawable.ic_mp_shuffle_on_btn);
			img_repeat.setTag("随机播放");
		}

		initView(position);

		progressSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {

					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {

					}

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						if (arg2) {
							if (PlayMusicSevice.isPause) {
								img_play.setImageResource(R.drawable.pause_nomal);
								img_play.setTag("暂停");
							}

							PlayMusicSevice.mediaPlayer.seekTo(arg1);
							PlayMusicSevice.mediaPlayer.start();
						}

					}
				});

		CompletionBroadReceiver completionBroadReceiver = new CompletionBroadReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.waterworld.action.COMPLETIONBROAD");
		registerReceiver(completionBroadReceiver, intentFilter);

		playactivity_linear_back = (LinearLayout) findViewById(R.id.playactivity_linear_back);
		playactivity_linear_back.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {

				SharedPreferences playingPreferences = getSharedPreferences(
						"playing_music", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = playingPreferences.edit();
				editor.putString("musicName", musicName);
				editor.putString("singer", singer);
				editor.apply();

				PlayMusicSevice.isPause = false;
				finish();
			}
		});
	}

	private void initView(int musicPosition) {

		tv_musicing_singer = (TextView) findViewById(R.id.tv_musicing_singer);
		tv_musicing_album = (TextView) findViewById(R.id.tv_musicing_album);
		tv_musicing_musicName = (TextView) findViewById(R.id.tv_musicing_musicName);
		tv_totaltime = (TextView) findViewById(R.id.tv_totaltime);
		tv_music_lrc = (TextView) findViewById(R.id.tv_music_lrc);
		tv_currentime = (TextView) findViewById(R.id.tv_currentime);
		progressSeekBar = (SeekBar) findViewById(R.id.seekBar1);

		Music music = (Music) MainActivity.musicList.get(musicPosition);
		musicPath = music.getMusicPath();
		musicName = music.getMusicName();
		singer = music.getSinger();
		int time = (int) music.getMusicTime();
		String totalTime = TimeFormat.changeTime((int) music.getMusicTime());
		String album = music.getAlbum();

		tv_musicing_album.setText(album);
		tv_musicing_singer.setText(singer);
		tv_musicing_musicName.setText(musicName);
		tv_totaltime.setText(totalTime);

		tv_currentime.setText("00:00");
		progressSeekBar.setMax(time);

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		int id = view.getId();
		Intent intentBroadCast = new Intent();
		intentBroadCast.putExtra("musicPath", musicPath);
		intentBroadCast.setAction("com.waterworld.action.PLAYMUSICBROAD");

		if (id == R.id.img_play) {
			String tag = img_play.getTag().toString();
			if ("暂停".equals(tag)) {
				intentBroadCast.putExtra("PlayMessage", 2);
				img_play.setImageResource(R.drawable.play_nomal);
				img_play.setTag("播放");

			} else if ("播放".equals(tag)) {
				intentBroadCast.putExtra("PlayMessage", 1);
				img_play.setImageResource(R.drawable.pause_nomal);
				img_play.setTag("暂停");
			}
		} else if (id == R.id.Img_pre) {
			SharedPreferences repeatSharedPreferences = play.this
					.getSharedPreferences("repeat", Context.MODE_PRIVATE);
			String repeat_state = repeatSharedPreferences.getString(
					"repeat_state", "");
			if ("循环全部".equals(repeat_state)) {
				if (position == 0) {
					position = MainActivity.musicList.size() - 1;

				} else {
					position -= 1;
				}
			} else if ("单曲循环".equals(repeat_state)) {
				position = position;
			} else if ("随机播放".equals(repeat_state)) {
				Random random = new Random();
				int number = random.nextInt(MainActivity.musicList.size() - 1);

				position = number;
			}
			initView(position);
			intentBroadCast.putExtra("PlayMessage", 1);
			intentBroadCast.putExtra("musicPath", musicPath);
			img_play.setImageResource(R.drawable.pause_nomal);
			img_play.setTag("暂停");
			PlayMusicSevice.isPause = false;

		} else if (id == R.id.Img_next) {
			SharedPreferences repeatSharedPreferences = play.this
					.getSharedPreferences("repeat", Context.MODE_PRIVATE);
			String repeat_state = repeatSharedPreferences.getString(
					"repeat_state", "");
			if ("循环全部".equals(repeat_state)) {
				if (position == MainActivity.musicList.size() - 1) {
					position = 0;
				} else {
					position += 1;
				}
			} else if ("单曲循环".equals(repeat_state)) {
				position = position;
			} else if ("随机播放".equals(repeat_state)) {
				Random random = new Random();
				int number = random.nextInt(MainActivity.musicList.size() - 1);

				position = number;
			}

			initView(position);
			intentBroadCast.putExtra("PlayMessage", 1);
			intentBroadCast.putExtra("musicPath", musicPath);
			img_play.setImageResource(R.drawable.pause_nomal);
			img_play.setTag("暂停");
			PlayMusicSevice.isPause = false;
		} else if (id == R.id.img_repeat) {
			String repeat_tag = img_repeat.getTag().toString();
			SharedPreferences repeatSharedPreferences = play.this
					.getSharedPreferences("repeat", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = repeatSharedPreferences.edit();
			if ("循环全部".equals(repeat_tag)) {
				img_repeat.setImageResource(R.drawable.ic_mp_repeat_once_btn);
				img_repeat.setTag("单曲循环");
				editor.putString("repeat_state", "单曲循环");
				editor.apply();

				Toast.makeText(play.this, "单曲循环", 0).show();
			} else if ("单曲循环".equals(repeat_tag)) {
				img_repeat.setImageResource(R.drawable.ic_mp_shuffle_on_btn);
				img_repeat.setTag("随机播放");
				editor.putString("repeat_state", "随机播放");
				editor.apply();

				Toast.makeText(play.this, "随机播放", 0).show();
			} else if ("随机播放".equals(repeat_tag)) {
				img_repeat.setImageResource(R.drawable.ic_mp_repeat_all_btn);
				img_repeat.setTag("循环全部");
				editor.putString("repeat_state", "循环全部");
				editor.apply();

				Toast.makeText(play.this, "循环全部", 0).show();
			}
		} else if (id == R.id.img_currentList) {
			SharedPreferences playingPreferences = getSharedPreferences(
					"playing_music", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = playingPreferences.edit();
			editor.putString("musicName", musicName);
			editor.putString("singer", singer);
			editor.apply();

			PlayMusicSevice.isPause = false;

			Intent intent = new Intent(play.this, CurrentPlayList.class);

			startActivityForResult(intent, 0);
			// finish();
		}
		sendBroadcast(intentBroadCast);
	}

	class CompletionBroadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			String broadString = intent.getStringExtra("completion");
			if ("completed".equals(broadString)) {
				Intent intentBroadCast = new Intent();
				intentBroadCast
						.setAction("com.waterworld.action.PLAYMUSICBROAD");
				SharedPreferences repeatSharedPreferences = play.this
						.getSharedPreferences("repeat", Context.MODE_PRIVATE);
				String repeat_state = repeatSharedPreferences.getString(
						"repeat_state", "");
				if ("循环全部".equals(repeat_state)) {
					if (position == MainActivity.musicList.size() - 1) {
						position = 0;
					} else {
						position += 1;
					}
				} else if ("单曲循环".equals(repeat_state)) {
					position = position;
				} else if ("随机播放".equals(repeat_state)) {
					Random random = new Random();
					int number = random
							.nextInt(MainActivity.musicList.size() - 1);

					position = number;
				}
				initView(position);
				intentBroadCast.putExtra("PlayMessage", 1);
				intentBroadCast.putExtra("musicPath", musicPath);
				img_play.setImageResource(R.drawable.pause_nomal);
				img_play.setTag("暂停");
				PlayMusicSevice.isPause = false;
				sendBroadcast(intentBroadCast);
			}
		}

	}

	@SuppressLint("NewApi")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			SharedPreferences playingPreferences = getSharedPreferences(
					"playing_music", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = playingPreferences.edit();
			editor.putString("musicName", musicName);
			editor.putString("singer", singer);
			editor.apply();

			PlayMusicSevice.isPause = false;
			finish();
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (resultCode) {
		case RESULT_OK:
			Bundle bundle = data.getExtras();
			int musicId = bundle.getInt("musicId", position);
			initView(musicId);
			break;

		default:
			break;
		}
	}
}

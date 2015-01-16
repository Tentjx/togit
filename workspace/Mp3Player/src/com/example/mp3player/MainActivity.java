package com.example.mp3player;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static ListView musiclListView;
	private MusicAdapter musicAdapter;
	public static List<Music> musicsList;
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		musiclListView = (ListView) findViewById(R.id.listView1);
		musicsList = getLocalMusic(getApplicationContext());
		musicAdapter = new MusicAdapter(musicsList, this);
		musiclListView.setAdapter(musicAdapter);
		musiclListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intentStopService = new Intent(MainActivity.this,
						PlayMusicService.class);
				stopService(intentStopService);
				Intent intent = new Intent(MainActivity.this,
						PlayActivity.class);
				intent.putExtra("musicId", arg2);
				startActivity(intent);
				finish();
			}
		});

	}

	public class MusicAdapter extends BaseAdapter {

		private List<Music> listMusic;
		private Context context;

		public MusicAdapter() {
			super();
		}

		public MusicAdapter(List<Music> listMusic, Context context) {
			this.listMusic = listMusic;
			this.context = context;
		}

		public void setListItem(List<Music> listMusic) {
			this.listMusic = listMusic;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listMusic.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listMusic.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if (arg1 == null) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(MainActivity.this);
				arg1 = layoutInflater.inflate(R.layout.music_item, null);

			}
			Music music = listMusic.get(arg0);

			TextView tv_musicIndex = (TextView) arg1
					.findViewById(R.id.tv_musicNumber);
			TextView tv_musicName = (TextView) arg1
					.findViewById(R.id.tv_musicName);
			TextView tv_musicSonger = (TextView) arg1
					.findViewById(R.id.tv_songer);
			TextView tv_musicTime = (TextView) arg1
					.findViewById(R.id.tv_musicTime);
			TextView tv_musicPath = (TextView) arg1
					.findViewById(R.id.tv_musicPath);
			tv_musicIndex.setText("" + (arg0 + 1));
			tv_musicName.setText(music.getMusicName());
			tv_musicSonger.setText(music.getSinger());
			tv_musicTime.setText(TimeFormat.changeTime((int) music.getTime()));
			tv_musicPath.setText(music.getMusicPath());
			return arg1;
		}

	}

	public static List<Music> getLocalMusic(Context context) {
		List<Music> musicLists = new ArrayList<Music>();
		ContentResolver contentResolver = context.getContentResolver();
		if (contentResolver != null) {
			Cursor cursor = contentResolver.query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
					null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			if (null == cursor) {
				return null;
			}
			if (cursor.moveToFirst()) {
				do {
					Music music = new Music();
					// 获取歌曲名
					String musicName = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.TITLE));
					int musicIndex = musicName.indexOf("-");
					if (musicIndex != -1) {
						String singer = musicName.substring(0, musicIndex);
						music.setSinger(singer);

					} else {
						String singer = cursor.getString(cursor
								.getColumnIndex(MediaStore.Audio.Media.ARTIST));
						if ("<Undefined>".equals(singer)
								|| "<unknown>".equals(singer)) {
							singer = "未知演唱者";
						}
						music.setSinger(singer);

					}

					// 获得歌曲路径
					String musicPath = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.DATA));
					// 获得歌曲播放时间
					long musicTime = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Audio.Media.DURATION));
					String name = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
					music.setMusicName(musicName);
					music.setMusicPath(musicPath);
					music.setTime(musicTime);
					musicLists.add(music);
					System.out.println(musicName);

				} while (cursor.moveToNext());

			}
		}

		return musicLists;
	}

	// -------双击返回键退出程序------------------------

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);

			}

		}
		return super.onKeyDown(keyCode, event);
	}

}

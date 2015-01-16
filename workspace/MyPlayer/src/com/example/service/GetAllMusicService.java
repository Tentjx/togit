package com.example.service;

import java.util.ArrayList;
import java.util.List;

import com.example.vo.MusicVO;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;

public class GetAllMusicService extends Service {

	public List<MusicVO> list;
	private Cursor cursor;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		list=new ArrayList<MusicVO>();
		MusicVO music=new MusicVO();
		ContentResolver resolver=getContentResolver();
		cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
						null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		while(cursor.moveToNext()){
			music.music_id=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
			music.music_name=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));					music.music_uri=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			music.singer_name=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			music.file_size=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
			music.duration=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));					music.special=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
			list.add(music);
				}
		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_REDELIVER_INTENT;
	}

	public class MyBinder extends Binder{
		public GetAllMusicService getService(){
			return GetAllMusicService.this;
		}
	}
	private final IBinder binder = new MyBinder();
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(cursor!=null)
			cursor.close();
		super.onDestroy();
	}
	
	
}

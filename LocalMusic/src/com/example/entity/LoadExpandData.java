package com.example.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class LoadExpandData {
	public static  List<Map<String, String>> musicList = new ArrayList<Map<String, String>>();
	public static int size=0;
	public static String musicPath;
	public static String musicName;
	public static String musicAlbum;
	public static String musicArtist;
	public static String musicAlbumKey;
	public static String musicAlbumArtPath;
	public static int musicTime;

	public static List<Map<String, String>> loadSongs(Context context) {
		
		ContentResolver musicResolver = context.getContentResolver();
		Cursor musicCursor = musicResolver.query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		int musicColumnIndex;
		if (null != musicCursor && musicCursor.getCount() > 0) {
			for (musicCursor.moveToFirst(); !musicCursor.isAfterLast(); musicCursor
					.moveToNext()) {
				Map musicDataMap = new HashMap();

				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns._ID);
				int musicRating = musicCursor.getInt(musicColumnIndex);
				musicDataMap.put("musicRating", musicRating + "");
				musicDataMap.put("id",size+"");
				size=size+1;

				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
				musicPath = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("musicPath", musicPath);
				
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
				musicName = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("musicName", musicName);
				
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
				musicAlbum = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("musicAlbum", musicAlbum);
				
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
				musicArtist = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("musicArtist", musicArtist);
				
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY);
				musicAlbumKey = musicCursor.getString(musicColumnIndex);
				
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
				musicTime = musicCursor.getInt(musicColumnIndex);
                String time=TimeFormat.changeTime(musicTime);
				
				musicDataMap.put("musicTime", time);
				//
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
				String path = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("path", path);

				//
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
				String display = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("displayName", display);

				String[] argArr = { musicAlbumKey };
				ContentResolver albumResolver = context.getContentResolver();
				Cursor albumCursor = albumResolver.query(
						MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
						MediaStore.Audio.AudioColumns.ALBUM_KEY + " = ?",
						argArr, null);
				if (null != albumCursor && albumCursor.getCount() > 0) {
					albumCursor.moveToFirst();
					int albumArtIndex = albumCursor
							.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
					musicAlbumArtPath = albumCursor.getString(albumArtIndex);
					if (null != musicAlbumArtPath
							&& !"".equals(musicAlbumArtPath)) {
						musicDataMap.put("musicAlbumImage", musicAlbumArtPath);
					} else {
						musicDataMap.put("musicAlbumImage",
								"");
					}
				} else {

					musicDataMap.put("musicAlbumImage",
							"");
				}
				musicList.add(musicDataMap);
			}
		}
		return musicList;

	}
}

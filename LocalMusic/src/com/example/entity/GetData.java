package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


public class GetData {
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
							singer = "未知艺术家";
						}
						music.setSinger(singer);

					}
                     String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
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
					music.setMusicTime(musicTime);
					music.setAlbum(album);
					musicLists.add(music);
					System.out.println(musicName);

				} while (cursor.moveToNext());

			}
		}

		return musicLists;
	}
}

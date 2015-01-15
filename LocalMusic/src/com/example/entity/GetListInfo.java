package com.example.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetListInfo {
	// 主页的专辑List
	public static List<String> selectAlbums(List<Music> list) {

		List<String> albumList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Music music = list.get(i);
			String albumString = music.getAlbum();

			if (!albumList.contains(albumString)) {
				albumList.add(albumString);

			}
		}
		return albumList;

	}



	// 单击item后显示的list

	public static List<Music> selectAlbumSongs(List<Music> list,
			String albumName) {
		List<Music> AlbumSongs = new ArrayList<Music>();
		for (int i = 0; i < list.size(); i++) {
			Music music = list.get(i);
			String albumString = music.getAlbum();
			if (albumName.equals(albumString)) {
				AlbumSongs.add(music);
			}
		}
		return AlbumSongs;

	}

	// ----------------获得艺术家界面ExpandlistView 一级列表数据
	public static List<Map<String, String>> selectExpandGroup(
			List<Map<String, String>> musicListExpand) {
		List<Map<String, String>> lis = new ArrayList<Map<String, String>>();
		Map<String, String> pmap = new HashMap<String, String>();
		Map<String, String> m;
		m = new HashMap<String, String>();
		for (int i = 0; i < musicListExpand.size(); i++) {
			pmap = new HashMap<String, String>();
			String displayName = musicListExpand.get(i).get("displayName");

			if (m.get(displayName) == null) {
				m.put(displayName, displayName);
				pmap.put("name", displayName);
				lis.add(pmap);
			}

		}

		return lis;

	}

	// ---------------获得艺术家界面ExpandlistView 二级列表数据--
	public static List<List<Map<String, String>>> selectExpandChild(
			List<Map<String, String>> musicListExpand,
			List<Map<String, String>> musicExpandData) {
		List<List<Map<String, String>>> child = new ArrayList<List<Map<String, String>>>();

		Map<String, String> mc = new HashMap<String, String>();
		for (int j = 0; j < musicListExpand.size(); j++) {

			String entry = musicListExpand.get(j).get("name");

			List<Map<String, String>> l = new ArrayList<Map<String, String>>();
			for (int i = 0; i < musicExpandData.size(); i++) {

				String displayName = musicExpandData.get(i).get("displayName");
				// System.out.println(displayName + "displayName");
				if (entry.equals(displayName)) {
					mc = new HashMap<String, String>();
					// System.out.println(displayName + "displayName********");
					mc.put("musicName", musicExpandData.get(i).get("musicName"));

					String musicName = musicExpandData.get(i).get("musicName");
					String singer = null;
					int musicIndex = musicName.indexOf("-");
					if (musicIndex != -1) {
						singer = musicName.substring(0, musicIndex);

					} else {
						singer = musicExpandData.get(i).get("musicArtist");

					}

					mc.put("singer", singer);
					mc.put("musicTime", musicExpandData.get(i).get("musicTime"));
					mc.put("path", musicExpandData.get(i).get("path"));
					mc.put("id", musicExpandData.get(i).get("id"));
					mc.put("musicAlbumImage",
							musicExpandData.get(i).get("musicAlbumImage"));

					l.add(mc);
				}

			}
			child.add(l);
		}

		return child;

	}

	// 艺术家Frag子条目中的歌曲在 歌曲Frag中的位置
	public static int getPosition(String musicPath, List<Music> list) {
		int position = 0;
		for (int i = 0; i < list.size(); i++) {
			Music music = list.get(i);
			String path = music.getMusicPath();
			if (musicPath.equals(path)) {
				position = i;
			}

		}
		return position;

	}

}

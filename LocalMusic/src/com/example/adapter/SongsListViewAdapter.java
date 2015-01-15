package com.example.adapter;

import java.util.List;


import com.example.activity.MainActivity;
import com.example.entity.Music;
import com.example.entity.TimeFormat;
import com.example.localmusic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SongsListViewAdapter extends BaseAdapter {
    private List<Music> musicList;
    private Context context;

    
	public SongsListViewAdapter( Context context,List<Music> musicList) {

		this.musicList = musicList;
		this.context = context;
	}
	public void setMusicListItem(List<Music> musicList) {
		this.musicList=musicList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musicList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return musicList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
//			LayoutInflater LayoutInflater = LayoutInflater.from(context);
			convertView=LayoutInflater.from(context).inflate(R.layout.gq_frag_item, null);
	
		}
		 Music songsMusic=musicList.get(position);
		 TextView songs_tv_musicName=(TextView) convertView.findViewById(R.id.gqfrag_tv_songName);
		 TextView songs_tv_songer=(TextView) convertView.findViewById(R.id.gqfrag_tv_songer);
		 TextView songs_tv_totalTime=(TextView) convertView.findViewById(R.id.gqfrag_tv_toaltime);

		 songs_tv_musicName.setText(songsMusic.getMusicName());
		 songs_tv_songer.setText(songsMusic.getSinger());
		 String time=TimeFormat.changeTime((int)songsMusic.getMusicTime());
		 songs_tv_totalTime.setText(time);
		 
		return convertView;
	}

}

package com.example.listener;

import com.example.myplayer.MainActivity;
import com.example.service.PlayMusicService;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class ExpandableListOnChildItemClickListener implements
		OnChildClickListener {

	private MainActivity activity;
	public ExpandableListOnChildItemClickListener(MainActivity activity) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		activity.viewpager.setCurrentItem(4);
		activity.playlist=activity.musiclist.get(groupPosition);
		activity.currentmusicposition=childPosition;
		MusicVO music=activity.playlist.get(activity.currentmusicposition);
		Intent intent=new Intent();
		intent.putExtra("user_option", MyConstent.PLAY_MUSIC);
		intent.putExtra("duration", music.duration);
		intent.putExtra("music_uri", music.music_uri);
		intent.setClass(activity, PlayMusicService.class);
		activity.startService(intent);
		Log.i("ExpandableListOnChildClickListener", "成功触发！");
		return true;
	}

}

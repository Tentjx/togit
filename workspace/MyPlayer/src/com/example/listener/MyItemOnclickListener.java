package com.example.listener;

import com.example.fragment.PlayFragment;
import com.example.myplayer.MainActivity;
import com.example.service.PlayMusicService;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;
import com.example.vo.PopItemPosition;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MyItemOnclickListener implements OnItemClickListener {

	private MainActivity activity;
	private int whichlist;
	public MyItemOnclickListener(MainActivity activity,int whichlist) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.whichlist=whichlist;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		MusicVO  music=null;
		if(whichlist==MyConstent.ALL_MUSIC_LIST){
			activity.playlist=activity.list;
			activity.currentmusicposition=position;
		}else if(whichlist==MyConstent.SINGER_MUSIC_LIST){
			PopItemPosition popposition=(PopItemPosition)view.getTag();
			if(popposition!=null){
				activity.playlist=activity.musiclist.get(popposition.groupposition);
				activity.currentmusicposition=popposition.childposition;
			}
			Log.i("ExpendableFragment", "监听到单击事件！");
		}else{
			activity.playlist=activity.mylove;
			activity.currentmusicposition=position;
		}
		if(PlayFragment.update!=null)
			PlayFragment.update.handleMessage(new Message());
		activity.viewpager.setCurrentItem(3);
		music=activity.playlist.get(activity.currentmusicposition);
		Intent intent=new Intent();
		intent.putExtra("user_option", MyConstent.PLAY_MUSIC);
		intent.putExtra("duration", music.duration);
		intent.putExtra("music_uri", music.music_uri);
		intent.setClass(activity, PlayMusicService.class);
		activity.startService(intent);
	}

}

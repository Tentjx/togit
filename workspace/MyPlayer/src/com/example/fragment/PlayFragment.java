package com.example.fragment;

import com.example.myplayer.MainActivity;
import com.example.myplayer.R;
import com.example.myview.LrcView;
import com.example.service.PlayMusicService;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class PlayFragment extends Fragment{

	public static SeekBarHandler seekbarhandler;
	public static UpdateFragmentHandler update;
	private SeekBar seekbar;
	private MusicCompleteReceiver musiccompletion;
	private TextView music_name;
	private TextView singer_name;
	private MusicVO music;
	private MainActivity activity;
	private ImageButton play_btn;
	private ImageButton like_btn;
	public static LrcView lrc_view;
	public static boolean islove; 
	public static boolean isscroll;
	public static Handler UpdatePlayState;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity=(MainActivity) getActivity();
		View view=inflater.inflate(R.layout.playfragment, container,false);
		seekbar=(SeekBar)view.findViewById(R.id.seekBar);
		seekbar.setOnSeekBarChangeListener(new MySeekbarChangedListener());
		musiccompletion=new MusicCompleteReceiver();
		seekbarhandler=new SeekBarHandler();
		update=new UpdateFragmentHandler();
		UpdatePlayState=new UpdatePlayState();
		music_name=(TextView)view.findViewById(R.id.play_music_name);
		singer_name=(TextView)view.findViewById(R.id.play_singer_name);
		IntentFilter intentfilter=new IntentFilter();
		intentfilter.addAction("com.example.waterworld.play.COMPLITION");
		getActivity().registerReceiver(musiccompletion, intentfilter);
		ImageButton pre_btn=(ImageButton)view.findViewById(R.id.pre_btn);
		play_btn=(ImageButton)view.findViewById(R.id.playfragment_play_btn);
		ImageButton next_btn=(ImageButton)view.findViewById(R.id.next_btn);
		like_btn=(ImageButton)view.findViewById(R.id.like_btn);
		PlayButtonListener listener=new PlayButtonListener();
		pre_btn.setOnClickListener(listener);
		play_btn.setOnClickListener(listener);
		next_btn.setOnClickListener(listener);
		like_btn.setOnClickListener(listener);
		lrc_view=(LrcView)view.findViewById(R.id.lrc_view);
		lrc_view.setBackgroundColor(0xb9ffffff);
		isscroll=true;
//		lrc_view.hasLrc(music.music_uri.substring(0,music.music_uri.length()-4)+ ".lrc");
//		lrc_view.lrcreader.LogInfo(music.music_uri.substring(0,music.music_uri.length()-4)+ ".lrc");
//		WindowManager windowmanager=activity.getWindowManager();
//		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		int hight=windowmanager.getDefaultDisplay().getHeight();
//		params.width=LayoutParams.WRAP_CONTENT;
////		params.height=hight-singer_name.getBottom()-seekbar.getWidth()-play_btn.getWidth();
//		params.height=400;
//		lrc_view.setLayoutParams(params);
		initView();
		ViewGroup p = (ViewGroup)view.getParent();
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        }
		setRetainInstance(true);
		return view;
	}
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}




	public class SeekBarHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (isscroll&&lrc_view!=null&&lrc_view.lrcreader != null) {
				lrc_view.setOffsetY(lrc_view.getOffsetY() - lrc_view.SpeedLrc());
				lrc_view.SelectIndex(msg.what);
//				lrc_view.invalidate();
				updateLrc.post(mUpdateResults);
			}
			seekbar.setProgress(msg.what);
			super.handleMessage(msg);
		}
		
	}
	
	class MySeekbarChangedListener implements OnSeekBarChangeListener{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			if(fromUser){
				Intent intent=new Intent();
				intent.putExtra("user_option", MyConstent.FAST_FORWARD);
				intent.putExtra("fast_forward", seekbar.getProgress());
				Log.i("seekbar progress", ""+seekbar.getProgress());
				intent.setClass(getActivity(), PlayMusicService.class);
				getActivity().startService(intent);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			lrc_view.setOffsetY(0);
		}
		
	}
	
	public void initView(){
		if(activity.playlist==null){
				music=null;
				return ;
		}
		else{
				music=activity.playlist.get(activity.currentmusicposition>=
						activity.playlist.size()?0:activity.currentmusicposition);
			}
		seekbar.setMax((int)music.duration);
		music_name.setText(music.getMusic_name());
		singer_name.setText(music.getSinger_name());
		if (lrc_view != null) {
			lrc_view.hasLrc(music.music_uri.substring(0,music.music_uri.length()-4)+ ".lrc");
			lrc_view.SetTextSize();
			lrc_view.setOffsetY(lrc_view.getHeight()/2);
//			lrc_view.setOffsetY(0);
			Log.i("初始化LrcView", "hasLrc()以运行");
		}else{
			Log.i("初始化LrcView", "为初始化");
		}
		if("16".equals(music.miaoshu)){
			islove=true;
			like_btn.setBackgroundResource(R.drawable.pre_nomal);
		}else{
			islove=false;
			like_btn.setBackgroundResource(R.drawable.next_nomal);
		}
		play_btn.setBackgroundResource(R.drawable.play_nomal);
		
	}
	
	private void playmusic(MusicVO music,int user_position){
		play_btn.setBackgroundResource(R.drawable.pause_nomal);
		seekbar.setMax((int)music.duration);
		music_name.setText(music.music_name);
		singer_name.setText(music.singer_name);
		Intent intent=new Intent();
		intent.putExtra("music_uri", music.music_uri);
		intent.putExtra("user_option",user_position);
//		intent.putExtra("duration", music.duration);
		intent.setClass(getActivity(), PlayMusicService.class);
		getActivity().startService(intent);
	}
	
	class MusicCompleteReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals("com.example.waterworld.play.COMPLITION")){
				abortBroadcast();
				Toast.makeText(getActivity(), "广播已接收！", Toast.LENGTH_SHORT).show();
				MainActivity activity=(MainActivity)getActivity();
				if(++activity.currentmusicposition>=activity.playlist.size())
					activity.currentmusicposition=0;
				playmusic(activity.playlist.get(activity.currentmusicposition),MyConstent.PLAY_MUSIC);
			}
		}
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		initView();
//		islove=music.
		super.onResume();
	}

	public class UpdateFragmentHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			initView();
			super.handleMessage(msg);
		}
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(musiccompletion!=null)
			getActivity().unregisterReceiver(musiccompletion);
		super.onDestroy();
	}
	
	class PlayButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			int user_position = MyConstent.PLAY_MUSIC;
			if (id != R.id.like_btn) {
				switch (id) {
				case R.id.pre_btn:
					if (--activity.currentmusicposition < 0)
						activity.currentmusicposition = activity.playlist
								.size() - 1;
					initView();
					break;

				case R.id.playfragment_play_btn:
					user_position = MyConstent.PUASE_MUSIC;
					break;
				case R.id.next_btn:
					if (++activity.currentmusicposition >= activity.playlist
							.size())
						activity.currentmusicposition = 0;
					initView();
					break;
				}
//				music = activity.playlist.get(activity.currentmusicposition);
				playmusic(music, user_position);
			} else {
				if (islove) {
					islove = false;
					like_btn.setBackgroundResource(R.drawable.next_nomal);
					activity.mylove.remove(music);
					MyLoveFragment.myhandler.handleMessage(new Message());
					String where = MediaStore.Audio.Media._ID + "="
							+ music.music_id;
					ContentValues updatevalues = new ContentValues();
					updatevalues
							.put(MediaStore.Audio.Media.IS_NOTIFICATION, "");
					ContentResolver resover = activity.getContentResolver();
					resover.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
							updatevalues, where, null);
				} else {
					islove = true;
					like_btn.setBackgroundResource(R.drawable.pre_nomal);
					activity.mylove.add(music);
					MyLoveFragment.myhandler.handleMessage(new Message());
					String where = MediaStore.Audio.Media._ID + "="
							+ music.music_id;
					ContentValues updatevalues = new ContentValues();
					updatevalues.put(MediaStore.Audio.Media.IS_NOTIFICATION,
							"16");
					ContentResolver resover = activity.getContentResolver();
					resover.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
							updatevalues, where, null);
					Toast.makeText(activity, music.music_name + "\n收藏成功",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
		
	}
	public static Handler updateLrc=new Handler();
	public static  Runnable mUpdateResults = new Runnable() {  
	        public void run() {  
	            lrc_view.invalidate(); // 更新视图  
	        }  
	    };  
	

	    class UpdatePlayState extends Handler{

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==0)
					play_btn.setBackgroundResource(R.drawable.pause_nomal);
				else if(msg.what==1)
					play_btn.setBackgroundResource(R.drawable.play_nomal);
				super.handleMessage(msg);
			}
	    	
	    }
	    
}

package com.example.myplayer;

import java.util.ArrayList;
import java.util.List;

import com.example.fragment.AllMusicFragment;
import com.example.fragment.MyLoveFragment;
import com.example.fragment.PlayFragment;
import com.example.listener.SingerFragment;
import com.example.lrc.LrcReader;
import com.example.service.GetAllMusicService;
import com.example.service.GetAllMusicService.MyBinder;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends FragmentActivity {

	private List<LinearLayout> tablist;
	private int pretab;
	private int currenttab;
	public static int currentmusicposition;
	public static ViewPager viewpager;
	public static List<String> singer_name;
	public static List<List<MusicVO>> musiclist;
//	private ListView list;
	private List<Fragment> fragment;
	public static List<MusicVO> list;
	public static List<MusicVO> mylove;
	public static List<MusicVO> playlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WindowManager windowmanager=this.getWindowManager();
		int width=windowmanager.getDefaultDisplay().getWidth();
		tablist=new ArrayList<LinearLayout>();
		int []image=new int[]{R.id.singer_tab_line,R.id.special_tab_line,
				R.id.music_tab_line,R.id.list_tab_line};
		tablist.add((LinearLayout)findViewById(R.id.singer_tab_linear));
		tablist.add((LinearLayout)findViewById(R.id.special_tab_linear));
		tablist.add((LinearLayout)findViewById(R.id.music_tab_linear));
		tablist.add((LinearLayout)findViewById(R.id.list_tab_linear));
		viewpager=(ViewPager)findViewById(R.id.view_pager);
		fragment=new ArrayList<Fragment>();
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.width=width/4;
		initList(savedInstanceState);
		LinearLayoutOnClickListener listener=new LinearLayoutOnClickListener();
		for(int i=0;i<4;i++){
			LinearLayout linear=tablist.get(i);
			linear.setTag(i);
			linear.setLayoutParams(params);
			linear.setOnClickListener(listener);
			linear.setBackgroundColor(0xb9ffffff);
		}
//		Intent intent=new Intent(this,GetAllMusicService.class);
//		bindService(intent, con, Context.BIND_AUTO_CREATE);
		playlist=mylove;
		currentmusicposition=4;
		fragment.add(new SingerFragment());
		fragment.add(new AllMusicFragment(list,MyConstent.ALL_MUSIC_LIST));
		fragment.add(new MyLoveFragment(mylove,MyConstent.MYLOVE_MUSIC_LIST));
		fragment.add(new PlayFragment());
		viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		viewpager.setOnPageChangeListener(new MyPagerChanged());
	}
	
	
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		outState.putInt("currentmusicposition", currentmusicposition);
//		outState.putSerializable("allmusicliat", (Serializable)list);
//		outState.putSerializable("mylovelist", (Serializable)mylove);
//		outState.putSerializable("singerlist", (Serializable)musiclist);
//		outState.putSerializable("singername", (Serializable)singer_name);
//		outState.putSerializable("playlist", (Serializable)playlist);
//		super.onSaveInstanceState(outState);
//	}


	private GetAllMusicService getmusicservice=null;
	private ServiceConnection con=new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			getmusicservice=((MyBinder)service).getService();
//			Log.i("我是connection对象", service+"不为空");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}};
		
	class LinearLayoutOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			currenttab=(Integer) v.getTag();
			if(currenttab!=pretab){
				tablist.get(currenttab).setBackgroundColor(0xb900aeef);
				tablist.get(pretab).setBackgroundColor(0xb9ffffff);
				pretab=currenttab;
				viewpager.setCurrentItem(currenttab);
			}
		}
		
	}
	
	private void initList(Bundle savedInstanceState){
//		if(savedInstanceState!=null){
////			outState.putInt("currentmusicposition", currentmusicposition);
////			outState.putSerializable("allmusicliat", (Serializable)list);
////			outState.putSerializable("mylovelist", (Serializable)mylove);
////			outState.putSerializable("singerlist", (Serializable)musiclist);
////			outState.putSerializable("singername", (Serializable)singer_name);
////			outState.putSerializable("playlist", (Serializable)playlist);
//			currentmusicposition=savedInstanceState.getInt("currentmusicposition");
//			list=(List<MusicVO>) savedInstanceState.getSerializable("allmusicliat");
//			singer_name=(List<String>) savedInstanceState.getSerializable("singername");
//			musiclist=(List<List<MusicVO>>) savedInstanceState.getSerializable("allmusicliat");
//			mylove=(List<MusicVO>) savedInstanceState.getSerializable("allmusicliat");
//			return;
//		}
		list=new ArrayList<MusicVO>();
		singer_name=new ArrayList<String>();
		musiclist=new ArrayList<List<MusicVO>>();
		mylove=new ArrayList<MusicVO>();
		ContentResolver resolver=getContentResolver();
		Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
						null, MediaStore.Audio.Media.IS_MUSIC+"="+1, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		while(cursor.moveToNext()){
			MusicVO music=new MusicVO();
			music.music_id=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
			music.music_name=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
			music.music_uri=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			music.singer_name=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			music.file_size=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
			music.duration=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));					
			music.special=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//			music.ismusic=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC));
//			music.isalarm=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_ALARM));
			music.miaoshu=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_NOTIFICATION));
//			if("青花瓷".equals(music.music_name))
//				LrcReader.LogInfo(music.music_uri.substring(0,music.music_uri.length()-4)+".lrc");
			Log.i("Ismusic&&isalarm", music.toString());
			list.add(music);
			if(singer_name!=null){
				String sname=music.getSinger_name();
				if(singer_name.contains(sname)){
					int index=singer_name.indexOf(sname);
					musiclist.get(index).add(music);
				}else{
					singer_name.add(music.getSinger_name());
					List<MusicVO> onelist=new ArrayList<MusicVO>();
					onelist.add(music);
					musiclist.add(onelist);
				}
			}
			if("16".equals(music.miaoshu)){
				mylove.add(music);
			}	
			}
		
	}
	
	class ViewPagerAdapter extends FragmentPagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragment.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragment.size();
		}
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
//		super.onConfigurationChanged(newConfig);
	}
	
	class MyPagerChanged implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			currenttab=arg0;
			if(currenttab!=pretab){
				tablist.get(currenttab).setBackgroundColor(0xb900aeef);
				tablist.get(pretab).setBackgroundColor(0xb9ffffff);
				pretab=currenttab;
			}
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		SharedPreferences sp=getSharedPreferences("currentpager", Context.MODE_PRIVATE);
		Editor editer=sp.edit();
		editer.putInt("pagernumber", viewpager.getCurrentItem());
		editer.commit();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		SharedPreferences sp=getSharedPreferences("currentpager", Context.MODE_PRIVATE);
		pretab=currenttab=sp.getInt("pagernumber", 1);
		tablist.get(currenttab).setBackgroundColor(0xb900aeef);
		viewpager.setCurrentItem(currenttab);
		super.onResume();
	}
	
	

}

package com.example.activity;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.adapter.MyFragementAdapter;
import com.example.entity.Contents;
import com.example.entity.Exitactivity;
import com.example.entity.GetData;
import com.example.entity.GetListInfo;
import com.example.entity.LoadExpandData;
import com.example.entity.Music;
import com.example.localmusic.R;
import com.example.service.PlayMusicSevice;


public class MainActivity extends FragmentActivity implements OnClickListener {
	private ArrayList<Fragment> list = null;
	private ViewPager myViewPager;
	public static List<Music> musicList = null;
	public static List<Map<String, String>> musicListExpand = null;
	private LinearLayout ysj_linearLayout;
	private LinearLayout zj_linearLayout;
	private LinearLayout gq_linearLayout;
	private LinearLayout bflb_linearLayout;
	private ImageView ysj_img;
	private ImageView zj_img;
	private ImageView gq_img;
	private ImageView bflb_img;
	public static int position;
	private LinearLayout bottom_musicInfo;
	public static HashSet<String> sets;
	public static List<String> albumLists = null;
	public static int count = 0;
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		// 记录Activity创建的次数
		Exitactivity.getInstance().addActivity(this);
		Intent serviceIntent = new Intent(this, PlayMusicSevice.class);
		startService(serviceIntent);
		count += 1;
		// 歌曲列表信息
		musicList = GetData.getLocalMusic(getApplicationContext());
		// 艺术家信息
		if (count == 1) {
			musicListExpand = LoadExpandData.loadSongs(getApplicationContext());
		}
		// 专辑信息
		albumLists = GetListInfo.selectAlbums(musicList);

		// -----------接收从别的Activity传过来的数据-----------------------------------------
		// 初始化控件
		initView();
		intentViewPager();

	}

	private void initView() {
		myViewPager = (ViewPager) findViewById(R.id.viewpage);
		// 找到顶部Tab
		ysj_linearLayout = (LinearLayout) findViewById(R.id.tab_linear_ysj);
		ysj_img = (ImageView) findViewById(R.id.tab_bg_ysj);
		ysj_linearLayout.setOnClickListener(this);

		zj_linearLayout = (LinearLayout) findViewById(R.id.tab_linear_zj);
		zj_img = (ImageView) findViewById(R.id.tab_bg_zj);
		zj_linearLayout.setOnClickListener(this);

		gq_linearLayout = (LinearLayout) findViewById(R.id.tab_linear_gq);
		gq_img = (ImageView) findViewById(R.id.tab_bg_gq);
		gq_linearLayout.setOnClickListener(this);

		bflb_linearLayout = (LinearLayout) findViewById(R.id.tab_linear_bflb);
		bflb_img = (ImageView) findViewById(R.id.tab_bg_bflb);
		bflb_linearLayout.setOnClickListener(this);

		// 找到底部的歌曲信息
		bottom_musicInfo = (LinearLayout) findViewById(R.id.bottom_music_info);
		bottom_musicInfo.setOnClickListener(this);

	}

	// 把Fragment放入ViewPager
	private void intentViewPager() {
		Fragment ysj = MyFragment.newInstance(Contents.YSJ);
		Fragment zj = MyFragment.newInstance(Contents.ZJ);
		Fragment gq = MyFragment.newInstance(Contents.GQ);
		Fragment bflb = MyFragment.newInstance(Contents.BFLB);

		list = new ArrayList<Fragment>();
		list.add(ysj);
		list.add(zj);
		list.add(gq);
		list.add(bflb);

		myViewPager.setAdapter(new MyFragementAdapter(
				getSupportFragmentManager(), list));
		myViewPager.setCurrentItem(0);

		myViewPager.setOnPageChangeListener(new myPageChangeListener());

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		TextView tv_bottomMusicName = (TextView) findViewById(R.id.musicing_nusicname);
		TextView tv_bottomSinger = (TextView) findViewById(R.id.musicing_singer);

		SharedPreferences playingPreferences = getSharedPreferences(
				"playing_music", Activity.MODE_PRIVATE);
		String musicInfoName = playingPreferences.getString("musicName", "");
		String musicInfoSinger = playingPreferences.getString("singer", "");

		tv_bottomMusicName.setText(musicInfoName);
		tv_bottomSinger.setText(musicInfoSinger);

	}

	// 改变顶部Tab的图片标志
	public void changeTab(int flag) {
		if (flag == 0) {
			ysj_img.setImageResource(R.drawable.header_bg);
			zj_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			gq_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			bflb_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
		}

		if (flag == 1) {
			zj_img.setImageResource(R.drawable.header_bg);
			ysj_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			gq_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			bflb_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
		}
		if (flag == 2) {
			ysj_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			zj_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			gq_img.setImageResource(R.drawable.header_bg);
			bflb_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
		}
		if (flag == 3) {
			ysj_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			zj_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			gq_img.setImageResource(R.drawable.nowplaying_seekbar_progress_h);
			bflb_img.setImageResource(R.drawable.header_bg);
		}

	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.tab_linear_ysj) {
			changeTab(0);
			position = 0;
			myViewPager.setCurrentItem(0);

		} else if (id == R.id.tab_linear_zj) {
			changeTab(1);
			position = 1;

			myViewPager.setCurrentItem(1);

		} else if (id == R.id.tab_linear_gq) {
			changeTab(2);
			position = 2;

			myViewPager.setCurrentItem(2);

		} else if (id == R.id.tab_linear_bflb) {
			changeTab(3);
			position = 3;
			myViewPager.setCurrentItem(3);

		} else if (id == R.id.bottom_music_info) {
			/*
			 * Intent intent=new Intent(MainActivity.this,PlayActivity.class);
			 * startActivity(intent);
			 */
		}
	}

	class myPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {

			if (position == 0) {
				changeTab(0);

			} else if (position == 1) {
				changeTab(1);

			} else if (position == 2) {
				changeTab(2);

			} else if (position == 3) {
				changeTab(3);

			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.yes: // do something here
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setTitle("退出") // 设置对话框标题 -
					.setMessage("你确定退出吗？"); // 提示内容
			new com.example.entity.ExitAlertDialog().setPositiveButton(builder); // 为AlertDialog.Builder添加【确定】按钮
			new com.example.entity.ExitAlertDialog().setNegativeButton(builder) // 为AlertDialog.Builder添加【取消】按钮
					.create().show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}

package com.jk.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.jk.impl.IMusic;
import com.jk.impl.onMusicOver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class KuGouActivity extends ALLActivity {
	/** Called when the activity is first created. */
	private List<Map<String, String>> musicList = new ArrayList<Map<String, String>>();
	private String musicPath;
	private String musicName;
	private String musicAlbum;
	private String musicArtist;
	private String musicAlbumKey;
	private String musicAlbumArtPath;
	private ListView lv_sing;
	private int musicTime;
	private long mExitTime = 0;
	private Intent in;
	private IMusic binder;
	private ImageView img_zj;
	private ImageView img_up;
	private ImageView img_pause;
	private ImageView img_next;
	private ImageView img_menu;
	private SeekBar seekBar;
	private SimpleAdapter adapter;
	private List<List<Map<String, String>>> chile;
	private int musicid=10000;
	private String action = "com.jk.service.musicservice";
	private boolean f = false;
	private int size=0;

	private SimpleExpandableListAdapter a;
	private ExpandableListView pand;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		musicList = loadSongs();
		initView();
		if (musicList.size() > 0) {
			in = new Intent();
			in.setAction(action);
			in.putExtra("path", musicList.get(0).get("path"));
			startService(in);
			bindService(in, conn, Context.BIND_AUTO_CREATE);
		}
	}

	ServiceConnection conn = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			System.out.println("onServiceDisconnected");
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			System.out.println("onServiceConnected");
			binder = (IMusic) service;

		}

	};
	private String p;
	private Notification notification;

	public void initView() {

		TabHost tabHost = (TabHost) findViewById(R.id.table);
		tabHost.setup();
		TabSpec spec = tabHost.newTabSpec("spec1");
		View view = View.inflate(this, R.layout.tab_item, null);
		spec.setIndicator(view);
		spec.setContent(R.id.linear1);
		tabHost.addTab(spec);

		TabSpec spec2 = tabHost.newTabSpec("spec2");
		View view2 = View.inflate(this, R.layout.tab_item2, null);
		spec2.setIndicator(view2);
		spec2.setContent(R.id.linear2);
		tabHost.addTab(spec2);

		TabSpec spec3 = tabHost.newTabSpec("spec3");
		View view3 = View.inflate(this, R.layout.tab_item3, null);
		spec3.setIndicator(view3);
		spec3.setContent(R.id.linear3);
		tabHost.addTab(spec3);

		TabSpec spec4 = tabHost.newTabSpec("spec4");
		View view4 = View.inflate(this, R.layout.tab_item4, null);
		spec4.setIndicator(view4);
		spec4.setContent(R.id.linear4);
		tabHost.addTab(spec4);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		pand = (ExpandableListView) findViewById(R.id.expand);
		img_zj = (ImageView) findViewById(R.id.img_zj);
		img_up = (ImageView) findViewById(R.id.img_up);
		img_pause = (ImageView) findViewById(R.id.img_pause);
		img_next = (ImageView) findViewById(R.id.img_next);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		lv_sing = (ListView) findViewById(R.id.lv_sing);
		// �����ҳ�����ֵ
		adapter = new SimpleAdapter(getApplicationContext(), musicList,
				R.layout.song_item, new String[] { "musicName", "musicArtist",
						"musicTime", "musicPath", "musicRating" }, new int[] {
						R.id.tv_name, R.id.tv_songer, R.id.tv_time,
						R.id.tv_musicPath, R.id.tv_musicRating });
		lv_sing.setAdapter(adapter);
		lv_sing.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Intent intent=new Intent();
				// intent.putExtra("id",
				// musicList.get(position).get("musicRating"));
				// intent.setClass(getApplicationContext(), play.class);
				// startActivity(intent);
				System.out.println(position+"***********");
				Map<String, String> m = new HashMap<String, String>();
				m = (Map<String, String>) adapter.getItem(position);
				if(musicid==Integer.parseInt(m.get("id"))){
					start(null);
				}else{
				musicid = Integer.parseInt(m.get("id"));
				if (binder != null) {
					binder.dorevmo();
					binder.doRset();
					unbindService(conn);
					stopService(in);
					binder = null;
					in = null;
					in = new Intent();
					in.setAction(action);
					
					// img_zj.
					if("".equals(m.get("musicAlbumImage"))){
						img_zj.setBackgroundDrawable(getResources().getDrawable(R.drawable.defaul));
					}else{
					
					InputStream i = ClassLoader.getSystemClassLoader()
							.getResourceAsStream(m.get("musicAlbumImage"));

					byte[] data = read(i);

					Bitmap bit = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					img_zj.setImageBitmap(bit);
					}
					// musicAlbumImage
					in.putExtra("path", m.get("path"));
					// System.out.println("path" + m.get("path") + "********");
					startService(in);
					bindService(in, conn, Context.BIND_AUTO_CREATE);

					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								Thread.sleep(500);
								if (binder != null) {

									binder.init(seekBar, new onMusicOver() {
										@Override
										public void onMusicOver() {
											// TODO Auto-generated method stub
											next(null);
										}
									});

								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}).start();

					f = true;
					img_pause.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.start));
				}
			}
			}
		});

		if (binder != null) {
			img_pause.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.start));
		} else {
			img_pause.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.pause));
		}

		// ��������ҳ�������
		List<Map<String, String>> lis = new ArrayList<Map<String, String>>();
		chile = new ArrayList<List<Map<String, String>>>();
		Map<String, String> pmap = new HashMap<String, String>();
		Map<String, String> m;
		m = new HashMap<String, String>();
		for (int i = 0; i < musicList.size(); i++) {
			pmap = new HashMap<String, String>();
			
			String displayName = musicList.get(i).get("displayName");

			if (m.get(displayName) == null) {
				m.put(displayName, displayName);
				pmap.put("name", displayName);
				lis.add(pmap);
			}

			
			
		}
		Map<String, String> mc = new HashMap<String, String>();
		for (int j = 0; j < lis.size(); j++) {

			String entry = lis.get(j).get("name");

			List<Map<String, String>> l = new ArrayList<Map<String, String>>();
			for (int i = 0; i < musicList.size(); i++) {

				String displayName = musicList.get(i).get("displayName");
				System.out.println(displayName + "displayName");
				if (entry.equals(displayName)) {
					mc = new HashMap<String, String>();
					System.out.println(displayName + "displayName********");
					mc.put("musicName", musicList.get(i).get("musicName"));
					mc.put("musicTime", musicList.get(i).get("musicTime"));
					mc.put("path", musicList.get(i).get("path"));
					mc.put("id", musicList.get(i).get("id"));
					mc.put("musicAlbumImage",
							musicList.get(i).get("musicAlbumImage"));

					l.add(mc);
				}

			}
			chile.add(l);
		}
		a = new SimpleExpandableListAdapter(this, lis,
				android.R.layout.simple_expandable_list_item_1,
				new String[] { "name" }, new int[] { android.R.id.text1 },
				chile, android.R.layout.simple_expandable_list_item_2,
				new String[] { "musicName", "musicTime" }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		pand.setAdapter(a);

		pand.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				System.out.println(	Integer.parseInt(chile.get(groupPosition)
						.get(childPosition).get("id"))+"ccccccccc*****");
				if(musicid==Integer.parseInt(chile.get(groupPosition)
						.get(childPosition).get("id"))){
					start(null);
				}else{
				
				musicid = Integer.parseInt(chile.get(groupPosition)
						.get(childPosition).get("id"));
				
				if (binder != null) {
					binder.dorevmo();
					binder.doRset();
					unbindService(conn);
					stopService(in);
					binder = null;
					in = null;
					in = new Intent();
					in.setAction(action);

					
					in.putExtra(
							"path",
							chile.get(groupPosition).get(childPosition)
									.get("path"));
					// System.out.println("path" + m.get("path") + "********");
					startService(in);
					bindService(in, conn, Context.BIND_AUTO_CREATE);
					img_pause.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.start));
					if("".equals(chile.get(groupPosition).get(childPosition)
							.get("musicAlbumImage"))){
						img_zj.setBackgroundDrawable(getResources().getDrawable(R.drawable.defaul));
					}else{
					
					InputStream i = ClassLoader.getSystemClassLoader()
							.getResourceAsStream(
									chile.get(groupPosition).get(childPosition)
											.get("musicAlbumImage"));

					byte[] data = read(i);

					Bitmap bit = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					img_zj.setImageBitmap(bit);
					}
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								Thread.sleep(500);
								if (binder != null) {

									binder.init(seekBar, new onMusicOver() {
										@Override
										public void onMusicOver() {
											// TODO Auto-generated method stub
											next(null);
										}
									});

								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}).start();

					f = true;
					img_pause.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.start));
				}

			
			}
				return false;
			}
		});
	}

	public void up(View v) {
		if (in != null && binder != null) {
			binder.dorevmo();
			binder.dostop();
			unbindService(conn);
			stopService(in);
		}

		int size = musicList.size();

		if (musicid <= 0) {
			musicid = size-1 ;
		} else {
			musicid = musicid - 1;
		}
		in = new Intent();
		in.setAction(action);
		in.putExtra("path", musicList.get(musicid).get("path"));
		startService(in);
		bindService(in, conn, Context.BIND_AUTO_CREATE);
		img_pause.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.start));
		 
		if("".equals(musicList.get(musicid).get("musicAlbumImage"))){
			img_zj.setBackgroundDrawable(getResources().getDrawable(R.drawable.defaul));
		}else{
		
		InputStream i = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(
						musicList.get(musicid).get("musicAlbumImage"));

		byte[] data = read(i);

		Bitmap bit = BitmapFactory.decodeByteArray(data, 0,
				data.length);
		img_zj.setImageBitmap(bit);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (binder != null) {
					binder.init(seekBar, new onMusicOver() {
						@Override
						public void onMusicOver() {
							// TODO Auto-generated method stub
							next(null);
						}
					});
				}
			}
		}).start();
	}

	public void start(View v) {

		if (f) {
			binder.dopause();
			img_pause.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.pause));
			f = false;

		} else {
			binder.dostart();
			img_pause.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.start));
			f = true;
		}

	}

	public void next(View v) {
		if (in != null && binder != null) {
			binder.dorevmo();
			binder.dostop();
			unbindService(conn);
			stopService(in);
		}

		int size = musicList.size();
		if (size-1  <= musicid) {
			musicid = 0;
		} else {
			musicid = musicid + 1;
		}
		in = new Intent();
		in.setAction(action);
		in.putExtra("path", musicList.get(musicid).get("path"));
		startService(in);
		bindService(in, conn, Context.BIND_AUTO_CREATE);
		img_pause.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.start));
		if("".equals(musicList.get(musicid).get("musicAlbumImage"))){
			img_zj.setBackgroundDrawable(getResources().getDrawable(R.drawable.defaul));
		}else{
		
		InputStream i = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(
						musicList.get(musicid).get("musicAlbumImage"));

		byte[] data = read(i);

		Bitmap bit = BitmapFactory.decodeByteArray(data, 0,
				data.length);
		img_zj.setImageBitmap(bit);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (binder != null) {
					binder.init(seekBar, new onMusicOver() {
						@Override
						public void onMusicOver() {
							// TODO Auto-generated method stub
							next(null);
						}
					});

				}
			}
		}).start();
		 showNotification();
	}

	public List<Map<String, String>> loadSongs() {
		
		ContentResolver musicResolver = this.getContentResolver();
		Cursor musicCursor = musicResolver.query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Audio.Media.SIZE + ">80000", null, null);
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
				// ȡ�����ֵ�����
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
				musicName = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("musicName", musicName);
				// ȡ�����ֵ�ר�����
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
				musicAlbum = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("musicAlbum", musicAlbum);
				// ȡ�����ֵ��ݳ���
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
				musicArtist = musicCursor.getString(musicColumnIndex);
				musicDataMap.put("musicArtist", musicArtist);
				// ȡ�ø����Ӧ��ר����Ӧ��Key
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY);
				musicAlbumKey = musicCursor.getString(musicColumnIndex);
				// ȡ�ø���Ĵ�С
				musicColumnIndex = musicCursor
						.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
				musicTime = musicCursor.getInt(musicColumnIndex);

				//
				// Time musicTime = new Time();
				// musicTime.set(musicTime);
				String readableTime =  ":" ;
				int m=musicTime% 60000/1000;
				int o=musicTime / 60000;
				if(o==0){
					readableTime="00"+readableTime;
				}else if(0<o&&o<10){
					readableTime="0"+o+readableTime;
				}else{
					readableTime=o+readableTime;
				}
				if(m<10){
					readableTime=readableTime+"0"+m;
				}else{
					readableTime=	readableTime+m;
				}
				musicDataMap.put("musicTime", readableTime);
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
				ContentResolver albumResolver = this.getContentResolver();
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - mExitTime > 2000) {
				Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {

				for (int i = 0; i < activityList.size(); i++) {
					if (null != activityList.get(i)) {
						activityList.get(i).finish();
					}
				}
				System.exit(0);// �����˳�����
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public static byte[] read(InputStream in) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = in.read(buffer)) != -1) {
				outs.write(buffer, 0, len);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outs.toByteArray();
	}
	 @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		 showNotification();
		
		super.onStop();
	}
	
	public void showNotification(){
		 if(musicid!=10000){
			 notification = new Notification(R.drawable.logo, "���ڲ��Ÿ���"+musicList.get(musicid).get("musicName"), System.currentTimeMillis());
				//Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:18554622114"));
				try {
					ActivityInfo[] activitys = getPackageManager()
							.getPackageInfo(getPackageName(),
									PackageManager.GET_ACTIVITIES).activities;
					Intent intent=new Intent();
					intent.setClassName(getPackageName(), activitys[0].name);
					PendingIntent in=PendingIntent.getActivity(getApplicationContext(), 100,intent , 0);
					notification.setLatestEventInfo(getApplicationContext(),"���ڲ���",musicList.get(musicid).get("musicName"), in);
					//notification.defaults=Notification.DEFAULT_SOUND;
					notification.flags=notification.FLAG_AUTO_CANCEL;
					
					NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					nm.notify(0,notification);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
}
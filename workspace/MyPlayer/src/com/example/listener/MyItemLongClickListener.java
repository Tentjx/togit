package com.example.listener;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.example.fragment.ExpendableFragment;
import com.example.fragment.AllMusicFragment;
import com.example.fragment.MyLoveFragment;
import com.example.myplayer.MainActivity;
import com.example.myplayer.R;
import com.example.service.GetAllMusicService;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;
import com.example.vo.PopItemPosition;

import android.app.ExpandableListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MyItemLongClickListener implements
		OnItemLongClickListener {

	private int whichlist;
	private LayoutInflater inflater;
	private ListView listview;
	private MainActivity activity;
	public MyItemLongClickListener(LayoutInflater inflater,MainActivity activity,final int whichlist) {
		// TODO Auto-generated constructor stub
		this.inflater=inflater;
		this.whichlist=whichlist;
		this.activity=activity;
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, final View view,
			final int position, long id) {
		// TODO Auto-generated method stub
		if(view.getTag()==null&&whichlist==MyConstent.SINGER_MUSIC_LIST)
			return true;
		View popview =inflater.inflate(R.layout.popupwindow_layout, null);
		TextView pop_music_name=(TextView)popview.findViewById(R.id.pop_music_name);
		pop_music_name.setText(((TextView)view.findViewById(R.id.music_name)).getText());
		listview=(ListView)popview.findViewById(R.id.option_item);
		listview.setAdapter(new PopOptionListViewAdapter());
		final PopupWindow pop=new PopupWindow(popview,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new ColorDrawable(-00000));
//		pop.showAsDropDown(view);
		if(parent!=null){
			pop.showAtLocation(parent, Gravity.CENTER, 0, 0);
//			pop.update();
		}
		pop.setFocusable(true);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int p, long i) {
				// TODO Auto-generated method stub
				MusicVO music=null;
				PopItemPosition popposition=null;
				int groupposition=0;
				String option=(String)((TextView)v.findViewById(R.id.option_name)).getText();
				switch(whichlist){
				case MyConstent.ALL_MUSIC_LIST:
					music=activity.list.get(position);
					break;
				case MyConstent.SINGER_MUSIC_LIST:
					popposition=(PopItemPosition)view.getTag();
					groupposition=popposition.groupposition;
					music=activity.musiclist.get(groupposition).
							get(popposition.childposition);
					break;
				case MyConstent.MYLOVE_MUSIC_LIST:
					music=activity.mylove.get(position);
				}
				if("收藏".equals(option)){
					if(activity.mylove.contains(music)){
						Toast.makeText(activity, "本曲已经被收藏", Toast.LENGTH_SHORT).show();
						pop.dismiss();
						return;}
					music.miaoshu="16";
					activity.mylove.add(music);
					MyLoveFragment.myhandler.sendMessage(new Message());
					String where=MediaStore.Audio.Media._ID+"="+music.music_id;
					ContentValues updatevalues=new ContentValues();
					updatevalues.put(MediaStore.Audio.Media.IS_NOTIFICATION, "16");
					ContentResolver resover=activity.getContentResolver();
					resover.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, updatevalues,
							where, null);
					Toast.makeText(activity, music.music_name+"\n收藏成功", Toast.LENGTH_SHORT).show();
				}else if("删除".equals(option)){
					File file=new File(music.music_uri);
					String where=MediaStore.Audio.Media._ID+"="+music.music_id;
					ContentResolver resover=activity.getContentResolver();
					if(file.delete()&resover.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, where, null)==1){
						activity.list.remove(music);
						int index=-1;
						for(List<MusicVO> onelist:activity.musiclist){
							index++;
							if(onelist.remove(music))
								break;
						}
						if(activity.musiclist.get(index).size()==0){
							activity.musiclist.remove(index);
							activity.singer_name.remove(index);
						}
						activity.mylove.remove(music);
						Message msg=new Message();
						switch (whichlist) {
						case MyConstent.ALL_MUSIC_LIST:
							AllMusicFragment.myhandler.sendMessage(msg);
							break;
						case MyConstent.SINGER_MUSIC_LIST:
							SingerFragment.myhandler.sendMessage(msg);
							break;
						case MyConstent.MYLOVE_MUSIC_LIST:
							MyLoveFragment.myhandler.sendMessage(msg);
							break;
						}
//						String where=MediaStore.Audio.Media._ID+"="+music.music_id;
//						ContentResolver resover=activity.getContentResolver();
//						resover.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, where, null);
						Toast.makeText(activity, "成功删除\t"+music.music_name, Toast.LENGTH_SHORT).show();
						}else {
							Toast.makeText(activity, "未能成功删除", Toast.LENGTH_SHORT).show();
						}
				}else if("设为铃声".equals(option)){
					String where=MediaStore.Audio.Media._ID+"="+music.music_id;
					ContentValues updatevalues=new ContentValues();
					updatevalues.put(MediaStore.Audio.Media.IS_RINGTONE, true);
					ContentResolver resover=activity.getContentResolver();
					resover.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, updatevalues,
							where, null);
					Uri uri=Uri.parse(music.music_uri);
					RingtoneManager.setActualDefaultRingtoneUri(activity, RingtoneManager.TYPE_RINGTONE,
							uri);
					Toast.makeText(activity, "成功将\""+music.music_name+"\"设为铃声", Toast.LENGTH_SHORT).show();
				}else if("设为闹钟".equals(option)){
					String where=MediaStore.Audio.Media._ID+"="+music.music_id;
					ContentValues updatevalues=new ContentValues();
					updatevalues.put(MediaStore.Audio.Media.IS_ALARM, true);
					ContentResolver resover=activity.getContentResolver();
					resover.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, updatevalues,
							where, null);
					Uri uri=Uri.parse(music.music_uri);
					RingtoneManager.setActualDefaultRingtoneUri(activity, RingtoneManager.TYPE_ALARM, uri);
					Toast.makeText(activity, "成功将\""+music.music_name+"\"设为闹钟", Toast.LENGTH_SHORT).show();
				}else if("取消收藏".equals(option)){
					activity.mylove.remove(music);
					MyLoveFragment.myhandler.handleMessage(new Message());
					String where=MediaStore.Audio.Media._ID+"="+music.music_id;
					ContentValues updatevalues=new ContentValues();
					updatevalues.put(MediaStore.Audio.Media.IS_NOTIFICATION, "");
					ContentResolver resover=activity.getContentResolver();
					resover.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, updatevalues,
							where, null);
				}
				pop.dismiss();
			}
		});
		return true;
	}
	
	
	class PopOptionListViewAdapter extends BaseAdapter{

		List<String> optionlist;
		public PopOptionListViewAdapter(){
			optionlist=new ArrayList<String>();
			optionlist.add("收藏");
			optionlist.add("删除");
			optionlist.add("设为铃声");
			optionlist.add("设为闹钟");
			optionlist.add("取消收藏");
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return optionlist.size()-1;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return optionlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null)
				convertView=inflater.inflate(R.layout.pop_item_layout, null);
			ImageView image=(ImageView)convertView.findViewById(R.id.option_image);
			TextView text=(TextView)convertView.findViewById(R.id.option_name);
			if(whichlist==MyConstent.MYLOVE_MUSIC_LIST&&position==0){
				text.setText(optionlist.get(optionlist.size()-1));
			}else{
				text.setText(optionlist.get(position));
			}
			return convertView;
		}
		
	}

}

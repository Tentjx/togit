package com.example.listener;

import com.example.fragment.PlayFragment;
import com.example.myhandler.ListviewUpdateHandler;
import com.example.myplayer.MainActivity;
import com.example.myplayer.R;
import com.example.service.PlayMusicService;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;
import com.example.vo.PopItemPosition;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class SingerFragment extends Fragment {

	private MainActivity activity;
	public static ListviewUpdateHandler myhandler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setRetainInstance(true);
		activity=(MainActivity)getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.expendable_layout, container,false);
		ExpandableListView expand=(ExpandableListView)view.findViewById(R.id.expendablelistview);
		ExpandAdapter adapter=new ExpandAdapter();
		myhandler=new ListviewUpdateHandler(adapter);
		expand.setAdapter(adapter);
		expand.setOnItemLongClickListener(new MyItemLongClickListener(inflater,
				(MainActivity)getActivity(), MyConstent.SINGER_MUSIC_LIST));
		ViewGroup p=(ViewGroup) view.getParent();
		if(p!=null)
			p.removeAllViewsInLayout();
		return view;
	}
	
	class ExpandAdapter extends BaseExpandableListAdapter{

		private LayoutInflater inflater;
		public ExpandAdapter() {
			// TODO Auto-generated constructor stub
			inflater=activity.getLayoutInflater();
		}
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return activity.singer_name.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return activity.musiclist.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return activity.singer_name.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return activity.musiclist.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null)
				convertView=inflater.inflate(R.layout.group_layout,parent,false);
			TextView singer_name=(TextView)convertView.findViewById(R.id.group_singer_name);
			TextView count_music=(TextView)convertView.findViewById(R.id.group_count);
			singer_name.setText(activity.singer_name.get(groupPosition));
			count_music.setText("共"+activity.musiclist.get(groupPosition).size()+"首");
			convertView.setTag(null);
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null)
				convertView=inflater.inflate(R.layout.music_item_layout, parent,false);
			MusicVO music=activity.musiclist.get(groupPosition).get(childPosition);
			((TextView)convertView.findViewById(R.id.music_name)).setText(music.getMusic_name());
			((TextView)convertView.findViewById(R.id.singer)).setText(music.getSinger_name());
			((TextView)convertView.findViewById(R.id.file_size)).setText(music.getFile_size());
			((TextView)convertView.findViewById(R.id.music_time)).setText(music.getDuration());
//			ImageView singer_picture=(ImageView)convertView.findViewById(R.id.singer_picture);
			convertView.setTag(new PopItemPosition(groupPosition, childPosition));
			convertView.setLongClickable(true);
//			convertView.setClickable(false);
			convertView.setTag(new PopItemPosition(groupPosition, childPosition));
			convertView.setOnClickListener(onclick);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	android.view.View.OnClickListener onclick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PopItemPosition popposition=(PopItemPosition)v.getTag();
			if(popposition!=null){
				activity.playlist=activity.musiclist.get(popposition.groupposition);
				activity.currentmusicposition=popposition.childposition;
			}
//			if(PlayFragment.update!=null)
//				PlayFragment.update.handleMessage(new Message());
			activity.viewpager.setCurrentItem(3);
			MusicVO music=activity.playlist.get(activity.currentmusicposition);
			Intent intent=new Intent();
			intent.putExtra("user_option", MyConstent.PLAY_MUSIC);
			intent.putExtra("duration", music.duration);
			intent.putExtra("music_uri", music.music_uri);
			intent.setClass(activity, PlayMusicService.class);
			activity.startService(intent);
		}
	};
}

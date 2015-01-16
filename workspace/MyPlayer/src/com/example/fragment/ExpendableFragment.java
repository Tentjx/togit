package com.example.fragment;

import java.util.List;

import com.example.listener.ExpandableListOnChildItemClickListener;
import com.example.listener.MyItemLongClickListener;
import com.example.myhandler.ListviewUpdateHandler;
import com.example.myplayer.MainActivity;
import com.example.myplayer.R;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;
import com.example.vo.PopItemPosition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class ExpendableFragment extends Fragment {

	private List<String> singer_name;
	private List<List<MusicVO>> musiclist;
	private ExpandableListView expandlist;
	public static ListviewUpdateHandler myhandler;
	public ExpendableFragment(List<String> singer_name,List<List<MusicVO>> musiclist) {
		// TODO Auto-generated constructor stub
		this.singer_name=singer_name;
		this.musiclist=musiclist;
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.expendable_layout, container,false);
		expandlist=(ExpandableListView)view.findViewById(R.id.expendablelistview);
		ExpandableAdapter myadapter=new ExpandableAdapter();
		expandlist.setAdapter(myadapter);
		myhandler=new ListviewUpdateHandler(myadapter);
		expandlist.setOnItemLongClickListener(new MyItemLongClickListener(inflater,
				(MainActivity)getActivity(), MyConstent.SINGER_MUSIC_LIST));
		
		expandlist.setOnChildClickListener(
				new OnChildClickListener() {
					
					@Override
					public boolean onChildClick(ExpandableListView parent, View v,
							int groupPosition, int childPosition, long id) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "asdfsa", Toast.LENGTH_SHORT).show();
						return true;
					}
				});
		ViewGroup p = (ViewGroup)view.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        } 
		return view;
	}
	
	class ExpandableAdapter extends BaseExpandableListAdapter{

//		LinkedList<MusicVO> linkedlist;
//		List<String> singer_name;
//		List<List<MusicVO>> musiclist;
		MusicVO music;
		LayoutInflater inflater;
		public ExpandableAdapter() {
			inflater=getActivity().getLayoutInflater();
		}
		
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return singer_name.size();
		}
		
		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return musiclist.get(groupPosition).size();
		}
		
		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return singer_name.get(groupPosition);
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return musiclist.get(groupPosition).get(childPosition);
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
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if(convertView==null)
				convertView=inflater.inflate(R.layout.group_layout, null);
			((TextView)convertView.findViewById(R.id.group_singer_name)).setText(singer_name.get(groupPosition));
			((TextView)convertView.findViewById(R.id.group_count)).setText("共"+musiclist.get(groupPosition).size()+"首");
			convertView.setTag(null);
			return convertView;
		}
		
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null)
				convertView=inflater.inflate(R.layout.music_item_layout, null);
			MusicVO music=musiclist.get(groupPosition).get(childPosition);
			((TextView)convertView.findViewById(R.id.music_name)).setText(music.getMusic_name());
			((TextView)convertView.findViewById(R.id.singer)).setText(music.getSinger_name());
			((TextView)convertView.findViewById(R.id.file_size)).setText(music.getFile_size());
			((TextView)convertView.findViewById(R.id.music_time)).setText(music.getDuration());
//			ImageView singer_picture=(ImageView)convertView.findViewById(R.id.singer_picture);
			convertView.setTag(new PopItemPosition(groupPosition, childPosition));
			convertView.setLongClickable(true);
			convertView.setClickable(true);
			return convertView;
		}
		
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean areAllItemsEnabled() {
			// TODO Auto-generated method stub
			return true;
		}
		
		}

}

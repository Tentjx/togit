package com.example.adapter;

import java.util.List;
import java.util.Map;

import com.example.localmusic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public  class MyExpandListviewAdapter extends BaseExpandableListAdapter {
	private List<List<Map<String, String>>> expandChild;
	private List<Map<String, String>> expandGroup;
    private Context context;

    
	public MyExpandListviewAdapter(List<List<Map<String, String>>> expandChild,
			List<Map<String, String>> expandGroup, Context context) {
		super();
		this.expandChild = expandChild;
		this.expandGroup = expandGroup;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPostion) {
		return expandChild.get(groupPosition).get(childPostion);
	}

	@Override
	public long getChildId(int groupPosition, int childPostion) {
		return childPostion;
	}

	@Override
	public View getChildView(int groupPosition, int childPostion, boolean isLastChild, View convertView,
			ViewGroup parent) {
     if (convertView==null) {
  		convertView=LayoutInflater.from(context).inflate(com.example.localmusic.R.layout.gq_frag_item, null);
        
      }
        TextView tv_musicName=(TextView) convertView.findViewById(R.id.gqfrag_tv_songName);
        TextView tv_singerTextView=(TextView) convertView.findViewById(R.id.gqfrag_tv_songer);
        TextView tv_totaltimeTextView=(TextView) convertView.findViewById(R.id.gqfrag_tv_toaltime);
        @SuppressWarnings("unchecked")
		String musicNameString=((Map<String, String>) getChild(groupPosition, childPostion)).get("musicName");
		@SuppressWarnings("unchecked")
		String singerString=((Map<String, String>) getChild(groupPosition, childPostion)).get("singer");
		@SuppressWarnings("unchecked")
		String timesss=""+((Map<String, String>) getChild(groupPosition, childPostion)).get("musicTime");
		
		tv_musicName.setText(musicNameString);
		tv_singerTextView.setText(singerString);
		tv_totaltimeTextView.setText(timesss);
		
        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return expandChild.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return expandGroup.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return expandGroup.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		   if (convertView==null) {
		  		convertView=LayoutInflater.from(context).inflate(com.example.localmusic.R.layout.ysj_frag_expand_group, null);
		        
		      }
		   TextView tv_singerNameTextView=(TextView) convertView.findViewById(R.id.tv_expan_group_singer);
		   TextView tv_songsCounTextView=(TextView) convertView.findViewById(R.id.tv_expan_group_count);
		   
		   String airistsString=expandGroup.get(groupPosition).get("name");
		   tv_singerNameTextView.setText(airistsString);
		   tv_songsCounTextView.setText(""+expandChild.get(groupPosition).size()+"首歌曲");
		   return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPostion) {
		return true;
	}

}

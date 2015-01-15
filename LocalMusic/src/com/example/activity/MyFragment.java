package com.example.activity;


import java.util.List;
import java.util.Map;
import com.example.adapter.AlbumsListViewAdapter;
import com.example.adapter.MyExpandListviewAdapter;
import com.example.adapter.SongsListViewAdapter;
import com.example.entity.Contents;
import com.example.entity.GetListInfo;
import com.example.entity.Music;
import com.example.entity.TimeFormat;
import com.example.localmusic.R;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MyFragment extends Fragment {
	private String key = null;
	public static Context context;
	private ListView zjFragListView;
	public static ListView gqFragListView;
	
	static MyFragment newInstance(String s) {
		MyFragment myFragment = new MyFragment();
		Bundle bundle = new Bundle();
		bundle.putString("key", s);
		myFragment.setArguments(bundle);
		return myFragment;

	}

	
	public MyFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Bundle bundle = getArguments();
		key = bundle != null? bundle.getString("key") : null;
		context=getActivity().getApplicationContext();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
     View view=null;
     if (key==null||key.equals(Contents.YSJ)) {
         view=inflater.inflate(R.layout.ysj_fragment,container, false);
         ExpandableListView expandableListView=(ExpandableListView) view.findViewById(R.id.expandableListView1);
         final List<Map<String, String>> expandGroup=GetListInfo.selectExpandGroup(MainActivity.musicListExpand);
     	 final  List<List<Map<String, String>>> expandChild=GetListInfo.selectExpandChild(expandGroup,MainActivity. musicListExpand);

        final MyExpandListviewAdapter adapter=new MyExpandListviewAdapter(expandChild, expandGroup, context);
         expandableListView.setAdapter(adapter);
         
         expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition,
					int childPosition, long arg4) {
				 
					@SuppressWarnings("unchecked")
					String musicPath=((Map<String, String>)adapter.  getChild(groupPosition, childPosition)).get("path");	
					
					int position=GetListInfo.getPosition(musicPath, MainActivity.musicList);					
					sendBroad(musicPath);
		
					startPlayMusicActivity1( position);

					return true;
			}
		});
 
     }
     if (key!=null&&key.equals(Contents.ZJ)) {
         view=inflater.inflate(R.layout.zj_fragment,container, false);
         zjFragListView=(ListView)view.findViewById(R.id.zj_frag_listview);
         final  List<String> albumlList=MainActivity.albumLists;
         AlbumsListViewAdapter adapter=new AlbumsListViewAdapter(albumlList, context);
         zjFragListView.setAdapter(adapter);
          
         zjFragListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
             String albumString=albumlList.get(arg2).toString();
             Intent intent=new Intent(context,AlbumSongsActivity.class);
             intent.putExtra("albumName", albumString);
             startActivity(intent);
				
			}
		});

	}
     if (key!=null&&key.equals(Contents.GQ)) {
         view=inflater.inflate(R.layout.gq_fragment,container, false);
         gqFragListView=(ListView) view.findViewById(R.id.songs_listview);
         List<Music> musicList=MainActivity.musicList;
         SongsListViewAdapter adapter=new SongsListViewAdapter(context , musicList);
         gqFragListView.setAdapter(adapter);
          gqFragListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Music music = (Music) gqFragListView.getItemAtPosition(position);
				String musicPath = music.getMusicPath();
				sendBroad(musicPath);
				startPlayMusicActivity1( position);
                   
			}
		});  

	}
     if ( key!=null&&key.equals(Contents.BFLB)) {
         view=inflater.inflate(R.layout.bflb_fragment,container, false);
         LinearLayout linearLayout=(LinearLayout) view.findViewById(R.id.bflb_linear);
         linearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
                Intent intent=new Intent(context,PlayListActivity.class);
                startActivity(intent);
				
			}
		});

	}
		return view;
	} 
	
	public static void sendBroad(String musicPath){
		Intent intentBroadCast = new Intent();
		intentBroadCast.putExtra("musicPath", musicPath);
		intentBroadCast.putExtra("PlayMessage", 1);
		intentBroadCast
				.setAction("com.waterworld.action.PLAYMUSICBROAD");
		context.sendBroadcast(intentBroadCast);
		
	}
	public void startPlayMusicActivity(String musicName,String singer,String musicPath,String totalTime,int position){
		Intent intent=new Intent(context,PlayMusicAcitvity.class);
		intent.putExtra("musicName", musicName);
		intent.putExtra("singer", singer);
		intent.putExtra("musicPath", musicPath);
		intent.putExtra("totalTime", totalTime);
		intent.putExtra("position", position);
		startActivity(intent);
		
	}
	
	public void startPlayMusicActivity1(int position){
		Intent intent=new Intent(context,PlayMusicAcitvity.class);
		intent.putExtra("position", position);
		startActivity(intent);
       
	}
}

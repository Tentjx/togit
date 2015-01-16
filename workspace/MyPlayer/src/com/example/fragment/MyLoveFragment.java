package com.example.fragment;

import java.io.FileDescriptor;
import java.util.List;

import com.example.fragment.AllMusicFragment.ListViewAdapter;
import com.example.listener.MyItemLongClickListener;
import com.example.listener.MyItemOnclickListener;
import com.example.myhandler.ListviewUpdateHandler;
import com.example.myplayer.MainActivity;
import com.example.myplayer.R;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;

import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyLoveFragment extends Fragment {

	private ListView listview;
	private List<MusicVO> list;
	private Uri uri;
	private ParcelFileDescriptor pfd;
	private FileDescriptor fd;
	private ListViewAdapter listviewadapter;
	public static ListviewUpdateHandler myhandler;
	private int whichlist;
	public MyLoveFragment(List<MusicVO> list,int whichlist) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.whichlist=whichlist;
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_layout, container, false);
		listview=(ListView)view.findViewById(R.id.fragmentlistview);
		listviewadapter=new ListViewAdapter(inflater,list,MyConstent.MYLOVE_MUSIC_LIST,(MainActivity)getActivity());
		myhandler=new ListviewUpdateHandler(listviewadapter);
		listview.setAdapter(listviewadapter);
		listview.setOnItemClickListener(new MyItemOnclickListener((MainActivity)getActivity(),
				whichlist));
		listview.setOnItemLongClickListener(new MyItemLongClickListener(inflater,
        		(MainActivity)getActivity(), whichlist));
		ViewGroup p = (ViewGroup)view.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        } 
		return view;
	}
	
}

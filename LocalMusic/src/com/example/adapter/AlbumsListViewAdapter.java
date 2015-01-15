package com.example.adapter;


import java.util.List;
import com.example.localmusic.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlbumsListViewAdapter extends BaseAdapter {
    private List<String> albumList;
    private Context context;
    

	public AlbumsListViewAdapter(List<String> albumList, Context context) {
		super();
		this.albumList = albumList;
		this.context = context;
	}
	public void setMusicListItem(List<String> albumList) {
		this.albumList=albumList;
	}
	
	@Override
	public int getCount() {
		return albumList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return albumList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(com.example.localmusic.R.layout.zj_frag_item, null);

      }
      TextView tv_albumName=(TextView) convertView.findViewById(R.id.zj_tv_zjName);
      tv_albumName.setText(albumList.get(position));
		return convertView;
	}

}

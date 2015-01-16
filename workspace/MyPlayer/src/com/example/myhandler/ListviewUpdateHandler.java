package com.example.myhandler;


import android.os.Handler;
import android.os.Message;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;

public class ListviewUpdateHandler extends Handler {

	private Object adapter;
	public ListviewUpdateHandler(Object adapter) {
		// TODO Auto-generated constructor stub
		this.adapter=adapter;
	}
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		if(adapter instanceof BaseAdapter){
			((BaseAdapter) adapter).notifyDataSetChanged();
		}else if(adapter instanceof BaseExpandableListAdapter){
			((BaseExpandableListAdapter) adapter).notifyDataSetChanged();
		}
	}

}

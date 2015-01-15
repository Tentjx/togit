package com.example.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragementAdapter extends FragmentPagerAdapter {
  
	private ArrayList<Fragment> list;

	public MyFragementAdapter(FragmentManager fm) {
		super(fm);
	}

	public MyFragementAdapter(android.support.v4.app.FragmentManager fragmentManager,ArrayList<Fragment> list) {
		super(fragmentManager);

		this.list=list;
	}


	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}

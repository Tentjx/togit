package com.jk.impl;

import android.widget.SeekBar;

public interface IMusic {

	//ÔÝÍ£²¥·Å
	public void dopause();
	//¼ÌÐø²¥·Å
	public void doresume();
	//²¥·Å
	public void dostart();
	//Í£Ö¹²¥·Å
	public void dostop();
	public void init(SeekBar seekbar,onMusicOver over);
	public void doRset();
	public void dorevmo();
}


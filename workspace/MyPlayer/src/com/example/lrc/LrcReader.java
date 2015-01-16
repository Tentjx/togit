package com.example.lrc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.example.vo.LrcUtil;

public class LrcReader {
	
	public List<LrcUtil> lyricslist;
	private String path;
	public int maxlenth;
	public LrcReader(String path) {
		// TODO Auto-generated constructor stub
		this.path=path;
		maxlenth=0;
		lyricslist=new ArrayList<LrcUtil>();
		try {
			loadLyrics();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(lyricslist,new SortByTime());  
	}
	private void loadLyrics() throws IOException{
		String linelyrics=null;
			InputStreamReader in=new InputStreamReader(new FileInputStream(path));
			BufferedReader bufferin=new BufferedReader(in);
			while((linelyrics=bufferin.readLine())!=null){
				parseLine(linelyrics);
			}
			in.close();
			bufferin.close();
	}
	
	private String parseLine(String linelyrics) {
		String reg="\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
		Pattern pattern=Pattern.compile(reg);
		Matcher matcher=pattern.matcher(linelyrics);
		if(matcher.find()){
			LrcUtil lrc=new LrcUtil();
			String time=matcher.group();
			lrc.lrctime=parseTime(time);
			lyricslist.add(lrc);
			return (lrc.lrctext=parseLine(linelyrics.substring(time.length())));
		}else{
			maxlenth=maxlenth<linelyrics.length()?linelyrics.length():maxlenth;
			return linelyrics;
		}
	}
	
	private int parseTime(String time) {
		// TODO Auto-generated method stub
		String temp=time.substring(1,time.length()-1);
        String[] s = temp.split(":");  
        int min = Integer.parseInt(s[0]);  
        String[] ss = s[1].split("\\.");  
        int sec = Integer.parseInt(ss[0]);  
        int mill = Integer.parseInt(ss[1]);  
        return min * 60 * 1000 + sec * 1000 + mill * 10; 
	}
	
	/**
	 *  public void parseLine(String line){
        String reg="\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
        Pattern pattern=Pattern.compile(reg);
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String time=matcher.group();
            LrcModel lModel=new LrcModel();
            lModel.setCurrentTime(parseTime(time));
            lModel.setCurrentContent(line.substring(time.length()));
            lmlist.addEle(lModel);
        }
    }
	 * */
	public static void LogInfo(String path){
		LrcReader lrc=new LrcReader(path);
		for(LrcUtil lrcutil:lrc.lyricslist){
			Log.i("info","time:"+lrcutil.lrctime+"\ttext:"+lrcutil.lrctext);
		}
	}
	
	class SortByTime implements Comparator<LrcUtil>{

		@Override
		public int compare(LrcUtil lhs, LrcUtil rhs) {
			// TODO Auto-generated method stub
			if(lhs.lrctime>rhs.lrctime)
				return 0;
			else
				return 1;
		}
		
	}
	
}

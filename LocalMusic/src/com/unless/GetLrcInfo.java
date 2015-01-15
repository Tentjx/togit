package com.unless;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;

public class GetLrcInfo {
      private List<Lrc> lrcList;
      private Lrc lrc;
	public GetLrcInfo() {
		lrcList = new ArrayList<Lrc>();
		lrc = new Lrc();
	}
	
	public String readLrc(String musicPath){
		StringBuilder stringBuilder=new StringBuilder();
		File file = new File(musicPath.replace(".mp3", ".lrc"));

		try {
			FileInputStream fileInputStream=new FileInputStream(file);
			InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"utf-8");
		    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
		    String string="";
		   while ((string=bufferedReader.readLine())!=null) {
		    	string = string.replace("[", "");
		    	string = string.replace("]", "@");
				
		    	String lrcData[]=string.split("@");
		    	if (lrcData.length>1) {
					lrc.setLrcString(lrcData[1]);
					
					int time=Str2Time(lrcData[0]);
					lrc.setLrcTime(time);
					
					lrcList.add(lrc);
					lrc=new Lrc();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			stringBuilder.append("暂无歌词");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			stringBuilder.append("暂无歌词");

		}
		return stringBuilder.toString();
		
		
	};
	
	public List<Lrc> getLrcList() {
		return lrcList;
	}
	
	public int Str2Time(String timeStr){
		timeStr=timeStr.replace(":", ".");
		timeStr=timeStr.replace(".", "@");
		
		String sqlitTime[]=timeStr.split("@");
		
		int minute=Integer.parseInt(sqlitTime[0]);
		int second=Integer.parseInt(sqlitTime[1]);
		int mililSecond=Integer.parseInt(sqlitTime[2]);
		
		int currentTime=(minute*60+second)*1000+mililSecond*10;

		return currentTime;
		
	}
	
	
      
}

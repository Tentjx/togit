package com.example.vo;

public class MusicVO {

	public int music_id;
	public String music_name;
	public String singer_name;
	public long file_size;
	public long duration;
	public String special;
	public String music_uri;
	public String ismusic;
	public String isalarm;
	public String miaoshu;
	public String getMusic_name() {
		if(isUnavailable(music_name))
			return music_name;
		else
			return "";
	}
	public String getSinger_name() {
		if(isUnavailable(singer_name))
			return singer_name;
		else
			return "";
	}
	public String getFile_size() {
		return dataFormat(true);
	}
	public String getDuration() {
		return dataFormat(false);
	}
	public String getSpecial() {
		if(isUnavailable(special))
			return special;
		else
			return "";
	}
	
	private boolean isUnavailable(String str){
		if(str==null||"<unknown>".equals(str)||"<Undefined>".equals(str))
			return false;
		else
			return true;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "music_name:"+music_name+"\tismusic:"+ismusic+"\t"+"isalarm:"+isalarm;
	}
	private String dataFormat(boolean flog){
		if(flog){
			if(file_size!=0){
				float size=((float)file_size)/1024/1024;
				return String.valueOf(size).substring(0,4)+"M";
			}else
				return "";
		}else{
			int length=(int)(duration/1000);
			int houers=length/3600;
			length%=3600;
			int min=length/60;
			length%=60;
			return String.format("%02d:%02d:%02d",houers,min,length);
		}
	}
}

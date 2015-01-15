package com.example.entity;

public class Music {
   private String singer; //歌手
   private String  album;  //专辑名
   private String musicName; //歌曲名
   private String musicPath; //歌曲路径
   private long musicTime;
   private long musicSize;
public String getSinger() {
	return singer;
}
public void setSinger(String singer) {
	this.singer = singer;
}
public String getAlbum() {
	return album;
}
public void setAlbum(String album) {
	this.album = album;
}
public String getMusicName() {
	return musicName;
}
public void setMusicName(String musicName) {
	this.musicName = musicName;
}
public String getMusicPath() {
	return musicPath;
}
public void setMusicPath(String musicPath) {
	this.musicPath = musicPath;
}
public long getMusicTime() {
	return musicTime;
}
public void setMusicTime(long musicTime) {
	this.musicTime = musicTime;
}
public long getMusicSize() {
	return musicSize;
}
public void setMusicSize(long musicSize) {
	this.musicSize = musicSize;
}
public Music() {
	super();
	// TODO Auto-generated constructor stub
}
public Music(String singer, String album, String musicName, String musicPath,
		long musicTime, long musicSize) {
	super();
	this.singer = singer;
	this.album = album;
	this.musicName = musicName;
	this.musicPath = musicPath;
	this.musicTime = musicTime;
	this.musicSize = musicSize;
}
public Music(String singer, String album, String musicName, String musicPath,
		long musicTime) {
	super();
	this.singer = singer;
	this.album = album;
	this.musicName = musicName;
	this.musicPath = musicPath;
	this.musicTime = musicTime;
}
   
   
}

package com.example.mp3player;

public class Music {
    private String singer;
    private String musicName;
    private String musicPath;
    private long time;
    
    
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Music(String singer, String musicName, String musicPath, long time
			) {
		super();
		this.singer = singer;
		this.musicName = musicName;
		this.musicPath = musicPath;
		this.time = time;
	}
	public Music() {
		super();
	}
    
    
}

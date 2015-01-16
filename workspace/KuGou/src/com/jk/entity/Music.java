package com.jk.entity;

public class Music {
	private String musicPath;
	private String musicName;
	private String musicAlbum;
	private String musicArtist;
	private String musicAlbumKey;
	private String musicAlbumArtPath;

	public String getMusicPath() {
		return musicPath;
	}

	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getMusicAlbum() {
		return musicAlbum;
	}

	public void setMusicAlbum(String musicAlbum) {
		this.musicAlbum = musicAlbum;
	}

	public String getMusicArtist() {
		return musicArtist;
	}

	public void setMusicArtist(String musicArtist) {
		this.musicArtist = musicArtist;
	}

	public String getMusicAlbumKey() {
		return musicAlbumKey;
	}

	public void setMusicAlbumKey(String musicAlbumKey) {
		this.musicAlbumKey = musicAlbumKey;
	}

	public String getMusicAlbumArtPath() {
		return musicAlbumArtPath;
	}

	public void setMusicAlbumArtPath(String musicAlbumArtPath) {
		this.musicAlbumArtPath = musicAlbumArtPath;
	}

	@Override
	public String toString() {
		return "Music [musicPath=" + musicPath + ", musicName=" + musicName
				+ ", musicAlbum=" + musicAlbum + ", musicArtist=" + musicArtist
				+ ", musicAlbumKey=" + musicAlbumKey + ", musicAlbumArtPath="
				+ musicAlbumArtPath + "]";
	}
	

}

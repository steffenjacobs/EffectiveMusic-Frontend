package me.steffenjacobs.effectivemusic.frontend.common.domain;

public class TrackImpl implements TrackDto {

	private String artist, album, title, year, comment, track, discNo, composer, artistSort;
	private long length;
	private double volume, position;

	public TrackImpl() {

	}

	@Override
	public String getArtist() {
		return artist;
	}

	@Override
	public String getAlbum() {
		return album;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public String getYear() {
		return year;
	}

	@Override
	public String getTrack() {
		return track;
	}

	@Override
	public String getDisc_no() {
		return discNo;
	}

	@Override
	public String getComposer() {
		return composer;
	}

	@Override
	public String getArtist_sort() {
		return artistSort;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public long getLength() {
		return length;
	}

	@Override
	public void setLength(long trackLength) {
		this.length = trackLength;
	}

	@Override
	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public void setAlbum(String album) {
		this.album = album;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public void setTrack(String track) {
		this.track = track;
	}

	@Override
	public void setDisc_no(String disc_no) {
		this.discNo = disc_no;
	}

	@Override
	public void setComposer(String composer) {
		this.composer = composer;
	}

	@Override
	public void setArtist_sort(String artist_sort) {
		this.artistSort = artist_sort;
	}

	@Override
	public double getPosition() {
		return position;
	}

	@Override
	public void setPosition(double position) {
		this.position = position;
	}

	@Override
	public double getVolume() {
		return volume;
	}

	@Override
	public void setVolume(double volume) {
		this.volume = volume;
	}
}
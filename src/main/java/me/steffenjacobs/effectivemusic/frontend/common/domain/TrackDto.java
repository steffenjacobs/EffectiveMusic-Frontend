package me.steffenjacobs.effectivemusic.frontend.common.domain;

/** @author Steffen Jacobs */
public interface TrackDto {

	String getArtist();

	String getAlbum();

	String getTitle();

	String getComment();

	String getYear();

	String getTrack();

	String getDisc_no();

	String getComposer();

	String getArtist_sort();

	void setTitle(String title);

	long getLength();

	void setLength(long trackLength);

	void setArtist(String artist);

	void setAlbum(String album);

	void setComment(String comment);

	void setYear(String year);

	void setTrack(String track);

	void setDisc_no(String disc_no);

	void setComposer(String composer);

	void setArtist_sort(String artist_sort);

	double getPosition();

	void setPosition(double position);

	double getVolume();

	void setVolume(double volume);
}
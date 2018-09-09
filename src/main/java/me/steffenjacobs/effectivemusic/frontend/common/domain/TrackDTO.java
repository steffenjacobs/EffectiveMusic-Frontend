package me.steffenjacobs.effectivemusic.frontend.common.domain;

import java.util.Date;

/** @author Steffen Jacobs */
public interface TrackDTO {

	long getBitrate();

	void setBitrate(long bitrate);

	int getTrack();

	void setTrack(int track);

	int getDiscNo();

	void setDiscNo(int discNo);

	String getArtistSort();

	void setArtistSort(String artistSort);

	Date getCreationDate();

	void setCreationDate(Date creationDate);

	String getArtist();

	void setArtist(String artist);

	String getAlbum();

	void setAlbum(String album);

	String getTitle();

	void setTitle(String title);

	String getComment();

	void setComment(String comment);

	Date getYear();

	void setYear(Date year);

	int getBpm();

	void setBpm(int bpm);

	String getComposer();

	void setComposer(String composer);

	String getGenre();

	void setGenre(String genre);

	String getPath();

	void setPath(String path);

	long getLength();

	void setLength(long length);

	long getListencount();

	void setListencount(long listencount);

}
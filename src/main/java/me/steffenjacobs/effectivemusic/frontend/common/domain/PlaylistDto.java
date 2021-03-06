package me.steffenjacobs.effectivemusic.frontend.common.domain;

import java.util.List;

/** @author Steffen Jacobs */
public interface PlaylistDto {

	void setTracks(List<TrackWrapperDto> tracks);

	List<TrackWrapperDto> getTracks();

	void setRepeatLoopStatus(int status);

	int getRepeatLoopStatus();

	void setCurrentIndex(int currentIndex);

	int getCurrentIndex();

	String getPlaylistName();
	
	void setPlaylistName(String playlistName);

}

package me.steffenjacobs.effectivemusic.frontend.common.domain;

/** @author Steffen Jacobs */
public interface TrackWrapperDto {

	LiveTrackDto getTrackDTO();

	String getPath();

	void setTrackDTO(LiveTrackDto trackDTO);

	void setPath(String path);

}

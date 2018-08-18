package me.steffenjacobs.effectivemusic.frontend.common.domain;

/** @author Steffen Jacobs */
public interface TrackWrapperDto {

	TrackDto getTrackDTO();

	String getPath();

	void setTrackDTO(TrackDto trackDTO);

	void setPath(String path);

}

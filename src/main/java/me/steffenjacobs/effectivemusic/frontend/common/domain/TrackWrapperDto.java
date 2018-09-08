package me.steffenjacobs.effectivemusic.frontend.common.domain;

/** @author Steffen Jacobs */
public interface TrackWrapperDto {

	LiveTrackDTO getTrackDTO();

	String getPath();

	void setTrackDTO(LiveTrackDTO trackDTO);

	void setPath(String path);

}

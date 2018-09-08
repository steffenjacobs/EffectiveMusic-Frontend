package me.steffenjacobs.effectivemusic.frontend.common.domain;

import java.util.List;

/** @author Steffen Jacobs */
public interface TrackListDTO {

	List<TrackDTO> getTracks();

	void setTracks(List<TrackDTO> tracks);

}

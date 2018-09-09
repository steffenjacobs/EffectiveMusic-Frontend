package me.steffenjacobs.effectivemusic.frontend.common.domain;

import java.util.List;

/** @author Steffen Jacobs */
public interface WeightedTrackListDTO {

	List<WeightedTrackDTO> getTracks();

	void setTracks(List<WeightedTrackDTO> tracks);

}

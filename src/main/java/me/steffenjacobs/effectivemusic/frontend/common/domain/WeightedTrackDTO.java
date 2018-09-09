package me.steffenjacobs.effectivemusic.frontend.common.domain;

/** @author Steffen Jacobs */
public interface WeightedTrackDTO {

	long getWeight();

	void setWeight(long weight);

	TrackDTO getTrack();

	void setTrack(TrackDTO track);

}
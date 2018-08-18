package me.steffenjacobs.effectivemusic.frontend.common.domain;

import java.util.List;

public class PlaylistImpl implements PlaylistDto {

	private List<TrackWrapperDto> tracks;

	@Override
	public void setTracks(List<TrackWrapperDto> tracks) {
		this.tracks = tracks;
	}

	@Override
	public List<TrackWrapperDto> getTracks() {
		return tracks;
	}
}
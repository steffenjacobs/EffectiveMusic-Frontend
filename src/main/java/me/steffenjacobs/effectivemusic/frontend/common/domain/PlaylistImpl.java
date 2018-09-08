package me.steffenjacobs.effectivemusic.frontend.common.domain;

import java.util.ArrayList;
import java.util.List;

public class PlaylistImpl {

	private List<TrackWithPathImpl> tracks = new ArrayList<>();

	public PlaylistImpl(PlaylistDto dto) {
		for (TrackWrapperDto track : dto.getTracks()) {
			tracks.add(new TrackWithPathImpl(track.getPath(), new LiveTrackImpl(track.getTrackDTO())));
		}
	}

	public PlaylistImpl() {
	}

	public void setTracks(List<TrackWithPathImpl> tracks) {
		this.tracks = tracks;
	}

	public List<TrackWithPathImpl> getTracks() {
		return tracks;
	}
}
package me.steffenjacobs.effectivemusic.frontend.common.domain;

import java.util.ArrayList;
import java.util.List;

public class PlaylistImpl {

	private List<TrackWithPathImpl> tracks = new ArrayList<>();
	
	private String playlistName;

	public PlaylistImpl(PlaylistDto dto) {
		playlistName = dto.getPlaylistName();
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

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playlistName == null) ? 0 : playlistName.hashCode());
		result = prime * result + ((tracks == null) ? 0 : tracks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaylistImpl other = (PlaylistImpl) obj;
		if (playlistName == null) {
			if (other.playlistName != null)
				return false;
		} else if (!playlistName.equals(other.playlistName))
			return false;
		if (tracks == null) {
			if (other.tracks != null)
				return false;
		} else if (!tracks.equals(other.tracks))
			return false;
		return true;
	}
	
	
}
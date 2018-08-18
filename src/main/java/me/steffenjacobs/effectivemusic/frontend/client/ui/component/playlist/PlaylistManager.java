package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import java.util.LinkedList;
import java.util.List;

import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistDto;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackDto;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackWrapperDto;

/** @author Steffen Jacobs */
public class PlaylistManager {

	private final List<TrackDto> playlist = new LinkedList<>();

	private final PlaylistPanel panel;

	public PlaylistManager(PlaylistPanel panel) {
		this.panel = panel;
	}

	public void add(TrackDto track) {
		playlist.add(track);
		refreshUi();
	}

	public void remove(TrackDto track) {
		playlist.remove(track);
		refreshUi();
	}

	public void insertAtPosition(TrackDto track, int position) {
		playlist.add(position, track);
		refreshUi();
	}

	private void refreshUi() {
		panel.clear();
		panel.setPlaylist(playlist);
	}

	public void updatePlaylist(PlaylistDto dto) {
		playlist.clear();
		for (TrackWrapperDto track : dto.getTracks()) {
			if (track != null && track.getTrackDTO() != null) {
				playlist.add(track.getTrackDTO());
			}
		}
		refreshUi();
	}

}

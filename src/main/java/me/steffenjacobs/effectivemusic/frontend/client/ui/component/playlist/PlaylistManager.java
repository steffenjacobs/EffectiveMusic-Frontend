package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.shared.SimpleEventBus;

import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.GotoPlaylistPositionEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.RemoveFromPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistImpl;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackWithPathImpl;

/** @author Steffen Jacobs */
public class PlaylistManager {

	private final List<TrackWithPathImpl> playlist = new LinkedList<>();

	private final PlaylistPanel panel;
	private final SimpleEventBus eventBus;
	private int currentlyPlayed = -1;

	public PlaylistManager(PlaylistPanel panel, SimpleEventBus evtBus) {
		this.panel = panel;
		this.eventBus = evtBus;
		panel.setManager(this);
	}

	private void refreshUi() {
		panel.clear();
		panel.setPlaylist(playlist);
	}

	public void updatePlaylist(PlaylistImpl newPlaylist) {
		if (playlist.equals(newPlaylist.getTracks())) {
			return;
		}
		playlist.clear();
		for (TrackWithPathImpl track : newPlaylist.getTracks()) {
			if (track != null) {
				playlist.add(track);
			}
		}
		refreshUi();
	}

	public void setCurrentTrack(int index) {
		if (currentlyPlayed != index) {
			currentlyPlayed = index;
			panel.setCurrentlyPlaying(index);
		}
	}

	public void elementClicked(TrackWithPathImpl track) {
		int position = playlist.indexOf(track);
		eventBus.fireEvent(new GotoPlaylistPositionEvent(position));
	}

	public void removeFromPlaylist(long index) {
		eventBus.fireEvent(new RemoveFromPlaylistEvent(Arrays.asList(index)));
	}
}

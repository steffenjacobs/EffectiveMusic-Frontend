package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import java.util.Arrays;

import com.google.gwt.event.shared.SimpleEventBus;

import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.GotoPlaylistPositionEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.LoadPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.NewPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.RemoveFromPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.RenamePlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.StorePlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.DefaultRequestCallback;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.remotefilebrowser.RemoteFileBrowserDialog;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistImpl;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackWithPathImpl;

/** @author Steffen Jacobs */
public class PlaylistManager {

	private PlaylistImpl playlist;

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
		if (newPlaylist.equals(playlist)) {
			return;
		}
		playlist = newPlaylist;
		refreshUi();
	}

	public void setCurrentTrack(int index) {
		if (currentlyPlayed != index) {
			currentlyPlayed = index;
			panel.setCurrentlyPlaying(index);
		}
	}

	public void elementClicked(TrackWithPathImpl track) {
		int position = playlist.getTracks().indexOf(track);
		eventBus.fireEvent(new GotoPlaylistPositionEvent(position));
	}

	public void removeFromPlaylist(long index) {
		eventBus.fireEvent(new RemoveFromPlaylistEvent(Arrays.asList(index)));
	}

	public void importPlaylist() {
		new RemoteFileBrowserDialog(this::onLoadPlaylist, eventBus, true);
	}

	private void onLoadPlaylist(String selectedFile) {
		eventBus.fireEvent(new LoadPlaylistEvent(selectedFile, new DefaultRequestCallback()));
	}

	public void renamePlaylist(String newName) {
		eventBus.fireEvent(new RenamePlaylistEvent(newName, new DefaultRequestCallback()));
	}

	public void storePlaylist() {
		eventBus.fireEvent(new StorePlaylistEvent("", new DefaultRequestCallback()));
	}

	public void createNewPlaylist(String name) {
		eventBus.fireEvent(new NewPlaylistEvent(name, new DefaultRequestCallback()));
	}
}

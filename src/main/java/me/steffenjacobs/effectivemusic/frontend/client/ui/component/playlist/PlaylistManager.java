package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import java.util.Arrays;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import me.steffenjacobs.effectivemusic.frontend.client.controller.MusicAutobeanFactory;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.GotoPlaylistPositionEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.LoadPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.NewPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.RemoveFromPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.RenamePlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.StorePlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.settings.GetSettingsEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.DefaultRequestCallback;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.misc.NotifcationService;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.remotefilebrowser.RemoteFileBrowserDialog;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistImpl;
import me.steffenjacobs.effectivemusic.frontend.common.domain.SettingsDTO;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackWithPathImpl;

/** @author Steffen Jacobs */
public class PlaylistManager {

	private PlaylistImpl playlist;

	private final PlaylistPanel panel;
	private final SimpleEventBus eventBus;
	private int currentlyPlayed = -1;
	private final MusicAutobeanFactory factory;
	private boolean hasPlaylistChanged = false;

	public PlaylistManager(PlaylistPanel panel, SimpleEventBus evtBus, MusicAutobeanFactory factory) {
		this.panel = panel;
		this.eventBus = evtBus;
		this.factory = factory;
		panel.setManager(this);
	}

	private void refreshUi() {
		panel.clear();
		panel.setPlaylist(playlist);
		hasPlaylistChanged = true;
		if (currentlyPlayed > -1) {
			setCurrentTrack(currentlyPlayed);
		}
	}

	public void updatePlaylist(PlaylistImpl newPlaylist) {
		if (newPlaylist.equals(playlist)) {
			return;
		}
		playlist = newPlaylist;
		refreshUi();
	}

	public void setCurrentTrack(int index) {
		if (currentlyPlayed != index || hasPlaylistChanged) {
			hasPlaylistChanged = false;
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
		eventBus.fireEvent(new GetSettingsEvent(new DefaultRequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				AutoBean<SettingsDTO> bean = AutoBeanCodex.decode(factory, SettingsDTO.class, response.getText());
				final SettingsDTO dto = bean.as();
				new RemoteFileBrowserDialog(PlaylistManager.this::onLoadPlaylist, eventBus, true, dto.getPlaylistLocation());
			}
		}));
	}

	private void onLoadPlaylist(String selectedFile) {
		if (selectedFile != null && !"".equals(selectedFile)) {
			eventBus.fireEvent(new LoadPlaylistEvent(selectedFile, new DefaultRequestCallback()));
		}
	}

	public void renamePlaylist(String newName) {
		eventBus.fireEvent(new RenamePlaylistEvent(newName, new DefaultRequestCallback()));
	}

	public void storePlaylist() {
		eventBus.fireEvent(new StorePlaylistEvent("", new DefaultRequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				NotifcationService.showNotification("Playlist saved successful.");
			}
		}));
	}

	public void createNewPlaylist(String name) {
		eventBus.fireEvent(new NewPlaylistEvent(name, new DefaultRequestCallback()));
	}
}

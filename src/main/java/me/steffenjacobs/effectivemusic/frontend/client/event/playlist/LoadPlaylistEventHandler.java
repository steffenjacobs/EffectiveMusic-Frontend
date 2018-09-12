package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface LoadPlaylistEventHandler extends EventHandler {

	void onLoadPlaylist(LoadPlaylistEvent event);

}

package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface StorePlaylistEventHandler extends EventHandler {

	void onStorePlaylist(StorePlaylistEvent event);

}

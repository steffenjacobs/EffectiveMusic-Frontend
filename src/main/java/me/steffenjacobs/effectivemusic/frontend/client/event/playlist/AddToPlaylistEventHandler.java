package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface AddToPlaylistEventHandler extends EventHandler {

	void onStart(AddToPlaylistEvent event);

}

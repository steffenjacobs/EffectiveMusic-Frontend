package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface NewPlaylistEventHandler extends EventHandler {

	void onCreate(NewPlaylistEvent event);

}

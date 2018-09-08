package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface GotoPlaylistPositionEventHandler extends EventHandler {

	void onGotoPosition(GotoPlaylistPositionEvent event);

}

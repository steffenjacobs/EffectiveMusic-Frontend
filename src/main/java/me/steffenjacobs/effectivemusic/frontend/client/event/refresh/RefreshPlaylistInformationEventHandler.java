package me.steffenjacobs.effectivemusic.frontend.client.event.refresh;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface RefreshPlaylistInformationEventHandler extends EventHandler {

	void onRefresh(RefreshPlaylistInformationEvent event);

}

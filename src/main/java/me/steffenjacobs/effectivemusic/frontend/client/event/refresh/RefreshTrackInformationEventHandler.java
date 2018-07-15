package me.steffenjacobs.effectivemusic.frontend.client.event.refresh;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface RefreshTrackInformationEventHandler extends EventHandler {

	void onRefresh(RefreshTrackInformationEvent event);

}

package me.steffenjacobs.effectivemusic.frontend.client.event.refresh;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface RefreshPlayerInformationEventHandler extends EventHandler {

	void onRefresh(RefreshPlayerInformationEvent event);

}

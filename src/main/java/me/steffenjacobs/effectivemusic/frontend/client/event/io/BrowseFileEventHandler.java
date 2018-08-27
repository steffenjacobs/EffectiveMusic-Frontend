package me.steffenjacobs.effectivemusic.frontend.client.event.io;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface BrowseFileEventHandler extends EventHandler {

	void onSuccess(BrowseFileEvent event);

}

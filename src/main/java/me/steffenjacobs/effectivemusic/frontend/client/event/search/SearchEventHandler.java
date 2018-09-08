package me.steffenjacobs.effectivemusic.frontend.client.event.search;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface SearchEventHandler extends EventHandler {

	void onSearch(SearchEvent event);

}

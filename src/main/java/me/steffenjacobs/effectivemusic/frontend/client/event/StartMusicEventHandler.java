package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface StartMusicEventHandler extends EventHandler {

	void onStart(StartMusicEvent event);

}

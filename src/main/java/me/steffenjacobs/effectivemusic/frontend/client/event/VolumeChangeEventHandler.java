package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface VolumeChangeEventHandler extends EventHandler {

	void onVolumeChanged(VolumeChangeEvent event);

}

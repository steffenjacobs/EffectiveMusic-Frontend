package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface ResumeMusicEventHandler extends EventHandler {

	void onResume(ResumeMusicEvent resumeMusicEvent);

}

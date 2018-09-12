package me.steffenjacobs.effectivemusic.frontend.client.event.settings;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface SetPlaylistLocationSettingEventHandler extends EventHandler {

	void onPlaylistLocationSet(SetPlaylistLocationSettingEvent event);

}

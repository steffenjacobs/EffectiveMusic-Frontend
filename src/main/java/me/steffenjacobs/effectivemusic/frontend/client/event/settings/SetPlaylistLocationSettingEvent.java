package me.steffenjacobs.effectivemusic.frontend.client.event.settings;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class SetPlaylistLocationSettingEvent extends GwtEvent<SetPlaylistLocationSettingEventHandler> {

	public static Type<SetPlaylistLocationSettingEventHandler> TYPE = new Type<>();

	final String path;
	final RequestCallback callback;

	public SetPlaylistLocationSettingEvent(String path, RequestCallback callback) {
		this.path = path;
		this.callback = callback;
	}

	public String getPath() {
		return path;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(SetPlaylistLocationSettingEventHandler handler) {
		handler.onPlaylistLocationSet(this);
	}

	@Override
	public Type<SetPlaylistLocationSettingEventHandler> getAssociatedType() {
		return TYPE;
	}
}

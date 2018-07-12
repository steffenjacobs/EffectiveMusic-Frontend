package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class StartMusicEvent extends GwtEvent<StartMusicEventHandler> {

	public static Type<StartMusicEventHandler> TYPE = new Type<>();

	final String path;
	final RequestCallback callback;

	public StartMusicEvent(String path, RequestCallback callback) {
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
	protected void dispatch(StartMusicEventHandler handler) {
		handler.onStart(this);
	}

	@Override
	public Type<StartMusicEventHandler> getAssociatedType() {
		return TYPE;
	}
}

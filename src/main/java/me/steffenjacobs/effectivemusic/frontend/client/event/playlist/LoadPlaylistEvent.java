package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class LoadPlaylistEvent extends GwtEvent<LoadPlaylistEventHandler> {

	public static Type<LoadPlaylistEventHandler> TYPE = new Type<>();

	final String path;
	final RequestCallback callback;

	public LoadPlaylistEvent(String path, RequestCallback callback) {
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
	protected void dispatch(LoadPlaylistEventHandler handler) {
		handler.onLoadPlaylist(this);
	}

	@Override
	public Type<LoadPlaylistEventHandler> getAssociatedType() {
		return TYPE;
	}
}

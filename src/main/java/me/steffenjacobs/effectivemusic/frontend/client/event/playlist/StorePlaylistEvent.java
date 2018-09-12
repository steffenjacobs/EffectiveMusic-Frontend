package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class StorePlaylistEvent extends GwtEvent<StorePlaylistEventHandler> {

	public static Type<StorePlaylistEventHandler> TYPE = new Type<>();

	final String path;
	final RequestCallback callback;

	/** path is currently not used */
	public StorePlaylistEvent(String path, RequestCallback callback) {
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
	protected void dispatch(StorePlaylistEventHandler handler) {
		handler.onStorePlaylist(this);
	}

	@Override
	public Type<StorePlaylistEventHandler> getAssociatedType() {
		return TYPE;
	}
}

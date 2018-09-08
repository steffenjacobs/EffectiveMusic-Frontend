package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class AddToPlaylistEvent extends GwtEvent<AddToPlaylistEventHandler> {

	public static Type<AddToPlaylistEventHandler> TYPE = new Type<>();

	final String path;
	final RequestCallback callback;

	public AddToPlaylistEvent(String path, RequestCallback callback) {
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
	protected void dispatch(AddToPlaylistEventHandler handler) {
		handler.onStart(this);
	}

	@Override
	public Type<AddToPlaylistEventHandler> getAssociatedType() {
		return TYPE;
	}
}

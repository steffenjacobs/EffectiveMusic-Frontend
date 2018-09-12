package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class AddToPlaylistEvent extends GwtEvent<AddToPlaylistEventHandler> {

	public static Type<AddToPlaylistEventHandler> TYPE = new Type<>();

	final List<String> paths;
	final RequestCallback callback;

	public AddToPlaylistEvent(List<String> paths, RequestCallback callback) {
		this.paths = paths;
		this.callback = callback;
	}

	public List<String> getPaths() {
		return paths;
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

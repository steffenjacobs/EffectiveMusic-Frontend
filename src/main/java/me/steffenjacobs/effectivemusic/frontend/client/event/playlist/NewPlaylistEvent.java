package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class NewPlaylistEvent extends GwtEvent<NewPlaylistEventHandler> {

	public static Type<NewPlaylistEventHandler> TYPE = new Type<>();

	final String playlistName;
	final RequestCallback callback;

	/** path is currently not used */
	public NewPlaylistEvent(String playlistName, RequestCallback callback) {
		this.playlistName = playlistName;
		this.callback = callback;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(NewPlaylistEventHandler handler) {
		handler.onCreate(this);
	}

	@Override
	public Type<NewPlaylistEventHandler> getAssociatedType() {
		return TYPE;
	}
}

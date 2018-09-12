package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class RenamePlaylistEvent extends GwtEvent<RenamePlaylistEventHandler> {

	public static Type<RenamePlaylistEventHandler> TYPE = new Type<>();

	final String playlistName;
	final RequestCallback callback;

	/** path is currently not used */
	public RenamePlaylistEvent(String playlistName, RequestCallback callback) {
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
	protected void dispatch(RenamePlaylistEventHandler handler) {
		handler.onRename(this);
	}

	@Override
	public Type<RenamePlaylistEventHandler> getAssociatedType() {
		return TYPE;
	}
}

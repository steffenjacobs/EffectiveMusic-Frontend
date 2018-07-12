package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class StopMusicEvent extends GwtEvent<StopMusicEventHandler> {

	public static Type<StopMusicEventHandler> TYPE = new Type<>();

	private final RequestCallback callback;

	public StopMusicEvent(RequestCallback callback) {
		this.callback = callback;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(StopMusicEventHandler handler) {
		handler.onStop(this);
	}

	@Override
	public Type<StopMusicEventHandler> getAssociatedType() {
		return TYPE;
	}
}

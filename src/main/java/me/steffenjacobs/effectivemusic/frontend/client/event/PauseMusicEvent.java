package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class PauseMusicEvent extends GwtEvent<PauseMusicEventHandler> {

	public static Type<PauseMusicEventHandler> TYPE = new Type<>();

	private RequestCallback callback;

	public PauseMusicEvent(RequestCallback callback) {
		this.callback = callback;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(PauseMusicEventHandler handler) {
		handler.onPause(this);
	}

	@Override
	public Type<PauseMusicEventHandler> getAssociatedType() {
		return TYPE;
	}
}

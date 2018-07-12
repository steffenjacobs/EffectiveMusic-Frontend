package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class ResumeMusicEvent extends GwtEvent<ResumeMusicEventHandler> {

	public static Type<ResumeMusicEventHandler> TYPE = new Type<>();

	private final RequestCallback callback;

	public ResumeMusicEvent(RequestCallback callback) {
		this.callback = callback;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(ResumeMusicEventHandler handler) {
		handler.onResume(this);
	}

	@Override
	public Type<ResumeMusicEventHandler> getAssociatedType() {
		return TYPE;
	}
}

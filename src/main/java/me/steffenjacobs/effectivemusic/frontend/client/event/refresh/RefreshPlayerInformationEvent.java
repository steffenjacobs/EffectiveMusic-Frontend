package me.steffenjacobs.effectivemusic.frontend.client.event.refresh;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class RefreshPlayerInformationEvent extends GwtEvent<RefreshPlayerInformationEventHandler> {

	public static final Type<RefreshPlayerInformationEventHandler> TYPE = new Type<>();

	final RequestCallback callback;

	public RefreshPlayerInformationEvent(RequestCallback callback) {
		this.callback = callback;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(RefreshPlayerInformationEventHandler handler) {
		handler.onRefresh(this);
	}

	@Override
	public Type<RefreshPlayerInformationEventHandler> getAssociatedType() {
		return TYPE;
	}
}

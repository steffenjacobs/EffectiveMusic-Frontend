package me.steffenjacobs.effectivemusic.frontend.client.event.refresh;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class RefreshTrackInformationEvent extends GwtEvent<RefreshTrackInformationEventHandler> {

	public static final Type<RefreshTrackInformationEventHandler> TYPE = new Type<>();

	final RequestCallback callback;

	public RefreshTrackInformationEvent(RequestCallback callback) {
		this.callback = callback;
	}

	public RefreshTrackInformationEvent(String path, RequestCallback callback) {
		this.callback = callback;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(RefreshTrackInformationEventHandler handler) {
		handler.onRefresh(this);
	}

	@Override
	public Type<RefreshTrackInformationEventHandler> getAssociatedType() {
		return TYPE;
	}
}

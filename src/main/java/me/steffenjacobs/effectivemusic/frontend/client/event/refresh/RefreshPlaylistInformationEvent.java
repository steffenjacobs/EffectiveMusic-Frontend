package me.steffenjacobs.effectivemusic.frontend.client.event.refresh;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class RefreshPlaylistInformationEvent extends GwtEvent<RefreshPlaylistInformationEventHandler> {

	public static final Type<RefreshPlaylistInformationEventHandler> TYPE = new Type<>();

	final RequestCallback callback;

	public RefreshPlaylistInformationEvent(RequestCallback callback) {
		this.callback = callback;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(RefreshPlaylistInformationEventHandler handler) {
		handler.onRefresh(this);
	}

	@Override
	public Type<RefreshPlaylistInformationEventHandler> getAssociatedType() {
		return TYPE;
	}
}

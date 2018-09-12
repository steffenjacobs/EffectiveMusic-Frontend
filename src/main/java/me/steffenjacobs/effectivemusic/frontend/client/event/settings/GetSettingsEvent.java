package me.steffenjacobs.effectivemusic.frontend.client.event.settings;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class GetSettingsEvent extends GwtEvent<GetSettingsEventHandler> {

	public static Type<GetSettingsEventHandler> TYPE = new Type<>();

	final RequestCallback callback;

	public GetSettingsEvent(RequestCallback callback) {
		this.callback = callback;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(GetSettingsEventHandler handler) {
		handler.onGetSettings(this);
	}

	@Override
	public Type<GetSettingsEventHandler> getAssociatedType() {
		return TYPE;
	}
}

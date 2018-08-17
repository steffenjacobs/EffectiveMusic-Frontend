package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class TrackPositionChangeEvent extends GwtEvent<TrackPositionChangeEventHandler> {

	public static Type<TrackPositionChangeEventHandler> TYPE = new Type<>();

	final double position;
	final RequestCallback callback;

	public TrackPositionChangeEvent(double position, RequestCallback callback) {
		this.position = position;
		this.callback = callback;
	}

	public double getPosition() {
		return position;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(TrackPositionChangeEventHandler handler) {
		handler.onTrackPositionChange(this);
	}

	@Override
	public Type<TrackPositionChangeEventHandler> getAssociatedType() {
		return TYPE;
	}
}

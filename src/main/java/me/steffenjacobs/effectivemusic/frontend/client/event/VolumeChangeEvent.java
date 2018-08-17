package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class VolumeChangeEvent extends GwtEvent<VolumeChangeEventHandler> {

	public static Type<VolumeChangeEventHandler> TYPE = new Type<>();

	final double volume;
	final RequestCallback callback;

	public VolumeChangeEvent(double volume, RequestCallback callback) {
		this.volume = volume;
		this.callback = callback;
	}

	public double getVolume() {
		return volume;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(VolumeChangeEventHandler handler) {
		handler.onVolumeChanged(this);
	}

	@Override
	public Type<VolumeChangeEventHandler> getAssociatedType() {
		return TYPE;
	}
}

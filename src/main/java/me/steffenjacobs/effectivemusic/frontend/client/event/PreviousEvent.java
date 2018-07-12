package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Steffen Jacobs
 */
public class PreviousEvent extends GwtEvent<PreviousEventHandler> {

	public static Type<PreviousEventHandler> TYPE = new Type<>();

	@Override
	protected void dispatch(PreviousEventHandler handler) {
		handler.onPrevious();
	}

	@Override
	public Type<PreviousEventHandler> getAssociatedType() {
		return TYPE;
	}
}

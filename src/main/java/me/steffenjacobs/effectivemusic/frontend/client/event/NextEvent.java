package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Steffen Jacobs
 */
public class NextEvent extends GwtEvent<NextEventHandler> {

	public static Type<NextEventHandler> TYPE = new Type<>();

	@Override
	protected void dispatch(NextEventHandler handler) {
		handler.onNext();
	}

	@Override
	public Type<NextEventHandler> getAssociatedType() {
		return TYPE;
	}
}

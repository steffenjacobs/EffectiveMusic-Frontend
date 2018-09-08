package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Steffen Jacobs
 */
public class GotoPlaylistPositionEvent extends GwtEvent<GotoPlaylistPositionEventHandler> {

	public static Type<GotoPlaylistPositionEventHandler> TYPE = new Type<>();

	final int position;

	public GotoPlaylistPositionEvent(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	@Override
	protected void dispatch(GotoPlaylistPositionEventHandler handler) {
		handler.onGotoPosition(this);
	}

	@Override
	public Type<GotoPlaylistPositionEventHandler> getAssociatedType() {
		return TYPE;
	}
}

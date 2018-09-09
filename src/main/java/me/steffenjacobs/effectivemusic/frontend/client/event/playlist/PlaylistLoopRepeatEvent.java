package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Steffen Jacobs
 */
public class PlaylistLoopRepeatEvent extends GwtEvent<PlaylistLoopRepeatEventHandler> {

	public static Type<PlaylistLoopRepeatEventHandler> TYPE = new Type<>();

	final int loopRepeatStatus;

	public PlaylistLoopRepeatEvent(int loopRepeatStatus) {
		this.loopRepeatStatus = loopRepeatStatus;
	}

	public int getLoopRepeatStatus() {
		return loopRepeatStatus;
	}

	@Override
	protected void dispatch(PlaylistLoopRepeatEventHandler handler) {
		handler.onStatus(this);
	}

	@Override
	public Type<PlaylistLoopRepeatEventHandler> getAssociatedType() {
		return TYPE;
	}
}

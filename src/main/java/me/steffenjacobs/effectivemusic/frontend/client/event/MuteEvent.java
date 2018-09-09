package me.steffenjacobs.effectivemusic.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Steffen Jacobs
 */
public class MuteEvent extends GwtEvent<MuteEventHandler> {

	public static Type<MuteEventHandler> TYPE = new Type<>();

	final boolean mute;

	public MuteEvent(boolean mute) {
		this.mute = mute;
	}

	public boolean isMute() {
		return mute;
	}

	@Override
	protected void dispatch(MuteEventHandler handler) {
		handler.onMuteChange(this);
	}

	@Override
	public Type<MuteEventHandler> getAssociatedType() {
		return TYPE;
	}
}

package me.steffenjacobs.effectivemusic.frontend.client.event.io;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class BrowseFileEvent extends GwtEvent<BrowseFileEventHandler> {

	public static Type<BrowseFileEventHandler> TYPE = new Type<>();

	final RequestCallback callback;
	final String path;

	public BrowseFileEvent(RequestCallback callback, String path) {
		this.callback = callback;
		this.path = path;
	}

	public RequestCallback getCallback() {
		return callback;
	}
	
	public String getPath() {
		return path;
	}

	@Override
	protected void dispatch(BrowseFileEventHandler handler) {
		handler.onSuccess(this);
	}

	@Override
	public Type<BrowseFileEventHandler> getAssociatedType() {
		return TYPE;
	}
}

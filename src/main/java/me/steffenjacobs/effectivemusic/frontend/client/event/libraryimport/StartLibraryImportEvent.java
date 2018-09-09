package me.steffenjacobs.effectivemusic.frontend.client.event.libraryimport;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class StartLibraryImportEvent extends GwtEvent<StartLibraryImportEventHandler> {

	public static Type<StartLibraryImportEventHandler> TYPE = new Type<>();

	final String path;
	final RequestCallback callback;

	public StartLibraryImportEvent(String path, RequestCallback callback) {
		this.path = path;
		this.callback = callback;
	}

	public String getPath() {
		return path;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(StartLibraryImportEventHandler handler) {
		handler.onStart(this);
	}

	@Override
	public Type<StartLibraryImportEventHandler> getAssociatedType() {
		return TYPE;
	}
}

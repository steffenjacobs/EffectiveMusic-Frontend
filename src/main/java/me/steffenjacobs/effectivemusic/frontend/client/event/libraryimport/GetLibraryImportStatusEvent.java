package me.steffenjacobs.effectivemusic.frontend.client.event.libraryimport;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class GetLibraryImportStatusEvent extends GwtEvent<GetLibraryImportStatusEventHandler> {

	public static Type<GetLibraryImportStatusEventHandler> TYPE = new Type<>();

	final RequestCallback callback;

	public GetLibraryImportStatusEvent(RequestCallback callback) {
		this.callback = callback;
	}


	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(GetLibraryImportStatusEventHandler handler) {
		handler.onStatusUpdate(this);
	}

	@Override
	public Type<GetLibraryImportStatusEventHandler> getAssociatedType() {
		return TYPE;
	}
}

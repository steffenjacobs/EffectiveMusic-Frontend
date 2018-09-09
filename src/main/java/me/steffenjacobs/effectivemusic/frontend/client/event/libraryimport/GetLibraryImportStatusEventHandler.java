package me.steffenjacobs.effectivemusic.frontend.client.event.libraryimport;

import com.google.gwt.event.shared.EventHandler;

/** @author Steffen Jacobs */
public interface GetLibraryImportStatusEventHandler extends EventHandler {

	void onStatusUpdate(GetLibraryImportStatusEvent event);

}

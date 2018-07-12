package me.steffenjacobs.effectivemusic.frontend.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import me.steffenjacobs.effectivemusic.frontend.client.controller.WebAppController;
import me.steffenjacobs.effectivemusic.frontend.client.resource.ApplicationResources;
import me.steffenjacobs.effectivemusic.frontend.client.ui.MainPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * Point d'entrée du module GWT
 */
public class GwtWebApp implements EntryPoint {

	/**
	 * gin injector
	 */
	private final GwtWebAppGinjector injector = GWT.create(GwtWebAppGinjector.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// ensure resources are injected
		ApplicationResources.INSTANCE.style().ensureInjected();
		// get controler from gin jector
		WebAppController controller = injector.getWebAppController();
		// bind event handlers
		controller.bindHandlers();
		// get main panel
		MainPanel mainPanel = injector.getMainPanel();
		// add for display
		RootLayoutPanel.get().add(mainPanel);
	}
}

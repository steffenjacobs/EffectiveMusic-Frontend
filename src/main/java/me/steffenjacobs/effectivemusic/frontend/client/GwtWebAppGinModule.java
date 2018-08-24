package me.steffenjacobs.effectivemusic.frontend.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import me.steffenjacobs.effectivemusic.frontend.client.controller.WebAppController;
import me.steffenjacobs.effectivemusic.frontend.client.resource.ApplicationResources;
import me.steffenjacobs.effectivemusic.frontend.client.resource.EffectiveMusicMessages;
import me.steffenjacobs.effectivemusic.frontend.client.ui.MainPanel;

public class GwtWebAppGinModule extends AbstractGinModule{

	@Override
	protected void configure() {
		// Resources
		bind(EffectiveMusicMessages.class).in(Singleton.class);
		bind(ApplicationResources.class).in(Singleton.class);
		
		// Core
		bind(SimpleEventBus.class).in(Singleton.class);
		bind(WebAppController.class).in(Singleton.class);
		
		// UI
		bind(MainPanel.class).in(Singleton.class);
	}

}

package me.steffenjacobs.effectivemusic.frontend.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import me.steffenjacobs.effectivemusic.frontend.client.controller.WebAppController;
import me.steffenjacobs.effectivemusic.frontend.client.resource.EffectiveMusicMessages;
import me.steffenjacobs.effectivemusic.frontend.client.ui.MainPanel;

@GinModules(GwtWebAppGinModule.class)
public interface GwtWebAppGinjector extends Ginjector {

	public SimpleEventBus getEventBus();

	public EffectiveMusicMessages getMessages();

	public WebAppController getWebAppController();

	public MainPanel getMainPanel();
}

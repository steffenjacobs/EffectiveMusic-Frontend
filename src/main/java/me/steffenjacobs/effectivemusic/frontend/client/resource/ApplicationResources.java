package me.steffenjacobs.effectivemusic.frontend.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface ApplicationResources extends ClientBundle {
	public static final ApplicationResources INSTANCE = GWT.create(ApplicationResources.class);

	@Source("me/steffenjacobs/effectivemusic/frontend/client/resource/GwtWebAppStyles.css")
	public GwtWebAppStyles style();

	@Source("warning.png")
	@ImageOptions(repeatStyle = RepeatStyle.Both)
	ImageResource warningIcon();
}

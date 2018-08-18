package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/** @author Steffen Jacobs */
public class EffectiveMusicResources {

	public static final RES RES = GWT.create(RES.class);

	public interface RES extends ClientBundle {
		@Source("style.css")
		public CSS style();
	}

	public interface CSS extends CssResource {
		String track();

		String selectedTrack();

		String playlistBackground();
	}
}

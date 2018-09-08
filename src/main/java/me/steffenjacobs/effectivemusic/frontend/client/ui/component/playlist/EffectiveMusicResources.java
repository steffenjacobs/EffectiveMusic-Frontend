package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/** @author Steffen Jacobs */
public class EffectiveMusicResources {

	public static final RES INSTANCE = GWT.create(RES.class);

	public interface RES extends ClientBundle {
		@Source("style.css")
		public CSS style();
	}

	public interface CSS extends CssResource {
		String track();

		String playlistBackground();

		String playingTrack();

		String backgroundColor();

		String highlightColor();

		String selectionColor();

		String controlFirstCol();

		String controlSecondCol();

		String controlPanel();

		String secondColor();

		String thirdColor();

		String trackTitleTextBox();

		String fileBrowserItemNonClickable();

		String fileBrowserItem();

		String dialogBox();

		String dialogPanel();
		
		String contentPanel();

		String contextMenuButton();
		
		String fullsizeAbsolute();
		
		String fullsize();
		
		String columnWithRightBorder();
		
		String marginBottom20();
		
		String sendButton();
		
		String fontSize17();
		
		String secondColorInverted();
		
		String smallButton();
	}
}

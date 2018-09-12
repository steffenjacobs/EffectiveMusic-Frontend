package me.steffenjacobs.effectivemusic.frontend.client.ui.component.misc;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;

/** @author Steffen Jacobs */
public class NotifcationService {

	public static void showNotification(String text) {
		final PopupPanel notification = new PopupPanel(true);
		notification.setWidget(new Label(text));
		notification.addStyleName(EffectiveMusicResources.INSTANCE.style().infoPopup());

		RootPanel.get().add(notification);

		notification.setPopupPositionAndShow((w, h) -> notification.setPopupPosition((Window.getClientWidth() - w) / 2, 50));
		notification.getElement().getStyle().setOpacity(0);

		new Timer() {
			@Override
			public void run() {
				RootPanel.get().remove(notification);
				this.cancel();
			}
		}.schedule(4000);
	}
}

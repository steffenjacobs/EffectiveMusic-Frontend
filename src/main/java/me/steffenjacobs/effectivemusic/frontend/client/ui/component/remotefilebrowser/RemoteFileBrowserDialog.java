package me.steffenjacobs.effectivemusic.frontend.client.ui.component.remotefilebrowser;

import java.util.function.Consumer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import me.steffenjacobs.effectivemusic.frontend.client.resource.ApplicationResources;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;

/** @author Steffen Jacobs */
public class RemoteFileBrowserDialog extends DialogBox {

	public RemoteFileBrowserDialog(final Consumer<String> consumer, final SimpleEventBus eventBus) {
		EffectiveMusicResources.INSTANCE.style().ensureInjected();
		ApplicationResources.INSTANCE.style().ensureInjected();
		super.setText("Choose a directory");

		final RemoteFileBrowser remoteFileBrowser = new RemoteFileBrowser();

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				consumer.accept(null);
				RemoteFileBrowserDialog.super.hide();
			}
		});

		Button okButton = new Button("OK");
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RemoteFileBrowserDialog.super.hide();
				consumer.accept(remoteFileBrowser.getPath());
			}
		});

		okButton.addStyleName(ApplicationResources.INSTANCE.style().sendButton());
		okButton.addStyleName(EffectiveMusicResources.INSTANCE.style().secondColor());

		cancelButton.addStyleName(ApplicationResources.INSTANCE.style().sendButton());
		cancelButton.addStyleName(EffectiveMusicResources.INSTANCE.style().secondColor());

		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("100");
		panel.setWidth("300");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		remoteFileBrowser.setEventBus(eventBus);
		panel.add(remoteFileBrowser);

		FlowPanel buttons = new FlowPanel();
		buttons.add(okButton);
		buttons.add(cancelButton);

		panel.add(buttons);
		panel.addStyleName(EffectiveMusicResources.INSTANCE.style().highlightColor());
		panel.addStyleName(EffectiveMusicResources.INSTANCE.style().dialogPanel());

		super.setWidget(panel);

		super.setTitle("Choose a directory");
		super.setAnimationEnabled(true);
		super.setAnimationType(AnimationType.ROLL_DOWN);
		super.setStyleName("style_dialog");

		 super.setGlassEnabled(true);

		super.center();
		super.show();
		super.getCaption().setHTML("<div style='text-align: center; font-size: 1.66em;color:#c2c8d6; background-color: #313746; border-radius: none;'>Choose a directory</div>");
		panel.getParent().getParent().addStyleName(EffectiveMusicResources.INSTANCE.style().dialogBox());
	}
}

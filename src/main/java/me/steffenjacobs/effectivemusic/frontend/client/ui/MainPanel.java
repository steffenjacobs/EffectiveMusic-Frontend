package me.steffenjacobs.effectivemusic.frontend.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import me.steffenjacobs.effectivemusic.frontend.client.controller.Base64Encoder;
import me.steffenjacobs.effectivemusic.frontend.client.event.NextEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PauseMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PreviousEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.ResumeMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StartMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StopMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.resource.Messages;

public class MainPanel extends Composite {

	private static MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);

	private static Messages msg = GWT.create(Messages.class);

	interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
	}

	@UiField
	Button stopButton;

	@UiField
	Button startPauseButton;

	@UiField
	Button nextButton;

	@UiField
	Button previousButton;

	@UiField
	TextBox textBox;

	@UiField
	FlowPanel todoPanel;

	private SimpleEventBus eventBus;

	private boolean playing = false;
	private boolean paused = false;

	public void setPlaying(boolean value) {
		startPauseButton.setText(value ? msg.pauseButton() : msg.startButton());
		playing = value;
	}

	@Inject
	public MainPanel(SimpleEventBus evtBus) {
		eventBus = evtBus;
		// init display
		initWidget(uiBinder.createAndBindUi(this));
		textBox.getElement().setPropertyString("placeholder", msg.textboxPlaceholder());
	}

	@UiHandler("stopButton")
	void onStopButtonClicked(ClickEvent e) {
		eventBus.fireEvent(new StopMusicEvent(new DefaultRequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				setPlaying(false);
				paused = false;
			}
		}));
	}

	@UiHandler("startPauseButton")
	void onStartPauseButtonClicked(ClickEvent e) {
		if (playing) {
			eventBus.fireEvent(new PauseMusicEvent(new DefaultRequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					setPlaying(false);
					paused = true;
				}
			}));
		} else if (paused) {
			eventBus.fireEvent(new ResumeMusicEvent(new DefaultRequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					setPlaying(true);
					paused = false;
				}
			}));
		} else {
			eventBus.fireEvent(new StartMusicEvent(new String(Base64Encoder.encode(textBox.getText().getBytes())), new DefaultRequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					setPlaying(true);
				}
			}));
		}
	}

	@UiHandler("nextButton")
	void onNextButtonClicked(ClickEvent e) {
		eventBus.fireEvent(new NextEvent());
	}

	@UiHandler("previousButton")
	void onPreviousButtonClicked(ClickEvent e) {
		eventBus.fireEvent(new PreviousEvent());
	}
}

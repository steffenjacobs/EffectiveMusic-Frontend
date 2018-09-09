package me.steffenjacobs.effectivemusic.frontend.client.ui.component.controlpanel;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import me.steffenjacobs.effectivemusic.frontend.client.controller.Base64Encoder;
import me.steffenjacobs.effectivemusic.frontend.client.event.MuteEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.NextEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PauseMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PreviousEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.ResumeMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StopMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.TrackPositionChangeEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.VolumeChangeEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.AddToPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.PlaylistLoopRepeatEvent;
import me.steffenjacobs.effectivemusic.frontend.client.resource.EffectiveMusicMessages;
import me.steffenjacobs.effectivemusic.frontend.client.ui.DefaultRequestCallback;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.FormattingUtils;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.multistatebutton.MultiStateButton;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.simpleslider.SimpleSlider;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.simpleslider.SliderEventHandler;

/** @author Steffen Jacobs */
public class ControlPanel extends Composite {

	private static ControlPanelUiBinder uiBinder = GWT.create(ControlPanelUiBinder.class);

	interface ControlPanelUiBinder extends UiBinder<Widget, ControlPanel> {
	}

	private static EffectiveMusicMessages msg = GWT.create(EffectiveMusicMessages.class);

	@UiField
	Button stopButton;

	@UiField
	Button startPauseButton;

	@UiField
	Button nextButton;

	@UiField
	Button previousButton;

	@UiField
	Button startYoutubeButton;

	@UiField
	MultiStateButton repeatLoopButtonUi;

	@UiField
	TextBox textBox;

	@UiField
	Label playTime;

	@UiField
	Label playTitle;

	@UiField
	Label playVolume;

	@UiField
	SimpleSlider sliderVolumeUi;

	@UiField
	SimpleSlider sliderTrackUi;

	SimpleEventBus eventBus;

	@UiField
	MultiStateButton muteButtonUi;

	private boolean playing = false;
	private boolean paused = false;

	public ControlPanel() {
		EffectiveMusicResources.INSTANCE.style().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));

		createRepeatLoopButton();
		createMuteButton();
	}

	private void createMuteButton() {
		final Map<Integer, String> captions = new HashMap<>();
		captions.put(0, "🔊");
		captions.put(1, "🔇");
		muteButtonUi.setButtonCaptions(captions);
		muteButtonUi.addStateChangeHandler(s -> eventBus.fireEvent(new MuteEvent(s == 1)));
	}

	private void createRepeatLoopButton() {
		final Map<Integer, String> captions = new HashMap<>();
		captions.put(0, "→");
		captions.put(1, "∞");
		captions.put(2, "↻");
		captions.put(3, "🔀");
		repeatLoopButtonUi.setButtonCaptions(captions);
		repeatLoopButtonUi.addStateChangeHandler(s -> eventBus.fireEvent(new PlaylistLoopRepeatEvent(s)));
	}

	public void setEventBus(SimpleEventBus evtBus) {
		this.eventBus = evtBus;
		textBox.getElement().setPropertyString("placeholder", msg.textboxPlaceholder());
		setupTrackListener();
		setupVolumeListener();
	}

	public void setPosition(double position, long length) {
		playTime.setText(FormattingUtils.formatPosition(position, length) + " - " + FormattingUtils.formatTime(length));
		sliderTrackUi.setPosition(position * 100);
	}

	public void setVolume(double volume) {
		playVolume.setText("Volume: " + FormattingUtils.formatPercent(volume) + "%");
		sliderVolumeUi.setPosition(volume);
	}

	public void setPlaying(boolean value) {
		startPauseButton.setText(value ? msg.pauseButton() : msg.startButton());
		playing = value;
	}

	public void setRepeatStatus(int status) {
		repeatLoopButtonUi.setStatus(status);
	}

	public void setMuteStatus(boolean mute) {
		muteButtonUi.setStatus(mute ? 1 : 0);
	}

	private void setupVolumeListener() {
		sliderVolumeUi.addEventHandler(new SliderEventHandler() {
			@Override
			public void onClick(NativeEvent event) {
				eventBus.fireEvent(new VolumeChangeEvent(sliderVolumeUi.getPosition(), null));
			}

			@Override
			public void onDrag(NativeEvent event) {
				eventBus.fireEvent(new VolumeChangeEvent(sliderVolumeUi.getPosition(), null));
			}

			@Override
			public void onDragEnd(NativeEvent event) {
				eventBus.fireEvent(new VolumeChangeEvent(sliderVolumeUi.getPosition(), null));
			}
		});
	}

	private void setupTrackListener() {
		sliderTrackUi.addEventHandler(new SliderEventHandler() {
			@Override
			public void onClick(NativeEvent event) {
				eventBus.fireEvent(new TrackPositionChangeEvent(sliderTrackUi.getPosition(), null));
			}

			@Override
			public void onDrag(NativeEvent event) {
				eventBus.fireEvent(new TrackPositionChangeEvent(sliderTrackUi.getPosition(), null));
			}

			@Override
			public void onDragEnd(NativeEvent event) {
				eventBus.fireEvent(new TrackPositionChangeEvent(sliderTrackUi.getPosition(), null));
			}
		});

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
		}
	}

	@UiHandler("startYoutubeButton")
	void onStartYoutubeButtonClicked(ClickEvent e) {
		eventBus.fireEvent(new AddToPlaylistEvent(new String(Base64Encoder.encode(textBox.getText().getBytes())), null));
	}

	@UiHandler("nextButton")
	void onNextButtonClicked(ClickEvent e) {
		eventBus.fireEvent(new NextEvent());
	}

	@UiHandler("previousButton")
	void onPreviousButtonClicked(ClickEvent e) {
		eventBus.fireEvent(new PreviousEvent());
	}

	public void clearTextFields() {
		playTitle.setText("No Track");
		playTime.setText("");
		playVolume.setText("");
	}

	public void setTrackTitle(String trackTitle) {
		playTitle.setText(trackTitle);
	}

}

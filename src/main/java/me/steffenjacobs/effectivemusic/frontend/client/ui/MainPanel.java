package me.steffenjacobs.effectivemusic.frontend.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import me.steffenjacobs.effectivemusic.frontend.client.controller.Base64Encoder;
import me.steffenjacobs.effectivemusic.frontend.client.controller.MusicAutobeanFactory;
import me.steffenjacobs.effectivemusic.frontend.client.event.NextEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PauseMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PreviousEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.ResumeMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StartMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StopMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshTrackInformationEvent;
import me.steffenjacobs.effectivemusic.frontend.client.resource.Messages;
import me.steffenjacobs.effectivemusic.frontend.client.resource.MusicTemplates;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackDto;

public class MainPanel extends Composite {

	private static MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);
	private static MusicAutobeanFactory factory = GWT.create(MusicAutobeanFactory.class);
	private static MusicTemplates templates = GWT.create(MusicTemplates.class);

	private static Messages msg = GWT.create(Messages.class);
	private static final NumberFormat DOUBLE_DIGITS = NumberFormat.getFormat("00");

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
	Label playTime;

	@UiField
	Label playTitle;

	@UiField
	Label playVolume;

	private SimpleEventBus eventBus;

	private boolean playing = false;
	private boolean paused = false;

	private Timer t = new Timer() {

		@Override
		public void run() {
			eventBus.fireEvent(new RefreshTrackInformationEvent(new DefaultRequestCallback() {

				private void clearTextFields() {
					playTitle.setText("No Track");
					playTime.setText("");
					playVolume.setText("");
				}

				private String formatPosition(double position, long lengthInMillis) {
					long timeInMillis = (long) (position * lengthInMillis);
					return formatTime(timeInMillis);
				}

				private String formatTime(long timeInMillis) {
					timeInMillis /= 1000;
					long hours = timeInMillis / 360;
					long minutes = (timeInMillis % 360) / 60;
					long seconds = timeInMillis % 360 % 60;
					return templates.formatTimestamp(DOUBLE_DIGITS.format(hours), DOUBLE_DIGITS.format(minutes), DOUBLE_DIGITS.format(seconds)).asString();
				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					if ("".equals(response.getText())) {
						clearTextFields();
						return;
					} else {
						AutoBean<TrackDto> bean = AutoBeanCodex.decode(factory, TrackDto.class, response.getText());
						TrackDto dto = bean.as();
						if (dto == null) {
							clearTextFields();
							return;
						}
						if (dto.getTitle() == null) {
							playTitle.setText("No Track");
						} else {
							playTitle.setText(dto.getTitle() + " - " + dto.getArtist());
						}
						playTime.setText(formatPosition(dto.getPosition(), dto.getLength()) + " - " + formatTime(dto.getLength()));
						playVolume.setText("Volume: " + dto.getVolume() + "%");
					}
				}
			}));
		}
	};

	public void setPlaying(boolean value) {
		startPauseButton.setText(value ? msg.pauseButton() : msg.startButton());
		playing = value;
	}

	private void startAutoUpdate() {
		t.scheduleRepeating(1000);
	}

	@Inject
	public MainPanel(SimpleEventBus evtBus) {
		eventBus = evtBus;
		// init display
		initWidget(uiBinder.createAndBindUi(this));
		textBox.getElement().setPropertyString("placeholder", msg.textboxPlaceholder());
		startAutoUpdate();
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

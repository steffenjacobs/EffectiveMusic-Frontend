package me.steffenjacobs.effectivemusic.frontend.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import me.steffenjacobs.effectivemusic.frontend.client.controller.MusicAutobeanFactory;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshPlayerInformationEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshPlaylistInformationEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshTrackInformationEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.controlpanel.ControlPanel;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.PlaylistManager;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.PlaylistPanel;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlayerInformationDTO;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistDto;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackDto;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackImpl;

public class MainPanel extends Composite {

	private static MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);
	private static MusicAutobeanFactory factory = GWT.create(MusicAutobeanFactory.class);

	interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
	}

	@UiField
	ControlPanel controlPanel;

	@UiField
	PlaylistPanel playlistPanelUi;

	private SimpleEventBus eventBus;

	private final PlaylistManager playlistManager;

	private Timer t = new Timer() {

		private TrackDto lastTrack;

		@Override
		public void run() {
			eventBus.fireEvent(new RefreshTrackInformationEvent(new DefaultRequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					if ("".equals(response.getText())) {
						controlPanel.clearTextFields();
						return;
					} else {
						AutoBean<TrackDto> bean = AutoBeanCodex.decode(factory, TrackDto.class, response.getText());
						final TrackDto autoBeanTrack = bean.as();
						TrackDto dto = new TrackImpl(autoBeanTrack);
						if (dto.equals(lastTrack)) {
							controlPanel.setPosition(dto.getPosition(), dto.getLength());
							return;
						}
						playlistManager.setCurrentTrack(dto);
						lastTrack = dto;
						if (autoBeanTrack == null) {
							controlPanel.clearTextFields();
							return;
						}
						if (dto.getTitle() == null) {
							controlPanel.setTrackTitle("No Track");
						} else {
							controlPanel.setTrackTitle(dto.getTitle() + " - " + dto.getArtist());
						}
						controlPanel.setPosition(dto.getPosition(), dto.getLength());
					}
				}
			}));

			eventBus.fireEvent(new RefreshPlaylistInformationEvent(new DefaultRequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					AutoBean<PlaylistDto> bean = AutoBeanCodex.decode(factory, PlaylistDto.class, response.getText());
					PlaylistDto dto = bean.as();
					playlistManager.updatePlaylist(dto);
				}
			}));

			eventBus.fireEvent(new RefreshPlayerInformationEvent(new DefaultRequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					AutoBean<PlayerInformationDTO> bean = AutoBeanCodex.decode(factory, PlayerInformationDTO.class, response.getText());
					PlayerInformationDTO dto = bean.as();
					controlPanel.setPlaying("PLAYING".equals(dto.getStatus()));
					controlPanel.setVolume(dto.getVolume());
				}
			}));
		}
	};

	private void startAutoUpdate() {
		t.scheduleRepeating(1000);
	}

	@Inject
	public MainPanel(SimpleEventBus evtBus) {
		eventBus = evtBus;
		// init display
		initWidget(uiBinder.createAndBindUi(this));
		controlPanel.setEventBus(evtBus);
		EffectiveMusicResources.INSTANCE.style().ensureInjected();
		playlistManager = new PlaylistManager(playlistPanelUi);
		startAutoUpdate();

	}
}

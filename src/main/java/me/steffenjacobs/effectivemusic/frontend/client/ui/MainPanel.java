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
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.browser.BrowserPanel;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.controlpanel.ControlPanel;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.PlaylistManager;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.PlaylistPanel;
import me.steffenjacobs.effectivemusic.frontend.common.domain.LiveTrackDTO;
import me.steffenjacobs.effectivemusic.frontend.common.domain.LiveTrackImpl;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlayerInformationDTO;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistDto;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistImpl;

public class MainPanel extends Composite {

	private static MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);
	private static MusicAutobeanFactory factory = GWT.create(MusicAutobeanFactory.class);

	interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
	}

	@UiField
	ControlPanel controlPanel;

	@UiField
	PlaylistPanel playlistPanelUi;

	@UiField
	BrowserPanel browserPanelUi;

	private SimpleEventBus eventBus = null;

	private final PlaylistManager playlistManager;

	private Timer t = new Timer() {

		private LiveTrackDTO lastTrack;

		@Override
		public void run() {
			/** ------ Live Track Info ----- */
			eventBus.fireEvent(new RefreshTrackInformationEvent(new DefaultRequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if ("".equals(response.getText())) {
						controlPanel.clearTextFields();
						return;
					} else {
						AutoBean<LiveTrackDTO> bean = AutoBeanCodex.decode(factory, LiveTrackDTO.class, response.getText());
						final LiveTrackDTO autoBeanTrack = bean.as();
						LiveTrackDTO dto = new LiveTrackImpl(autoBeanTrack);
						if (dto.equals(lastTrack)) {
							controlPanel.setPosition(dto.getPosition(), dto.getLength());
							// return;
						}
						lastTrack = dto;
						if (autoBeanTrack == null) {
							controlPanel.clearTextFields();
							return;
						}
						if (dto.getTitle() == null) {
							controlPanel.setTrackTitle("No Track");
						} else if (dto.getArtist() != null) {
							controlPanel.setTrackTitle(dto.getTitle() + " - " + dto.getArtist());
						} else {
							controlPanel.setTrackTitle(dto.getTitle());
						}
						controlPanel.setPosition(dto.getPosition(), dto.getLength());
					}
				}
			}));

			/** ----- Playlist Information ----- */
			eventBus.fireEvent(new RefreshPlaylistInformationEvent(new DefaultRequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					AutoBean<PlaylistDto> bean = AutoBeanCodex.decode(factory, PlaylistDto.class, response.getText());
					PlaylistDto dto = bean.as();
					playlistManager.updatePlaylist(new PlaylistImpl(dto));
					playlistManager.setCurrentTrack(dto.getCurrentIndex());
					controlPanel.setRepeatStatus(dto.getRepeatLoopStatus());
				}
			}));

			/** -----Player Information ----- */
			eventBus.fireEvent(new RefreshPlayerInformationEvent(new DefaultRequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					AutoBean<PlayerInformationDTO> bean = AutoBeanCodex.decode(factory, PlayerInformationDTO.class, response.getText());
					PlayerInformationDTO dto = bean.as();
					controlPanel.setPlaying("PLAYING".equals(dto.getStatus()));
					controlPanel.setVolume(dto.getVolume());
					controlPanel.setMuteStatus(dto.getMute());
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
		browserPanelUi.setEventBus(evtBus);
		EffectiveMusicResources.INSTANCE.style().ensureInjected();
		playlistManager = new PlaylistManager(playlistPanelUi, evtBus);
		startAutoUpdate();

	}
}

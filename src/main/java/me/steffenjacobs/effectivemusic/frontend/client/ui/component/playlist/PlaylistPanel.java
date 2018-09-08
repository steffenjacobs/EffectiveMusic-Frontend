package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import me.steffenjacobs.effectivemusic.frontend.client.ui.component.FormattingUtils;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackWithPathImpl;

/** @author Steffen Jacobs */
public class PlaylistPanel extends Composite {

	private static PlaylistPanelUiBinder uiBinder = GWT.create(PlaylistPanelUiBinder.class);

	interface PlaylistPanelUiBinder extends UiBinder<Widget, PlaylistPanel> {
	}

	@UiField
	HTMLPanel panelUi;

	private TrackWithPathImpl currentlyPlaying;
	private Map<TrackWithPathImpl, FlowPanel> playlistElements;
	private PlaylistManager playlistManager;

	public PlaylistPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setCurrentlyPlaying(TrackWithPathImpl liveTrackDTO) {
		FlowPanel elem = playlistElements.get(currentlyPlaying);
		if (currentlyPlaying != null) {
			elem.removeStyleName(EffectiveMusicResources.INSTANCE.style().playingTrack());
		}
		elem = playlistElements.get(liveTrackDTO);
		currentlyPlaying = liveTrackDTO;
		elem.addStyleName(EffectiveMusicResources.INSTANCE.style().playingTrack());
	}

	public void setPlaylist(Iterable<TrackWithPathImpl> tracks) {
		playlistElements = new HashMap<>();
		for (TrackWithPathImpl track : tracks) {
			final FlowPanel elem = createTrackItem(track);
			panelUi.add(elem);
			playlistElements.put(track, elem);
			if (track.equals(currentlyPlaying)) {
				elem.addStyleName(EffectiveMusicResources.INSTANCE.style().playingTrack());
			}
		}
	}

	private FlowPanel createTrackItem(TrackWithPathImpl track) {
		FlowPanel item = new FlowPanel();
		item.sinkEvents(Event.ONCLICK);
		item.addHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				playlistManager.elementClicked(track);
			}
		}, ClickEvent.getType());
		item.addStyleName(EffectiveMusicResources.INSTANCE.style().track());
		item.add(new Label(FormattingUtils.formatTrackForPlaylist(track.getTrack())));
		return item;
	}

	public void clear() {
		panelUi.clear();
	}

	public void setManager(PlaylistManager playlistManager) {
		this.playlistManager = playlistManager;
	}

}

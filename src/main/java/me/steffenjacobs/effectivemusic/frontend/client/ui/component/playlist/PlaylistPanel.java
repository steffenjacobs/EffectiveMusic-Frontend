package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
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

	@UiField
	Button loadPlaylistUi;

	@UiField
	Button storePlaylistUi;

	@UiField
	DivElement playlistHeaderUi;

	private FlowPanel currentlyPlayingPanel;
	private Map<Integer, FlowPanel> playlistElements;
	private PlaylistManager playlistManager;

	public PlaylistPanel() {
		EffectiveMusicResources.INSTANCE.style().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));
		playlistHeaderUi.setInnerHTML("Playlist");
	}

	public void setCurrentlyPlaying(int index) {
		if (currentlyPlayingPanel != null) {
			currentlyPlayingPanel.removeStyleName(EffectiveMusicResources.INSTANCE.style().playingTrack());
		}
		FlowPanel elem = playlistElements.get(index);
		currentlyPlayingPanel = elem;
		elem.addStyleName(EffectiveMusicResources.INSTANCE.style().playingTrack());
	}

	public void setPlaylist(List<TrackWithPathImpl> tracks) {
		playlistElements = new HashMap<>();

		int count = 0;
		for (TrackWithPathImpl track : tracks) {
			final FlowPanel elem = createTrackItem(track, count);
			panelUi.add(elem);
			playlistElements.put(count, elem);
			count++;
		}
		playlistHeaderUi.setInnerHTML("Playlist (" + FormattingUtils.formatTime(tracks.stream().collect(Collectors.summingLong(t -> t.getTrack().getLength()))) + ")");
	}

	private FlowPanel createTrackItem(TrackWithPathImpl track, int index) {
		FlowPanel item = new FlowPanel();
		item.sinkEvents(Event.ONCLICK);
		item.addHandler(event -> playlistManager.elementClicked(track), ClickEvent.getType());
		item.addStyleName(EffectiveMusicResources.INSTANCE.style().track());
		final Label labelTrack = new Label(FormattingUtils.formatTrackForPlaylist(track.getTrack()));
		item.add(labelTrack);

		final PopupPanel popupMenu = new PopupPanel(true);
		final Label removeFromPlaylistButton = new Label("Remove from playlist");
		removeFromPlaylistButton.addStyleName(EffectiveMusicResources.INSTANCE.style().contextMenuButton());
		removeFromPlaylistButton.addClickHandler(event -> {
			playlistManager.removeFromPlaylist(index);
			popupMenu.hide();
		});
		popupMenu.add(removeFromPlaylistButton);

		item.sinkEvents(Event.ONCONTEXTMENU);
		item.addHandler(event -> {
			event.preventDefault();
			event.stopPropagation();

			popupMenu.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
			popupMenu.show();
		}, ContextMenuEvent.getType());
		return item;
	}

	public void clear() {
		panelUi.clear();
	}

	public void setManager(PlaylistManager playlistManager) {
		this.playlistManager = playlistManager;
	}

}

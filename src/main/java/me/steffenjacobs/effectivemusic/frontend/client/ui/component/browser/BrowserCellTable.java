package me.steffenjacobs.effectivemusic.frontend.client.ui.component.browser;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;

import me.steffenjacobs.effectivemusic.frontend.client.controller.Base64Encoder;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.AddToPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.FormattingUtils;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackDTO;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BrowserCellTable implements IsWidget {

	private final CellTable<TrackDTO> cellTable = new CellTable<>(200, (CellTable.Resources) GWT.create(ImprovedCellTableResources.class));

	private SimpleEventBus eventBus;

	public BrowserCellTable(BrowserPanel parentPanel) {
		cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		final MultiSelectionModel<TrackDTO> multiSelectionModel = new MultiSelectionModel<>();
		multiSelectionModel.addSelectionChangeHandler(e -> parentPanel.setResultCount(multiSelectionModel.getSelectedSet().size()));
		cellTable.setSelectionModel(multiSelectionModel);

		titleColumn.setCellStyleNames(EffectiveMusicResources.INSTANCE.style().columnWithRightBorder());
		artistColumn.setCellStyleNames(EffectiveMusicResources.INSTANCE.style().columnWithRightBorder());
		lengthColumn.setCellStyleNames(EffectiveMusicResources.INSTANCE.style().columnWithRightBorder());
		albumColumn.setCellStyleNames(EffectiveMusicResources.INSTANCE.style().columnWithRightBorder());

		cellTable.addColumn(titleColumn, "Title");
		cellTable.addColumn(artistColumn, "Artist");
		cellTable.addColumn(lengthColumn, "Length");
		cellTable.addColumn(albumColumn, "Album");
		cellTable.addColumn(bitrateColumn, "Bitrate");

		PopupPanel popupMenu = new PopupPanel(true);
		final Label addToPlaylist = new Label("Add selection to Playlist");
		addToPlaylist.addStyleName(EffectiveMusicResources.INSTANCE.style().contextMenuButton());
		addToPlaylist.addClickHandler(e -> {
			multiSelectionModel.getSelectedSet().forEach(t -> eventBus.fireEvent(new AddToPlaylistEvent(new String(Base64Encoder.encode(t.getPath().getBytes())), null)));
			popupMenu.hide();
			multiSelectionModel.clear();
		});
		popupMenu.add(addToPlaylist);

		cellTable.sinkEvents(Event.ONCONTEXTMENU);
		cellTable.addHandler(new ContextMenuHandler() {
			@Override
			public void onContextMenu(ContextMenuEvent event) {
				event.preventDefault();
				event.stopPropagation();
				popupMenu.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
				popupMenu.show();
			}
		}, ContextMenuEvent.getType());

		SimplePager pager = new SimplePager();
		pager.setDisplay(cellTable);
		pager.setPageSize(200);
	}

	public void updateTracks(List<TrackDTO> tracks) {
		cellTable.setRowCount(tracks.size(), true);
		cellTable.setRowData(0, tracks);
	}

	private final TextColumn<TrackDTO> titleColumn = new TextColumn<TrackDTO>() {
		@Override
		public String getValue(TrackDTO track) {
			return track.getTitle();
		}
	};
	private final TextColumn<TrackDTO> artistColumn = new TextColumn<TrackDTO>() {
		@Override
		public String getValue(TrackDTO track) {
			return track.getArtist();
		}
	};
	private final TextColumn<TrackDTO> lengthColumn = new TextColumn<TrackDTO>() {
		@Override
		public String getValue(TrackDTO track) {
			return FormattingUtils.formatTime(track.getLength());
		}
	};
	private final TextColumn<TrackDTO> albumColumn = new TextColumn<TrackDTO>() {
		@Override
		public String getValue(TrackDTO track) {
			return track.getAlbum();
		}
	};
	private final TextColumn<TrackDTO> bitrateColumn = new TextColumn<TrackDTO>() {
		@Override
		public String getValue(TrackDTO track) {
			return track.getBitrate() + "kbit/s";
		}
	};

	@Override
	public Widget asWidget() {
		return cellTable;
	}

	public void setEventBus(SimpleEventBus evtBus) {
		this.eventBus = evtBus;
	}

}

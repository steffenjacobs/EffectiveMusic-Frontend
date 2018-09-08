package me.steffenjacobs.effectivemusic.frontend.client.ui.component.browser;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;

import me.steffenjacobs.effectivemusic.frontend.client.controller.Base64Encoder;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.AddToPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.FormattingUtils;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackDTO;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BrowserCellTable implements IsWidget {

	private final CellTable<TrackDTO> cellTable = new CellTable<>(200, (CellTable.Resources) GWT.create(ImprovedCellTableResources.class));

	private SimpleEventBus eventBus;

	public BrowserCellTable() {
		cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		final MultiSelectionModel<TrackDTO> multiSelectionModel = new MultiSelectionModel<>();
		multiSelectionModel.addSelectionChangeHandler(
				e -> multiSelectionModel.getSelectedSet().forEach(t -> eventBus.fireEvent(new AddToPlaylistEvent(new String(Base64Encoder.encode(t.getPath().getBytes())), null))));
		cellTable.setSelectionModel(multiSelectionModel);

		cellTable.addColumn(titleColumn, "Title");
		cellTable.addColumn(artistColumn, "Artist");
		cellTable.addColumn(lengthColumn, "Length");
		cellTable.addColumn(albumColumn, "Album");
		cellTable.addColumn(bitrateColumn, "Bitrate");

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

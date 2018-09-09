package me.steffenjacobs.effectivemusic.frontend.client.ui.component.browser;

import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import me.steffenjacobs.effectivemusic.frontend.client.controller.MusicAutobeanFactory;
import me.steffenjacobs.effectivemusic.frontend.client.event.libraryimport.GetLibraryImportStatusEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.search.SearchEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.DefaultRequestCallback;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.FormattingUtils;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.Wrapper;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.remotefilebrowser.RemoteFileBrowserDialog;
import me.steffenjacobs.effectivemusic.frontend.common.domain.WeightedTrackListDTO;
import me.steffenjacobs.effectivemusic.frontend.common.domain.index.IndexStatusDTO;

/** @author Steffen Jacobs */
public class BrowserPanel extends Composite {

	private static ControlPanelUiBinder uiBinder = GWT.create(ControlPanelUiBinder.class);

	interface ControlPanelUiBinder extends UiBinder<Widget, BrowserPanel> {
	}

	private static MusicAutobeanFactory factory = GWT.create(MusicAutobeanFactory.class);

	@UiField
	Button importButtonUi;

	@UiField
	ScrollPanel contentPanelUi;

	@UiField
	TextBox searchBoxUi;

	@UiField
	Label searchResultCountUi;

	@UiField
	Label importStatusUi;

	SimpleEventBus eventBus;

	final BrowserCellTable browserCellTable;

	public BrowserPanel() {
		EffectiveMusicResources.INSTANCE.style().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));
		browserCellTable = new BrowserCellTable(this);
		contentPanelUi.setWidget(browserCellTable);
		searchBoxUi.getElement().setPropertyString("placeholder", "Search...");
	}

	public void setEventBus(SimpleEventBus evtBus) {
		this.eventBus = evtBus;
		browserCellTable.setEventBus(evtBus);
		checkImportStatus();
		refreshUi("");

	}

	private void updateImportStatus(long indexedFiles, long filesToIndex) {
		if (filesToIndex == indexedFiles) {
			importStatusUi.setText("Import of " + indexedFiles + " tracks finished!");
		} else {
			importStatusUi.setText("Import Status: " + indexedFiles + "/" + filesToIndex + "(" + FormattingUtils.formatPercent(100 * indexedFiles / (double) filesToIndex) + "%)");
		}
	}

	private void checkImportStatus() {
		eventBus.fireEvent(new GetLibraryImportStatusEvent(new DefaultRequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				AutoBean<IndexStatusDTO> bean = AutoBeanCodex.decode(factory, IndexStatusDTO.class, response.getText());
				final IndexStatusDTO indexStatus = bean.as();
				//
				if (!"FINISHED".equals(indexStatus.getIndexingStatus())) {
					updateImportStatus(indexStatus.getFilesIndexed(), indexStatus.getFilesToIndex());
					final Wrapper<Timer> timerWrapper = new Wrapper<>();
					timerWrapper.setValue(new Timer() {

						@Override
						public void run() {
							eventBus.fireEvent(new GetLibraryImportStatusEvent(new DefaultRequestCallback() {
								@Override
								public void onResponseReceived(Request request, Response response) {
									AutoBean<IndexStatusDTO> bean = AutoBeanCodex.decode(factory, IndexStatusDTO.class, response.getText());
									final IndexStatusDTO indexStatus = bean.as();
									//
									if ("FINISHED".equals(indexStatus.getIndexingStatus())) {
										timerWrapper.getValue().cancel();
									}
									updateImportStatus(indexStatus.getFilesIndexed(), indexStatus.getFilesToIndex());
								}
							}));
						}
					});

					timerWrapper.getValue().scheduleRepeating(300);
				}
			}
		}));
	}

	public void setResultCount(int count) {
		searchResultCountUi.setText("(" + count + " Tracks)");
	}

	@UiHandler("searchBoxUi")
	void onKeyUp(KeyUpEvent e) {
		refreshUi(searchBoxUi.getText());
	}

	private void refreshUi(String searchText) {
		eventBus.fireEvent(new SearchEvent(searchText, new DefaultRequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				AutoBean<WeightedTrackListDTO> bean = AutoBeanCodex.decode(factory, WeightedTrackListDTO.class, response.getText());
				final WeightedTrackListDTO autoBeanTracklist = bean.as();
				browserCellTable.updateTracks(autoBeanTracklist.getTracks().stream().map(t -> t.getTrack()).collect(Collectors.toList()));
				setResultCount(autoBeanTracklist.getTracks().size());
			}
		}));
	}

	@UiHandler("importButtonUi")
	void onStopButtonClicked(ClickEvent e) {
		new RemoteFileBrowserDialog(f -> GWT.log("chosen: " + f), eventBus);
	}
}

package me.steffenjacobs.effectivemusic.frontend.client.ui.component.browser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import me.steffenjacobs.effectivemusic.frontend.client.controller.MusicAutobeanFactory;
import me.steffenjacobs.effectivemusic.frontend.client.event.search.SearchEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.DefaultRequestCallback;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.remotefilebrowser.RemoteFileBrowserDialog;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackListDTO;

/** @author Steffen Jacobs */
public class BrowserPanel extends Composite {

	private static ControlPanelUiBinder uiBinder = GWT.create(ControlPanelUiBinder.class);

	interface ControlPanelUiBinder extends UiBinder<Widget, BrowserPanel> {
	}

	private static MusicAutobeanFactory factory = GWT.create(MusicAutobeanFactory.class);

	@UiField
	Button importButtonUi;

	@UiField
	SimplePanel contentPanelUi;

	@UiField
	TextBox searchBoxUi;

	SimpleEventBus eventBus;

	final BrowserCellTable browserCellTable = new BrowserCellTable();

	public BrowserPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		contentPanelUi.setWidget(browserCellTable);
		searchBoxUi.getElement().setPropertyString("placeholder", "Search...");
	}

	public void setEventBus(SimpleEventBus evtBus) {
		this.eventBus = evtBus;
		browserCellTable.setEventBus(evtBus);
	}

	@UiHandler("searchBoxUi")
	void onKeyUp(KeyUpEvent e) {
		eventBus.fireEvent(new SearchEvent(searchBoxUi.getText(), new DefaultRequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				AutoBean<TrackListDTO> bean = AutoBeanCodex.decode(factory, TrackListDTO.class, response.getText());
				final TrackListDTO autoBeanTracklist = bean.as();
				browserCellTable.updateTracks(autoBeanTracklist.getTracks());
			}
		}));
	}

	@UiHandler("importButtonUi")
	void onStopButtonClicked(ClickEvent e) {
		new RemoteFileBrowserDialog(f -> GWT.log("chosen: " + f), eventBus);
	}
}

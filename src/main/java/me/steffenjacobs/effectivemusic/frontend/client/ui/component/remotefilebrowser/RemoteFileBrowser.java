package me.steffenjacobs.effectivemusic.frontend.client.ui.component.remotefilebrowser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import me.steffenjacobs.effectivemusic.frontend.client.controller.MusicAutobeanFactory;
import me.steffenjacobs.effectivemusic.frontend.client.event.io.BrowseFileEvent;
import me.steffenjacobs.effectivemusic.frontend.client.ui.DefaultRequestCallback;
import me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist.EffectiveMusicResources;
import me.steffenjacobs.effectivemusic.frontend.common.domain.io.BrowseResult;
import me.steffenjacobs.effectivemusic.frontend.common.domain.io.FileDTO;

/** @author Steffen Jacobs */
public class RemoteFileBrowser extends Composite {

	private static RemoteFileBrowserUiBinder uiBinder = GWT.create(RemoteFileBrowserUiBinder.class);
	private static MusicAutobeanFactory factory = GWT.create(MusicAutobeanFactory.class);

	interface RemoteFileBrowserUiBinder extends UiBinder<Widget, RemoteFileBrowser> {
	}

	@UiField
	TextBox browsePathUi;

	@UiField
	Button browseButtonUi;

	@UiField
	FlowPanel fileListUi;

	private SimpleEventBus eventBus;

	public RemoteFileBrowser() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setEventBus(SimpleEventBus evtBus) {
		this.eventBus = evtBus;
		updatePath("/");
	}

	public void updatePath(String newPath) {
		eventBus.fireEvent(new BrowseFileEvent(new DefaultRequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				AutoBean<BrowseResult> bean = AutoBeanCodex.decode(factory, BrowseResult.class, response.getText());
				final BrowseResult autoBeanBrowseResult = bean.as();
				browsePathUi.setText(autoBeanBrowseResult.getDirectoryPath());
				updateList(autoBeanBrowseResult);
			}
		}, newPath));
	}

	@UiHandler("browseButtonUi")
	void onClick(ClickEvent e) {
		updatePath(browsePathUi.getText());
	}

	public String getPath() {
		return browsePathUi.getText();
	}

	private void updateList(BrowseResult result) {
		fileListUi.clear();
		browsePathUi.setText(result.getDirectoryPath());
		if (!"/".equals(result.getDirectoryPath())) {
			Label parent = new Label("directory - ..");
			int index = result.getDirectoryPath().lastIndexOf('/');
			if (index == -1) {
				index = result.getDirectoryPath().lastIndexOf('\\');
			}
			final int indexFinal = index;
			if (index > -1) {
				parent.addClickHandler(e -> updatePath(result.getDirectoryPath().substring(0, indexFinal)));
				parent.addStyleName(EffectiveMusicResources.INSTANCE.style().fileBrowserItem());
				fileListUi.add(parent);
			}
		}

		for (final FileDTO file : result.getFiles()) {
			Label lab = new Label(file.getType() + " - " + file.getName());
			if ("directory".equals(file.getType())) {
				lab.addClickHandler(e -> updatePath(file.getAbsolutePath()));
				lab.addStyleName(EffectiveMusicResources.INSTANCE.style().fileBrowserItem());
			} else {
				lab.addStyleName(EffectiveMusicResources.INSTANCE.style().fileBrowserItemNonClickable());
			}
			fileListUi.add(lab);
		}
	}

}

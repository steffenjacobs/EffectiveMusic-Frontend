package me.steffenjacobs.effectivemusic.frontend.client.ui.component.playlist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import me.steffenjacobs.effectivemusic.frontend.client.ui.component.FormattingUtils;
import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackDto;

/** @author Steffen Jacobs */
public class PlaylistPanel extends Composite {

	private static PlaylistPanelUiBinder uiBinder = GWT.create(PlaylistPanelUiBinder.class);

	interface PlaylistPanelUiBinder extends UiBinder<Widget, PlaylistPanel> {
	}

	@UiField
	DivElement panelUi;

	public PlaylistPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPlaylist(Iterable<TrackDto> tracks) {
		tracks.forEach(t -> panelUi.appendChild(createTrackItem(t)));
		panelUi.getParentElement().getParentElement().getStyle().setHeight(100, Unit.PC);
	}

	private Element createTrackItem(TrackDto track) {
		Element span = DOM.createDiv();
		span.addClassName(EffectiveMusicResources.RES.style().track());
		span.setInnerHTML(FormattingUtils.formatTrackForPlaylist(track));
		return span;
	}

	public void clear() {
		panelUi.removeAllChildren();
	}

}

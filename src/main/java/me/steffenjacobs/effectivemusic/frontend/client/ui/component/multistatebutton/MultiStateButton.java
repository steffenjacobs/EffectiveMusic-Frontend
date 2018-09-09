package me.steffenjacobs.effectivemusic.frontend.client.ui.component.multistatebutton;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** @author Steffen Jacobs */
public class MultiStateButton extends Composite {

	private static MultiStateButtonUiBinder uiBinder = GWT.create(MultiStateButtonUiBinder.class);

	public static interface StateChangeHandler {
		void onStateChange(int state);
	}

	interface MultiStateButtonUiBinder extends UiBinder<Widget, MultiStateButton> {
	}

	@UiField
	Button button;

	private int state = 0;
	private Map<Integer, String> buttonCaptions;

	private final Collection<StateChangeHandler> handlers = new LinkedList<>();

	private void onStateChange() {
		if (state + 1 < buttonCaptions.size()) {
			state++;
		} else {
			state = 0;
		}
		button.setText(buttonCaptions.get(state));
		handlers.forEach(h -> h.onStateChange(state));
	}

	public MultiStateButton() {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText("?");
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		onStateChange();
	}

	public void setButtonCaptions(Map<Integer, String> buttonCaptions) {
		this.buttonCaptions = buttonCaptions;
	}

	public void addStateChangeHandler(StateChangeHandler handler) {
		handlers.add(handler);
	}

	public void setStatus(int status) {
		state = status;
		button.setText(buttonCaptions.get(state));
		handlers.forEach(h -> h.onStateChange(state));
	}

}

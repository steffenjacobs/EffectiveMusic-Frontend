package me.steffenjacobs.effectivemusic.frontend.client.ui.component.editable;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/** @author Steffen Jacobs */
public class InlineEditTextbox extends Composite implements HasText {

	private static InlineEditTextboxUiBinder uiBinder = GWT.create(InlineEditTextboxUiBinder.class);

	interface InlineEditTextboxUiBinder extends UiBinder<Widget, InlineEditTextbox> {
	}

	@UiField
	Label editButton;

	@UiField
	Label label;

	@UiField
	TextBox textbox;

	public static interface ValueChangeHandler<T> {
		public void onValueChange(T value);
	}

	private boolean editMode = false;

	private final Collection<ValueChangeHandler<String>> handlers = new ArrayList<>();

	public InlineEditTextbox() {
		initWidget(uiBinder.createAndBindUi(this));
		setText("Empty");
	}

	@UiHandler("label")
	void onEdit(ClickEvent e) {
		setEditMode(true);
	}

	@UiHandler("textbox")
	void onKeyUp(KeyUpEvent e) {
		if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			setEditMode(false);
		}
	}

	private void setEditMode(boolean edit) {
		setEditMode(edit, false);
	}

	private void setEditMode(boolean edit, boolean suppressEvents) {
		this.editMode = edit;
		textbox.setVisible(edit);
		if (edit) {
			editButton.setText("Save");
			textbox.getElement().getStyle().setWidth(label.getOffsetWidth(), Unit.PX);
			textbox.getElement().getStyle().setProperty("minWidth", label.getOffsetWidth(), Unit.PX);
			textbox.setText(label.getText());
			textbox.setFocus(true);
		} else {
			editButton.setText("Edit");
			label.setText(textbox.getText());
			if (!suppressEvents) {
				handlers.forEach(h -> h.onValueChange(getText()));
			}
		}
		label.setVisible(!edit);
	}

	@UiHandler("editButton")
	void onClick(ClickEvent e) {
		setEditMode(!editMode);
	}

	public void setText(String text) {
		setEditMode(false, true);
		label.setText(text);
	}

	public String getText() {
		return label.getText();
	}

	public void addListener(ValueChangeHandler<String> handler) {
		handlers.add(handler);
	}

}

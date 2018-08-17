package me.steffenjacobs.effectivemusic.frontend.client.ui.component.simpleslider;

import com.google.gwt.dom.client.NativeEvent;

/** @author Steffen Jacobs */
public interface SliderEventHandler {

	/** called when the slider is clicked */
	default void onClick(NativeEvent event) {
	}

	/** called when the users starts to drag the slider */
	default void onDragStart(NativeEvent event) {
	}

	/** called while the user is dragging the slider */
	default void onDrag(NativeEvent event) {
	}

	/** called when the user finished dragging the slider */
	default void onDragEnd(NativeEvent event) {
	}
}
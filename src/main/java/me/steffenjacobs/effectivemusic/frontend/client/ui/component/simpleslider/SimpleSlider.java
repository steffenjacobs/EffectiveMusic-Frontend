package me.steffenjacobs.effectivemusic.frontend.client.ui.component.simpleslider;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** @author Steffen Jacobs */
public class SimpleSlider extends Composite {

	private static BeautifulSliderUiBinder uiBinder = GWT.create(BeautifulSliderUiBinder.class);

	interface BeautifulSliderUiBinder extends UiBinder<Widget, SimpleSlider> {
	}

	@UiField
	DivElement divBox;
	@UiField
	DivElement divLeft;

	private double position = 0;

	final BeautifulSliderEventListener divEventListener;

	public SimpleSlider() {
		initWidget(uiBinder.createAndBindUi(this));

		final SliderEventHandler graphicsHandler = new SliderEventHandler() {

			@Override
			public void onDragEnd(NativeEvent event) {
				setPosition((((double) event.getClientX() - divBox.getAbsoluteLeft()) / divBox.getClientWidth()) * 100);
			}

			@Override
			public void onDrag(NativeEvent event) {
				setPosition((((double) event.getClientX() - divBox.getAbsoluteLeft()) / divBox.getClientWidth()) * 100);
			}

			@Override
			public void onClick(NativeEvent event) {
				new SlideAnimation(divLeft, (double) event.getClientX() - divBox.getAbsoluteLeft()).run(100);
			}
		};

		divEventListener = new BeautifulSliderEventListener();
		divEventListener.addEventHandler(graphicsHandler);
		DOM.sinkEvents(divBox, Integer.MAX_VALUE);
		DOM.setEventListener(divBox, divEventListener);
	}

	/**
	 * sets the position of the slider to <i>percent</i>
	 * 
	 * @param percent
	 *            percentage between 0 and 100
	 */
	public void setPosition(double percent) {
		percent = percent < 0 ? 0 : percent > 100 ? 100 : percent;
		divLeft.getStyle().setWidth(divBox.getOffsetWidth() * (percent / 100), Unit.PX);
		position = percent;
	}

	/** @return the value of the slider between 0 and 100 */
	public double getPosition() {
		return position;
	}

	/** add an event handler for SliderEvents */
	public void addEventHandler(SliderEventHandler handler) {
		divEventListener.addEventHandler(handler);
	}

	public void removeEventHandler(SliderEventHandler handler) {
		divEventListener.removeEventHandler(handler);
	}

	public void setWidth(int width) {
		divBox.getStyle().setWidth(width, Unit.PX);
		divLeft.getStyle().setProperty("maxWidth", width + "px");
		setPosition(position);
	}

	public void setHeight(int height) {
		divBox.getStyle().setHeight(height, Unit.PX);
		divLeft.getStyle().setHeight(height, Unit.PX);
	}

	public void setBarColor(String color) {
		divLeft.getStyle().setBackgroundColor(color);
	}

	private class SlideAnimation extends Animation {
		private final Element element;
		private final double targetWidth;
		private final double startWidth;

		//TODO: use CSS transition instead
		public SlideAnimation(Element element, double targetWidth) {
			position = 100 * targetWidth / divBox.getClientWidth();
			this.element = element;
			this.targetWidth = targetWidth;
			this.startWidth = element.getClientWidth();
		}

		@Override
		protected void onComplete() {
			element.getStyle().setWidth(targetWidth, Unit.PX);
		}

		@Override
		protected void onUpdate(double progress) {
			element.getStyle().setWidth(startWidth + progress * (targetWidth - startWidth), Unit.PX);
		}
	}

	private class BeautifulSliderEventListener implements EventListener {

		boolean isDragging = false;
		boolean dragged = false;
		boolean dragJustEnded = false;

		private final List<SliderEventHandler> handlers = new LinkedList<>();

		private BeautifulSliderEventListener() {
			Event.addNativePreviewHandler(event -> {
				switch (event.getNativeEvent().getType()) {
				case "mouseup":
					mouseUp(event.getNativeEvent());
					break;
				case "mousemove":
					mouseMove(event.getNativeEvent());
					break;
				default:
					break;
				}
			});
		}

		@Override
		public void onBrowserEvent(Event event) {
			switch (event.getType()) {
			case "click":
				if (dragJustEnded) {
					dragJustEnded = false;
				} else {
					handlers.forEach(h -> h.onClick(event));
				}
				break;
			case "mousemove":
				mouseMove(event);
				break;
			case "mousedown":
				isDragging = true;
				break;
			case "mouseup":
				mouseUp(event);
				break;
			default:
				break;
			}
		}

		private void mouseUp(NativeEvent event) {
			isDragging = false;
			if (dragged) {
				dragged = false;
				dragJustEnded = true;
				handlers.forEach(h -> h.onDragEnd(event));
			}
		}

		private void mouseMove(NativeEvent event) {
			if (isDragging) {
				if (!dragged) {
					dragged = true;
					handlers.forEach(h -> h.onDragStart(event));
				}
				handlers.forEach(h -> h.onDrag(event));
			}
		}

		public void addEventHandler(SliderEventHandler handler) {
			handlers.add(handler);
		}

		public void removeEventHandler(SliderEventHandler handler) {
			handlers.remove(handler);
		}
	}

}

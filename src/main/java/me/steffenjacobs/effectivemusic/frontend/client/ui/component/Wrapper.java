package me.steffenjacobs.effectivemusic.frontend.client.ui.component;

/** @author Steffen Jacobs */
public class Wrapper<T> {

	private T t;

	public T getValue() {
		return t;
	}

	public void setValue(T t) {
		this.t = t;
	}

}

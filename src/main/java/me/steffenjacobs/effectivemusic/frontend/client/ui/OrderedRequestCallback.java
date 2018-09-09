package me.steffenjacobs.effectivemusic.frontend.client.ui;

/** @author Steffen Jacobs */
public class OrderedRequestCallback extends DefaultRequestCallback {

	private final int sequenceNumber;

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public OrderedRequestCallback(final int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

}

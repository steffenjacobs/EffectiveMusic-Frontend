package me.steffenjacobs.effectivemusic.frontend.client.event.search;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.RequestCallback;

/**
 * @author Steffen Jacobs
 */
public class SearchEvent extends GwtEvent<SearchEventHandler> {

	public static Type<SearchEventHandler> TYPE = new Type<>();

	final String searchText;
	final RequestCallback callback;

	public SearchEvent(String searchText, RequestCallback callback) {
		this.searchText = searchText;
		this.callback = callback;
	}

	public String getSearchText() {
		return searchText;
	}

	public RequestCallback getCallback() {
		return callback;
	}

	@Override
	protected void dispatch(SearchEventHandler handler) {
		handler.onSearch(this);
	}

	@Override
	public Type<SearchEventHandler> getAssociatedType() {
		return TYPE;
	}
}

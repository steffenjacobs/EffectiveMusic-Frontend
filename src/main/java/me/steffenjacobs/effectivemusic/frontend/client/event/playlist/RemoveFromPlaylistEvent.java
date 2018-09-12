package me.steffenjacobs.effectivemusic.frontend.client.event.playlist;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Steffen Jacobs
 */
public class RemoveFromPlaylistEvent extends GwtEvent<RemoveFromPlaylistEventHandler> {

	public static Type<RemoveFromPlaylistEventHandler> TYPE = new Type<>();

	final List<Long> indices;

	public RemoveFromPlaylistEvent(List<Long> indices) {
		this.indices = indices;
	}

	public List<Long> getIndices() {
		return indices;
	}

	@Override
	protected void dispatch(RemoveFromPlaylistEventHandler handler) {
		handler.onRemoveFromPlaylist(this);
	}

	@Override
	public Type<RemoveFromPlaylistEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getIndicesAsQueryString() {
		if (indices.size() > 1) {
			return String.join("&index=", indices.stream().map(l -> l.toString()).collect(Collectors.toList()));
		}
		return indices.get(0).toString();
	}
}

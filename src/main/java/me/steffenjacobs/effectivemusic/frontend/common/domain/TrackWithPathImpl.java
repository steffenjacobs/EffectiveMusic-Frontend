package me.steffenjacobs.effectivemusic.frontend.common.domain;

/** @author Steffen Jacobs */
public class TrackWithPathImpl{

	private String path;
	private LiveTrackImpl track;

	public TrackWithPathImpl(String path, LiveTrackImpl track) {
		super();
		this.path = path;
		this.track = track;
	}

	public String getPath() {
		return path;
	}

	public LiveTrackImpl getTrack() {
		return track;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((track == null) ? 0 : track.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackWithPathImpl other = (TrackWithPathImpl) obj;
		if (track == null) {
			if (other.track != null)
				return false;
		} else if (!track.equals(other.track))
			return false;
		return true;
	}

}

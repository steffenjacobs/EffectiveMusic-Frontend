package me.steffenjacobs.effectivemusic.frontend.common.domain;

public class TrackImpl implements LiveTrackDTO {

	private String artist, album, title, year, comment, track, discNo, composer, artistSort;
	private long length;
	private double volume, position;

	public TrackImpl(LiveTrackDTO liveTrackDTO) {
		if (liveTrackDTO == null) {
			return;
		}
		this.artist = liveTrackDTO.getArtist();
		this.album = liveTrackDTO.getAlbum();
		this.title = liveTrackDTO.getTitle();
		this.year = liveTrackDTO.getYear();
		this.comment = liveTrackDTO.getComment();
		this.track = liveTrackDTO.getTrack();
		this.discNo = liveTrackDTO.getDisc_no();
		this.composer = liveTrackDTO.getComposer();
		this.artistSort = liveTrackDTO.getArtist_sort();
		this.length = liveTrackDTO.getLength();
		this.volume = liveTrackDTO.getVolume();
		this.position = liveTrackDTO.getPosition();
	}

	public TrackImpl() {

	}

	@Override
	public String getArtist() {
		return artist;
	}

	@Override
	public String getAlbum() {
		return album;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public String getYear() {
		return year;
	}

	@Override
	public String getTrack() {
		return track;
	}

	@Override
	public String getDisc_no() {
		return discNo;
	}

	@Override
	public String getComposer() {
		return composer;
	}

	@Override
	public String getArtist_sort() {
		return artistSort;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public long getLength() {
		return length;
	}

	@Override
	public void setLength(long trackLength) {
		this.length = trackLength;
	}

	@Override
	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public void setAlbum(String album) {
		this.album = album;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public void setTrack(String track) {
		this.track = track;
	}

	@Override
	public void setDisc_no(String disc_no) {
		this.discNo = disc_no;
	}

	@Override
	public void setComposer(String composer) {
		this.composer = composer;
	}

	@Override
	public void setArtist_sort(String artist_sort) {
		this.artistSort = artist_sort;
	}

	@Override
	public double getPosition() {
		return position;
	}

	@Override
	public void setPosition(double position) {
		this.position = position;
	}

	@Override
	public double getVolume() {
		return volume;
	}

	@Override
	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		TrackImpl other = (TrackImpl) obj;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}

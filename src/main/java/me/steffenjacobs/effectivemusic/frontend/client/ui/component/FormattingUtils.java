package me.steffenjacobs.effectivemusic.frontend.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;

import me.steffenjacobs.effectivemusic.frontend.client.resource.MusicTemplates;
import me.steffenjacobs.effectivemusic.frontend.common.domain.LiveTrackDTO;

/** @author Steffen Jacobs */
public class FormattingUtils {

	private static MusicTemplates templates = GWT.create(MusicTemplates.class);
	private static final NumberFormat DOUBLE_DIGITS = NumberFormat.getFormat("00");
	private static final NumberFormat PERCENT = NumberFormat.getFormat("#0.0");

	public static String formatPercent(double percent) {
		return PERCENT.format(percent);
	}

	public static String formatPosition(double position, long lengthInMillis) {
		long timeInMillis = (long) (position * lengthInMillis);
		return formatTime(timeInMillis);
	}

	public static String formatTime(long timeInMillis) {
		timeInMillis /= 1000;
		long hours = timeInMillis / 360;
		long minutes = (timeInMillis % 360) / 60;
		long seconds = timeInMillis % 360 % 60;
		return hours > 0 ? templates.formatTimestamp(DOUBLE_DIGITS.format(hours), DOUBLE_DIGITS.format(minutes), DOUBLE_DIGITS.format(seconds)).asString()
				: templates.formatTimestampSmall(DOUBLE_DIGITS.format(minutes), DOUBLE_DIGITS.format(seconds)).asString();
	}

	public static String formatTrackForPlaylist(LiveTrackDTO track) {
		StringBuilder sb = new StringBuilder();
		if (track.getTitle() != null) {
			sb.append(track.getTitle());
		}
		if (track.getArtist() != null && !track.getArtist().equals("")) {
			sb.append(" - ");
			sb.append(track.getArtist());
		}
		sb.append(" ");
		sb.append(formatTime(track.getLength()));
		return sb.toString();
	}

}

package me.steffenjacobs.effectivemusic.frontend.client.resource;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

/** @author Steffen Jacobs */
public interface MusicTemplates extends SafeHtmlTemplates {

	@Template("{0}:{1}:{2}")
	SafeHtml formatTimestamp(String hour, String minute, String second);
}

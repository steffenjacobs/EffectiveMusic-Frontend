package me.steffenjacobs.effectivemusic.frontend.client.controller;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

import me.steffenjacobs.effectivemusic.frontend.common.domain.TrackDto;

/** @author Steffen Jacobs */
public interface MusicAutobeanFactory extends AutoBeanFactory {
	AutoBean<TrackDto> trackDto();

}
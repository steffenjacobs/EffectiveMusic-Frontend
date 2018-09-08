package me.steffenjacobs.effectivemusic.frontend.client.controller;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

import me.steffenjacobs.effectivemusic.frontend.common.domain.PlayerInformationDTO;
import me.steffenjacobs.effectivemusic.frontend.common.domain.PlaylistDto;
import me.steffenjacobs.effectivemusic.frontend.common.domain.LiveTrackDto;
import me.steffenjacobs.effectivemusic.frontend.common.domain.io.BrowseResult;
import me.steffenjacobs.effectivemusic.frontend.common.domain.io.FileDTO;

/** @author Steffen Jacobs */
public interface MusicAutobeanFactory extends AutoBeanFactory {
	AutoBean<LiveTrackDto> liveTrackDto();

	AutoBean<PlaylistDto> playlistDto();

	AutoBean<PlayerInformationDTO> playerInformationDTO();

	AutoBean<BrowseResult> browseResult();

	AutoBean<FileDTO> fileDTO();

}
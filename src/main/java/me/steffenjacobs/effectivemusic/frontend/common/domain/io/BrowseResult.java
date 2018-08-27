package me.steffenjacobs.effectivemusic.frontend.common.domain.io;

import java.util.List;

/** @author Steffen Jacobs */
public interface BrowseResult {
	
	public List<FileDTO> getFiles();
	
	public void setFiles(List<FileDTO> files);
	
	public String getDirectoryPath();
	
	public void setDirectoryPath(String directoryPath);

}

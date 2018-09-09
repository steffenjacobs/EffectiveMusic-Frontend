package me.steffenjacobs.effectivemusic.frontend.common.domain.index;

/** @author Steffen Jacobs */
public interface IndexStatusDTO {
	long getFilesToIndex();

	long getFilesIndexed();

	long getNumDirectories();

	String getIndexingStatus();

	void setFilesToIndex(long filesToIndex);

	void setFilesIndexed(long filesIndexed);

	void setIndexingStatus(String indexingStatus);

}

package me.steffenjacobs.effectivemusic.frontend.common.domain;

/** @author Steffen Jacobs */
public interface PlayerInformationDTO {
	void setStatus(String status);
	
	String getStatus();
	
	void setVolume(double volume);
	
	double getVolume();
	
	void setMute(boolean mute);
	
	boolean getMute();
	
	long getLoopStatus();
	
	void setLoopStatus(long loopStatus);

}

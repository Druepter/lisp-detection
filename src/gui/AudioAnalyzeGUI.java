package gui;

import logic.AudioAnalyzeLogic;

public interface AudioAnalyzeGUI {

	public void setAudioAnalyzeLogic(AudioAnalyzeLogic audioAnalyzelogic);
	public void audioAnalyzeNotification();
	public void matlabEngineLoaded();
	public void matlabEngineLoading();
	public String getConfigFileName();
	
}

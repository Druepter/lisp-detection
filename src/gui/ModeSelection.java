package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.AudioAnalyzeLogic;

public class ModeSelection {

	
	static JFrame modeSelectionFrame = new JFrame();
	static JPanel modeSelectionPanel = new JPanel();
	
	
	public ModeSelection() {

		createModeSelectionFrame();
		
	}
	
	
	//In dieser Methode wird der mainFrame der Anwendung erstellt
	public void createModeSelectionFrame() {
		
		
		JButton lispDetection = new JButton("LispDetection");
		lispDetection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Thread modeSelectionThread = new Thread(new Runnable() {
					
					public void run() {
				
						modeSelectionFrame.dispose();

						try {
							LispDetectionGUI lispDetectionGUI = new LispDetectionGUI();
							AudioAnalyzeLogic audioAnalyzeLogic;
							
							audioAnalyzeLogic = new AudioAnalyzeLogic(lispDetectionGUI);
							lispDetectionGUI.setAudioAnalyzeLogic(audioAnalyzeLogic);
						} 
						catch (IOException e) {
							// TODO Auto-generated catch block
							ErrorGUI errorGUI = new ErrorGUI("Config Datei wurde nicht gefunden. Bitte erstellen sie diese!");
							e.printStackTrace();
						}
						
					}
				});
				modeSelectionThread.start();	
					
			}
		});
		
		JButton noiseGate = new JButton("NoiseGate");
		noiseGate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Thread modeSelectionThread = new Thread(new Runnable() {
					
					public void run() {
				
						modeSelectionFrame.dispose();

						try {
							NoiseGateGUI noiseGateGUI = new NoiseGateGUI();
							AudioAnalyzeLogic audioAnalyzeLogic;
							
							audioAnalyzeLogic = new AudioAnalyzeLogic(noiseGateGUI);
							noiseGateGUI.setAudioAnalyzeLogic(audioAnalyzeLogic);
						} 
						catch (IOException e) {
							// TODO Auto-generated catch block
							ErrorGUI errorGUI = new ErrorGUI("Config Datei wurde nicht gefunden. Bitte erstellen sie diese!");
							e.printStackTrace();
						}
						
					}
				});
				modeSelectionThread.start();
				
				
				
			}
		});
	

	
		modeSelectionPanel.add(lispDetection);
		modeSelectionPanel.add(noiseGate);		
		
		modeSelectionPanel.setLayout(new GridLayout(2, 2));
		modeSelectionPanel.setPreferredSize(new Dimension(300, 200));
		modeSelectionPanel.setVisible(true);
		modeSelectionPanel.setBackground(Color.white);
		modeSelectionFrame.add(modeSelectionPanel, BorderLayout.CENTER);
		modeSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		modeSelectionFrame.setTitle("Mode Selection");
		modeSelectionFrame.pack();
		modeSelectionFrame.setVisible(true);
		
		
	}
	
}

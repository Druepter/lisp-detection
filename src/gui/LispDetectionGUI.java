package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.AudioAnalyzeLogic;

//Klasse für die GUI der LispDetection
public class LispDetectionGUI implements ActionListener, AudioAnalyzeGUI{

	AudioAnalyzeLogic audioAnalyzeLogic;
	
	
	boolean lispDetected = false;
	JLabel label = new JLabel("Hallo 1234");

	
	
	
	public LispDetectionGUI() {
		
		JFrame frame = new JFrame();
		
		
		JButton startButton = new JButton("Starte LispDetection");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Starte");
				audioAnalyzeLogic.callAudioProcessing();
				
				
				
				System.out.println("Nach aufruf");
				//Observer einbauen
				//Oder Die GUI mitgeben dann dann Text dirket im Thread ändern = unschön
				
				
			}
		});
		
		JButton stopButton = new JButton("Stoppe LispDetection");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Stoppe");
				audioAnalyzeLogic.stopAudioProcessing();
			}
		});		

				
			
		
		
		

		
		
		
		JPanel panel = new JPanel();
		
		panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(startButton);
		panel.add(stopButton);
		
		panel.add(label);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Meine GUI");
		frame.pack();
		frame.setVisible(true);
		
		
		JButton switchButton = new JButton("Ändere label");
		switchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("huhu");
				label.setText("123");
			}
		});
		
		panel.add(switchButton);
		
	}
	
	
	
	public void sendNotification() {
		label.setText("true");
		
		/*Timer timer = new Timer();
		TimerTask setLispNotificationToFalse = new TimerTask() {
			@Override
			public void run() {
                System.out.println("das sollte verzögter ausgeführt werden");
                label.setText("false");
			}
		};
		
		timer.schedule(setLispNotificationToFalse, 15000);*/
		
	}
	
	
	public static void main(String[] args) {
		
		//Erstelle die LispDetectionGui
		LispDetectionGUI lispDetectionGUI = new LispDetectionGUI();
		//Erstelle die AudioAnalyzeLogic und übergebe LispDetectionGUI
		AudioAnalyzeLogic audioAnalyzeLogic = new AudioAnalyzeLogic(lispDetectionGUI);
		
		//Setzte in der LispDetectionGUI die AudioAnalyzeLogic
		lispDetectionGUI.setAudioAnalyzeLogic(audioAnalyzeLogic);
		
		
		
		
		
		

		
		
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("huhu");
		
	}

	@Override
	public void setAudioAnalyzeLogic(AudioAnalyzeLogic audioAnalyzeLogic) {
		// TODO Auto-generated method stub
		this.audioAnalyzeLogic = audioAnalyzeLogic;
	}



	@Override
	public void audioAnalyzeNotification() {
		// TODO Auto-generated method stub
		label.setText("true");
		
	}
	
}

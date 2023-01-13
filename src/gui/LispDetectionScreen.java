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

import logic.LispDetectionLogic;


public class LispDetectionScreen implements ActionListener{

	
	boolean lispDetected = false;
	JLabel label = new JLabel("Hallo 1234");

	
	public LispDetectionScreen(LispDetectionLogic lispDetectionLogic) {
		
		JFrame frame = new JFrame();
		
		
		JButton startButton = new JButton("Starte LispDetection");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Starte");
				lispDetectionLogic.callAudioProcessing();
				System.out.println("Nach aufruf");
				//Observer einbauen
				//Oder Die GUI mitgeben dann dann Text dirket im Thread ändern = unschön
				
				
			}
		});
		
		JButton stopButton = new JButton("Stoppe LispDetection");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Stoppe");
				lispDetectionLogic.stopRecording();
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
		
		Timer timer = new Timer();

		
		TimerTask setLispNotificationToFalse = new TimerTask() {
			@Override
			public void run() {
                System.out.println("das sollte verzögter ausgeführt werden");
                label.setText("false");
			}
		};
		
		timer.schedule(setLispNotificationToFalse, 15000);
		
	}
	
	
	public static void main(String[] args) {
		
		LispDetectionLogic lispDetectionLogic = new LispDetectionLogic();
		
		LispDetectionScreen lispDetectionScreen = new LispDetectionScreen(lispDetectionLogic);
		
		
		
		lispDetectionLogic.setLispDetectionScreen(lispDetectionScreen);
		
		

		
		
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("huhu");
		
	}
	
}

package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.LispDetectionLogic;


public class LispDetectionScreen implements ActionListener{

	
	public LispDetectionScreen(LispDetectionLogic lispDetectionLogic) {
		
		JFrame frame = new JFrame();
		
		
		JButton startButton = new JButton("Starte LispDetection");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Starte");
				lispDetectionLogic.startRecording();
			}
		});
		
		JButton stopButton = new JButton("Stoppe LispDetection");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Stoppe");
				lispDetectionLogic.stopRecording();
			}
		});		
		
		
		
		JLabel label = new JLabel("Hallo 1234");
		
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
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		LispDetectionLogic lispDetectionLogic = new LispDetectionLogic();
		
		new LispDetectionScreen(lispDetectionLogic);
		
		
		
		
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("huhu");
		
	}
	
}

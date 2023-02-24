package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorGUI {

	static JFrame errorFrame = new JFrame();
	static JPanel errorPanel = new JPanel();
	
	
	public ErrorGUI(String errorMessage) {
		createErrorFrame(errorMessage);
	}
	
	
	public static void createErrorFrame(String errorMessage) {

        errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Als erstes soll ein normaler S-Laut für die nächsten 7 Sekunden gemacht werden
        JLabel errorMessageLabel = new JLabel(errorMessage);
     
        errorPanel.setSize(200, 200);
        errorPanel.setBackground(Color.white);
        
        errorPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        //calibrationPanel.setLayout(new GridLayout(3, 3));
        errorPanel.add(errorMessageLabel);    
        errorFrame.add(errorPanel, BorderLayout.CENTER);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		errorFrame.setTitle("Error");
		errorFrame.pack();
		errorFrame.setResizable(false);
		errorFrame.setVisible(true);
		 

	}	
	
}

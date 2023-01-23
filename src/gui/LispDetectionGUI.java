package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.UIManager;

import logic.AudioAnalyzeLogic;

//Klasse für die GUI der LispDetection
public class LispDetectionGUI implements ActionListener, AudioAnalyzeGUI{

	AudioAnalyzeLogic audioAnalyzeLogic;
	
	static JFrame mainFrame = new JFrame();
	static JFrame lispStatusFrame = new JFrame();
	
	JPanel mainPanel = new JPanel();
	static JPanel lispStatusPanel = new JPanel();
	
	boolean lispDetected = false;
	JLabel label = new JLabel("Hallo 1234");

	
	
	
	public LispDetectionGUI() {
		
		//Erstelle den Main Frame der Seite
		createMainFrame();
		
	}
	
	
	//In dieser Methode wird der mainFrame der Anwendung erstellt
	public void createMainFrame() {
		
		JLabel headline = new JLabel("Realtime Lisp Detection");
		
		
		JButton startCalibration = new JButton("Kalibrierung");
		
		
		
		
		
		JButton startButton = new JButton("Starte LispDetection");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Starte");
				createLispStatusFrame();
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
		
		
		
		
		//mainPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));

		mainPanel.add(headline);
		mainPanel.add(startButton);
		mainPanel.add(stopButton);
		mainPanel.add(startCalibration);
		
		mainPanel.add(label);
		
		
		mainPanel.setLayout(new GridLayout(3, 3));
		mainPanel.setPreferredSize(new Dimension(800, 500));
		mainPanel.setVisible(true);
		
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("Lisp Detection");
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		
		JButton switchButton = new JButton("Ändere label");
		switchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createLispStatusFrame();
				System.out.println("huhu");
				label.setText("123");
			}
		});
		
		mainPanel.add(switchButton);
		
	}
	
	
	public static void createLispStatusFrame() {
		
		EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                
                lispStatusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                /*try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }*/
                
                
            
                lispStatusPanel.setSize(300, 300);
                
                
                //static Color red = black;
                lispStatusPanel.setBackground(Color.green);
                
                
        		lispStatusPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
        		lispStatusPanel.setLayout(new GridLayout(0, 1));
                
        		lispStatusFrame.add(lispStatusPanel, BorderLayout.CENTER);
        		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		lispStatusFrame.setTitle("Lisp Status");
        		lispStatusFrame.pack();
        		lispStatusFrame.setResizable(true);
        		lispStatusFrame.setVisible(true);
        		
        		
                       }
        });
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
		//Wenn eine Meldung der AudioAnalyzeLogic kommt dann setze Hintergrundfarbe auf rot
		lispStatusPanel.setBackground(Color.red);
		
		//Erstelle einen Timer
		//Dieser setzt die HIntergrundfarbe des LispStatusFrames nach 10 Sekunden
		//wieder auf grün
		/*Timer timer = new Timer();
		TimerTask setLispStatusToFalse = new TimerTask() {
			@Override
			public void run() {
                System.out.println("das sollte verzögter ausgeführt werden");
                lispStatusPanel.setBackground(Color.green);
			}
		};
		
		timer.schedule(setLispStatusToFalse, 15000);*/
	}


	@Override
	public void matlabEngineLoaded() {
		// TODO Auto-generated method stub
		System.out.println("Matlab Engine fertig geladen");
		lispStatusPanel.setBackground(Color.green);
		
	}
	
}

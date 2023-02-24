package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logic.AudioAnalyzeLogic;

//Klasse für die GUI der LispDetection
public class NoiseGateGUI implements ActionListener, AudioAnalyzeGUI{

	static AudioAnalyzeLogic audioAnalyzeLogic;
	
	static JFrame mainFrame = new JFrame();
	static JFrame noiseStatusFrame = new JFrame();
	static JFrame loadingFrame = new JFrame();
	static JFrame calibrationFrame = new JFrame();
	
	static JPanel loadingPanel = new JPanel();
	static JPanel mainPanel = new JPanel();
	static JPanel noiseStatusPanel = new JPanel();
	static JPanel calibrationPanel = new JPanel();
	
	
	boolean noiseDetected = false;

	static String configFileName = "configNoiseGate.txt";

	
	public NoiseGateGUI() {
		
		//Erstelle den LoadingFrame, dieser wird zuerst angezeigt
		createLoadingFrame();
		createMainFrame();
		//mainFrame.setVisible(false);
		
		
		//Erstelle den Main Frame der Seite
		//createMainFrame();
		
		
	}
	
	
	//Hier wird eine Ladeframe erstellt
	public void createLoadingFrame() {
		
		//Hier wird der LoadingSpinner geladen
		ImageIcon loading = new ImageIcon("spinning-loading.gif");
		
		//loadingPanel.add(new JLabel("Anwendung wird geladen... ", loading, JLabel.CENTER));
		loadingPanel.add(new JLabel(loading));
		loadingPanel.setLayout(new GridLayout(1, 1));
		loadingFrame.setTitle("loading");
		loadingFrame.add(loadingPanel);
		loadingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loadingFrame.setSize(300, 300);
		loadingFrame.setVisible(true);
		loadingFrame.setResizable(false);
	}
	
	
	//In dieser Methode wird der mainFrame der Anwendung erstellt
	public void createMainFrame() {
		
		JButton startCalibration = new JButton("Start Calibration");
		startCalibration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hier neues Fenster aufrufen, welches den Nutzer dazu
				//In diesem wird auch die Kalibrierung aufgerufen
				createCalibrationFrame();
					
			}
		});
		
		JButton startLispDetection = new JButton("Start Noise Detection");
		startLispDetection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Erstelle den LispStatusFrame
				createLispStatusFrame();
				
			}
		});
		
		JButton stopLispDetection = new JButton("Stop Noise Detection");
		stopLispDetection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Stoppe die Lisp Detection
				audioAnalyzeLogic.stopAudioProcessing();
			}
		});		

		
		//mainPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));

		mainPanel.add(startLispDetection);
		mainPanel.add(stopLispDetection);
		mainPanel.add(startCalibration);
		
		
		mainPanel.setLayout(new GridLayout(2, 2));
		mainPanel.setPreferredSize(new Dimension(800, 500));
		mainPanel.setVisible(true);
		mainPanel.setBackground(Color.white);
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("Noise Detection");
		mainFrame.pack();
		mainFrame.setVisible(false);
		
		
	}
	
	
	public static void createLispStatusFrame() {
		
		EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
            	//Call Audio Processing
            	audioAnalyzeLogic.callAudioProcessing();
                
                noiseStatusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                /*try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }*/
            
                noiseStatusPanel.setSize(300, 300);
                
                //static Color red = black;
                noiseStatusPanel.setBackground(Color.green);
                
        		noiseStatusPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
        		noiseStatusPanel.setLayout(new GridLayout(0, 1));
                
        		noiseStatusFrame.add(noiseStatusPanel, BorderLayout.CENTER);
        		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		noiseStatusFrame.setTitle("Lisp Status");
        		noiseStatusFrame.pack();
        		noiseStatusFrame.setResizable(true);
        		noiseStatusFrame.setVisible(true);
        		
 
        		noiseStatusFrame.addWindowListener(new WindowAdapter(){
        	        public void windowClosing(WindowEvent e){
        	            //int i=JOptionPane.showConfirmDialog(null, "Seguro que quiere salir?");
        	            //if(i==0)
        	                //System.exit(0);//cierra aplicacion
        	        	noiseStatusFrame.dispose();
        	        	audioAnalyzeLogic.stopAudioProcessing();
        	        	
        	        }
        	    });
        		
                       }
        });
    }
		

	
	public static void createCalibrationFrame() {
		
		//Hier wird zunächst callCalibrate mit normalen Frequenzen aufgerufen
		//Hier mit dem Parameter override um die bestehende config-Datei zu
		//überschreiben
		audioAnalyzeLogic.callCalibrate("noisegate", "threshold", "override", configFileName);
	         	
        calibrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //Threshold muss überschritten werden
        JLabel sSound = new JLabel("Please make loud noise.");
     
        calibrationPanel.setSize(200, 200);
        calibrationPanel.setBackground(Color.white);
        
        calibrationPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        //calibrationPanel.setLayout(new GridLayout(3, 3));
        calibrationPanel.add(sSound);    
        calibrationFrame.add(calibrationPanel, BorderLayout.CENTER);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calibrationFrame.setTitle("Calibration");
		calibrationFrame.pack();
		calibrationFrame.setResizable(false);
		calibrationFrame.setVisible(true);
		
   
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
		
		//Wenn eine Meldung der AudioAnalyzeLogic kommt dann setze Hintergrundfarbe auf rot
		noiseStatusPanel.setBackground(Color.red);
		
		//Erstelle einen Timer
		//Dieser setzt die HIntergrundfarbe des LispStatusFrames nach 10 Sekunden
		//wieder auf grün
		Timer timer = new Timer();
		TimerTask setLispStatusToFalse = new TimerTask() {
			@Override
			public void run() {
                noiseStatusPanel.setBackground(Color.green);
			}
		};
		
		timer.schedule(setLispStatusToFalse, 10000);
	}


	
	
	@Override
	public void matlabEngineLoaded() {	
		
		//Wenn die MatlabEngineGeladen ist dann schließe den Ladeframe
		loadingFrame.setVisible(false);
		loadingFrame.dispose();
		loadingFrame = null;
		//Erstelle dann den MainFrame
		//Erstelle diesen schon ehr aber setzte hier nur ob dieser Sichtbar ist
		mainFrame.setVisible(true);
		
		
	}


	@Override
	public void matlabEngineLoading() {
		
		//Wenn die Matlab Engine läd dann schließe den Main Frame
		mainFrame.setVisible(false);
		//Öffene den Loading Frame
		//Der Loading Spinner lädt nicht neu -> keine Ahnung warum. Sollte eigentlich klappen
		loadingFrame = new JFrame();
		loadingPanel = new JPanel();
		createLoadingFrame();
		//loadingFrame.setVisible(true);
		
		
	}


	@Override
	public String getConfigFileName() {
		// TODO Auto-generated method stub
		return configFileName;
	}
	
}

package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;

import gui.LispDetectionScreen;

public class LispDetectionLogic {

	
	private static MatlabEngine eng;
	
	String mode = "";
	String normalFreqs = "";
	String lispFreqs = "";
	String restFreqs = "";
	
	boolean isRecording = false;

	
	boolean lispDetected = false;
	
	
	LispDetectionScreen lispDetectionScreen;
	
	public LispDetectionLogic() {
		
		//Bestehende Config Datei wird geladen
		loadConfigFile();


		System.out.println(mode);
		System.out.println(normalFreqs);
		System.out.println(lispFreqs);
		System.out.println(restFreqs);
	}
	
	
	//Lade bestehende Config Datei aus Filesystem
	public void loadConfigFile() {
		
		Properties p = new Properties();
		
		try {
			
			p.load(new FileInputStream("config.txt"));
			
			mode = p.getProperty("mode");
			normalFreqs = p.getProperty("normalFreqs");
			lispFreqs = p.getProperty("lispFreqs");
			restFreqs = p.getProperty("restFreqs");
			
			
			
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}		
	}
	
	
	public void startRecording() {
		
		
		MatlabThread matlabThread = new MatlabThread(mode, normalFreqs, lispFreqs, restFreqs);
		Thread thread = new Thread(matlabThread);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean value = matlabThread.getValue();
		
		System.out.println(value);
		
		//runMatlabLispDetection();
		
		//Erstelle neuen Thread damit die Methode im Hintergrund läuft
		/*new Thread(new Runnable() {
			
			public void run() {
				isRecording = true;
				
				while(isRecording == true) {
					System.out.println("wird aufgenommen");
				}
			}
		}).start();*/
		

		
	}
	
	public void stopRecording() {
		
		isRecording = false;
		try {
			//eng.disconnect();
			//Gewollt Exception auslösen um Programm zu beenden
			eng.quit();
			//eng.close();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setLispDetectionScreen(LispDetectionScreen _lispDetectionScreen) {
		
		lispDetectionScreen = _lispDetectionScreen;
		
	}
	
	public void lispDetected() {
				
		new Thread(new Runnable() {
			
			public void run() {
				
				try {
					
					eng = MatlabEngine.startMatlab();
					
					eng.eval("cd 'C:\\Users\\druep\\Documents\\Hochschule\\Master\\Sprachverständlichkeit\\lisp_detection\\src'");
					
					lispDetected = eng.feval("realTimeAudioProcessingFunction", mode, normalFreqs, lispFreqs, restFreqs);	
					
					
					if(lispDetected == true) {
						lispDetectionScreen.sendNotification();
						lispDetected();
					}
					
					//eng.feval("test", 7);
					
				} catch (EngineException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}	
		}).start();		

	}
	
	
	
	
	
	
	public void runMatlabLispDetection() {
		
		
		new Thread(new Runnable() {
			
			public void run() {
				
				try {
					
					eng = MatlabEngine.startMatlab();
					
					eng.eval("cd 'C:\\Users\\druep\\Documents\\Hochschule\\Master\\Sprachverständlichkeit\\lisp_detection\\src'");
					
					boolean lispDeteced = eng.feval("realTimeAudioProcessingFunction", mode, normalFreqs, lispFreqs, restFreqs);	
					
					System.out.println(lispDeteced);
				
				
					
					//eng.feval("test", 7);
					
				} catch (EngineException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}).start();
		
		
	
	}
}

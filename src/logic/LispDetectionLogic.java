package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;

public class LispDetectionLogic {

	
	private static MatlabEngine eng;
	
	String mode = "";
	String normalFreqs = "";
	String lispFreqs = "";
	String restFreqs = "";
	
	boolean isRecording = false;
	
	
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
		
		runMatlabLispDetection();
		
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
	}
	
	
	public void runMatlabLispDetection() {
		
		try {
			
			eng = MatlabEngine.startMatlab();
			
			eng.eval("cd 'C:\\Users\\druep\\Documents\\Hochschule\\Master\\Sprachverständlichkeit\\lisp_detection\\src'");
			
			eng.feval("realTimeAudioProcessingFunction", mode, normalFreqs, lispFreqs, restFreqs);
			
			
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
}

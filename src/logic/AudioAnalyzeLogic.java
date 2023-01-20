package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;

import gui.AudioAnalyzeGUI;

public class AudioAnalyzeLogic {

	
	private static MatlabEngine eng;
	
	String mode = "";
	String normalFreqs = "";
	String lispFreqs = "";
	String restFreqs = "";
	
	boolean isRecording = false;

	
	boolean lispDetected = false;
	
	
	AudioAnalyzeGUI audioAnalyzeGUI;
	
	
	List<String> paramsArrayList = new ArrayList<String>();
	String[] paramsArray;
	
	
	
	public AudioAnalyzeLogic(AudioAnalyzeGUI audioAnalyzeGUI) {
		
		this.audioAnalyzeGUI = audioAnalyzeGUI;
		
		//Bestehende Config Datei wird geladen
		loadConfigFile();


		/*System.out.println(mode);
		System.out.println(normalFreqs);
		System.out.println(lispFreqs);
		System.out.println(restFreqs);*/
	}
	
	
	//Lade bestehende Config Datei aus Filesystem
	public void loadConfigFile() {
		
		Properties p = new Properties();
		
		try {
			
			p.load(new FileInputStream("config.txt"));
			
			//Interation über alle Parameter die in der Config Datei gespeichert sind
			for(String key : p.stringPropertyNames()) {
				//Hole jeweils den Wert des Keys
				String value = p.getProperty(key);
				//Speichere Parameter in einer ArrayList
				//Da der Mode jedes mal gebraucht wird, wird dieser später seperat ausgelesen
				if(!key.equals("mode")) {
					paramsArrayList.add(value);
				}
				
			}
			
			//Da Matlab keine ArrayListen entgegen nimmt muss die Arraylist hier in eine normales Array umgewandelt werden
			paramsArray = paramsArrayList.toArray(new String[paramsArrayList.size()]);
			//Da der Mode immer der erste Parameter in der config Datei ist und dieser immmer
			//benötigt wird kann dieser hier direkt ausgelesen werden
			mode = p.getProperty("mode");
			
			/*p.load(new FileInputStream("config.txt"));
			
			p.forEach(null);
			
			*/
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
	

	
	public void stopAudioProcessing() {
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
	

	
	
	
	//Methode, welche Audio processing Script in Matlab aufruft
	public void callAudioProcessing() {
		
		//Hier wird ein neuer Thread erstellt, damit die Funktion noch abgebrochen werden kann
		new Thread(new Runnable() {
			
			public void run() {
				
				try {
				
				
					
					//Da die Matlab Engine einen absoluten Pfad entgegen nimmt wird hier der absolute Pfad es Projektes ermittelt
					Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
					String rootPath = root.toString();
					rootPath = rootPath + "\\src";
					
					//MatlabEngine wird gestartet
					eng = MatlabEngine.startMatlab();
					//Pfad zum Ordner im welchen das Script liegt
					eng.eval("cd " + rootPath);
					
					
					//Matlab Funktion wird aufgerufen
					lispDetected = eng.feval("realTimeAudioProcessingFunction", mode, paramsArray);	
		
					System.out.println(lispDetected);
					
					
					//Wenn Matlab true zurückgibt dann sende eine Notification an die GUI 
					if(lispDetected == true) {
						//sende Notification
						audioAnalyzeGUI.audioAnalyzeNotification();
						//Starte die AudioDetection neu
						callAudioProcessing();
					}
					
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

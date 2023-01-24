package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

import gui.AudioAnalyzeGUI;

public class AudioAnalyzeLogic {

	
	private static MatlabEngine eng;
	
	private static MatlabEngine eng2;
	
	Thread audioProcessingThread;
	
	String stopFunction = "false";
	
	String mode = "";
	String normalFreqs = "";
	String lispFreqs = "";
	String restFreqs = "";
	
	double count;
	
	
	AudioAnalyzeGUI audioAnalyzeGUI;
	
	
	List<String> paramsArrayList = new ArrayList<String>();
	String[] paramsArray;
	
	
	
	public AudioAnalyzeLogic(AudioAnalyzeGUI audioAnalyzeGUI) {
		
		this.audioAnalyzeGUI = audioAnalyzeGUI;
		
		try {			
			//Hier wird die Matlab Engine gestartet
			//So muss diese nur einmal gestartet werden
			//Nur einmal am Anfang muss etwas länger geladen werden
			eng = MatlabEngine.startMatlab();
			
			eng2 = MatlabEngine.startMatlab();
			
			//Da die Matlab Engine einen absoluten Pfad entgegen nimmt wird hier der absolute Pfad es Projektes ermittelt
			Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
			String rootPath = root.toString();
			rootPath = rootPath + "\\src";

			//Pfad zum Ordner im welchen das Script liegt
			eng.eval("cd " + rootPath);
			eng2.eval("cd " + rootPath);
			
			//Variable zum Abbruch der Matlab Funktion zum Matlab Workspace hinzufügen
			eng2.putVariable("stopFunction", stopFunction);
			
		} catch (EngineException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//ToDO
		//Hier die GUI von Ladescreen auf normalen umändern
		audioAnalyzeGUI.matlabEngineLoaded();
		
		
		
		
		//Bestehende Config Datei wird geladen
		loadConfigFile();


		/*System.out.println(mode);
		System.out.println(normalFreqs);
		System.out.println(lispFreqs);
		System.out.println(restFreqs);*/
		
		

		
	}
	
	
	//Methode um die Eigenschaften der Config Datei in richtiger Reihenfolge zu bekommen
	public Map<String, String> getOrderedProperties(InputStream in) throws IOException{
	    Map<String, String> mp = new LinkedHashMap<>();
	    (new Properties(){
	        public synchronized Object put(Object key, Object value) {
	            return mp.put((String) key, (String) value);
	        }
	    }).load(in);
	    return mp;
	}
	
	//Lade bestehende Config Datei aus Filesystem
	public void loadConfigFile() {
		
		//Properties p = new Properties();
		
		try {
			
			Map<String,String> properties = getOrderedProperties(new FileInputStream("config.txt"));
			
			//Hier wird einer abgezogen, da der mode in einer extra variable gespeichert wird
			paramsArray = new String[properties.size() - 1];
			
			//Properties aus Datei werden gelesen und in Array überführt
			//Matlab nimmt keine Map an deshalb müssen die Werte in ein Array gespeichert werden
			int i = 0;
			for (Map.Entry<String, String> entry : properties.entrySet()) {
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			    if(!entry.getKey().equals("mode")) {
				    paramsArray[i] = entry.getValue();
				    i = i + 1;	
			    }
			    else {
			    	mode = entry.getValue();
			    }

			    //paramsArrayList.add(entry.getValue());
			}
			
			//Da der Mode immmer benötigt wird und immer an erster Stelle der Parameter stehen soll wird
			//dieser hier extra ausgelesen und übergeben
			//mode = paramsArray[0];
			
			
			
			/*p.load(new FileInputStream("config.txt"));
			
			//Interation über alle Parameter die in der Config Datei gespeichert sind
			for(String key : p.stringPropertyNames()) {
				System.out.println(key);
				//Hole jeweils den Wert des Keys
				String value = p.getProperty(key);
				//Speichere Parameter in einer ArrayList
				//Da der Mode jedes mal gebraucht wird, wird dieser später seperat ausgelesen
				if(!key.equals("mode")) {
					paramsArrayList.add(value);
				}
				
			}
			
			System.out.println("Parmas Array Liste");
			for (int i = 0; i < paramsArrayList.size(); i++) {
				System.out.println(paramsArrayList.get(i));
			}
			
			
			//Da Matlab keine ArrayListen entgegen nimmt muss die Arraylist hier in eine normales Array umgewandelt werden
			paramsArray = paramsArrayList.toArray(new String[paramsArrayList.size()]);
			//Da der Mode immer der erste Parameter in der config Datei ist und dieser immmer
			//benötigt wird kann dieser hier direkt ausgelesen werden
			mode = p.getProperty("mode");
			*/
			/*p.load(new FileInputStream("config.txt"));
			
			p.forEach(null);
			
			*/
			/*mode = p.getProperty("mode");
			normalFreqs = p.getProperty("normalFreqs");
			lispFreqs = p.getProperty("lispFreqs");
			restFreqs = p.getProperty("restFreqs");*/
			
			
			
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
			stopFunction = "true";
			eng2.putVariable("stopFunction", stopFunction);
			eng2.eval("test");
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
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
		
		/*try {
			//eng.disconnect();
			//Gewollt Exception auslösen um Programm zu beenden
			//eng.disconnect();
			//eng.close();
			//eng.close();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	

	
	
	
	//Methode, welche Audio processing Script in Matlab aufruft
	public void callAudioProcessing() {
		

		//Hier wird ein neuer Thread erstellt, damit die Funktion noch abgebrochen werden kann
		audioProcessingThread = new Thread(new Runnable() {
			
			public void run() {
				
				try {
					
					//Diese Funktion wird dazu genutzt ein Signal zu geben wann Matlab geladen ist
					//Eine Sekunden später wird dann auch realTimeAudioProcessing geladen sein
					/*boolean isReady = eng.feval("isReady");	
					
					if(isReady == true) {
						audioAnalyzeGUI.matlabEngineLoaded();
					}*/
					
					
					//Matlab Funktion wird aufgerufen
					count = eng.feval("realTimeAudioProcessingFunction", mode, paramsArray);	
					
					
					//Wenn Matlab einen Wert zurückgegeben hat dann sende eine Notification an die GUI 
					if(count > 0) {
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
			
		});
		audioProcessingThread.start();

	}	
	
	
	
	public void callCalibrate() {
		
		
		try {
			
			//Matlab Funktion wird aufgerufen
			Object params = eng.feval("callCalibrate", mode);
			System.out.println(params.toString());
			
		} catch (RejectedExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
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

package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
	
	//private static MatlabEngine eng2;
	
	Thread audioProcessingThread;
	Thread calibrationThread;
	
	String stopFunction = "false";
	
	String mode = "";
	String normalFreqs = "";
	String lispFreqs = "";
	String restFreqs = "";
	
	double count;
	
	boolean matlabEngineLoaded = false;
	
	
	AudioAnalyzeGUI audioAnalyzeGUI;
	
	
	List<String> paramsArrayList = new ArrayList<String>();
	String[] paramsArray;
	
	
	
	public AudioAnalyzeLogic(AudioAnalyzeGUI audioAnalyzeGUI) throws IOException {
		
		this.audioAnalyzeGUI = audioAnalyzeGUI;
		
		try {			
			//Hier wird die Matlab Engine gestartet
			//So muss diese nur einmal gestartet werden
			//Nur einmal am Anfang muss etwas länger geladen werden
			eng = MatlabEngine.startMatlab();
			
			//eng2 = MatlabEngine.startMatlab();
			
			//Da die Matlab Engine einen absoluten Pfad entgegen nimmt wird hier der absolute Pfad es Projektes ermittelt
			Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
			String rootPath = root.toString();
			rootPath = rootPath + "\\src";

			//Pfad zum Ordner im welchen das Script liegt
			eng.eval("cd " + rootPath);
			//eng2.eval("cd " + rootPath);
			
			//Variable zum Abbruch der Matlab Funktion zum Matlab Workspace hinzufügen
			//eng2.putVariable("stopFunction", stopFunction);
			
			audioAnalyzeGUI.matlabEngineLoaded();
			
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
	public void loadConfigFile() throws IOException{
		
		//Properties p = new Properties();
		
			
		Map<String,String> properties = getOrderedProperties(new FileInputStream(audioAnalyzeGUI.getConfigFileName()));
		
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
			
	
	}
	

	
	
	
	
	
	
	
	
	public void stopAudioProcessing() {
		
		//Hier wird ein neuer Thread erstellt, damit die Funktion noch abgebrochen werden kann
		audioProcessingThread = new Thread(new Runnable() {
			
		public void run() {
		
			try {
				eng.close();
				audioAnalyzeGUI.matlabEngineLoading();
				//Immer wenn die Matlab Engine hier neugestartet wird dann zeige Loading Screen
				eng = MatlabEngine.startMatlab();
				//Da die Matlab Engine einen absoluten Pfad entgegen nimmt wird hier der absolute Pfad es Projektes ermittelt
				Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
				String rootPath = root.toString();
				rootPath = rootPath + "\\src";
	
				//Pfad zum Ordner im welchen das Script liegt
				eng.eval("cd " + rootPath);
				
				//Wenn Matlab Engine fertig geladen ist dann starte main Frame über matlabEngineLoaded
				audioAnalyzeGUI.matlabEngineLoaded();
				
			} catch (CancellationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EngineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		}
		
		});
		audioProcessingThread.start();
		
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
	
	
	//writeMode: soll die bestehende Datei überschrieben werden oder sollen
	//Werte angehangen werden
	public void callCalibrate(String mode, String property, String writeMode, String configFileName) {
		
		
		//Hier wird ein neuer Thread erstellt, damit die Funktion noch abgebrochen werden kann
		calibrationThread = new Thread(new Runnable() {
			
			public void run() {
				
				try {
					
					//Matlab Funktion wird aufgerufen
					//Double property_values_1 = eng.feval("callCalibrate", mode, property);
					//System.out.println("Values 1 " + property_values_1);
					
					//Varibale um nachher zu vergleichen ob es ein Double oder double Array ist
					Double doubleD = 0.0;
					double[] property_values;
					
					Object property_values_object = eng.feval("callCalibrate", mode, property);
					System.out.println(property_values_object);
					
					if(property_values_object.getClass().equals(doubleD.getClass())) {
						property_values = new double[1];
						property_values[0] = (double) property_values_object;
						System.out.println(property_values[0]);
					}
					else {
						property_values = (double[]) property_values_object;
					}
					
					
					//System.out.println(property_values2.getClass().equals(doubleArray.getClass()));
					//System.out.println(property_values2.getClass().equals(doubleD.getClass()));
					
					//double[] property_values = eng.feval("callCalibrate", mode, property);

					
					try {
						//Fall falls die Datei überschrieben werden soll
						if(writeMode.equals("overwrite")) {
							PrintWriter writer = new PrintWriter(configFileName, "UTF-8");
							
							//Mode kann hier manuell gesetzt werden, da mode immer die
							//erste Eigenschaft in der Config Datei ist
							writer.println("mode = " + mode);
							
							//Hier wird der andere Teil des Parameters geschrieben
							writer.print(property + " = ");
							//Durchlaufe alle Eigenschaften, welche von Matlab zurückgegeben
							//wurde und schreibe diese hinter den Parameter
							for(int i = 0; i < property_values.length; i++) {
								if(i != property_values.length - 1) {
									writer.print((double)property_values[i] + ", ");
								}
								else {
									writer.print((double)property_values[i]);
								}
								
								
							}
							
							writer.close();
						}
						else if(writeMode.equals("append")) {
							//Fall wenn an die Config Datei angehangen werden soll
							FileWriter fileWriter = new FileWriter(configFileName, true);
							PrintWriter writer = new PrintWriter(fileWriter);
							
							writer.println("");
							//Hier wird der erste Teil (Key) des Parameters geschrieben
							writer.print(property + " = ");
							//Durchlaufe alle Eigenschaften, welche von Matlab zurückgegeben
							//wurde und schreibe diese hinter den Parameter
							for(int i = 0; i < property_values.length; i++) {
								if(i != property_values.length - 1) {
									writer.print((double)property_values[i] + ", ");
								}
								else {
									writer.print((double)property_values[i]);
								}
								
								
							}
							
							writer.close();
						}
						
						//Hier muss die gerade erstellte Config Datei dann geladen werden
						loadConfigFile();
						

					} catch (FileNotFoundException ex) {
					    // file does not exist
					} catch (IOException ex) {
					    // I/O error
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
		calibrationThread.start();
	}

}

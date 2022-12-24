package data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;



public class MicData {
	
	
	private static MatlabEngine eng;
	
	/*public static void main(String[] args) {
		
		Properties p = new Properties();
		
		String mode = "";
		String normalFreqs = "";
		String lispFreqs = "";
		String restFreqs = "";
		
		
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
		
		
		try {
			
			eng = MatlabEngine.startMatlab();
			try {
				
				eng.putVariable("z", 100);
				
				eng.eval("x = 2;");
				eng.eval("y = 3;");

				eng.eval("cd 'C:\\Users\\druep\\Documents\\Hochschule\\Master\\Sprachverständlichkeit\\lisp_detection\\src'");
				
				//eng.eval("test(7)");
				  
				//String huhu = eng.feval("realTimeAudioProcessingFunction").toString();
				
				eng.feval("realTimeAudioProcessingFunction", mode, normalFreqs, lispFreqs, restFreqs);
				
				//eng.eval("realTimeAudioProcessingFunction()");
				
				//System.out.println("Ergebnis: " + huhu);
				
				eng.eval("disp(z);");
				
				//double myVar = eng.getVariable("z");
				//System.out.println(myVar);
				
				Thread.sleep(5000);
				
				
				//Solange nicht gelispelt wird Schleife in Matlab ausführen, wenn gelispelt wird Schleife abbrechen und Rückgabe an Java -> Script neu starten
				
				
				
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
		}
		
		try
		{
		
		*/
			/*AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			
			DataLine.Info dataInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			final SourceDataLine sourceLine = (SourceDataLine)AudioSystem.getLine(dataInfo);
			sourceLine.open();
			
			dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			final TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(dataInfo);
			targetLine.open();			
			
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			
			Thread sourceThread = new Thread() {
				
				@Override public void run() {
					sourceLine.start();
					
					while(true) {
						sourceLine.write(out.toByteArray(), 0, out.toByteArray().length);
						//System.out.println(out.toByteArray().length);
					}
					
						
				}
				
			};
			
			Thread targetThread = new Thread() {
				
				@Override public void run() {
					targetLine.start();
					byte[] data = new byte[targetLine.getBufferSize()];
					int readBytes;
					
					while(true) {
						readBytes = targetLine.read(data, 0, data.length);
						out.write(data, 0, readBytes);
					}
						
				}
				
			};*/			
			
			/*targetThread.start();
			System.out.println("Recording Start");	
			sourceThread.start();
			System.out.println("Playback start");
			Thread.sleep(5000);
			
			
			
			targetLine.stop();
			targetLine.close();
			System.out.println("Recording Stop");
			

			
			sourceLine.stop();
			sourceLine.close();
			System.out.println("Playback stop");	*/		
			
			
			//Man könnte immer 5 Sekunden aufnehmen dann analysieren
			
			
			/*targetThread.start();
			System.out.println("Recording Start");		
			Thread.sleep(5000);
			targetLine.stop();
			targetLine.close();
			System.out.println("Recording Stop");
			
			sourceThread.start();
			System.out.println("Playback start");
			Thread.sleep(5000);
			sourceLine.stop();
			sourceLine.close();
			System.out.println("Playback stop");
			System.out.println(out.toByteArray());
			
			
			for(int i = 0; i < 20; i++) {
				System.out.println(out.toByteArray()[i]);
			}*/
			
			//JOptionPane.showMessageDialog(null, "Sie lieben sich zu " +sum + "%!", "Beweis", JOptionPane.PLAIN_MESSAGE);
			
			
		/*}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}*/
	
}

package logic;

import java.util.concurrent.ExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

public class MatlabThread implements Runnable{

	boolean lispDeteced;
	private static MatlabEngine eng;
	
	String mode = "";
	String normalFreqs = "";
	String lispFreqs = "";
	String restFreqs = "";
	
	public MatlabThread(String _mode, String _normalFreqs, String _lispFreqs, String _restFreqs) {
		mode = _mode;
		normalFreqs = _normalFreqs;
		lispFreqs = _lispFreqs;
		restFreqs = _restFreqs;
	}
	
	@Override
	public void run() {
		
		try {
			
			eng = MatlabEngine.startMatlab();
			
			eng.eval("cd 'C:\\Users\\druep\\Documents\\Hochschule\\Master\\Sprachverständlichkeit\\lisp_detection\\src'");
			
			lispDeteced = eng.feval("realTimeAudioProcessingFunction", mode, normalFreqs, lispFreqs, restFreqs);	
			
		} catch (EngineException | IllegalArgumentException | IllegalStateException | InterruptedException e) {
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
	
	
	public boolean getValue() {
		return lispDeteced;
	}

}

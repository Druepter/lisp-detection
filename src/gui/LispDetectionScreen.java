package gui;

import controlP5.ControlP5;
import processing.core.PApplet;

public class LispDetectionScreen extends PApplet{

	public ControlP5 cp5;
	
	
	public void settings() {
		
		
		
		cp5 = new ControlP5(this);
		
		
		size(500, 500);
		
		
	    /*cp5.addLabel("Boids:")
    	.setPosition(50, 25)
    	.setColor(0);*/
	}
	
	public void draw() {
		ellipse(mouseX, mouseY, 50, 50);
	}
	
	public static void main(String[] args) {
		
		String[] processingArgs = {"LispDetection"};
		LispDetectionScreen lispDetectionScreen = new LispDetectionScreen();
		PApplet.runSketch(processingArgs, lispDetectionScreen);
		
	}
	
}

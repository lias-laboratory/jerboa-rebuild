package fr.ensma.lias.jerboa;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.ensma.lias.jerboa.embeddings.Points;

public class Vectors {
	private float x;
	private float y;
	private float z;
	
	public String toString() { 
	    return " Vector: X: " + this.x + ", Y: " + this.y + ", Z: " + this.z;
	} 
	
	public Vectors(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vectors(double x, double y, double z) {
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
	}
	
	public Vectors(Vectors ebd) {
		this.x = ebd.x;
		this.y = ebd.y;
		this.z = ebd.z;
	}
	
	public Vectors (Points pt1, Points pt2) {
		this.x = pt2.getX()-pt1.getX();
		this.y = pt2.getY()-pt1.getY();
		this.z = pt2.getZ()-pt1.getZ();
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	public void reverse() {
		this.x = 0-this.x;
		this.y = 0-this.y;
		this.z = 0-this.z;
	}

	public static Vectors askForVector() {
		JFrame floatFrame = new JFrame();
		floatFrame.setLocationRelativeTo(null);
		float a = Float.parseFloat(JOptionPane.showInputDialog(floatFrame, "X coordinate", "0"));
		float b = Float.parseFloat(JOptionPane.showInputDialog(floatFrame, "Y coordinate", "0"));
		float c = Float.parseFloat(JOptionPane.showInputDialog(floatFrame, "Z coordinate", "0"));
		Vectors res = new Vectors(a, b, c);
		return res;
	}
	
}

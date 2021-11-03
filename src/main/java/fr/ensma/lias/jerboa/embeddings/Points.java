package fr.ensma.lias.jerboa.embeddings;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.ensma.lias.jerboa.embeddings.Vec3;

public class Points {
	private float x;
	private float y;
	private float z;
	
	public String toString() { 
	    return "X: " + this.x + ", Y: " + this.y + ", Z: " + this.z;
	} 
	
	public Points() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Points(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Points(double x, double y, double z) {
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
	}

	public Points(Points ebd) {
		this.x = ebd.x;
		this.y = ebd.y;
		this.z = ebd.z;
	}
	
	public Points(Points pt, Vec3 vect) {
		this.x = pt.x + vect.getX();
		this.y = pt.y + vect.getY();
		this.z = pt.z + vect.getZ();
	}
	
	public Points(java.util.List<Points> list) {
		float x = 0;
		float y = 0;
		float z = 0;
		float count = 0;
		for (Points pt : list) {
			x += pt.x;
			y += pt.y;
			z += pt.z;
			count ++;
		}
		x = x/count;
		y = y/count;
		z = z/count;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void centerPoint(java.util.List<Points> list) {
		System.out.println(list);
		this.x = 0;
		this.y = 0;
		this.z = 0;
		float count = 0;
		for (Points pt : list) {
			this.x += pt.x;
			this.y += pt.y;
			this.z += pt.z;
			count ++;
		}
		this.x = x/count;
		this.y = y/count;
		this.z = z/count;
	}
	
	public void centerPoint(Points pt1, Points pt2) {
		this.x = (pt1.x + pt2.x)/2;
		this.y = (pt1.y + pt2.y)/2;
		this.z = (pt1.z + pt2.z)/2;
	}
	
	public void distFromCorner(java.util.List<Points> list, Points b, double d) {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		float times = 0;
		for (Points pt : list) {
			this.x += pt.x;
			this.y += pt.y;
			this.z += pt.z;
			times ++;
		}
		this.x = x/times;
		this.y = y/times;
		this.z = z/times;
		
		this.x += d * b.getX();
		this.y += d * b.getY();
		this.z += d * b.getZ();
		
		this.x = (float) (x/(d+1));
		this.y = (float) (y/(d+1));
		this.z = (float) (z/(d+1));
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

	public void add(Vec3 ebd) {
		this.x += ebd.getX();
		this.y += ebd.getY();
		this.z += ebd.getZ();
	}
	
	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void movePoint(Points pt, Vec3 vect) {
		pt.x = pt.x + vect.getX();
		pt.y = pt.y + vect.getY();
		pt.z = pt.z + vect.getZ();
	}
	
	public float halfWay(float a, float b) {
		return b-((a-b)/2);
	}
	
	public static Points askForCoords() {
		JFrame floatFrame = new JFrame();
		floatFrame.setLocationRelativeTo(null);
		float a = Float.parseFloat(JOptionPane.showInputDialog(floatFrame, "X coordinate", "0"));
		float b = Float.parseFloat(JOptionPane.showInputDialog(floatFrame, "Y coordinate", "0"));
		float c = Float.parseFloat(JOptionPane.showInputDialog(floatFrame, "Z coordinate", "0"));
		Points res = new Points(a, b, c);
		return res;
	}

}

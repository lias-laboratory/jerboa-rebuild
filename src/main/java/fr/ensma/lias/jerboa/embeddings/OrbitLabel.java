package fr.ensma.lias.jerboa.embeddings;

public class OrbitLabel {
	
	private int label;
	private static int counter = 0;
	
	public OrbitLabel() {
		this.label = counter;
		counter+=1;
	}
	
	public int getLabel() {
		return this.label;
	}
	
}

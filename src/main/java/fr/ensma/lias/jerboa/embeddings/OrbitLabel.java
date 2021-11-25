package fr.ensma.lias.jerboa.embeddings;

public class OrbitLabel {

	private Integer label;
	private static int counter = 0;

	@Override
	public String toString() {
		return label.toString();
	}

	public OrbitLabel() {
		this.label = counter;
		counter += 1;
	}

	public Integer getLabel() {
		return this.label;
	}

}

package fr.ensma.lias.jerboa.embeddings;

public class OrbitLabel {

	private Integer label;
	private static int counter = 0;

	public OrbitLabel() {
		this.label = counter;
		counter += 1;
	}

	public Integer getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(label.toString());
		return sb.toString();
	}

}

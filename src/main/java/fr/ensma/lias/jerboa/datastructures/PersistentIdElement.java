package fr.ensma.lias.jerboa.datastructures;

/*
 * A PIElement is an element of a PersistentID. It holds two references through an int and a string.
 * the field appNumber is the index of the applied operation in a ParametricSpecification and
 * nodeName is the name of the ruleNode filtering the JerboaDart in the GMAP at the moment of the
 * application.
 */
public class PersistentIdElement {
	private int appNumber;
	private String nodeName;

	public PersistentIdElement() {}

	public PersistentIdElement(int appNumber, String nodeName) {
		this.appNumber = appNumber;
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public int getAppNumber() {
		return appNumber;
	}

	@Override
	public String toString() {
		return getAppNumber() + " - " + getNodeName();
	}
}

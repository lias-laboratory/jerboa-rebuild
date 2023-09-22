package fr.ensma.lias.jerboa.datastructures;

/**
 * Link
 */
public class Link {

	LinkType type;
	NodeEvent child;

	public Link(LinkType type, NodeEvent child) {
		this.type = type;
		this.child = child;
	}

	public LinkType getType() {
		return type;
	}

	public void setType(LinkType type) {
		this.type = type;
	}

	public NodeEvent getChild() {
		return child;
	}

	public void setChild(NodeEvent child) {
		this.child = child;
	}



	@Override
	public String toString() {
		return "[type: " + type.toString() + "; child: " + child.toString() + "]\n";
	}

	@Deprecated
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Link) {
			Link l = (Link) obj;
			return type == l.type && child.equals(l.child);
		}
		return super.equals(obj);
	}



}

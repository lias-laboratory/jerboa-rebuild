package fr.ensma.lias.jerboa.datastructures;

/**
 * NodeEventHR
 */
public class NodeEventHR {

	Event event;
	NodeOrbitHR child;

	public NodeEventHR(Event event) {
		this.event = event;
	}

	public NodeEventHR(Event event, NodeOrbitHR child) {
		this.event = event;
		this.child = child;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public NodeOrbitHR getChild() {
		return child;
	}

	public void setChild(NodeOrbitHR child) {
		this.child = child;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[type:" + event.toString());
		if (child != null)
			sb.append("; child: " + child.toString());
		sb.append("]");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NodeEventHR) {
			NodeEventHR ne = (NodeEventHR) obj;
			return event == ne.event;
		}
		return super.equals(obj);
	}


}

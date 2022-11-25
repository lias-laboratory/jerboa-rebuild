package fr.ensma.lias.jerboa.datastructures;

/**
 * NodeEventHR
 */
public class NodeEvent {

	private Event event;
	private NodeOrbit child;

	public NodeEvent(Event event) {
		this.event = event;
	}

	public NodeEvent(Event event, NodeOrbit child) {
		this.event = event;
		this.child = child;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public NodeOrbit getChild() {
		return child;
	}

	public void setChild(NodeOrbit child) {
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
		if (obj instanceof NodeEvent) {
			NodeEvent ne = (NodeEvent) obj;
			return event == ne.event;
		}
		return super.equals(obj);
	}


}

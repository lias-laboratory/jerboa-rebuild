package fr.ensma.lias.jerboa.datastructures;

import java.util.List;

public abstract class LevelEvent {

	protected int appNumber;
	protected ApplicationType appType;
	protected List<NodeEvent> eventList;
	// protected LevelOrbitType nextLevelOrbit;

	public int getAppNumber() {
		return appNumber;
	}

	public List<NodeEvent> getEventList() {
		return eventList;
	}

	public void addEvent(NodeEvent nodeEvent) {
		eventList.add(nodeEvent);
	}

	// public LevelOrbitType getNextLevelOrbit() {
	// return nextLevelOrbit;
	// }

	// @Override
	// public String toString() {
	// return "appNumber " + appNumber + " | " + eventList + " --> " + nextLevelOrbit;
	// }

}

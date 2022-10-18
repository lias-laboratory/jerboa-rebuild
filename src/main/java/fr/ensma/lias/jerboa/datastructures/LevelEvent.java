package fr.ensma.lias.jerboa.datastructures;

import java.util.List;

public abstract class LevelEvent<NodeEventType, LevelOrbitType> {

	public int appNumber;
	public List<NodeEventType> eventList;
	public LevelOrbitType nextLevelOrbit;

	public int getAppNumber() {
		return appNumber;
	}

	public List<NodeEventType> getEventList() {
		return eventList;
	}

	public void addEvent(NodeEventType nodeEvent) {
		eventList.add(nodeEvent);
	}

	public LevelOrbitType getNextLevelOrbit() {
		return nextLevelOrbit;
	}

	@Override
	public String toString() {
		return "appNumber " + appNumber + " | " + eventList + " --> " + nextLevelOrbit;
	}

}

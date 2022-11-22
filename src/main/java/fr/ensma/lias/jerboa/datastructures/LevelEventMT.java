package fr.ensma.lias.jerboa.datastructures;

import java.util.List;

/**
 * LevelEventMT
 */
public class LevelEventMT {

	private int appNumber;
	private ApplicationType appType;
	private List<NodeEventHR> eventList;
	private LevelOrbitMT nextLevelOrbit;

	public LevelEventMT(List<NodeEventHR> eventList, ApplicationType appType) {
		this.eventList = eventList;
		this.appType = appType;
	}

	public LevelEventMT(int appNumber, List<NodeEventHR> eventList, ApplicationType appType) {
		this.appNumber = appNumber;
		this.appType = appType;
		this.eventList = eventList;
	}

	public LevelEventMT() {}

	public List<NodeEventHR> getEventList() {
		return eventList;
	}

	public LevelOrbitMT getNextLevelOrbit() {
		return nextLevelOrbit;
	}

	public void setNextLevelOrbit(LevelOrbitMT nextLevelOrbit) {
		this.nextLevelOrbit = nextLevelOrbit;
	}

	@Override
	public String toString() {
		return "appNumber " + appNumber + " | " + eventList + " --> " + nextLevelOrbit;
	}

}

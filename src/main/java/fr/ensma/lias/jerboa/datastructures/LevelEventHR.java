package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * LevelEventHR
 */
public class LevelEventHR extends LevelEvent implements Iterable<LevelEventHR> {

	// private int appNumber;
	// private ApplicationType appType;
	// private List<NodeEvent> eventList;
	private LevelOrbitHR nextLevelOrbit;

	public LevelEventHR() {
		eventList = new ArrayList<>();
	}

	public LevelEventHR(int appNumber) {
		eventList = new ArrayList<>();
		this.appNumber = appNumber;
	}

	public LevelEventHR(int appID, LevelOrbitHR nextLevelOrbit) {
		this.appNumber = appID;
		eventList = new ArrayList<>();
		this.nextLevelOrbit = nextLevelOrbit;
	}

	public int getAppNumber() {
		return appNumber;
	}

	public LevelOrbitHR getNextLevelOrbit() {
		return nextLevelOrbit;
	}

	public List<NodeEvent> getEventList() {
		return eventList;
	}

	public void addEvent(NodeEvent event) {
		this.eventList.add(event);
	}

	@Override
	public String toString() {
		return "appNumber " + appNumber + " | " + eventList + " --> " + nextLevelOrbit;
	}

	// public void setEventList(List<NodeEventHR> eventList) {
	// this.eventList = eventList;
	// }

	@Override
	public Iterator<LevelEventHR> iterator() {
		return new LevelEventHRIterator(this);
	}
	//
	// public LevelEventHR(int appID, List<NodeEventHR> eventList) {
	// this.appNumber = appID;
	// this.eventList = eventList;
	// }

	// public LevelEventHR(LevelEventHR levelEvent) {
	// this.appNumber = levelEvent.getAppNumber();
	// this.eventList = levelEvent.getEventList();
	// this.nextLevelOrbit = levelEvent.getNextLevelOrbitHR();
	// }

}

package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * LevelOrbitHR
 */
public class LevelOrbitHR {

	private String nodeName;
	private List<NodeOrbitHR> orbitList;
	private List<LevelEventHR> nextLevelEvents;
	// private List<Integer> nextLevelEventsAppNumbers;

	public LevelOrbitHR(String nodeName, NodeOrbitHR nodeOrbit) {
		this.nodeName = nodeName;
		orbitList = new ArrayList<>();
		orbitList.add(nodeOrbit);
	}

	public LevelOrbitHR(String nodeName, List<NodeOrbitHR> orbitList,
			List<LevelEventHR> nextLevelEvents) {

		this.nodeName = nodeName;
		this.orbitList = orbitList;
		// this.nextLevelEventsAppNumbers = new ArrayList<>();
		this.nextLevelEvents = nextLevelEvents;
	}

	public String getNodeName() {
		return nodeName;
	}

	public List<NodeOrbitHR> getOrbitList() {
		return orbitList;
	}

	public List<LevelEventHR> getNextLevelEvents() {
		return nextLevelEvents;
	}

	public LevelEventHR getNextLevelEventAtIndex(int index) {
		return nextLevelEvents.get(index);
	}

	// public void mergeNodesFromList(List<NodeOrbitHR> nodeOrbitList) {
	// // impl: merge a list of NodeOrbitHR into current LevelOrbitHR
	// }

	@Override
	public String toString() {
		return "nodeName " + nodeName + " | " + orbitList;
	}

	// public void setOrbitList(List<NodeOrbitHR> orbitList) {
	// this.orbitList = orbitList;
	// }

}

package fr.ensma.lias.jerboa.datastructures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;



/* HistoryRecord data structure used to record the evolution of a construction */

// public class HistoryRecord {

// private LevelEventHR leaf;
// private List<LevelEventHR> leaves;

// public HistoryRecord() {
// this.leaves = new ArrayList<>();
// }

// public HistoryRecord(PersistentID pId, JerboaOrbit orbitType,
// ParametricSpecifications parametricSpecification) {

// // initialize leaves
// leaves = new ArrayList<LevelEventHR>();
// leaves.add(leaf);
// // initialize nodeOrbitList
// List<NodeOrbitHR> nodeOrbitList = new ArrayList<>();
// nodeOrbitList.add(new NodeOrbitHR(orbitType));

// List<PersistentIdElement> pIdElements = pId.getPIdElements();

// for (int index = pIdElements.size() - 1; index >= 0; index--) {
// nodeOrbitList =
// addLevel(nodeOrbitList, pIdElements.get(index), parametricSpecification);
// }
// }

// public LevelEventHR getLeaf(int index) {
// return leaves.get(index);
// }

// public List<LevelEventHR> getLeaves() {
// return leaves;
// }

// // public void setLevelEventLeaves(List<LevelEventHR> levelEventLeaves) {
// // this.levelEventLeaves = levelEventLeaves;
// // }

// /**
// * Create new empty LevelEvent and LevelOrbit containing nodeOrbitList, starting from pId's last
// * persistent Id element and given list of NodeOrbitHR
// *
// * @param nodeOrbitList current list of NodeOrbitHR
// *
// * @param pId current PersistentID
// *
// * @param parametricSpecification given parametric specification
// *
// * @return next step's nodeOrbitList
// */
// private List<NodeOrbitHR> addLevel(List<NodeOrbitHR> nodeOrbitList,
// PersistentIdElement pIdElement, ParametricSpecifications parameSpec) {

// List<NodeOrbitHR> nextStepOrbitHRs = new ArrayList<>();
// JerboaRuleOperation currentRule =
// parameSpec.getSpecEntry(pIdElement.getAppNumber()).getRule();

// // create empty level orbit
// LevelOrbitHR levelOrbit =
// new LevelOrbitHR(pIdElement.getNodeName(), nodeOrbitList, Arrays.asList(leaf));
// // create empty level event with last pIdElement appID
// LevelEventHR levelEvent = new LevelEventHR(pIdElement.getAppNumber(), levelOrbit);
// leaf = levelEvent;

// for (NodeOrbitHR currentNodeOrbit : nodeOrbitList) {
// fillLevel(currentNodeOrbit, levelEvent, nextStepOrbitHRs, pIdElement.getNodeName(),
// currentRule);
// }

// // On some condition leaf maybe be a distinct leaf from the last one in leaves
// // then add leaf to leaves
// leaves.set(leaves.size() - 1, leaf);

// return nextStepOrbitHRs;
// }



// /**
// * Fill current level event and current node orbit list with current node orbit trace for a
// * given rule
// *
// * @param currentNodeOrbit traced orbit
// *
// * @param levelEvent levelEvent to fill
// *
// * @param nodeOrbitList node orbit list to fill
// *
// * @param nodeName right node name in a given rule from which the orbit is traced
// *
// * @param rule given rule
// *
// */
// private void fillLevel(NodeOrbitHR currentNodeOrbit, LevelEventHR levelEvent,
// List<NodeOrbitHR> nextStepOrbitHRs, String nodeName, JerboaRuleOperation rule) {
// // TODO impl: method to compute one building board rule application
// // Adding currentNodeOrbit because of ORIGIN nodes which are not taken from the
// // rule input
// currentNodeOrbit.BBBuildEntry(nodeName, rule, levelEvent, nextStepOrbitHRs);
// // System.out.println("nextStepOrbitHRs insidefillLevel " + nextStepOrbitHRs);
// // TODO impl: merge next orbit nodes in current nodeOrbitList
// // levelOrbit.mergeNodesFromList(nodeOrbitList);
// }

// /**
// * Draw a visual representation for a HistoryRecord
// */
// @Override
// public String toString() {
// StringBuilder sb = new StringBuilder();
// for (LevelEventHR leaf : leaves) {
// for (LevelEventHR level : leaf) {
// sb.append(level.getEventList()).append("\n");
// sb.append(level.getNextLevelOrbitHR().getOrbitList()).append("\n");
// }
// System.out.println("");
// }
// return sb.toString();
// }

// public void export(String fileName) {
// // List<LevelEventHR> levels = new ArrayList<>();
// // for (LevelEventHR level : this) {
// // levels.add(level);
// // }

// try

// {
// JSONPrinter.exportHistoryRecord(leaves, fileName);
// } catch (IOException exception) {
// System.out.println("Could not write to file");
// }

// }

// }

public class HistoryRecord {

	HashMap<Integer, List<LevelEventHR>> leaves;

	public HistoryRecord() {
		leaves = new LinkedHashMap<>();
	}

	public HistoryRecord(PersistentID pID, JerboaOrbit orbitType,
			ParametricSpecifications parametricSpecification) {
		leaves = new LinkedHashMap<>();

		List<NodeOrbitHR> nodeOrbitList = new ArrayList<>();
		nodeOrbitList.add(new NodeOrbitHR(orbitType));

		List<PersistentIdElement> pIDElements = pID.getPIdElements();
		for (int index = pIDElements.size() - 1; index >= 0; index--) {
			nodeOrbitList =
					addLevel(nodeOrbitList, pIDElements.get(index), parametricSpecification);
		}
	}

	public HashMap<Integer, List<LevelEventHR>> getLeaves() {
		return leaves;
	}

	private List<LevelEventHR> getTopLevelEvents() {
		List<Integer> entries = new LinkedList<Integer>(this.leaves.keySet());
		if (entries.isEmpty())
			return null;
		else {
			return leaves.get(((LinkedList<Integer>) entries).getLast());
		}
	}

	private List<NodeOrbitHR> addLevel(List<NodeOrbitHR> nodeOrbitList,
			PersistentIdElement pIDElement, ParametricSpecifications parametricSpecification) {

		List<LevelEventHR> entryLevelEvent = new ArrayList<>();
		List<NodeOrbitHR> nextStepOrbits = new ArrayList<>();
		String nodeName = pIDElement.getNodeName();
		int appNumber = pIDElement.getAppNumber();

		LevelOrbitHR levelOrbit = new LevelOrbitHR(nodeName, nodeOrbitList, getTopLevelEvents());
		LevelEventHR levelEvent = new LevelEventHR(appNumber, levelOrbit);


		JerboaRuleOperation currentRule = parametricSpecification.getSpecEntry(appNumber).getRule();
		for (NodeOrbitHR currentNodeOrbit : nodeOrbitList) {
			fillLevel(currentNodeOrbit, levelEvent, nextStepOrbits, pIDElement.getNodeName(),
					currentRule);

		}

		if (leaves.containsKey(pIDElement.getAppNumber())) {
			entryLevelEvent = leaves.get(pIDElement.getAppNumber());
		}
		entryLevelEvent.add(levelEvent);
		// List<Integer> appNumbers = entryLevelEvent.stream().mapToInt(LevelEventHR::getAppNumber)
		// .boxed().collect(Collectors.toList());
		// levelOrbit.setNextLevelEventsAppNumbers(appNumbers);
		leaves.put(appNumber, entryLevelEvent);


		return nextStepOrbits;
	}

	private void fillLevel(NodeOrbitHR currentNodeOrbit, LevelEventHR levelEvent,
			List<NodeOrbitHR> nextStepOrbits, String nodeName, JerboaRuleOperation rule) {

		currentNodeOrbit.BBBuildEntry(nodeName, rule, levelEvent, nextStepOrbits);

	}

	public void export(String fileName) {
		try {
			JSONPrinter.exportHistoryRecord(leaves, fileName);
		} catch (IOException exception) {
			System.out.println("Could not write to file");
		}

	}

}

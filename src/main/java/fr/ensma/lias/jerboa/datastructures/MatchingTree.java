package fr.ensma.lias.jerboa.datastructures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;

public class MatchingTree {

	// LevelOrbitMT root;
	JerboaDart topoParameter; // the last dartID computed at the end of the matching process
	List<List<LevelEventMT>> leaves;

	/**
	 * * Data structure to match a history record against a GMap and build a model from a history *
	 * record.
	 */
	public MatchingTree() {
		leaves = new ArrayList<>();
	}

	public JerboaDart getTopoParameter() {
		return topoParameter;
	}

	/*
	 * Add a LevelEventMT to this matching tree
	 *
	 * @param levelEventHR
	 *
	 * @param application
	 *
	 * @param appResult
	 */
	public void addLevel(LevelEventHR levelEventHR, Application application,
			JerboaRuleResult appResult) {

		LevelEventMT newLevelEventMT = new LevelEventMT();
		JerboaRebuiltRule rule = (JerboaRebuiltRule) application.getRule();
		ApplicationType appType = application.getApplicationType();

		switch (appType) {
			case INIT:
				int appNumber = levelEventHR.getAppNumber();
				String nodeName = levelEventHR.getNextLevelOrbit().getNodeName();
				boolean ISNOEFFECT = false;


				if (nodeName == "ø") {
					ISNOEFFECT = true;
				}

				nodeNameToDartID(appNumber, nodeName, appResult, application, ISNOEFFECT);

				matchLevel(levelEventHR, application, nodeName, rule, ISNOEFFECT);

				registerLevel(appNumber, newLevelEventMT, levelEventHR.getEventList(),
						levelEventHR.getNextLevelOrbit().getOrbitList(), appType);
				break;

			case ADD:
				// Look for a node which filters the current topological parameter
				int nodeIndex = -1;
				for (int i = 0; i < appResult.sizeCol(); i++) {
					if (appResult.get(i).contains(topoParameter)) {
						// compute events
						nodeIndex = i;
						break;
					}
				}

				// Those lists should contain events and orbits for the new level
				List<NodeEvent> eventList = new ArrayList<>();
				List<NodeOrbit> orbitList = new ArrayList<>();

				// if the topological parameter is not filtered by any node
				if (nodeIndex == -1) {
					// TODO: prepare support for deleted topological parameters
					computeLevelLists(eventList, orbitList, getLastLevel().get(0));
				} else {
					JerboaRuleNode rootNode = rule.getRightRuleNode(nodeIndex);
					computeLevelLists(eventList, orbitList, rootNode, newLevelEventMT, rule);
				}
				registerLevel(application.getApplicationID(), newLevelEventMT, eventList, orbitList,
						appType);
				break;

			default:
				break;
		}

		// System.out.println("\n");
		// System.out.println("topLevelEventMT: " +

		// getLastLevel());
		// System.out.println("\n");
	}

	/**
	 * Register a new level event in the matching tree
	 *
	 * @param applicationID
	 * @param levelEventMT
	 * @param eventList
	 * @param orbitList
	 * @param type
	 */
	private void registerLevel(int applicationID, LevelEventMT newLevelEventMT,
			List<NodeEvent> eventList, List<NodeOrbit> orbitList, ApplicationType type) {

		LevelOrbitMT levelOrbitMT;

		newLevelEventMT = new LevelEventMT(applicationID, eventList, type);
		levelOrbitMT = new LevelOrbitMT(topoParameter.getID(), orbitList, null);
		newLevelEventMT.setNextLevelOrbit(levelOrbitMT);

		if (!leaves.isEmpty())
			getLastLevel().get(0).getNextLevelOrbit()
					.setNextLevelEventMTList(Arrays.asList(newLevelEventMT));

		leaves.add(Arrays.asList(newLevelEventMT));
	}

	/**
	 * Compute events and orbits for the new level event
	 *
	 * @param eventList
	 * @param orbitList
	 * @param newLevelEventMT
	 */
	private void computeLevelLists(List<NodeEvent> eventList, List<NodeOrbit> orbitList,
			LevelEventMT newLevelEventMT) {

		// Until the support of added splits/merges consider this case as NOEFFECT as it
		// could also represent a deleted parameter
		// NOTE: maybe dynamically check for non-filtering

		// for each node event in top level LevelEventMT
		for (NodeEvent nodeEventHR : getLastLevel().get(0).getEventList()) {
			JerboaOrbit orbitType = nodeEventHR.getChild().getOrbit();
			Event event = Event.NOEFFECT;

			NodeEvent newNodeEvent = new NodeEvent(event);
			// set new node event's child
			newNodeEvent.setChild(new NodeOrbit(orbitType));
			// child's own children are upper node event's child children
			newNodeEvent.getChild().setChildren(nodeEventHR.getChild().getChildren());

			orbitList.add(newNodeEvent.getChild());
			eventList.add(newNodeEvent);
			nodeEventHR.getChild()
					.setChildren(Arrays.asList(new Link(LinkType.TRACE, newNodeEvent)));
		}
	}

	/**
	 * Compute events and orbits for the new level event
	 *
	 * @param eventList
	 * @param orbitList
	 * @param rootNode
	 * @param newLevelEventMT
	 * @param rule
	 */
	private void computeLevelLists(List<NodeEvent> eventList, List<NodeOrbit> orbitList,
			JerboaRuleNode rootNode, LevelEventMT newLevelEventMT, JerboaRebuiltRule rule) {

		// for each node event in top level LevelEventMT
		for (NodeEvent nodeEventHR : getLastLevel().get(0).getEventList()) {
			JerboaOrbit orbitType = nodeEventHR.getChild().getOrbit();
			List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(rootNode, orbitType);
			Event event = rule.getRuleNodesOrbitEvent(ruleNodesOrbit, orbitType);

			// create new node event to insert
			NodeEvent newNodeEvent = new NodeEvent(event);
			// set new node event's child
			newNodeEvent.setChild(new NodeOrbit(orbitType));
			// child's own children are upper node event's child children
			newNodeEvent.getChild().setChildren(nodeEventHR.getChild().getChildren());

			orbitList.add(newNodeEvent.getChild());
			eventList.add(newNodeEvent);
			nodeEventHR.getChild()
					.setChildren(Arrays.asList(new Link(LinkType.TRACE, newNodeEvent)));

		}
	}

	/**
	 * Match a reevaluated application against an initial evaluation
	 *
	 * @param levelEventHR
	 * @param application
	 */
	private void matchLevel(LevelEventHR levelEventHR, Application application, String nodeName,
			JerboaRebuiltRule rule, boolean ISNOEFFECT) {
		// MatchingType levelMatchingType = MatchingType.IDENTICAL;
		// List<NodeEvent> eventList = new ArrayList<>();

		// if nodeName is "0" then it is de facto NOEFFECT in the whole current level
		if (ISNOEFFECT) {
			/*
			 * if (parameter has been merged and it is no longer noeffects nor exists)
			 *
			 * {compute the new events accordingly}
			 */

			return;

		}
		// find root node to match level
		JerboaRuleNode rootNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));

		// for each event in the current level
		for (NodeEvent nodeEventHR : levelEventHR.getEventList()) {
			JerboaOrbit orbitType = nodeEventHR.getChild().getOrbit();
			List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(rootNode, orbitType);
			// compute an event
			Event event = rule.getRuleNodesOrbitEvent(ruleNodesOrbit, orbitType);

			// if there is no match replace the event with the newly computed one
			if (event != nodeEventHR.getEvent()) {
				// levelMatchingType = MatchingType.MODIFIED;
				nodeEventHR.setEvent(event);
			}
		}

	}


	// private void isLevelIdentical(LevelEventHR levelEventHR, Application application) {
	// List<NodeEvent> nodeEventHRs = levelEventHR.getEventList();
	// return;
	// }

	/**
	 * Method to convert a nodeName from HR to an actual dart id (NID) the gmap can recognize when
	 * applying a rule
	 *
	 * @param iSNOEFFECT
	 */
	private void nodeNameToDartID(int AppNumber, String nodeName, JerboaRuleResult appResult,
			Application specEntry, boolean ISNOEFFECT) {

		if (ISNOEFFECT) {
			/*
			 * if (parameter has been merged and it is no longer noeffects nor exists)
			 *
			 * { find at least one new dart in appResult}
			 */
			return;
		}

		int rowIndex = 0;
		for (int i = 0; i < appResult.sizeCol(); i++) {
			for (int j = 0; j < appResult.sizeRow(i); j++) {
				if (appResult.get(i, j) == topoParameter) {
					rowIndex = j;
				}
			}
		}

		int colIndex = specEntry.getRule().getRightIndexRuleNode(nodeName);
		// System.out.println("colIndex " + colIndex);
		// System.out.println(
		// appResult.sizeCol() + " - " + colIndex + " - " + rowIndex + " - " + nodeName);
		JerboaDart dart = appResult.get(colIndex, rowIndex);
		this.topoParameter = dart;
	}

	private List<LevelEventMT> getLastLevel() {
		return leaves.get(leaves.size() - 1);
	}

	/**
	 * Export this matching tree as a json file
	 *
	 * @param fileName name of the export file
	 */
	// public void export(String fileName) {
	// try {
	// JSONPrinter.exportMatchingTree(leaves, fileName);
	// } catch (IOException exception) {
	// System.out.println("Could not write to file");
	// }

	// }

	/**
	 * Export this matching tree as a json file
	 *
	 * @param directory Path relative to the project
	 * @param filaName name of the export file
	 */
	public void export(String directory, String fileName) {
		try {
			JSONPrinter.exportMatchingTree(leaves, directory, fileName);
		} catch (IOException exception) {
			System.out.println("Could not write to file");
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var level : leaves) {
			sb.append(level).append('\n');
			for (var orbit : level.get(0).getNextLevelOrbit().getOrbitList()) {
				sb.append(orbit.getChildren()).append('\n');
			}
		}
		return sb.toString();
	}
}

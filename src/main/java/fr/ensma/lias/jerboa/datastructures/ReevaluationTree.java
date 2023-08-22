package fr.ensma.lias.jerboa.datastructures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;

/**
 * ReevaluationTree
 */
public class ReevaluationTree {

	List<JerboaDart> topoParameters = new ArrayList<>();
	int TreeClones;
	List<List<LevelEventMT>> trees = new ArrayList<>();

	public ReevaluationTree() {
		topoParameters.add(null);
		trees.add(null);
		TreeClones = 0;
	}

	public List<JerboaDart> getTopoParameters() {
		return topoParameters;
	}

	public JerboaDart getTopoParameter(int index) {
		return topoParameters.get(index);
	}

	/* Add level to a reevaluation tree */
	public void addLevel(HistoryRecord evaluationTree, LevelEventHR levelEventEval,
			Application application, JerboaRuleResult applicationResult, JerboaDart controlDart) {

		JerboaRuleOperation rule = application.getRule();
		ApplicationType applicationType = application.getApplicationType();

		int nbTrees = trees.size(); // Fixing loop size prevents trees's expansion while
									// for-looping

		switch (applicationType) {
			case INIT:


				for (int treeIndex = 0; treeIndex < nbTrees; treeIndex++) {

					String nodeName = levelEventEval.getNextLevelOrbit().getNodeName();
					boolean NOEFFECT = isApplicationNOEFFECT(nodeName);

					matchDartParameters(treeIndex, nodeName, applicationResult, rule, NOEFFECT);
					matchLevel(levelEventEval, application, nodeName, rule, NOEFFECT);
					registerLevel(treeIndex, levelEventEval.getAppNumber(),
							levelEventEval.getEventList(),
							levelEventEval.getNextLevelOrbit().getOrbitList(), applicationType);

				}
				break;

			case ADD:

				for (int treeIndex = 0; treeIndex < nbTrees; treeIndex++) {

					int controlDartNodeIndex =
							getControlDartNodeID(controlDart, applicationResult, 0);
					List<NodeEvent> newEventList = new ArrayList<>();
					List<NodeOrbit> newOrbitList = new ArrayList<>();

					computeNewLevel(getTreeLastLevel(treeIndex), newOrbitList, newEventList,
							application.getRule(), treeIndex, controlDartNodeIndex,
							applicationResult);
					registerLevel(treeIndex, application.getApplicationID(), newEventList,
							newOrbitList, applicationType);
				}
				break;
			case DELETE:
				// prevent application
				return;
			case MOVE:
				// find a possible parameter if any exists
				break;
			default:
				break;
		}

		return;

	}

	private void matchDartParameters(int treeIndex, String nodeName,
			JerboaRuleResult applicationResult, JerboaRuleOperation rule, boolean NOEFFECT) {


		if (NOEFFECT) {
			// TODO: method - control wether or not the current application remains NOEFFECT
			return;
		}

		int rowIndex = 0;
		for (int i = 0; i < applicationResult.sizeCol(); i++) {
			for (int j = 0; j < applicationResult.sizeRow(i); j++) {
				if (applicationResult.get(i, j) == getTopoParameter(treeIndex)) {
					rowIndex = j;
				}
			}
		}

		int colIndex = rule.getRightIndexRuleNode(nodeName);
		JerboaDart dart = applicationResult.get(colIndex, rowIndex);


		if (treeIndex > topoParameters.size())
			topoParameters.add(dart);
		else
			topoParameters.set(treeIndex, dart);
	}


	private void matchLevel(LevelEvent levelEvent, Application application, String nodeName,
			JerboaRuleOperation rule, boolean NOEFFECT) {

		if (NOEFFECT) {
			return;
		}

		JerboaRuleNode levelRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));

		for (NodeEvent nodeEvent : levelEvent.getEventList()) {

			JerboaOrbit orbitType = nodeEvent.getChild().getOrbit();

			JerboaStaticDetection detector = new JerboaStaticDetection((JerboaRuleGenerated) rule);

			nodeEvent.setEvent(detector.getEventFromOrbit(levelRuleNode, orbitType));
		}
	}

	private void addTree(LevelEvent oldLevelEvent, List<NodeEvent> newEventList,
			List<NodeOrbit> newOrbitList, int treeIndex) {

		// create new split-added levelEvent
		// LevelEventMT newLevelEvent = new LevelEventMT(newEventList, null)
		// for(NodeEvent)
	}

	private int getControlDartNodeID(JerboaDart controlDart, JerboaRuleResult applicationResult,
			int topoParameterIndex) {
		int nodeIndex = -1;
		for (int i = 0; i < applicationResult.sizeCol(); i++) {
			if (applicationResult.get(i).contains(topoParameters.get(topoParameterIndex))
					|| (controlDart != null && applicationResult.get(i).contains(controlDart))) {
				// compute events
				nodeIndex = i;
				break;
			}
		}
		return nodeIndex;
	}

	private boolean isApplicationNOEFFECT(String nodeName) {
		return nodeName == "ø";
	}

	public void registerLevel(int treeIndex, int applicationNumber, List<NodeEvent> matchedEvents,
			List<NodeOrbit> matchedOrbits, ApplicationType applicationType) {

		LevelEvent newLevelEvent =
				new LevelEventMT(applicationNumber, matchedEvents, applicationType);
		LevelOrbit newLevelOrbit =
				new LevelOrbitMT(getTopoParameter(treeIndex).getID(), matchedOrbits, null);

		((LevelEventMT) newLevelEvent).setNextLevelOrbit((LevelOrbitMT) newLevelOrbit);

		if (trees.get(treeIndex) != null && !trees.get(treeIndex).isEmpty()) {
			getTreeLastLevel(treeIndex).getNextLevelOrbit()
					.setNextLevelEventMTList(Arrays.asList((LevelEventMT) newLevelEvent));
		} else {
			if (trees.get(treeIndex) == null)
				trees.remove(treeIndex);
			trees.add(Arrays.asList((LevelEventMT) newLevelEvent));
		}

	}

	/*
	 * This method computes an additional level. If there is a split event, then as many trees as
	 * subdivided orbits must be added to the ReevaluationTree structure.
	 *
	 * @param oldLevelEvent Last registered event after which the newly computed event must be
	 * inserted
	 *
	 * @param newOrbitList List of node orbits to compute
	 *
	 * @param newEventList List of node events to compute
	 *
	 * @param jerboaRuleOperation Current application's rule
	 *
	 * @param treeIndex Index of the tree for which the level must be computed
	 *
	 * @param nodeIndex Rule node used to compute new events
	 */
	private void computeNewLevel(LevelEvent oldLevelEvent, List<NodeOrbit> newOrbitList,
			List<NodeEvent> newEventList, JerboaRuleOperation rule, int treeIndex, int nodeIndex,
			JerboaRuleResult applicationResult) {

		for (NodeEvent oldNodeEvent : oldLevelEvent.getEventList()) {

			// NO SPLIT
			JerboaOrbit orbitType = oldNodeEvent.getChild().getOrbit();

			Event newEvent = Event.NOEFFECT;
			if (nodeIndex != -1) {
				JerboaRuleNode ruleNode = rule.getRightRuleNode(nodeIndex);
				JerboaStaticDetection detector =
						new JerboaStaticDetection((JerboaRuleGenerated) rule);
				newEvent = detector.getEventFromOrbit(ruleNode, orbitType);
			}


			NodeEvent newNodeEvent = new NodeEvent(newEvent);
			newNodeEvent.setChild(new NodeOrbit(orbitType));
			newNodeEvent.getChild().setChildren(oldNodeEvent.getChild().getChildren());

			newOrbitList.add(newNodeEvent.getChild());
			newEventList.add(newNodeEvent);

			oldNodeEvent.getChild()
					.setChildren(Arrays.asList(new Link(LinkType.TRACE, newNodeEvent)));

			if (newEvent == Event.SPLIT) {
				System.out.println("ReevaluationTree: added split detected ");
				for (int i = 0; i < computeNbSplits(rule, orbitType, nodeIndex,
						applicationResult); i++) {
					addTree(oldLevelEvent, newEventList, newOrbitList, treeIndex);
				}
			}

		}

	}

	private int computeNbSplits(JerboaRuleOperation rule, JerboaOrbit orbitType, int nodeIndex,
			JerboaRuleResult applicationResult) {

		List<Set<JerboaDart>> visitedSets = new ArrayList<>();

		for (int dartIndex = 0; dartIndex < applicationResult.sizeRow(nodeIndex); dartIndex++) {

			JerboaDart dart = applicationResult.get(nodeIndex, dartIndex);
			Set<JerboaDart> set = new HashSet<>(Arrays.asList(dart));
			int alphaIndex = 0;

			while (!set.contains(dart.alpha(alphaIndex))) {
				set.add(dart.alpha(alphaIndex));
				alphaIndex = (alphaIndex + 1) % orbitType.size();
			}
			visitedSets.add(set);
		}
		return visitedSets.size() - 1;

	}

	/*
	 * Get a tree
	 *
	 * @param treeIndex a tree's index
	 *
	 * @return a tree as List<LevelEventMT>
	 */
	private List<LevelEventMT> getTree(int treeIndex) {
		return trees.get(treeIndex);
	}

	/**
	 * Get a tree's last level
	 *
	 * @param treeIndex a tree's index
	 *
	 * @return a level {@link LevelEventMT}
	 */
	private LevelEventMT getTreeLastLevel(int treeIndex) {

		/*
		 * option with a possible indexOutOfBound if size == 0
		 *
		 * however, there should never be an empty tree
		 */
		List<LevelEventMT> tree = getTree(treeIndex);
		try {
			return tree.get(tree.size() - 1);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: ReevaluationTree is empty — " + e.getMessage());
		}
		return null;

		/*
		 * casting a List to LinkedList to access getLast() method
		 *
		 * cost may not be interesting
		 */
		// return ((LinkedList<LevelEventMT>) getTree(treeIndex)).getLast();
	}

	/**
	 * Export this matching tree as a json file
	 *
	 * @param directory Path relative to the project
	 * @param filaName name of the export file
	 */
	public void export(String directory, String fileName) {
		try {
			JSONPrinter.exportMatchingTree(trees, directory, fileName);
		} catch (IOException exception) {
			System.out.println("Could not write to file");
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var level : trees) {
			sb.append(level).append('\n');
			for (var orbit : level.get(0).getNextLevelOrbit().getOrbitList()) {
				sb.append(orbit.getChildren()).append('\n');
			}
		}
		return sb.toString();
	}
}

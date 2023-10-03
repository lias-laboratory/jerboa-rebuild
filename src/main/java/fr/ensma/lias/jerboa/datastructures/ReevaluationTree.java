package fr.ensma.lias.jerboa.datastructures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

/**
 * ReevaluationTree
 */
public class ReevaluationTree {

	private List<List<LevelEventMT>> tree;
	private List<JerboaDart> topoParameters;
	private List<LevelEventMT> addedBranches;

	public ReevaluationTree() {
		tree = new ArrayList<>();
		topoParameters = new ArrayList<>();
		addedBranches = new ArrayList<>();
	}

	public List<JerboaDart> getTopoParameters() {
		return topoParameters;
	}

	public int size() {
		return tree.size();
	}

	public JerboaDart getTopoParameter(int index) {
		return topoParameters.get(index);
	}

	/* Add level to a reevaluation tree */
	public void addLevel(HistoryRecord evaluationTree, LevelEventHR levelEventEval,
			Application application, JerboaRuleResult applicationResult,
			JerboaDart controlDart) {

		JerboaRuleOperation rule = application.getRule();
		ApplicationType applicationType = application.getApplicationType();

		int nbBranches = 1;
		if (!topoParameters.isEmpty())
			nbBranches = topoParameters.size();

		for (int branchIndex = 0; branchIndex < nbBranches; branchIndex++) {
			switch (applicationType) {
				case INIT:

					String nodeName = levelEventEval.getNextLevelOrbit().getNodeName();
					boolean NOEFFECT = isApplicationNOEFFECT(nodeName, branchIndex,
							applicationResult);
					matchDartParameters(branchIndex, levelEventEval, nodeName,
							applicationResult, rule, NOEFFECT);
					matchLevel(levelEventEval, application, nodeName, rule, NOEFFECT);
					registerLevel(branchIndex, levelEventEval.getAppNumber(),
							levelEventEval.getEventList(),
							levelEventEval.getNextLevelOrbit().getOrbitList(), applicationType);
					break;

				case ADD:

					int controlDartNodeIndex = getControlDartNodeID(controlDart,
							applicationResult, branchIndex);
					List<NodeEvent> newEventList = new ArrayList<>();
					List<NodeOrbit> newOrbitList = new ArrayList<>();
					compNewLevel2(getTreeLastLevel(branchIndex), newOrbitList, newEventList,
							application, branchIndex, controlDartNodeIndex,
							applicationResult);
					// computeNewLevel(getTreeLastLevel(branchIndex), newOrbitList, newEventList,
					// 		application, branchIndex, controlDartNodeIndex,
					// 		applicationResult);
					registerLevel(branchIndex, application.getApplicationID(), newEventList,
							newOrbitList, applicationType);
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
		}

		return;
	}

	private void matchDartParameters(int branchIndex, LevelEventHR levelEventHR, String nodeName,
			JerboaRuleResult applicationResult, JerboaRuleOperation rule, boolean NOEFFECT) {

		if (NOEFFECT) {
			// TODO: method - control wether or not the current application remains NOEFFECT
			return;
		}

		if (topoParameters.isEmpty()) {
			int rowIndex = 0;
			int colIndex = rule.getRightIndexRuleNode(nodeName);
			JerboaDart dart = applicationResult.get(colIndex, rowIndex);
			topoParameters.add(dart);
		} else {

			int colIndex = rule.getRightIndexRuleNode(nodeName);

			int rowIndex = -1;
			for (int i = 0; i < applicationResult.sizeCol(); i++) {
				for (int j = 0; j < applicationResult.sizeRow(i); j++) {
					if (applicationResult.get(i, j) == getTopoParameter(branchIndex)) {
						rowIndex = j;
					}
				}
			}
			if (rowIndex != -1) {
				JerboaDart dart = applicationResult.get(colIndex, rowIndex);
				topoParameters.set(branchIndex, dart);
			}
		}
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

	private void addBranch(List<NodeEvent> newEventList, List<NodeOrbit> newOrbitList,
			Application application, int branchIndex, JerboaDart newDart) {


		LevelEventMT splitaddedLevelEvent = new LevelEventMT(application.getApplicationID(),
				newEventList, application.getApplicationType());
		LevelOrbitMT splitaddedLevelOrbit = new LevelOrbitMT(branchIndex, newOrbitList);


		splitaddedLevelEvent.setNextLevelOrbit(splitaddedLevelOrbit);
		splitaddedLevelOrbit.setDartID(newDart.getID());

		topoParameters.add(newDart);

		addedBranches.add(splitaddedLevelEvent);

		System.out.println("\tAddedBranch: " + splitaddedLevelEvent);
	}

	private int getControlDartNodeID(JerboaDart controlDart, JerboaRuleResult applicationResult,
			int topoParameterIndex) {

		int nodeIndex = -1;
		for (int i = 0; !topoParameters.isEmpty() && i < applicationResult.sizeCol(); i++) {
			if (applicationResult.get(i).contains(topoParameters.get(topoParameterIndex))
					|| (controlDart != null && applicationResult.get(i).contains(controlDart))) {
				// compute events
				nodeIndex = i;
				break;
			}
		}
		return nodeIndex;
	}

	private boolean isApplicationNOEFFECT(String nodeName, int branchIndex,
			JerboaRuleResult applicationResult) {
		return nodeName == "ø";

	}

	public void registerLevel(int branchIndex, int applicationNumber, List<NodeEvent> matchedEvents,
			List<NodeOrbit> matchedOrbits, ApplicationType applicationType) {

		LevelEventMT newLevelEvent =
				new LevelEventMT(applicationNumber, matchedEvents, applicationType);
		LevelOrbitMT newLevelOrbit =
				new LevelOrbitMT(getTopoParameter(branchIndex).getID(), matchedOrbits);

		newLevelEvent.setNextLevelOrbit(newLevelOrbit);

		if (!tree.isEmpty()) {
			LevelEventMT lastRegisteredLevelEvent = getTreeLastLevel(branchIndex);
			// tree.get(branchIndex).add(newLevelEvent);
            lastRegisteredLevelEvent.getNextLevelOrbit().addNextLevelEventMT(newLevelEvent);
			// lastRegisteredLevelEvent.getNextLevelOrbit()
			// 		.setNextLevelEventMTList(new ArrayList<LevelEventMT>(Arrays.asList(newLevelEvent)));
			commitNewBranches(lastRegisteredLevelEvent);
			checkoutAddedBranches();
			tree.get(branchIndex).add(newLevelEvent);
		} else if (branchIndex >= tree.size()) {
			tree.add(new ArrayList<LevelEventMT>(Arrays.asList(newLevelEvent)));
		}


		// System.out.println("REGISTER:"+"\t\t\t" + "nb branches -> " + tree.size());

		// if (tree.isEmpty()) {
		// tree.add(Arrays.asList(newLevelEvent));
		// } else if (branchIndex < tree.size())
		// getTreeLastLevel(branchIndex).getNextLevelOrbit()
		// .setNextLevelEventMTList(Arrays.asList(newLevelEvent));
		// else if (branchIndex >= tree.size())
		// tree.add(Arrays.asList(newLevelEvent));
	}

	private void checkoutAddedBranches() {
		addedBranches = new ArrayList<>();
	}

	/*
	 * 0
	 *
	 * @param lastRegisteredLevelEvent
	 */
	private void commitNewBranches(LevelEventMT parentLevelEvent) {
		for (LevelEventMT addedLevelEventMT : addedBranches) {
			tree.add(new ArrayList<LevelEventMT>(Arrays.asList(addedLevelEventMT)));
			connectLevelToBranch(parentLevelEvent, addedLevelEventMT);
		}
	}

	private void connectLevelToBranch(LevelEventMT parentLevelEvent,
			LevelEventMT addedLevelEventMT) {

		// For each parent node orbit
		for (NodeOrbit parentNodeOrbit : parentLevelEvent.getNextLevelOrbit().getOrbitList()) {
			Link newChild = null;
			// select a child
			for (Link child : parentNodeOrbit.getChildren()) {
				// match child with a node orbit in added level event
				for (NodeEvent eventNode : addedLevelEventMT.getEventList()) {
					// event.setBranchIndex(branchIndex);
					// event.getChild().setBranchIndex(branchIndex);
					// create link with event of match node orbit and similar link type
					if (child.getChild().getChild().getOrbit()
							 == eventNode.getChild().getOrbit()) {
						newChild = new Link(child.getType(), eventNode);
					}
				}
			}
			// add child to parent node orbit
			if (newChild != null)
				parentNodeOrbit.addChild(newChild);
		}
		parentLevelEvent.getNextLevelOrbit().addNextLevelEventMT(addedLevelEventMT);
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
	 * @param branchIndex Index of the tree for which the level must be computed
	 *
	 * @param nodeIndex Rule node used to compute new events
	 */
	private void computeNewLevel(LevelEvent oldLevelEvent, List<NodeOrbit> newOrbitList,
			List<NodeEvent> newEventList, Application application, int branchIndex, int nodeIndex,
			JerboaRuleResult applicationResult) {

		boolean ISAFFECTED = false;

		// Afficher la branche courante
		System.out.println("CurBranch: " + branchIndex);

		// Afficher le paramètre topologique de la branche courante
		System.out.println("CurTopoParameter: " + topoParameters.get(branchIndex));

		// Est-ce le paramètre topologique est filtré par l'application courante ?
		if (!isBranchMatched(topoParameters.get(branchIndex), applicationResult)
				|| nodeIndex == -1) {
			System.out.println("ADD: NOT affected by added operation");
		} else {
			ISAFFECTED = true;
			System.out.println("ADD: is affected by added operation");
		}

		JerboaRuleGenerated addedRule = (JerboaRuleGenerated) application.getRule();

		// Pour chaque événement du niveau courants
		System.out.println("\t" + "Parent Event: ");
		for (NodeEvent oldNodeEvent : oldLevelEvent.getEventList()) {
			System.out.print("\t" + oldNodeEvent.getEvent());

			NodeOrbit oldNodeOrbit = oldNodeEvent.getChild();

			// Default event type
			Event newEvent = Event.NOEFFECT;

			// Si la branch est affectée calculer les changements
			JerboaOrbit orbitType = oldNodeOrbit.getOrbit();
			int splitLink = -1;
			if (ISAFFECTED) {
				JerboaRuleNode ruleNode = application.getRule().getRightRuleNode(nodeIndex);
				JerboaStaticDetection detector = new JerboaStaticDetection(addedRule);
				newEvent = detector.getEventFromOrbit(ruleNode, orbitType);
				splitLink = detector.getSplitLink();
			}

			System.out.println("\tComputed Event: " + newEvent);

			// Construction des nœuds événement et orbite
			NodeEvent newNodeEvent = new NodeEvent(newEvent);
			NodeOrbit newNodeOrbit = new NodeOrbit(orbitType);
			// Enregistrement `newNodeOrbit` fils de `newNodeEvent`
			newNodeEvent.setChild(newNodeOrbit);
			// les enfants des orbites de l'ancien niveau deviennent enfant du
			// nouveau niveau
			newNodeOrbit.setChildren(oldNodeOrbit.getChildren());

			// Ajout des nœuds construits dans les listes adéquates
			newOrbitList.add(newNodeEvent.getChild());
			newEventList.add(newNodeEvent);

			// Définir le nouvel événement comme enfant de l'orbite (lui-même
			// fils de l'ancien événement)
			oldNodeOrbit.setChildren(
					new ArrayList<Link>(Arrays.asList(new Link(LinkType.TRACE, newNodeEvent))));

			// S'il y a un nouvel événement SPLIT pour une branche affectée
			// --> créer autant de nouvelles branches que nécessaires
			// ----> Avec les paramètres topologiques adéquats
			if (newEvent == Event.SPLIT && ISAFFECTED) {

				JerboaDart dart = getTopoParameter(branchIndex);
				List<List<JerboaDart>> splits = computeSplits(application.getRule(), orbitType,
						nodeIndex, applicationResult);

				System.out.println(splits);
				System.out.println(dart);

				if (splits.stream().anyMatch(s -> s.contains(dart))) {
					for (int i = 0; i < splits.size(); i++) {
						if (!splits.get(i).contains(dart)) {
							// JerboaDart splitDart = splits.get(i).stream().findFirst().get();
							JerboaDart splitDart =
									computeSplitaddedDart(dart, orbitType, splitLink, addedRule);
							addBranch(newEventList, newOrbitList, application, branchIndex,
									splitDart);
						}
					}
				}
			}
		}
		System.out.println("");



		// if (newEvent == Event.SPLIT) {
		// System.out.println("ReevalTree: Split -> " + application.getApplicationID() + " "
		// + rule.getName() + " " + orbitType);

		// }
	}

	private void compNewLevel2(LevelEvent oldLevelEvent, List<NodeOrbit> newOrbitList,
			List<NodeEvent> newEventList, Application application, int branchIndex, int nodeIndex,
			JerboaRuleResult applicationResult) {

		// Est-ce le paramètre topologique est filtré par l'application courante ?
		boolean ISAFFECTED = isBranchMatched(topoParameters.get(branchIndex), applicationResult) && nodeIndex != -1;

		JerboaRuleGenerated addedRule = (JerboaRuleGenerated) application.getRule();

		List<Integer> splitLinks = new ArrayList<>();

		// Pour chaque événement du niveau courants
		for (NodeEvent oldNodeEvent : oldLevelEvent.getEventList()) {

			NodeOrbit oldNodeOrbit = oldNodeEvent.getChild();

			// Default event type
			Event newEvent = Event.NOEFFECT;

			// Si la branch est affectée calculer les changements
			JerboaOrbit orbitType = oldNodeOrbit.getOrbit();

			if (ISAFFECTED) {
				JerboaRuleNode ruleNode = application.getRule().getRightRuleNode(nodeIndex);
				JerboaStaticDetection detector = new JerboaStaticDetection(addedRule);
				newEvent = detector.getEventFromOrbit(ruleNode, orbitType);
				splitLinks.add(detector.getSplitLink());
			}

			// Construction des nœuds événement et orbite
			NodeEvent newNodeEvent = new NodeEvent(newEvent);
			NodeOrbit newNodeOrbit = new NodeOrbit(orbitType);
			// Enregistrement `newNodeOrbit` fils de `newNodeEvent`
			newNodeEvent.setChild(newNodeOrbit);
			// les enfants des orbites de l'ancien niveau deviennent enfant du
			// nouveau niveau
			newNodeOrbit.setChildren(oldNodeOrbit.getChildren());

			// Ajout des nœuds construits dans les listes adéquates
			newOrbitList.add(newNodeEvent.getChild());
			newEventList.add(newNodeEvent);

			// Définir le nouvel événement comme enfant de l'orbite (lui-même
			// fils de l'ancien événement)
			oldNodeOrbit.setChildren(
					new ArrayList<Link>(Arrays.asList(new Link(LinkType.TRACE, newNodeEvent))));

		}

		for (int index = 0; index < newEventList.size(); index++) {
			NodeEvent addedNodeEvent = newEventList.get(index);
			if (addedNodeEvent.getEvent() == Event.SPLIT && nodeIndex != -1) {
				int splitLink = splitLinks.get(index);

				JerboaOrbit orbitType = addedNodeEvent.getChild().getOrbit();
				JerboaDart dart = getTopoParameter(branchIndex);
				List<List<JerboaDart>> splits = computeSplits(application.getRule(), orbitType,
						nodeIndex, applicationResult);

				System.out.println(splits);
				System.out.println(dart);

				if (splits.stream().anyMatch(s -> s.contains(dart))) {
					for (int i = 0; i < splits.size(); i++) {
						if (!splits.get(i).contains(dart)) {
							// JerboaDart splitDart = splits.get(i).stream().findFirst().get();
							JerboaDart splitDart =
									computeSplitaddedDart(dart, orbitType, splitLink, addedRule);
							addBranch(newEventList, newOrbitList, application, branchIndex,
									splitDart);
						}
					}
				}
			}
		}

		System.out.println("");



		// if (newEvent == Event.SPLIT) {
		// System.out.println("ReevalTree: Split -> " + application.getApplicationID() + " "
		// + rule.getName() + " " + orbitType);

		// }
	}

	private boolean isBranchMatched(JerboaDart jerboaDart, JerboaRuleResult applicationResult) {
		for(int i = 0; i < applicationResult.sizeCol(); i++){
			for(int j = 0; j <applicationResult.sizeRow(i); j++){
				if(applicationResult.get(i, j) == jerboaDart)
					return true;
			}
		}
		return false;
	}

	private JerboaDart computeSplitaddedDart(JerboaDart dart, JerboaOrbit orbitType, int splitLink,
			JerboaRuleGenerated rule) {
		if (orbitType.size() > 0) {
			for (int link : orbitType) {
				dart = dart.alpha(link);
			}
			dart = dart.alpha(splitLink);
			for (int link = orbitType.size() - 1; link >= 0; link--) {
				dart = dart.alpha(link);
			}
		} else
			dart = dart.alpha(splitLink);
		return dart;
	}

	private List<List<JerboaDart>> computeSplits(JerboaRuleOperation rule, JerboaOrbit orbitType,
			int nodeIndex, JerboaRuleResult applicationResult) {

		JerboaGMap gmap = rule.getOwner().getGMap();
		List<List<JerboaDart>> visitedOrbits = new ArrayList<>();

		for (int dartIndex = 0; dartIndex < applicationResult.sizeRow(nodeIndex); dartIndex++) {

			JerboaDart dart = applicationResult.get(nodeIndex, dartIndex);

			try {
				List<JerboaDart> orbit = gmap.orbit(dart, orbitType);
				if (visitedOrbits.stream().noneMatch(l -> l.containsAll(orbit))) {
					visitedOrbits.add(orbit);
				}
			} catch (JerboaException e) {
				e.printStackTrace();
			}
		}
		return visitedOrbits;

	}

	/*
	 * Get a tree
	 *
	 * @param branchIndex a tree's index
	 *
	 * @return a tree as List<LevelEventMT>
	 */
	private List<LevelEventMT> getTree(int branchIndex) {
		return tree.get(branchIndex);
	}

	/**
	 * Get a tree's last level
	 *
	 * @param branchIndex a tree's index
	 *
	 * @return a level {@link LevelEventMT}
	 */
	private LevelEventMT getTreeLastLevel(int branchIndex) {

		/*
		 * option with a possible indexOutOfBound if size == 0
		 *
		 * however, there should never be an empty tree
		 */
		List<LevelEventMT> branch = getTree(branchIndex);
		if(branch.isEmpty())
			return null;
		else{
			return branch.get(branch.size()-1);
		}
		// LevelEventMT lastLevel = null;
		// for (int index = 0; index < tree.size(); index++)
		// 	lastLevel = tree.get(index);
		// return lastLevel;

		/*
		 * casting a List to LinkedList to access getLast() method
		 *
		 * cost may not be interesting
		 */
		// return ((LinkedList<LevelEventMT>) getTree(branchIndex)).getLast();
	}

	/**
	 * Export this matching tree as a json file
	 *
	 * @param directory Path relative to the project
	 * @param filaName name of the export file
	 */
	public void export(String directory, String fileName) {
		try {
			JSONPrinter.exportReevaluationTree(tree, directory, fileName);
		} catch (IOException exception) {
			System.out.println("Could not write to file");
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var level : tree) {
			sb.append(level).append('\n');
			for (var orbit : level.get(0).getNextLevelOrbit().getOrbitList()) {
				sb.append(orbit.getChildren()).append('\n');
			}
		}
		return sb.toString();
	}
}

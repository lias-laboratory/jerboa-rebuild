package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;

public class MatchingTree {

<<<<<<< HEAD
=======
	// LevelOrbitMT root;
>>>>>>> 470bd1e (Added application are partly supported - cleaning is needed urgently - do not launch DemoRejeuID it will not work as it is behind)
	JerboaDart topoParameter; // the last dartID computed at the end of the matching process
	List<List<LevelEventMT>> leaves;

	/**
<<<<<<< HEAD
	 * Data structure to match a history record against a GMap and build a model from a history
=======
	 * * Data structure to match a history record against a GMap and build a model from a history *
>>>>>>> 470bd1e (Added application are partly supported - cleaning is needed urgently - do not launch DemoRejeuID it will not work as it is behind)
	 * record.
	 */
	public MatchingTree() {
		leaves = new ArrayList<>();
	}

<<<<<<< HEAD
	public JerboaDart getTopoParameter() {
		return topoParameter;
	}

	/**
	 * Add a level to a matching tree from a history record by matching a rule application against a
	 * GMAP. This method allow to compute the topological parameters and reevaluate a model
	 *
	 * @param levelEventHR A given history record level used to compute an application's parameters
	 * @param prevApplication An application matched in the previous iteration
	 * @param curApplication Current application to match
	 * @param prevAppResult Array containing the jerboa darts filtered or created by the previous
	 *        application's rule
	 */
	public void addLevel(LevelEventHR levelEventHR, Application prevApplication,
			Application curApplication, JerboaRuleResult prevAppResult) {

		ApplicationType appType = prevApplication.getApplicationType();
		int appNumber = levelEventHR.getAppNumber();
		String nodeName = levelEventHR.getNextLevelOrbit().getNodeName();

		// // JerboaRebuiltRule prevRule = (JerboaRebuiltRule) prevApplication.getRule();
		// LevelOrbitHR levelOrbitHR = levelEventHR.getNextLevelOrbit();

		// for (NodeOrbitHR nodeOrbitHR : levelOrbitHR.getOrbitList()) {
		// JerboaRuleNode curRuleNode =
		// prevRule.getRightRuleNode(prevRule.getRightIndexRuleNode(nodeName));
		// for (Link child : nodeOrbitHR.getChildren()) {
		// // Event event = child.child.event;
		// List<JerboaRuleNode> ruleNodesOrbitList = JerboaRuleNode.orbit(curRuleNode.))
		// switch (child.child.event) {
		// case
		// }
		// }
		// }

		// NOTE: working with identical results for the time being
		//
		// For each levelEventHR check its matching type (i.e. identical, edited or notmatched)
		//
		// How to check for Identical ?
		//
		// --> from LevelEventHR check nodename·<nodeOrbit> matches levelEventHR's nodeEvents
		// ----> if it's a match then preserve current topological parameter
		// ----> if it's not a match then apply a default strategy (for later)
		//
		// In case of added operation
		//
		// For each levelEventHR check its matching type
		//
		// --> from LevelEventHR use node·<nodeOrbit> orbits to compute events
		// ----> if its a noeffect or nomodif preserve current topological parameter
		// ----> if its something else then apply relevant strategy (deleted, splitted or merge
		// darts)

		nodeNameToDartID(appNumber, nodeName, prevAppResult, prevApplication);

		LevelEventMT levelEvent =
				new LevelEventMT(levelEventHR.getAppNumber(), levelEventHR.getEventList(), appType);
		List<LevelEventMT> nextLeveleventMTs = Arrays.asList(levelEvent);

		if (!leaves.isEmpty()) {
			getLastLevel().get(0).getNextLevelOrbit().setNextLevelEventMTList(nextLeveleventMTs);
		}

		LevelOrbitMT levelOrbit = new LevelOrbitMT(topoParameter.getID(),
				levelEventHR.getNextLevelOrbit().getOrbitList(), null);
		levelEvent.nextLevelOrbit = levelOrbit;
		leaves.add(nextLeveleventMTs);
	}

	/**
	 * Convert a node name to an actual dart id (NID) the gmap can recognize when applying a rule
	 */
	private void nodeNameToDartID(int AppNumber, String nodeName, JerboaRuleResult appResult,
			Application application) {

		int rowIndex = 0;
		for (int i = 0; i < appResult.sizeCol(); i++) {
			for (int j = 0; j < appResult.sizeRow(i); j++) {
				if (appResult.get(i, j) == topoParameter) {
					rowIndex = j;
				}
			}
		}

		int colIndex = application.getRule().getRightIndexRuleNode(nodeName);
		// System.out.println("colIndex " + colIndex);

		// System.out.println(
		// appResult.sizeCol() + " - " + colIndex + " - " + rowIndex + " - " +
		// nodeName);
		JerboaDart dart = appResult.get(colIndex, rowIndex);
		this.topoParameter = dart;
	}

=======
	public MatchingTree(JerboaRuleResult appResult, Application specEntry, HistoryRecord HR) {

		leaves = new ArrayList<>();

		// if spec is unedited

		// for (LevelEventHR level : HR.getLeaf(0)) {
		// // addLevel(level, spec, appResults);
		// addLevel(level, specEntry, appResult);
		// }

		// else (spec is edited)
		// -- op added
		// -- op deleted
		// -- op moved
		// -- geometry modified (see if we should move it to `unedited` phase)
	}

	public JerboaDart getTopoParameter() {
		return topoParameter;
	}

	public void addLevel(LevelEventHR levelEventHR, Application application,
			JerboaRuleResult appResult) {

		LevelEventMT levelEventMT = new LevelEventMT();
		LevelOrbitMT levelOrbitMT;
		JerboaRebuiltRule rule = (JerboaRebuiltRule) application.getRule();
		if (application.getApplicationType() != ApplicationType.ADD) {

			int appNumber = levelEventHR.getAppNumber();
			String nodeName = levelEventHR.getNextLevelOrbitHR().getNodeName();

			nodeNameToDartID(appNumber, nodeName, appResult, application);

			matchLevel(levelEventHR, application, nodeName, rule);

			ApplicationType appType = application.getApplicationType();
			levelEventMT = new LevelEventMT(levelEventHR.getAppNumber(),
					levelEventHR.getEventList(), appType);

			levelOrbitMT = new LevelOrbitMT(topoParameter.getID(),
					levelEventHR.getNextLevelOrbitHR().getOrbitList(), null);
			levelEventMT.nextLevelOrbit = levelOrbitMT;

			// leaves.add(Arrays.asList(levelEventMT));

		} else {
			// ADD application type case
			int nodeIndex = -1;
			for (int i = 0; i < appResult.sizeCol(); i++) {
				if (appResult.get(i).contains(topoParameter)) {
					// compute events
					nodeIndex = i;
					break;
				}
			}
			LevelEventMT topLevelEventMT = getLastLevel().get(0);
			List<NodeEventHR> eventList = new ArrayList<>();
			List<NodeOrbitHR> orbitList = new ArrayList<>();
			if (nodeIndex < 0) {
				// topological parameter is not filtered
				// events are NOEFFECT
				for (NodeEventHR nodeEventHR : topLevelEventMT.getEventList()) {
					JerboaOrbit orbitType = nodeEventHR.getChild().getOrbit();
					Event event = Event.NOEFFECT;

					NodeEventHR newNodeEventHR = new NodeEventHR(event);
					// set new node event's child
					newNodeEventHR.setChild(new NodeOrbitHR(orbitType));
					// child's own children are upper node event's child children
					newNodeEventHR.child.setChildren(nodeEventHR.getChild().getChildren());

					orbitList.add(newNodeEventHR.getChild());
					eventList.add(newNodeEventHR);
					nodeEventHR.getChild()
							.setChildren(Arrays.asList(new Link(LinkType.TRACE, newNodeEventHR)));
				}
			} else {
				JerboaRuleNode rootNode = rule.getRightRuleNode(nodeIndex);
				for (NodeEventHR nodeEventHR : topLevelEventMT.getEventList()) {
					JerboaOrbit orbitType = nodeEventHR.getChild().getOrbit();
					List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(rootNode, orbitType);
					Event event = rule.getRuleNodesOrbitEvent(ruleNodesOrbit, orbitType);

					// create new node event to insert
					NodeEventHR newNodeEventHR = new NodeEventHR(event);
					// set new node event's child
					newNodeEventHR.setChild(new NodeOrbitHR(orbitType));
					// child's own children are upper node event's child children
					newNodeEventHR.child.setChildren(nodeEventHR.getChild().getChildren());

					orbitList.add(newNodeEventHR.getChild());
					eventList.add(newNodeEventHR);
					nodeEventHR.getChild()
							.setChildren(Arrays.asList(new Link(LinkType.TRACE, newNodeEventHR)));

				}
			}
			levelEventMT = new LevelEventMT(application.getApplicationID(), eventList,
					application.getApplicationType());
			levelOrbitMT = new LevelOrbitMT(topoParameter.getID(), orbitList, null);
			levelEventMT.nextLevelOrbit = levelOrbitMT;

		}

		leaves.add(Arrays.asList(levelEventMT));
		System.out.println("\n");
		System.out.println("topLevelEventMT: " + getLastLevel());
		System.out.println("\n");
	}

	/*
	 * Match a reevaluated application against an initial evaluation
	 *
	 * @param levelEventHR
	 *
	 * @param application
	 */
	private void matchLevel(LevelEventHR levelEventHR, Application application, String nodeName,
			JerboaRebuiltRule rule) {
		// MatchingType levelMatchingType = MatchingType.IDENTICAL;
		// List<NodeEventHR> eventList = new ArrayList<>();
		JerboaRuleNode rootNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));

		for (NodeEventHR nodeEventHR : levelEventHR.getEventList()) {
			JerboaOrbit orbitType = nodeEventHR.getChild().getOrbit();
			List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(rootNode, orbitType);
			Event event = rule.getRuleNodesOrbitEvent(ruleNodesOrbit, orbitType);

			if (event != nodeEventHR.getEvent()) {
				// levelMatchingType = MatchingType.MODIFIED;
				nodeEventHR.setEvent(event);
			}
		}
	}

	// private void isLevelIdentical(LevelEventHR levelEventHR, Application application) {
	// List<NodeEventHR> nodeEventHRs = levelEventHR.getEventList();
	// return;
	// }

	/**
	 * Method to convert a nodeName from HR to an actual dart id (NID) the gmap can recognize when
	 * applying a rule
	 */
	private void nodeNameToDartID(int AppNumber, String nodeName, JerboaRuleResult appResult,
			Application specEntry) {

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

>>>>>>> 470bd1e (Added application are partly supported - cleaning is needed urgently - do not launch DemoRejeuID it will not work as it is behind)
	private List<LevelEventMT> getLastLevel() {
		return leaves.get(leaves.size() - 1);
	}
}

package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;

public class MatchingTree {

	JerboaDart topoParameter; // the last dartID computed at the end of the matching process
	List<List<LevelEventMT>> leaves;

	/**
	 * Data structure to match a history record against a GMap and build a model from a history
	 * record.
	 */
	public MatchingTree() {
		leaves = new ArrayList<>();
	}

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

	private List<LevelEventMT> getLastLevel() {
		return leaves.get(leaves.size() - 1);
	}
}

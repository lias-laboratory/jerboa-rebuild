package fr.ensma.lias.jerboa.core.rule;

import fr.ensma.lias.jerboa.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.CreationExpression;
import fr.ensma.lias.jerboa.core.rule.expression.ModifyExpression;
import fr.ensma.lias.jerboa.core.rule.expression.SplitExpression;
import fr.ensma.lias.jerboa.core.rule.expression.UnchangedExpression;
import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

	public StringBuilder deletedLabels = new StringBuilder();
	protected JerboaRebuiltModeler rebuiltModeler;

	public JerboaRebuiltRule(JerboaRebuiltModeler modeler, String name, String category)
			throws JerboaException {
		super(modeler, name, category);
		rebuiltModeler = modeler;
	}

	public int attachedNode(int arg0) {
		return 0;
	}

	public int reverseAssoc(int arg0) {
		return 0;
	}

	protected void enrichTrackingExpressions() {
		List<JerboaEmbeddingInfo> trackers = rebuiltModeler.getTrackers();

		for (JerboaEmbeddingInfo tracker : trackers) {
			// List of visited nodes; Initialized at each orbit tracker
			var visited = new ArrayList<Integer>();

			// Course through right hand side nodes of the rule
			for (JerboaRuleNode node : right) {
				if (visited.contains(node.getID())) {
					continue;
				}
				// orbit is the list of all the nodes starting from `node`
				// and following tracker's orbit
				List<JerboaRuleNode> orbit = JerboaRuleNode.orbit(node, tracker.getOrbit());

				// this lambda register all node.getID() from orbit within visited
				visited.addAll(orbit.stream().map(n -> n.getID()).collect(Collectors.toList()));

				if (testCreationCondition(orbit, tracker)) {
					node.addExpression(new CreationExpression(tracker));
				} else if (testUnchangedCondition(orbit, tracker)) {
					node.addExpression(new UnchangedExpression(tracker));
				} else if (testSplitCondition(orbit, tracker)) {
					node.addExpression(new SplitExpression(tracker));
				} else {
					node.addExpression(new ModifyExpression(tracker));
				}
			}
		}
	}

	private boolean testCreationCondition(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
		return /* isOrbitFullyFiltered(orbit, tracker) && */ isOrbitFullyCreated(orbit);
	}

	private boolean testUnchangedCondition(List<JerboaRuleNode> orbit,
			JerboaEmbeddingInfo tracker) {
		return isOrbitFullyPreExistant(orbit) && isOrbitUnchanged(orbit, tracker);
	}

	private boolean testSplitCondition(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
		return isIthImplicitLinkReachable(orbit, tracker);
	}

	private boolean isOrbitFullyFiltered(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
		for (var node : orbit) {
			for (var aLink : tracker.getOrbit()) {
				// left side: check if aLink is not explicit
				// right side: check if aLink is not implicit
				if (node.alpha(aLink) == null && !node.getOrbit().contains(aLink))
					// if in both cases it is false then
					// the node is not filtering the tracker's orbit
					return false;
			}
		}
		return true;
	}

	// If at least one node in orbit is present in the left hand side of the
	// rule then the orbit is not created
	private boolean isOrbitFullyCreated(List<JerboaRuleNode> orbit) {
		for (var node : orbit) {
			if (left.contains(node))
				return false;
		}
		return true;
	}

	private boolean isOrbitFullyDeleted(List<JerboaRuleNode> orbit) {
		for (var node : orbit) {
			if (right.contains(node)) {
				return false;
			}
		}
		return true;
	}

	private boolean isOrbitFullyPreExistant(List<JerboaRuleNode> orbit) {
		for (var node : orbit) {
			if (reverseAssoc(node.getID()) == -1) {
				return false;
			}
		}
		return true;
	}

	// Check whether an explicit link between the left and right version of a node
	// is modified or not
	private boolean isExplicitLinkUnchanged(Integer aLink, JerboaRuleNode leftNode,
			JerboaRuleNode rightNode) {
		if (leftNode.alpha(aLink) == null) {
			return rightNode.alpha(aLink) == null;
		}
		return rightNode.alpha(aLink) != null;
	}


	private boolean isImplicitLinkUnchanged(Integer aLink, JerboaRuleNode leftNode,
			JerboaRuleNode rightNode) {
		// test that alink is present in both orbits
		return leftNode.getOrbit().contains(aLink) == rightNode.getOrbit().contains(aLink)
				// test that alink kept the same position in the orbit
				&& leftNode.getOrbit().indexOf(aLink) == rightNode.getOrbit().indexOf(aLink);
	}

	// Check if every node in the orbit remains unchanged in regard
	// of the nodes in the left hand side of the rule
	private boolean isOrbitUnchanged(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
		for (var node : orbit) {
			// for each explicit link test whether or it changed between left and
			// right
			for (var aLink : tracker.getOrbit()) {
				// Using reverseAssoc to match the actual node ID in left
				var leftNodeAssoc = left.get(reverseAssoc(node.getID()));
				if (!isImplicitLinkUnchanged(aLink, leftNodeAssoc, node)
						|| !isExplicitLinkUnchanged(aLink, leftNodeAssoc, node)) {
					return false;
				}
			}
		}
		return true;
	}

	// NOTE: this method should not be considered finished
	// TODO: test it,give a correct name, clean its
	/*
	 * Check if there is at least one i-th implicit alpha in left orbit that is never reachable in
	 * right orbit
	 */
	private boolean isIthImplicitLinkReachable(List<JerboaRuleNode> rightOrbit,
			JerboaEmbeddingInfo tracker) {

		// get leftnode relative to rightOrbit root node
		JerboaRuleNode leftNode = left.get(reverseAssoc(rightOrbit.get(0).getID()));
		List<JerboaRuleNode> leftOrbit = JerboaRuleNode.orbit(leftNode, tracker.getOrbit());
		int orbitTypeSize = leftNode.getOrbit().size();

		// init indexes list this list is used to know if for all nodes in
		// rightOrbit there is an ith implicit link that is never reachable
		Integer indexes[] = new Integer[orbitTypeSize];
		for (int i = 0; i < orbitTypeSize; i++) {
			indexes[i] = 0;
		}

		// for each node r in R
		for (JerboaRuleNode rightNode : rightOrbit) {
			JerboaOrbit rightNodeOrbitType = rightNode.getOrbit();
			// for each node l in L
			for (JerboaRuleNode lNode : leftOrbit) {
				JerboaOrbit leftNodeOrbitType = leftNode.getOrbit();
				// for each ith link in r if its implicit links are not in
				// l's orbitType then increment indexes counters
				for (int index = 0; index < orbitTypeSize; index++) {
					if (!leftNodeOrbitType.contains(rightNodeOrbitType.get(index)))
						indexes[index] += 1;
				}
			}
		}
		// if there is an index equal to the orbit size then there is definetely
		// an ith implicit that is never reachable
		for (int index = 0; index < orbitTypeSize; index++) {
			if (indexes[index] == rightOrbit.size())
				return true;
		}

		return false;
	}

	public boolean hasMidprocess() {
		return true;
	}

	public boolean midprocess(JerboaGMap gmap, List<JerboaRowPattern> leftPattern)
			throws JerboaException {

		// Process each tracker
		for (JerboaEmbeddingInfo tracker : rebuiltModeler.getTrackers()) {
			var visitedNodes = new ArrayList<Integer>();
			var visitedLabels = new ArrayList<Integer>();

			// Process each `deleted` node
			for (Integer nodeID : deleted) {

				if (visitedNodes.contains(nodeID)) {
					continue;
				}
				visitedNodes.add(nodeID);

				List<JerboaRuleNode> orbit =
						JerboaRuleNode.orbit(left.get(nodeID), tracker.getOrbit());

				// this lambda adds all node's IDs from the orbit to visited
				visitedNodes
						.addAll(orbit.stream().map(n -> n.getID()).collect(Collectors.toList()));

				// Process label extraction if orbit is fully deleted
				if (isOrbitFullyFiltered(orbit, tracker) && isOrbitFullyDeleted(orbit)) {

					for (JerboaRowPattern row : getLeftFilter()) {

						// Object label =
						// rowpattern.get(nodeID).getEmbedding(tracker.getID());
						Integer label = row.get(nodeID).<OrbitLabel>ebd(tracker.getID()).getLabel();

						if (visitedLabels.contains(label)) {
							continue;
						}

						visitedLabels.add(label);

						deletedLabels
								.append(tracker.getName().substring(0,
										tracker.getName().length() - 7))
								.append(" Delete Label:").append(label).append('\n');
					}
				}
			}
		}

		return true;
	}

	public boolean hasPostprocess() {
		return true;
	}

	public void postprocess(JerboaGMap gmap, JerboaRuleResult res) throws JerboaException {
		System.out.println(deletedLabels.toString());
		deletedLabels = new StringBuilder();
	}
}

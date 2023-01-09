package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.Pair;

/**
 * NodeOrbitHR
 */
public class NodeOrbit {

	private JerboaOrbit orbit;
	private List<Integer> alphaPath;
	private List<Link> children;

	public NodeOrbit(JerboaOrbit orbit) {
		this.orbit = orbit;
		alphaPath = new ArrayList<>();
		children = new LinkedList<Link>();
	}

	public JerboaOrbit getOrbit() {
		return orbit;
	}

	public List<Link> getChildren() {
		return children;
	}

	public List<Integer> getAlphaPath() {
		return this.alphaPath;
	}

	public void setChildren(List<Link> children) {
		this.children = new ArrayList<>(children);
	}

	/*
	 * Add a Link child to this node orbit
	 *
	 * @param child a node event pointed to by this node orbit
	 */
	public void addChild(Link child) {
		children.add(child);
	}

	/*
	 * Merge (additive) this node's list of children (Link) with those of an exterior node
	 *
	 * @param extNodeOrbitHR a given node to merge this node with
	 */
	private void mergeWithNode(NodeOrbit nodeOrbit) {
		List<Link> childrenToAdd = nodeOrbit.children.stream()//
				.filter((child) -> !children.contains(child))//
				.collect(Collectors.toList());
		this.children.addAll(childrenToAdd);
	}

	/*
	 * Merge (additive) nodes
	 *
	 * @param nodeToMerge
	 *
	 * @param nodeOrbitList
	 */
	public static void addNodes(NodeOrbit nodeToMerge, List<NodeOrbit> nodeOrbitList) {
		if (nodeOrbitList.isEmpty()) {
			nodeOrbitList.add(nodeToMerge);
			return;
		}
		for (NodeOrbit node : nodeOrbitList) {
			if (node.equals(nodeToMerge)) {
				node.mergeWithNode(nodeToMerge);
				return;
			}
		}
		nodeOrbitList.add(nodeToMerge);
	}

	/*
	 * Compute a building board build entry for this orbit from a given node name and a rule
	 *
	 * @param nodeName
	 *
	 * @param rule
	 *
	 * @return a node orbit list of the traced and/or origin of the current orbit for a given node
	 * name and rule
	 */
	public List<NodeOrbit> BBBuildEntry(String nodeName, JerboaRuleOperation rule,
			LevelEventHR levelEvent, List<NodeOrbit> nextStepOrbitHRs, boolean ISNOEFFECT) {

		JerboaOrbit orbitType = this.getOrbit();

		if (ISNOEFFECT) {
			NodeEvent NOEFFECTEvent = new NodeEvent(Event.NOEFFECT, this);
			levelEvent.addEvent(NOEFFECTEvent);
			NodeOrbit previousOrbitHR = new NodeOrbit(orbitType);
			previousOrbitHR.addChild(new Link(LinkType.TRACE, NOEFFECTEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			return nextStepOrbitHRs;
		}

		int currentRuleNodeIndex = rule.getRightIndexRuleNode(nodeName);
		JerboaRuleNode currentRuleNode = rule.getRightRuleNode(currentRuleNodeIndex);

		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(currentRuleNode, this.orbit);
		JerboaRebuiltRule rebuiltRule = (JerboaRebuiltRule) rule;

		/* // CREATED CASE ///////////////////////////////////////////////////// */
		if (rebuiltRule.isRuleNodesOrbitCreated(ruleNodesOrbit)) {
			NodeEvent creationEvent = new NodeEvent(Event.CREATION, this);
			levelEvent.addEvent(creationEvent);
			if (rebuiltRule.getLeft().isEmpty()) {
				return nextStepOrbitHRs;
			}
			NodeOrbit previousOrbitHR =
					new NodeOrbit(rebuiltRule.computeBBOrigin(ruleNodesOrbit, orbitType));
			previousOrbitHR.computePath(rebuiltRule, nodeName);
			previousOrbitHR.addChild(new Link(LinkType.ORIGIN, creationEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			return nextStepOrbitHRs;
		}
		/* // UNCHANGED CASE ///////////////////////////////////////////// */
		else if (rebuiltRule.isRuleNodesOrbitUnchanged(ruleNodesOrbit, orbitType)) {
			NodeEvent unchangedEvent = new NodeEvent(Event.NOMODIF, this);
			levelEvent.addEvent(unchangedEvent);
			NodeOrbit previousOrbitHR = new NodeOrbit(orbitType);
			previousOrbitHR.computePath(rebuiltRule, nodeName);
			previousOrbitHR.addChild(new Link(LinkType.TRACE, unchangedEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			return nextStepOrbitHRs;
		}
		/* // SPLIT CASE ///////////////////////////////////////////////////// */
		else if (rebuiltRule.isRuleNodesOrbitSplitted(ruleNodesOrbit, this.orbit)
				|| rebuiltRule.isRuleNodesOrbitMerged(ruleNodesOrbit, orbitType)) {
			//
			if (rebuiltRule.isRuleNodesOrbitSplitted(ruleNodesOrbit, this.orbit)) {
				NodeEvent splitEvent = new NodeEvent(Event.SPLIT, this);
				levelEvent.addEvent(splitEvent);
				NodeOrbit previousOrbitHR =
						new NodeOrbit(rebuiltRule.computeBBOrigin(ruleNodesOrbit, orbitType));
				previousOrbitHR.computePath(rebuiltRule, nodeName);
				previousOrbitHR.addChild(new Link(LinkType.ORIGIN, splitEvent));
				addNodes(previousOrbitHR, nextStepOrbitHRs);
				previousOrbitHR = new NodeOrbit(orbitType);
				previousOrbitHR.addChild(new Link(LinkType.TRACE, splitEvent));
				addNodes(previousOrbitHR, nextStepOrbitHRs);
			}
			if (rebuiltRule.isRuleNodesOrbitMerged(ruleNodesOrbit, orbitType)) {
				NodeEvent mergeEvent = new NodeEvent(Event.MERGE, this);
				levelEvent.addEvent(mergeEvent);
				NodeOrbit previousOrbitHR =
						new NodeOrbit(rebuiltRule.computeBBOrigin(ruleNodesOrbit, orbitType));
				previousOrbitHR.computePath(rebuiltRule, nodeName);
				previousOrbitHR.addChild(new Link(LinkType.ORIGIN, mergeEvent));
				addNodes(previousOrbitHR, nextStepOrbitHRs);
				previousOrbitHR = new NodeOrbit(orbitType);
				previousOrbitHR.addChild(new Link(LinkType.TRACE, mergeEvent));
				addNodes(previousOrbitHR, nextStepOrbitHRs);
			}
			return nextStepOrbitHRs;
		}
		/* // MODIFIED CASE ///////////////////////////////////////////// */
		else if (rebuiltRule.isRuleNodesOrbitModified(ruleNodesOrbit, orbitType)) {
			NodeEvent modificationEvent = new NodeEvent(Event.MODIFICATION, this);
			levelEvent.addEvent(modificationEvent);
			NodeOrbit previousOrbitHR = new NodeOrbit(orbitType);
			previousOrbitHR.computePath(rebuiltRule, nodeName);
			previousOrbitHR.addChild(new Link(LinkType.TRACE, modificationEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			return nextStepOrbitHRs;
		}
		return nextStepOrbitHRs;
	}

	// private List<Integer> collectLabelsFromSourceToClosestTarget(JerboaRebuiltRule rule,
	// JerboaRuleNode node, List<JerboaRuleNode> targets, JerboaRuleNode hook) {
	// List<Integer> pathToCompute = new ArrayList<>();
	// int dimension = rule.getOwner().getDimension();
	// LinkedList<Pair<JerboaRuleNode, List<Integer>>> queue = new LinkedList<>();
	// queue.push(new Pair<JerboaRuleNode, List<Integer>>(node, Arrays.asList()));

	// while (!queue.isEmpty()) {
	// Pair<JerboaRuleNode, List<Integer>> p = queue.pollFirst();
	// // JerboaRuleNode v = queue.pollFirst();

	// // if match
	// if (targets.contains(p.l())) {
	// pathToCompute = p.r();
	// if (hook == null)
	// hook = p.l();
	// break;
	// }

	// for (int index = 0; index <= dimension; index++) {

	// if (p.l().alpha(index) != null) {
	// JerboaRuleNode w = p.l().alpha(index);
	// List<Integer> path = new ArrayList<Integer>(p.r());
	// path.add(index);

	// Pair<JerboaRuleNode, List<Integer>> q =
	// new Pair<JerboaRuleNode, List<Integer>>(w, path);
	// if (w.isNotMarked()) {
	// w.setMark(true);
	// // neighbors.push(w);
	// queue.push(q);
	// }
	// }
	// }
	// }

	// for (JerboaRuleNode n : rule.getLeft()) {
	// if (!n.isNotMarked())
	// n.setMark(false);;
	// }
	// return pathToCompute;
	// }

	private void initializeUpdatePath(JerboaRuleNode sourceNode,
			List<Integer> implicitLinksIndexes) {
		JerboaRuleNode curNode = sourceNode;
		for (Integer label : this.alphaPath) {
			if (curNode.getOrbit().contains(label)) {
				implicitLinksIndexes.add(curNode.getOrbit().indexOf(label));
			} else if (curNode.alpha(label) != null) {
				curNode = curNode.alpha(label);
			}
		}
	}

	private void collectImplicitLabels(JerboaRuleNode node, List<Integer> implicitIndexes) {
		for (Integer index : implicitIndexes) {
			alphaPath.add(node.getOrbit().get(index));
		}
	}

	// NOTE: Draft
	public void computePath(JerboaRebuiltRule rule, String nodeName) {
		int nodeOfInterest = rule.getRightIndexRuleNode(nodeName);
		JerboaRuleNode rNode = rule.getRightRuleNode(nodeOfInterest);

		if (rule.isNodeCreated(rNode))
			return;

		if (this.alphaPath.isEmpty())
			// if (rule.isNodeCreated(rNode)) {
			// find hook in `rule.right`
			// }
			if (rule.isNodeHook(rNode)) {
				this.alphaPath = Arrays.asList();
			} else {
				int leftNodeOfInterest = rule.reverseAssoc(nodeOfInterest);
				JerboaRuleNode lNode = rule.getLeftRuleNode(leftNodeOfInterest);
				this.alphaPath =
						rule.collectLabelsFromSourceToClosestTarget(lNode, rule.getHooks(), null);
			}
		else {
			int leftNodeOfInterest = rule.reverseAssoc(nodeOfInterest);
			JerboaRuleNode lNode = rule.getLeftRuleNode(leftNodeOfInterest);
			JerboaRuleNode hook = rule.findClosestHook(lNode, rule.getHooks());
			if (hook == null) {
				hook = rule.getHooks().get(0);
			}

			List<Integer> implicitPath = new ArrayList<>();
			List<Integer> path = new ArrayList<>();

			// - rejouer le chemin à partir de nodeName dans right
			// et enregistrer les index d'arcs implicites traversés
			initializeUpdatePath(rNode, implicitPath);
			alphaPath = new ArrayList<>();
			// - aller de nodename vers hook (enregistrer les arcs explicites) dans left
			path = rule.collectLabelsFromSourceToClosestTarget(lNode, rule.getHooks(), hook);
			alphaPath.addAll(path);
			// - enregistrer les arcs implicites dans l'ordre des index enregistrés avant et dans
			// hook
			collectImplicitLabels(hook, implicitPath);
			// - aller de hook vers nodename en enregistrant les arcs explicites traversés dans left
			path = rule.collectLabelsFromSourceToClosestTarget(hook, Arrays.asList(lNode), null);
			alphaPath.addAll(path);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NodeOrbit) {
			NodeOrbit no = (NodeOrbit) obj;
			return orbit.equals(no.orbit);
		}

		return super.equals(obj);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(alphaPath).append("·");
		sb.append(orbit.toString());
		return sb.toString();
	}
}

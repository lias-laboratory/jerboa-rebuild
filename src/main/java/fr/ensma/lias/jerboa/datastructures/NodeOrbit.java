package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRuleNode;

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
			LevelEventHR levelEvent, List<NodeOrbit> nextStepOrbitHRs) {

		int currentRuleNodeIndex = rule.getRightIndexRuleNode(nodeName);
		JerboaOrbit orbitType = this.getOrbit();
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
			previousOrbitHR.addChild(new Link(LinkType.ORIGIN, creationEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			return nextStepOrbitHRs;
		}
		/* // UNCHANGED CASE ///////////////////////////////////////////// */
		else if (rebuiltRule.isRuleNodesOrbitUnchanged(ruleNodesOrbit, orbitType)) {
			NodeEvent unchangedEvent = new NodeEvent(Event.NOMODIF, this);
			levelEvent.addEvent(unchangedEvent);
			NodeOrbit previousOrbitHR = new NodeOrbit(orbitType);
			previousOrbitHR.addChild(new Link(LinkType.TRACE, unchangedEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			return nextStepOrbitHRs;
		}
		/* // SPLIT CASE ///////////////////////////////////////////////////// */
		else if (rebuiltRule.isRuleNodesOrbitSplitted(ruleNodesOrbit, this.orbit)) {
			//
			NodeEvent splitEvent = new NodeEvent(Event.SPLIT, this);
			levelEvent.addEvent(splitEvent);
			NodeOrbit previousOrbitHR =
					new NodeOrbit(rebuiltRule.computeBBOrigin(ruleNodesOrbit, orbitType));
			previousOrbitHR.addChild(new Link(LinkType.ORIGIN, splitEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			previousOrbitHR = new NodeOrbit(orbitType);
			previousOrbitHR.addChild(new Link(LinkType.TRACE, splitEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
		}
		/* // MODIFIED CASE ///////////////////////////////////////////// */
		else if (rebuiltRule.isRuleNodesOrbitModified(ruleNodesOrbit, orbitType)) {
			NodeEvent modificationEvent = new NodeEvent(Event.MODIFICATION, this);
			levelEvent.addEvent(modificationEvent);
			NodeOrbit previousOrbitHR = new NodeOrbit(orbitType);
			previousOrbitHR.addChild(new Link(LinkType.TRACE, modificationEvent));
			addNodes(previousOrbitHR, nextStepOrbitHRs);
			return nextStepOrbitHRs;
		}
		return nextStepOrbitHRs;
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
		return orbit.toString();
	}
}

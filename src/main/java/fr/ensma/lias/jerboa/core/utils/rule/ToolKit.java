package fr.ensma.lias.jerboa.core.utils.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;

/**
 * ToolKit
 */
public final class ToolKit {

	/**
	 * BFS to get closest hook in `left` from a given node
	 *
	 * @param node a given {@link JerboaRuleNode}
	 * @param targets a list of {@link JerboaRuleNode} hooks
	 * @return target a JerboaRuleNode hook
	 */
	public static JerboaRuleNode findClosestHook(JerboaRuleGenerated rule, JerboaRuleNode node,
			List<JerboaRuleNode> targets) {
		JerboaRuleNode target = null;
		int dimension = rule.getOwner().getDimension();
		LinkedList<JerboaRuleNode> queue = new LinkedList<>();
		queue.push(node);

		while (!queue.isEmpty()) {
			JerboaRuleNode v = queue.pollFirst();

			if (targets.contains(v)) {
				target = v;
				break;
			}

			for (int index = 0; index <= dimension; index++) {

				if (v.alpha(index) != null) {
					JerboaRuleNode w = v.alpha(index);

					if (w.isNotMarked()) {
						w.setMark(true);
						queue.push(w);
					}
				}
			}
		}

		for (JerboaRuleNode n : rule.getLeft()) {
			if (!n.isNotMarked())
				n.setMark(false);;
		}

		if (target == null) {
			target = targets.get(0);
		}

		return target;
	}

	public static List<Integer> collectLabelsFromSourceToClosestTarget(JerboaRuleGenerated rule,
			JerboaRuleNode node, List<JerboaRuleNode> targets, JerboaRuleNode hook) {
		List<Integer> pathToCompute = new ArrayList<>();
		int dimension = rule.getOwner().getDimension();
		LinkedList<Pair<JerboaRuleNode, List<Integer>>> queue = new LinkedList<>();
		queue.push(new Pair<JerboaRuleNode, List<Integer>>(node, Arrays.asList()));

		while (!queue.isEmpty()) {
			Pair<JerboaRuleNode, List<Integer>> p = queue.pollFirst();
			// JerboaRuleNode v = queue.pollFirst();

			// if match
			if (targets.contains(p.l())) {
				pathToCompute = p.r();
				if (hook == null)
					hook = p.l();
				break;
			}

			for (int index = 0; index <= dimension; index++) {

				if (p.l().alpha(index) != null) {
					JerboaRuleNode w = p.l().alpha(index);
					List<Integer> path = new ArrayList<Integer>(p.r());
					path.add(index);

					Pair<JerboaRuleNode, List<Integer>> q =
							new Pair<JerboaRuleNode, List<Integer>>(w, path);
					if (w.isNotMarked()) {
						w.setMark(true);
						// neighbors.push(w);
						queue.push(q);
					}
				}
			}
		}

		for (JerboaRuleNode n : rule.getLeft()) {
			if (!n.isNotMarked())
				n.setMark(false);;
		}
		return pathToCompute;
	}

	/*
	 * Breadth first search, find a closest target neighbor
	 *
	 * @param rule JerboaOperationRule -> current rule
	 *
	 * @param node added (in Right) node to which we research the reference node's PI
	 *
	 * @return (Left) ID of closest preserved neighbor node or attached hook else -1 (pure creation
	 * rule)
	 */
	public static int findClosestPreservedNode(JerboaRuleGenerated rule, JerboaRuleNode node) {
		int dimension = rule.getOwner().getDimension();
		LinkedList<JerboaRuleNode> queue = new LinkedList<>();
		queue.push(node);

		while (!queue.isEmpty()) {
			JerboaRuleNode v = queue.pollFirst();

			if (rule.reverseAssoc(v.getID()) != -1) {
				return rule.reverseAssoc(v.getID());
			}

			for (int index = 0; index <= dimension; index++) {

				if (v.alpha(index) != null) {
					JerboaRuleNode w = v.alpha(index);
					if (w.isNotMarked()) {
						w.setMark(true);
						queue.push(w);
					}
				}
			}
		}

		for (JerboaRuleNode n : rule.getRight()) {
			if (!n.isNotMarked())
				n.setMark(false);;
		}
		return rule.attachedNode(node.getID());
	}

}

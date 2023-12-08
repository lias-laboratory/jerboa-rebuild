package fr.ensma.lias.jerboa.core.utils.rule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;

public class PathToolKit {

  /**
   * Compute a path between two darts from different sub-instances of an orbit post-split. Cycling
   * this path is expected to bring back to the starting dart.
   *
   * <p>This method travels from a node until it reaches the split rewrite link and then goes back
   * to the starting node.
   *
   * @param rule The rule from which computing the path
   * @param node A starting jerboa rule node from the rule (most likely a hook)
   * @param splitRewriteLink The rewritten value of a split link
   * @param orbitType a JerboaOrbit
   * @return A path in the form a a list of alpha-links (Integers)
   */
  public static List<Integer> getNextInstancePath(
      JerboaRuleGenerated rule,
      JerboaRuleNode node,
      Integer splitRewriteLink,
      JerboaOrbit orbitType) {

    boolean REVERSE = false;
    List<Integer> pathToCompute = new ArrayList<>();
    LinkedList<Pair<JerboaRuleNode, List<Integer>>> queue = new LinkedList<>();
    queue.push(new Pair<JerboaRuleNode, List<Integer>>(node, new LinkedList<Integer>()));

    while (!queue.isEmpty()) {
      Pair<JerboaRuleNode, List<Integer>> p = queue.pollFirst();

      if (!REVERSE && p.l().isNotMarked()) {
        p.l().setMark(true);
      }

      if (p.l().equals(node) && REVERSE) {
        pathToCompute = p.r();
        break;
      }

      // Collect splitlink so that the computed path can actually travel from a
      // suborbit to another in a split
      if (p.l().getOrbit().contains(splitRewriteLink) && !REVERSE) {
        REVERSE = true;
        p.r().add(splitRewriteLink);
      }

      // Collect all implicit links from orbit type while not in REVERSE mode
      for (Integer index : orbitType) {
        if (p.l().getOrbit().contains(index) && !REVERSE) {
          p.r().add(index);
        }
      }

      for (Integer index : orbitType) {

        if (p.l().alpha(index) != null && !p.l().alpha(index).equals(p.l())) {

          JerboaRuleNode w = p.l().alpha(index);

          List<Integer> path = new ArrayList<Integer>(p.r());
          path.add(index);

          Pair<JerboaRuleNode, List<Integer>> q = new Pair<JerboaRuleNode, List<Integer>>(w, path);
          if (!REVERSE && q.l().isNotMarked()) {
            q.l().setMark(true);
            queue.push(q);
          } else if (REVERSE && !q.l().isNotMarked()) {
            q.l().setMark(false);
            queue.push(q);
          }
        }
      }
    }

    for (JerboaRuleNode n : rule.getRight()) {
      if (!n.isNotMarked()) n.setMark(false);
    }
    return pathToCompute;
  }
}

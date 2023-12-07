package fr.ensma.lias.jerboa.core.utils.rule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;

public class PathToolKit {

  public static List<Integer> getNextInstancePath(
      JerboaRuleGenerated rule, JerboaRuleNode node, Integer splitLink, JerboaOrbit orbitType) {

    boolean REVERSE = false;
    List<Integer> pathToCompute = new ArrayList<>();
    int dimension = rule.getOwner().getDimension();
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
      if (p.l().getOrbit().contains(splitLink) && !REVERSE) {
        REVERSE = true;
        p.r().add(splitLink);
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
      ;
    }
    return pathToCompute;
  }
}

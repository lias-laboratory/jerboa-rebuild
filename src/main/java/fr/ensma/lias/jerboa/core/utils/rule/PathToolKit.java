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
      System.out.println("cur node " + p.l());

      // if match
      if (p.l().equals(node) && !p.r().isEmpty()) {
        pathToCompute = p.r();
        break;
      }

      for (int index = 0; index <= dimension; index++) {

        if (p.l().alpha(index) == null) {
          if (orbitType.contains(index)) {

            if (index == splitLink) {
              // SplitLink might be ignored because a previous index might
              // actually hit a non-null link
              System.out.println("REVERSE: " + REVERSE);
              REVERSE = true;
            }
            if (!REVERSE && p.l().isNotMarked()) {
              p.r().add(index);
            } else if (REVERSE && !p.l().equals(node) && !p.l().isNotMarked()) {
              p.r().add(index);
            }
          }
        }

        // if index equals splitLink and p.l through index equals p.l
        if (p.l().alpha(index) != null) {
          JerboaRuleNode w = p.l().alpha(index);
          System.out.println("w(r): " + p.r());

          if (p.l().alpha(index).equals(p.l())) {}
          if (orbitType.contains(index)) {
            //
            List<Integer> path = new ArrayList<Integer>(p.r());
            path.add(index);

            Pair<JerboaRuleNode, List<Integer>> q =
                new Pair<JerboaRuleNode, List<Integer>>(w, path);
            if (!REVERSE && w.isNotMarked()) {
              w.setMark(true);
              // neighbors.push(w);
              queue.push(q);
            } else if (REVERSE && !w.isNotMarked()) {
              w.setMark(false);
              // neighbors.push(w);
              queue.push(q);
            }
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

package fr.ensma.lias.jerboa.core.utils.rule;

import java.util.ArrayList;
import java.util.Stack;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;

public class OrbitSearch {

  private JerboaOrbit orbit;
  private JerboaRuleNode root;
  private ArrayList<JerboaRuleNode> visited;

  public OrbitSearch(JerboaOrbit orbit, JerboaRuleNode root) {
    this.root = root;
    this.orbit = orbit;
    visited = new ArrayList<JerboaRuleNode>();
    search();
  }

  /*
   * searchAttachedKeptNode de Hakim Belhaouari
   */
  private void search() {
    var stack = new Stack<JerboaRuleNode>();
    stack.push(root);
    JerboaRuleNode node;
    while (true) {
      // Parcours de la stack. Tant que la stack n'est pas vide et que le
      // top element n'a pas été visité, node devient le top element. Si
      // la stack est vide la recherche se termine.
      do {
        if (stack.isEmpty()) {
          return;
        }
        node = (JerboaRuleNode) stack.pop();
      } while (visited.contains(node));

      visited.add(node);
      var orbitIter = orbit.iterator();

      // Pour chaque voisin v accessible via `orbit`, on ajoute v dans la
      // stack
      while (orbitIter.hasNext()) {
        var link = orbitIter.next();
        JerboaRuleNode neighbor = node.alpha(link);
        if (neighbor != null && neighbor != node && !visited.contains(neighbor)) {
          stack.push(neighbor);
        }
      }
    }
  }

  public ArrayList<JerboaRuleNode> getVisited() {
    return visited;
  }
}

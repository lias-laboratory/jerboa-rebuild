package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import fr.ensma.lias.jerboa.JerboaRebuiltModeler;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;

    public JerboaRebuiltRule(JerboaModeler modeler, String name, String category)
            throws JerboaException {
        super(modeler, name, category);
    }

    protected void computeTrackingConditions(List<JerboaEmbeddingInfo> trackers) {
        for (JerboaEmbeddingInfo tracker : trackers) {

        }
    }

    private boolean creationPredicate() {
        return true;
    }

    public int attachedNode(int arg0) {
        return 0;
    }

    public int reverseAssoc(int arg0) {
        return 0;
    }

    private List<Integer> search(JerboaOrbit orbit, JerboaRuleNode root) {
        List<Integer> visited = new ArrayList<Integer>();
        var stack = new Stack<JerboaRuleNode>();
        stack.push(root);
        JerboaRuleNode node;
        while (true) {
            // Parcours de la stack. Tant que la stack n'est pas vide et que le
            // top element n'a pas été visité, node devient le top element. Si
            // la stack est vide la recherche se termine.
            do {
                if (stack.isEmpty()) {
                    return visited;
                }
                node = (JerboaRuleNode) stack.pop();
            } while (visited.contains(node));

            visited.add(node.getID());
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

}

package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.CreationExpression;
import fr.ensma.lias.jerboa.core.rule.expression.MergeExpression;
import fr.ensma.lias.jerboa.core.rule.expression.ModifyExpression;
import fr.ensma.lias.jerboa.core.rule.expression.SplitExpression;
import fr.ensma.lias.jerboa.core.rule.expression.UnchangedExpression;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

    public enum Action {
        CREATE, DELETE, MERGE, SPLIT, MODIFY, UNCHANGED
    };

    public Action action = Action.MODIFY;

    public List<Integer> deletedLabels = new ArrayList<Integer>();

    public JerboaRebuiltRule(JerboaModeler modeler, String name, String category)
            throws JerboaException {
        super(modeler, name, category);
    }

    public int attachedNode(int arg0) {
        return 0;
    }

    public int reverseAssoc(int arg0) {
        return 0;
    }

    protected void computeExpressions(JerboaRebuiltModeler modeler) {
        var trackers = modeler.getTrackers();

        // Course through the trackers
        for (var tracker : trackers) {
            var visited = new ArrayList<Integer>();

            // Course through right hand side nodes
            for (var node : right) {
                if (visited.contains(node.getID())) {
                    continue;
                }
                var orbitNode = JerboaRuleNode.orbit(node, tracker.getOrbit());

                // Add orbitNode IDs to visited
                visited.addAll(orbitNode.stream().map(n -> n.getID()).collect(Collectors.toList()));

                // Course through orbit's filtered nodes
                for (JerboaRuleNode currentNode : orbitNode) {
                    if (isOrbitComplete(currentNode, tracker) && !left.contains(currentNode)) {
                        action = Action.CREATE;
                    } else if (isOrbitPreserved(currentNode, tracker)) {
                        action = Action.UNCHANGED;
                    } else {
                        printOrbitIDs(orbitNode);
                        action = Action.MODIFY;
                        break;
                    }
                }

                addExpression(node, tracker);

            }

            for (var node : left) {
                if (visited.contains(node.getID())) {
                    continue;
                }

                var orbitNode = JerboaRuleNode.orbit(node, tracker.getOrbit());
                visited.addAll(orbitNode.stream().map(n -> n.getID()).collect(Collectors.toList()));

                for (JerboaRuleNode currentNode : orbitNode) {
                    if (right.contains(currentNode)) {
                        action = Action.MODIFY;
                        addExpression(node, tracker);
                        break;
                    }
                    action = Action.DELETE;
                    System.out.println("Node " + currentNode + " is Deleted!");
                }

                addExpression(node, tracker);

            }

        }

    }

    private void printOrbitIDs(List<JerboaRuleNode> orbitNode) {
        System.out.printf("%s — %s: Modify case\n", name,
                orbitNode.stream().map(n -> n.getID()).collect(Collectors.toList()));
    }

    private boolean isOrbitPreserved(JerboaRuleNode node, JerboaEmbeddingInfo tracker) {

        for (var a : tracker.getOrbit()) {
            if (node.alpha(a) != left.get(node.getID()).alpha(a)) {
                return false;
            }
        }
        return node.getOrbit() == left.get(node.getID()).getOrbit();
    }

    private boolean isOrbitComplete(JerboaRuleNode node, JerboaEmbeddingInfo tracker) {
        var orbit = new ArrayList<Integer>();
        var innerLink = node.getOrbit();
        for (var a : tracker.getOrbit()) {
            if (node.alpha(a) != null)
                orbit.add(a);
        }
        for (var a : innerLink) {
            if (a != null && tracker.getOrbit().contains(a))
                orbit.add(a);
        }
        return orbit.size() == tracker.getOrbit().size();
    }

    private void addExpression(JerboaRuleNode node, JerboaEmbeddingInfo tracker) {
        switch (action) {
            case CREATE:
                node.addExpression(new CreationExpression(tracker));
                return;
            case DELETE:
                return;
            case MERGE:
                node.addExpression(new MergeExpression(tracker));
                return;
            case SPLIT:
                node.addExpression(new SplitExpression(tracker));
                return;
            case UNCHANGED:
                node.addExpression(new UnchangedExpression(tracker));
                return;
            default:
                node.addExpression(new ModifyExpression(tracker));
                return;
        }
    }

}

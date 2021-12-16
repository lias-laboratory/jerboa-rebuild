package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import fr.ensma.lias.jerboa.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.CreationExpression;
import fr.ensma.lias.jerboa.core.rule.expression.DeleteExpression;
import fr.ensma.lias.jerboa.core.rule.expression.MergeExpression;
import fr.ensma.lias.jerboa.core.rule.expression.ModifyExpression;
import fr.ensma.lias.jerboa.core.rule.expression.SplitExpression;
import fr.ensma.lias.jerboa.core.rule.expression.UnchangedExpression;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleExpression;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;

    public enum Action {
        CREATE, DELETE, MERGE, SPLIT, MODIFY, UNCHANGED
    };

    public Action action = Action.MODIFY;

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
        for (var tracker : modeler.getTrackers()) {
            List<Integer> visited = new ArrayList<>();
            for (var node : right) {
                if (visited.contains(node.getID())) {
                    continue;
                }
                // FIXME: this one is ODD it works but may fail majestically
                for (var currentNode : JerboaRuleNode.orbit(node, tracker.getOrbit())) {
                    visited.add(currentNode.getID());
                    if (isOrbitComplete(currentNode, tracker)) {
                        if (!left.contains(currentNode)) {
                            action = Action.CREATE;
                        }
                    } else if (isOrbitPreserved(currentNode, tracker)) {
                        action = Action.UNCHANGED;
                    } else {
                        action = Action.MODIFY;
                    }
                    addExpression(node, tracker);
                }

            }
        }

    }

    private boolean isOrbitPreserved(JerboaRuleNode node, JerboaEmbeddingInfo tracker) {

        for (var a : tracker.getOrbit()) {
            if (node.alpha(a) != left.get(node.getID()).alpha(a)) {
                return false;
            }
        }
        return true;
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
                node.addExpression(new DeleteExpression(tracker));
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

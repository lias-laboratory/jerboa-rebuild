package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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

    public int attachedNode(int arg0) {
        return 0;
    }

    public int reverseAssoc(int arg0) {
        return 0;
    }

    protected void computeTrackingConditions(List<JerboaEmbeddingInfo> trackers) {

        for (JerboaEmbeddingInfo tracker : trackers) {
            for (var node : right) {

                var nodeOrbit = JerboaRuleNode.orbit(node, tracker.getOrbit());
                if (creationCondition(tracker.getOrbit(), nodeOrbit)) {
                    node.addExpression(new TrackerRuleExpression(tracker, "Create"));
                } else if (unchangedCondition(tracker.getOrbit(), nodeOrbit)) {
                    node.addExpression(new TrackerRuleExpression(tracker, "Unchanged"));

                } else {
                    node.addExpression(new TrackerRuleExpression(tracker, "Modify"));
                }
            }
        }
    }

    private boolean creationCondition(JerboaOrbit orbit, List<JerboaRuleNode> nodeOrbit) {
        return isOrbitComplete(orbit, nodeOrbit) && areAllNotFoundInMember(left, nodeOrbit);
    }

    private boolean suppressionCondition(JerboaOrbit orbit, List<JerboaRuleNode> nodeOrbit) {
        return isOrbitComplete(orbit, nodeOrbit) && areAllNotFoundInMember(right, hooks);
    }

    private boolean unchangedCondition(JerboaOrbit orbit, List<JerboaRuleNode> nodeOrbit) {
        return isOrbitComplete(orbit, nodeOrbit) && areAllKept(nodeOrbit);
    }

    private boolean isOrbitComplete(JerboaOrbit orbit, List<JerboaRuleNode> nodeOrbit) {
        for (var node : nodeOrbit) {
            for (var link : orbit) {
                if (node.alpha(link) == null)
                    return false;
            }
        }
        return true;
    }

    private boolean areAllNotFoundInMember(List<JerboaRuleNode> member,
            List<JerboaRuleNode> nodeOrbit) {
        for (var node : nodeOrbit) {
            if (member.contains(node)) {
                return false;
            }
        }
        return true;
    }

    private boolean areAllKept(List<JerboaRuleNode> nodeOrbit) {
        for (var node : nodeOrbit) {
            if (!(left.contains(node) && right.contains(node))) {
                return false;
            }
        }
        return true;
    }
}

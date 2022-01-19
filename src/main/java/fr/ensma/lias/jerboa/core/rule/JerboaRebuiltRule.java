package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.CreationExpression;
import fr.ensma.lias.jerboa.core.rule.expression.ModifyExpression;
import fr.ensma.lias.jerboa.core.rule.expression.UnchangedExpression;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

    public StringBuilder deletedLabels = new StringBuilder();
    protected JerboaRebuiltModeler rebuiltModeler;

    public JerboaRebuiltRule(JerboaRebuiltModeler modeler, String name, String category) throws JerboaException {
        super(modeler, name, category);
        rebuiltModeler = modeler;
    }

    public int attachedNode(int arg0) {
        return 0;
    }

    public int reverseAssoc(int arg0) {
        return 0;
    }

    protected void enrichTrackingExpressions() {
        var trackers = rebuiltModeler.getTrackers();

        for (var tracker : trackers) {
            // List of visited nodes; Initialized at each orbit tracker
            var visited = new ArrayList<Integer>();

            // Course through right hand side nodes of the rule
            for (var node : right) {
                if (visited.contains(node.getID())) {
                    continue;
                }
                // subgraph is the list of all the nodes starting from `node`
                // and following tracker's orbit
                var subgraph = JerboaRuleNode.orbit(node, tracker.getOrbit());

                // this lambda adds all node's IDs from the subgraph to visited
                visited.addAll(subgraph.stream().map(n -> n.getID()).collect(Collectors.toList()));

                if (testCreationCondition(subgraph, tracker)) {
                    node.addExpression(new CreationExpression(tracker));
                } else if (testUnchangedCondition(subgraph, tracker)) {
                    node.addExpression(new UnchangedExpression(tracker));
                } else {
                    node.addExpression(new ModifyExpression(tracker));
                }
            }
        }
    }

    private boolean testCreationCondition(List<JerboaRuleNode> subgraph, JerboaEmbeddingInfo tracker) {
        return isOrbitFullyFiltered(subgraph, tracker) && isOrbitFullyCreated(subgraph);
    }

    private boolean testUnchangedCondition(List<JerboaRuleNode> subgraph, JerboaEmbeddingInfo tracker) {
        return isOrbitFullyPreExistant(subgraph) && isOrbitUnchanged(subgraph, tracker);
    }

    public boolean hasMidprocess() {
        return true;
    }

    public boolean midprocess(JerboaGMap gmap, List<JerboaRowPattern> leftPattern) throws JerboaException {

        // Process each tracker
        for (var tracker : rebuiltModeler.getTrackers()) {
            var visitedNodes = new ArrayList<Integer>();
            var visitedLabels = new ArrayList<Object>();

            // Process each `deleted` node
            for (var nodeID : deleted) {

                if (visitedNodes.contains(nodeID)) {
                    continue;
                }
                visitedNodes.add(nodeID);

                List<JerboaRuleNode> subgraph = JerboaRuleNode.orbit(left.get(nodeID), tracker.getOrbit());

                // this lambda adds all node's IDs from the subgraph to visited
                visitedNodes.addAll(subgraph.stream().map(n -> n.getID()).collect(Collectors.toList()));

                // Process label extraction if orbit is fully deleted
                if (isOrbitFullyFiltered(subgraph, tracker) && isOrbitFullyDeleted(subgraph)) {

                    for (JerboaRowPattern rowpattern : getLeftFilter()) {

                        var label = rowpattern.get(nodeID).getEmbedding(tracker.getID());

                        if (visitedLabels.contains(label)) {
                            continue;
                        }

                        visitedLabels.add(label);

                        deletedLabels.append(tracker.getName().substring(0, tracker.getName().length() - 7))
                                .append(" Delete Label:").append(label).append('\n');
                    }
                }
            }
        }

        return true;
    }

    public boolean hasPostprocess() {
        return true;
    }

    public void postprocess(JerboaGMap gmap, JerboaRuleResult res) throws JerboaException {
        System.out.println(deletedLabels.toString());
        deletedLabels = new StringBuilder();
    }

    private boolean isOrbitFullyFiltered(List<JerboaRuleNode> subgraph, JerboaEmbeddingInfo tracker) {
        for (var node : subgraph) {
            for (var aLink : tracker.getOrbit()) {
                // left side: check if aLink is not explicit
                // right side: check if aLink is not implicit
                if (node.alpha(aLink) == null && !node.getOrbit().contains(aLink))
                    // if in both cases it is false then
                    // the node is not filtering the tracker's orbit
                    return false;
            }
        }
        return true;
    }

    // If at least one node in subgraph is present in the left hand side of the
    // rule then the subgraph is not created
    private boolean isOrbitFullyCreated(List<JerboaRuleNode> subgraph) {
        for (var node : subgraph) {
            if (left.contains(node))
                return false;
        }
        return true;
    }

    private boolean isOrbitFullyDeleted(List<JerboaRuleNode> subgraph) {
        for (var node : subgraph) {
            if (right.contains(node)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOrbitFullyPreExistant(List<JerboaRuleNode> subgraph) {
        for (var node : subgraph) {
            if (reverseAssoc(node.getID()) == -1) {
                return false;
            }
        }
        return true;
    }

    // Check whether an explicit link between the left and right version of a node
    // is modified or not
    private boolean isExplicitLinkModified(Integer aLink, JerboaRuleNode leftNode, JerboaRuleNode rightNode) {
        return leftNode.alpha(aLink) != rightNode.alpha(aLink);
    }

    private boolean isImplicitLinkModified(Integer aLink, JerboaRuleNode leftNode, JerboaRuleNode rightNode) {
        return leftNode.getOrbit().contains(aLink) != rightNode.getOrbit().contains(aLink);
    }

    // Check if every node in the subgraph remains unchanged in regard
    // of the nodes in the left hand side of the rule
    private boolean isOrbitUnchanged(List<JerboaRuleNode> subgraph, JerboaEmbeddingInfo tracker) {
        for (var node : subgraph) {
            // for each explicit link test whether or it changed between left and
            // right
            for (var aLink : tracker.getOrbit()) {
                // Using reverseAssoc to match the actual node ID in left
                var leftNodeAssoc = left.get(reverseAssoc(node.getID()));
                if (isImplicitLinkModified(aLink, leftNodeAssoc, node)
                        || isExplicitLinkModified(aLink, leftNodeAssoc, node)) {
                    return false;
                }
            }
        }
        return true;
    }
}

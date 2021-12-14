package fr.ensma.lias.jerboa;

import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.util.JerboaModelerGeneric;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltModeler extends JerboaModelerGeneric {

    protected JerboaEmbeddingInfo vertexTracker;

    public JerboaRebuiltModeler(int dim) throws JerboaException {

        super(dim);

        vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
                fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);

    }


    /* Demonstrate feasibility of extract an orbit from a rulenode */
    // private List<JerboaRuleNode> extractOrbit(JerboaRuleGenerated rule, int nodeIndex,
    // JerboaOrbit orbit) {
    // var node = rule.getRightRuleNode(nodeIndex);
    // OrbitSearch os = new OrbitSearch(orbit, node);
    // return os.getVisited();
    // }

    /* Demonstrate how to add a rule expression within a rule node */
    // private void addExpressionToRule(JerboaRuleGenerated rule, JerboaEmbeddingInfo info,
    // int nodeIndex, String action) {
    // var node = rule.getRightRuleNode(nodeIndex);
    // node.addExpression(new TrackerRuleExpression(info, action));
    // }

    // private void updateTrackingEmbeddings() {
    // for (var rule : getRules()) {

    // var currentRule = (JerboaRuleGenerated) rule;
    // ArrayList<JerboaRuleNode> visited = new ArrayList<>();

    // // TODO: make the process valid for all action types

    // // Track creation actions
    // for (int index : currentRule.getCreatedIndexes()) {
    // var currentNode = currentRule.getRightRuleNode(index);
    // if (!visited.contains(currentNode)) {
    // var extracted = extractOrbit(currentRule, index, vertexTracker.getOrbit());
    // visited.addAll(extracted);
    // addExpressionToRule(currentRule, vertexTracker, index, "Create");

    // }
    // }
    // }
    // }

    @Override
    public void registerEbdsAndResetGMAP(JerboaEmbeddingInfo... ebd) throws JerboaException {
        for(JerboaEmbeddingInfo info : getAllEmbedding())
    }

    public final JerboaEmbeddingInfo getVertexTracker() {
        return vertexTracker;
    }

}

// JerboaRuleGenerated rule = (JerboaRuleGenerated) getRule("InsertVertex");
// for (int c : rule.getCreatedIndexes()) {
// JerboaRuleNode node = rule.getRight().get(c);
// int attachedID = rule.searchAttachedKeptNode(node); // protected
// // int attachedID = searchAttachedKeptNode(node, vertexTracker); // private
// if (attachedID != -1) {
// // System.out.println("SPREAD FROM nodeID:"+right.get(attachedID)+" to "+node);
// spreads.get(info.getID()).add(new Pair<Integer, Integer>(attachedID, node.getID()));
// }
// }


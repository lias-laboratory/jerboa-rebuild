package fr.ensma.lias.jerboa;

import fr.ensma.lias.jerboa.trackingModeler.JerboaTrackingModelerGenerated;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaModelerExtender extends JerboaTrackingModelerGenerated {

    // protected JerboaEmbeddingInfo vertexTracker;

    JerboaModelerExtender() throws JerboaException {

        super();

        // vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
        // fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);

        // this.addEmbedding(vertexTracker);
        // this.init();

        for (JerboaEmbeddingInfo info : getAllEmbedding()) {
            System.out.println(
                    "Embedding's name is: " + info.getName() + " Orbit is: " + info.getOrbit());
        }

        /*
         * The plan here is for each rule to add a JerboaRuleExpression
         */
        for (var rule : getRules()) {

            /*
             * We need to cast each rule to JerboaRuleGenerated to get to the actual data
             */
            var currentRule = (JerboaRuleGenerated) rule;

            /*
             * We create a string builder to have the choice to not create output in case some rules
             * does not have created indexes
             */
            StringBuilder sb = new StringBuilder();
            sb.append("Rule's name is: ").append(currentRule.getName());
            sb.append(" Expressions are: ");

            if (currentRule.getCreatedIndexes().length > 0) {

                /*
                 * There we get access to the rulenodes from the rule's right graph. We can
                 * potentially add some JerboaRuleExpression to the nodes and `express` how the rule
                 * affects the filtered part (left graph) of the gmap.
                 */
                for (int index : currentRule.getCreatedIndexes()) {
                    var ithNode = currentRule.getRightGraph().get(index);
                    // iNode.addExpression(null)
                    sb.append(ithNode).append(" ");
                }
                System.out.println(sb.toString());

            }
        }

    }

    // public final JerboaEmbeddingInfo getVertexTracker() {
    // return vertexTracke
    // }

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

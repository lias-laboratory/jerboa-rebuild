package fr.ensma.lias.jerboa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fr.ensma.lias.jerboa.trackingModeler.JerboaTrackingModelerGenerated;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
// import up.jerboa.core.JerboaEmbeddingInfo;
// import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

public class JerboaModelerExtender extends JerboaTrackingModelerGenerated {

    // protected JerboaEmbeddingInfo vertexTracker;

    JerboaModelerExtender() throws JerboaException {

        super();

        // vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
        // fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);

        // this.addEmbedding(vertexTracker);
        // this.init();

        var ebdsIter = getAllEmbedding().iterator();
        while (ebdsIter.hasNext()) {
            var info = ebdsIter.next();
            var infoName = info.getName();
            var infoOrbit = info.getOrbit();
            System.out.println("Embedding's name is: " + infoName + " Orbit is: " + infoOrbit);
        }

        /*
         * La volonté à cette étape est, dans chaque règle, d'ajouter une expression de plongement
         */
        var rulesIter = getRules().iterator();
        while (rulesIter.hasNext()) {
            JerboaRuleGenerated currentRule = (JerboaRuleGenerated) rulesIter.next();
            System.out.print("Rule's name is: " + currentRule.getName());
            System.out.print(" Deleted nodes are: ");
            // Tentative de récupération des nœuds
            for (int i : currentRule.getCreatedIndexes()) {
                var iNode = currentRule.getRightGraph().get(i);
                System.out.print(iNode + " ");
            }
            System.out.println();
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

    }

    // public final JerboaEmbeddingInfo getVertexTracker() {
    // return vertexTracke
    // }

}

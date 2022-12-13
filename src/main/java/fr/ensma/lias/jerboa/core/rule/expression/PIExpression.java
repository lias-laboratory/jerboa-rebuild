package fr.ensma.lias.jerboa.core.rule.expression;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentIdElement;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleExpression;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

public class PIExpression implements JerboaRuleExpression {

    private int embeddingID;
    private String embeddingName;

    public PIExpression(JerboaEmbeddingInfo ebdPI) {
        embeddingID = ebdPI.getID();
        embeddingName = ebdPI.getName();
    }

    @Override
    public Object compute(JerboaGMap gmap, JerboaRuleOperation rule, JerboaRowPattern rowPattern,
            JerboaRuleNode node) throws JerboaException {

        PersistentID PI = new PersistentID();
        JerboaRebuiltRule rebuiltRule = (JerboaRebuiltRule) rule;

        if (rowPattern != null) {
            /* find node in leftGraph */
            int nodeReverseID = rebuiltRule.reverseAssoc(node.getID());
            JerboaDart dart = null;

            /*
             * if node is created then find a node in left to compute PI else use pi from same node
             */
            if (nodeReverseID == -1) {
                /* ref is a hook IF hook still exists */
                int ref = getNodeReferenceID(rebuiltRule, node);
                /* if ref is not null then an oldPI is expected */
                if (ref == -1) {
                    PI = new PersistentID();
                } else {
                    dart = rowPattern.get(rule.getHooks().get(ref).getID());
                    PI = new PersistentID(dart.<PersistentID>ebd(embeddingID));
                }
            } else {
                PI = new PersistentID(rowPattern.get(nodeReverseID).<PersistentID>ebd(embeddingID));
            }

        }

        PI.add(new PersistentIdElement(rebuiltRule.getAppID(), node.getName()));
        return PI;

    }

    @Override
    public int getEmbedding() {
        return embeddingID;
    }

    @Override
    public String getName() {
        return embeddingName;
    }

    /*
     * Breadth first search, find a closest target neighbor
     *
     * @param rule JerboaOperationRule -> current rule
     *
     * @param node added (in Right) node to which we research the reference node's PI
     *
     * @return (Left) ID of closest preserved neighbor node or attached hook else -1 (pure creation
     * rule)
     */
    private int getNodeReferenceID(JerboaRebuiltRule rule, JerboaRuleNode node) {
        int dimension = rule.getOwner().getDimension();
        LinkedList<JerboaRuleNode> queue = new LinkedList<>();
        queue.push(node);

        while (!queue.isEmpty()) {
            JerboaRuleNode v = queue.pollFirst();

            if (rule.reverseAssoc(v.getID()) != -1) {
                return rule.reverseAssoc(v.getID());
            }

            for (int index = 0; index <= dimension; index++) {

                if (v.alpha(index) != null) {
                    JerboaRuleNode w = v.alpha(index);
                    if (w.isNotMarked()) {
                        w.setMark(true);
                        // neighbors.push(w);
                        queue.push(w);
                    }
                }
            }
        }

        for (JerboaRuleNode n : rule.getRight()) {
            if (!n.isNotMarked())
                n.setMark(false);;
        }
        return rule.attachedNode(node.getID());
    }

    /*
     * Replace nodes from rule's Right member with those from Left member if they do exist
     *
     * @param nodes from Right to replace with nodes from Left
     *
     * @param rule current given rule
     *
     * @return nodes from Left member of a given rule
     */
    // private void makeRightNodesLeftNodes(List<JerboaRuleNode> nodes, JerboaRebuiltRule rule) {
    // nodes.stream().filter(node -> rule.reverseAssoc(node.getID()) != -1)
    // .map((node) -> rule.getLeftRuleNode(rule.reverseAssoc(node.getID())))
    // .collect(Collectors.toList());
    // }

}

package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.core.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.CreationExpression;
import fr.ensma.lias.jerboa.core.rule.expression.MergeExpression;
import fr.ensma.lias.jerboa.core.rule.expression.ModifyExpression;
import fr.ensma.lias.jerboa.core.rule.expression.SplitExpression;
import fr.ensma.lias.jerboa.core.rule.expression.UnchangedExpression;
import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;
import up.jerboa.exception.JerboaException;

// TODO populate doc: javadoc style


public class JerboaRebuiltRule extends JerboaRuleGenerated {

    public StringBuilder deletedLabels = new StringBuilder();
    protected JerboaRebuiltModeler rebuiltModeler;

    public JerboaRebuiltRule(JerboaRebuiltModeler modeler, String name, String category)
            throws JerboaException {
        super(modeler, name, category);
        rebuiltModeler = modeler;
    }

    public int attachedNode(int arg0) {
        return 0;
    }

    public int reverseAssoc(int arg0) {
        return 0;
    }

    private void addAllNodesToVisited(List<JerboaRuleNode> orbit, List<Integer> visited) {
        visited.addAll(orbit.stream().map(n -> n.getID()).collect(Collectors.toList()));
    }

    protected void enrichTrackingExpressions() {
        List<JerboaEmbeddingInfo> trackers = rebuiltModeler.getTrackers();

        for (JerboaEmbeddingInfo tracker : trackers) {
            // List of visited nodes; Initialized at each orbit tracker
            var visited = new ArrayList<Integer>();

            // Course through right hand side nodes of the rule
            for (JerboaRuleNode node : right) {
                if (!visited.contains(node.getID())) {

                    // orbit is the list of all the nodes starting from `node`
                    // and following tracker's orbit
                    List<JerboaRuleNode> orbit = JerboaRuleNode.orbit(node, tracker.getOrbit());

                    addAllNodesToVisited(orbit, visited);

                    if (testCreationCondition(orbit, tracker)) {
                        node.addExpression(new CreationExpression(tracker));
                    } else if (testUnchangedCondition(orbit, tracker)) {
                        node.addExpression(new UnchangedExpression(tracker));
                    } else if (testSplitCondition(orbit, tracker)) {
                        node.addExpression(new SplitExpression(tracker));
                    } else if (testMergeCondition(orbit, tracker)) {
                        node.addExpression(new MergeExpression(tracker));
                    } else {
                        node.addExpression(new ModifyExpression(tracker));
                    }
                }
            }
        }
    }

    private boolean testCreationCondition(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        return /* isOrbitFullyFiltered(orbit, tracker) && */ isOrbitFullyCreated(orbit);
    }

    private boolean testUnchangedCondition(List<JerboaRuleNode> orbit,
            JerboaEmbeddingInfo tracker) {
        return isOrbitPreserved(orbit, tracker) && areNodesUnchanged(orbit, tracker);
    }

    private boolean testSplitCondition(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        return isOrbitSplitted(orbit, tracker) || isOrbitPatternSplitted(orbit, tracker);
    }

    private boolean testMergeCondition(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        return isOrbitMerged(orbit, tracker) || isOrbitPatternMerged(orbit, tracker);
    }

    private boolean isNodeCreated(JerboaRuleNode node) {
        return reverseAssoc(node.getID()) == -1;
    }

    private boolean isNodeCreated(Integer nodeID) {
        return reverseAssoc(nodeID) == -1;
    }

    private boolean isAlphaSet(JerboaRuleNode node, Integer aLink) {
        return node.alpha(aLink) != null;
    }

    private boolean isOrbitFullyFiltered(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        for (JerboaRuleNode node : orbit) {
            for (Integer aLink : tracker.getOrbit()) {
                // left side: check if aLink is not explicit
                // right side: check if aLink is not implicit
                if (!isAlphaSet(node, aLink) && !node.getOrbit().contains(aLink))
                    // if in both cases it is false then
                    // the node is not filtering the tracker's orbit
                    return false;
            }
        }
        return true;
    }

    // If at least one node in orbit is present in the left hand side of the
    // rule then the orbit is not created
    private boolean isOrbitFullyCreated(List<JerboaRuleNode> orbit) {
        for (JerboaRuleNode node : orbit) {
            if (!isNodeCreated(node))
                return false;
        }
        return true;
    }

    private boolean isOrbitFullyDeleted(List<JerboaRuleNode> orbit) {
        for (JerboaRuleNode node : orbit) {
            // if right contains node then node is not deleted
            if (right.contains(node)) {
                return false;
            }
        }
        return true;
    }

    /**
     * TODO : clean
     *
     * This method checks that every nodes of an orbit is preserved meaning that all of them must
     * have an equivalent in `left` AND the number of must remains the same
     *
     * @param rightOrbit an orbit from current rule's right side
     * @param tracker a JerboaEmbeddingInfo to have informations on a specific orbit type
     */
    private boolean isOrbitPreserved(List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {
        // // if at least one node is not associated to a node in left return false
        for (JerboaRuleNode rNode : rightOrbit) {
            if (isNodeCreated(rNode)) {
                return false;
            }
        }

        JerboaRuleNode leftNode = left.get(reverseAssoc(rightOrbit.get(0).getID()));
        List<JerboaRuleNode> leftOrbit = JerboaRuleNode.orbit(leftNode, tracker.getOrbit());

        for (JerboaRuleNode node : rightOrbit) {
            JerboaRuleNode lNode = left.get(reverseAssoc(node.getID()));
            if (!leftOrbit.contains(lNode))
                return false;
        }
        return leftOrbit.size() == rightOrbit.size();
    }

    /**
     * REVIEW
     *
     * Check whether an explicit link between the left and right version of a node is modified or
     * not
     */
    private boolean isExplicitLinkUnchanged(Integer aLink, JerboaRuleNode leftNode,
            JerboaRuleNode rightNode) {
        // if alpha is set in left it must be set in right
        // OR if alpha is not set in left it must not be set in right
        return isAlphaSet(leftNode, aLink) == isAlphaSet(rightNode, aLink);
    }


    /**
     * REVIEW
     *
     * Check for change in a node's orbit
     */
    private boolean isImplicitLinkUnchanged(Integer aLink, JerboaRuleNode leftNode,
            JerboaRuleNode rightNode) {
        // test that alink is present in both orbits
        return leftNode.getOrbit().contains(aLink) == rightNode.getOrbit().contains(aLink)
                // test that alink kept the same position in the orbit
                && leftNode.getOrbit().indexOf(aLink) == rightNode.getOrbit().indexOf(aLink);
    }

    /**
     * Check for each node in an orbit if its links (explicit and explicit) have changed
     */
    private boolean areNodesUnchanged(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        for (JerboaRuleNode node : orbit) {
            // for each value in tracker's orbit test if a link changed between
            // left and right
            for (Integer aLink : tracker.getOrbit()) {
                // Using reverseAssoc to match the actual node ID in left
                JerboaRuleNode leftNode = left.get(reverseAssoc(node.getID()));
                if (!isImplicitLinkUnchanged(aLink, leftNode, node)
                        || !isExplicitLinkUnchanged(aLink, leftNode, node)) {
                    return false;
                }
            }
        }
        return true;
    }

    private JerboaRuleNode getLeftNode(JerboaRuleNode node) {
        return left.get(reverseAssoc(node.getID()));
    }

    private List<JerboaRuleNode> getOrbit(JerboaRuleNode node, JerboaEmbeddingInfo tracker) {
        return JerboaRuleNode.orbit(node, tracker.getOrbit());
    }

    private List<JerboaRuleNode> getLeftOrbit(JerboaRuleNode node, JerboaEmbeddingInfo tracker) {
        return JerboaRuleNode.orbit(getLeftNode(node), tracker.getOrbit());
    }

    private boolean isOrbitMerged(List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {
        var leftNode = getLeftNode(rightOrbit.get(0));

        for (JerboaRuleNode rNode : rightOrbit) {
            if (isNodeCreated(rNode)) {
                break;
            }

            var lOrbit = getLeftOrbit(rNode, tracker);

            if (!lOrbit.contains(leftNode)) {
                return true;
            }

        }

        return false;

    }

    /**
     * REVIEW
     *
     * This method checks if two nodes belonging to two different orbits in `right` shared the same
     * orbit in `left`
     *
     * @param rightOrbit an orbit in `right`
     * @param tracker an embedding such as {vertex,half-face} tracker
     */
    private boolean isOrbitSplitted(List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {


        for (JerboaRuleNode rNode1 : right) {
            var rOrbit1 = getOrbit(rNode1, tracker);
            if (!isNodeCreated(rNode1)) {
                for (JerboaRuleNode rNode2 : right) {
                    if (rNode1 != rNode2) {
                        if (!isNodeCreated(rNode2) && !rOrbit1.contains(rNode2)) {
                            if (getLeftOrbit(rNode1, tracker).contains(getLeftNode(rNode2))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isOrbitMissingNode(List<JerboaRuleNode> leftOrbit,
            List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {

        Integer counter = 0;

        for (JerboaRuleNode rNode : rightOrbit) {
            int reverseID = reverseAssoc(rNode.getID());
            if (reverseID != -1) {
                if (leftOrbit.contains(left.get(reverseID))) {
                    counter += 1;
                }
            }
        }

        return counter != leftOrbit.size();
    }

    /**
     * REVIEW
     *
     * Initialize a 2D array. First row is a node index second row is an implicit link index. If a
     * link index is -1 or not in tracker's orbit then set the corresponding column to -1
     */
    private void initializeImplicitLinksArray(List<JerboaRuleNode> leftOrbit,
            JerboaEmbeddingInfo tracker, int orbitSize, int[] iLinksArray) {

        for (int iLinkIndex = 0; iLinkIndex < orbitSize; iLinkIndex++) {
            var counter = 0;
            for (int lNodeIndex = 0; lNodeIndex < leftOrbit.size(); lNodeIndex++) {
                var lNode = leftOrbit.get(lNodeIndex);
                if (!tracker.getOrbit().contains(lNode.getOrbit().get(iLinkIndex))) {
                    counter += 1;
                }
            }
            if (counter == leftOrbit.size())
                iLinksArray[iLinkIndex] = -1;
        }
    }


    /**
     * HACK me: maybe find a way to make this statement valid for merge case under logical NOT
     * operator
     *
     * Look for at least one untracked ith implicit link within rightOrbit
     */
    private boolean hasUntrackedIthLink(List<JerboaRuleNode> rightOrbit,
            JerboaEmbeddingInfo tracker, int[] iLinksArray) {

        // for each rNode
        for (var rNode : rightOrbit) {
            // for each iLink in rNode
            for (var iLink : rNode.getOrbit()) {
                // get iLinks' index
                var iLinkIndex = rNode.getOrbit().indexOf(iLink);
                // if iLink is not tracked
                if (!tracker.getOrbit().contains(iLink)) {
                    // and current iLink is supposed to be tracked
                    if (iLinksArray[iLinkIndex] != -1) {
                        // increment counter for the current ith iLink
                        iLinksArray[iLinkIndex] += 1;
                    }
                    // if a counter in iLinksArray equals the number of rNodes
                    if (iLinksArray[iLinkIndex] == rightOrbit.size()) {
                        // at least one ith iLink is never accessible for the
                        // current tracker, return false
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * REVIEW
     *
     * This method checks if there is at least one i-th implicit link present in an orbit from
     * `left` that is never reachable in the corresponding orbit from `right`.
     *
     * @param rightOrbit an orbit in `right`
     * @param tracker an embedding such as {vertex,half-face} tracker
     * @return true or false
     */
    private boolean isOrbitPatternSplitted(List<JerboaRuleNode> rightOrbit,
            JerboaEmbeddingInfo tracker) {

        JerboaRuleNode leftNode = getLeftNode(rightOrbit.get(0));
        List<JerboaRuleNode> leftOrbit = getOrbit(leftNode, tracker);

        // precondition -> all nodes in leftOrbit must be present in rightOrbit
        // if (isOrbitMissingNode(leftOrbit, rightOrbit, tracker))
        // return false;

        int nbImplicitLinks = leftNode.getOrbit().size();

        // track accessible implicit Links per nodes in leftOrbit
        int[] iLinksArray = new int[nbImplicitLinks];
        // compute untracked iLinks in leftOrbit
        initializeImplicitLinksArray(leftOrbit, tracker, nbImplicitLinks, iLinksArray);

        return hasUntrackedIthLink(rightOrbit, tracker, iLinksArray);
    }

    /**
     * TODO clean
     *
     */
    private boolean isOrbitPatternMerged(List<JerboaRuleNode> rightOrbit,
            JerboaEmbeddingInfo tracker) {

        for (var rNode : rightOrbit) {
            if (isNodeCreated(rNode)) {
                return false;
            }
        }

        JerboaRuleNode leftNode = getLeftNode(rightOrbit.get(0));
        List<JerboaRuleNode> leftOrbit = getOrbit(leftNode, tracker);

        int nbImplicitLinks = leftNode.getOrbit().size();

        int[] iLinksArray = new int[nbImplicitLinks];
        // compute untracked iLinks in leftOrbit
        initializeImplicitLinksArray(leftOrbit, tracker, nbImplicitLinks, iLinksArray);

        int[] iLinksArray2 = new int[nbImplicitLinks];
        // compute untracked iLinks in rightOrbit
        initializeImplicitLinksArray(rightOrbit, tracker, nbImplicitLinks, iLinksArray2);

        // if there is an ith implicit link that was untracked and is now
        // then the orbit pattern may have been merged
        for (int i = 0; i < nbImplicitLinks; i++) {
            if (iLinksArray[i] == -1 && iLinksArray2[i] != -1)
                return true;
        }

        return false;
    }

    public boolean hasMidprocess() {
        return true;
    }

    public boolean midprocess(JerboaGMap gmap, List<JerboaRowPattern> leftPattern)
            throws JerboaException {

        // Process each tracker
        for (JerboaEmbeddingInfo tracker : rebuiltModeler.getTrackers()) {
            var visitedNodes = new ArrayList<Integer>();
            var visitedLabels = new ArrayList<Integer>();

            // Process each `deleted` node
            for (Integer nodeID : deleted) {

                if (visitedNodes.contains(nodeID)) {
                    continue;
                }
                visitedNodes.add(nodeID);

                List<JerboaRuleNode> orbit =
                        JerboaRuleNode.orbit(left.get(nodeID), tracker.getOrbit());

                // this lambda adds all node's IDs from the orbit to visited
                visitedNodes
                        .addAll(orbit.stream().map(n -> n.getID()).collect(Collectors.toList()));

                // Process label extraction if orbit is fully deleted
                if (isOrbitFullyFiltered(orbit, tracker) && isOrbitFullyDeleted(orbit)) {

                    for (JerboaRowPattern row : getLeftFilter()) {

                        // Object label =
                        // rowpattern.get(nodeID).getEmbedding(tracker.getID());
                        Integer label = row.get(nodeID).<OrbitLabel>ebd(tracker.getID()).getLabel();

                        if (visitedLabels.contains(label)) {
                            continue;
                        }

                        visitedLabels.add(label);

                        deletedLabels
                                .append(tracker.getName().substring(0,
                                        tracker.getName().length() - 7))
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
}

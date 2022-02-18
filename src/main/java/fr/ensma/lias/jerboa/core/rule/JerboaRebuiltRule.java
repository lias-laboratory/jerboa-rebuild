package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.CreationExpression;
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
import up.jerboa.exception.JerboaException;

// TODO: rename methods to check unchanged orbit | enforce orbit type
// verification to prevent a super-orbit from being caught in place of one of
// its sub-orbit e.g.: <1,2,3> currently catches <1,2> and <0,1,3> catches <0,1>


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

    protected void enrichTrackingExpressions() {
        List<JerboaEmbeddingInfo> trackers = rebuiltModeler.getTrackers();

        for (JerboaEmbeddingInfo tracker : trackers) {
            // List of visited nodes; Initialized at each orbit tracker
            var visited = new ArrayList<Integer>();

            // Course through right hand side nodes of the rule
            for (JerboaRuleNode node : right) {
                if (visited.contains(node.getID())) {
                    continue;
                }

                // orbit is the list of all the nodes starting from `node`
                // and following tracker's orbit
                List<JerboaRuleNode> orbit = JerboaRuleNode.orbit(node, tracker.getOrbit());

                // this lambda register all node.getID() from orbit within visited
                visited.addAll(orbit.stream().map(n -> n.getID()).collect(Collectors.toList()));

                if (testCreationCondition(orbit, tracker)) {
                    node.addExpression(new CreationExpression(tracker));
                } else if (testUnchangedCondition(orbit, tracker)) {
                    node.addExpression(new UnchangedExpression(tracker));
                } else if (testSplitCondition(orbit, tracker)) {
                    node.addExpression(new SplitExpression(tracker));
                } else {
                    node.addExpression(new ModifyExpression(tracker));
                }
            }
        }
    }

    private boolean testCreationCondition(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        return /* isOrbitFullyFiltered(orbit, tracker) && */ isOrbitFullyCreated(orbit);
    }

    private boolean testUnchangedCondition(List<JerboaRuleNode> orbit,
            JerboaEmbeddingInfo tracker) {
        // TODO: clean everything here
        return isOrbitPreserved(orbit, tracker) && areNodesUnchanged(orbit, tracker);
    }

    private boolean testSplitCondition(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        // TODO: clean everything here
        return isOrbitSplitted(orbit, tracker) || isIthImplicitLinkReachable(orbit, tracker);
    }

    private boolean isOrbitFullyFiltered(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        for (var node : orbit) {
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

    // If at least one node in orbit is present in the left hand side of the
    // rule then the orbit is not created
    private boolean isOrbitFullyCreated(List<JerboaRuleNode> orbit) {
        for (var node : orbit) {
            if (left.contains(node))
                return false;
        }
        return true;
    }

    private boolean isOrbitFullyDeleted(List<JerboaRuleNode> orbit) {
        for (var node : orbit) {
            if (right.contains(node)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * This method checks that every nodes of an orbit is preserved meaning that all of them must
     * have an equivalent in `left` AND the number of must remains the same
     * </p>
     *
     * @param rightOrbit : an orbit from `right`
     * @param tracker : a custom embedding to have informations on a specific a orbit type
     */
    private boolean isOrbitPreserved(List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {
        // if at least one node is not associated to a node in left return false
        for (var node : rightOrbit) {
            if (reverseAssoc(node.getID()) == -1) {
                return false;
            }
        }

        var leftNode = left.get(reverseAssoc(rightOrbit.get(0).getID()));
        var leftOrbit = JerboaRuleNode.orbit(leftNode, tracker.getOrbit());
        // return leftOrbit.size() == rightOrbit.size();
        for (var node : rightOrbit) {
            var lNode = left.get(reverseAssoc(node.getID()));
            if (!leftOrbit.contains(lNode))
                return false;
        }
        return leftOrbit.size() == rightOrbit.size();

    }

    // Check whether an explicit link between the left and right version of a node
    // is modified or not
    private boolean isExplicitLinkUnchanged(Integer aLink, JerboaRuleNode leftNode,
            JerboaRuleNode rightNode) {
        if (leftNode.alpha(aLink) == null) {
            return rightNode.alpha(aLink) == null;
        }
        return rightNode.alpha(aLink) != null;
    }


    private boolean isImplicitLinkUnchanged(Integer aLink, JerboaRuleNode leftNode,
            JerboaRuleNode rightNode) {
        // test that alink is present in both orbits
        return leftNode.getOrbit().contains(aLink) == rightNode.getOrbit().contains(aLink)
                // test that alink kept the same position in the orbit
                && leftNode.getOrbit().indexOf(aLink) == rightNode.getOrbit().indexOf(aLink);
    }

    // Check if every node in the orbit remains unchanged in regard
    // of the nodes in the left hand side of the rule
    private boolean areNodesUnchanged(List<JerboaRuleNode> orbit, JerboaEmbeddingInfo tracker) {
        for (var node : orbit) {
            // for each explicit link test whether or it changed between left and
            // right
            for (var aLink : tracker.getOrbit()) {
                // Using reverseAssoc to match the actual node ID in left
                var leftNodeAssoc = left.get(reverseAssoc(node.getID()));
                if (!isImplicitLinkUnchanged(aLink, leftNodeAssoc, node)
                        || !isExplicitLinkUnchanged(aLink, leftNodeAssoc, node)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * TODO Check if it does actually work
     *
     * <p>
     * This method checks if two nodes belonging to two different orbits in `right` shared the same
     * orbit in `left`
     * </p>
     *
     * @param rightOrbit an orbit in `right`
     * @param tracker an embedding such as {vertex,half-face} tracker
     */
    private boolean isOrbitSplitted(List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {

        JerboaRuleNode rightNode = rightOrbit.get(0);
        JerboaRuleNode leftNode = left.get(reverseAssoc(rightNode.getID()));
        List<JerboaRuleNode> leftOrbit = JerboaRuleNode.orbit(leftNode, tracker.getOrbit());

        for (JerboaRuleNode rNode : right) {
            // if rightNode and rNode have the same orbit type
            if (rightNode.getOrbit().equals(rNode.getOrbit())) {
                // if rNode is not in the same orbit as rightNode
                if (!rightOrbit.contains(rNode)) {
                    int lNodeID = reverseAssoc(rNode.getID());
                    // if rNode exists in `left`
                    if (lNodeID >= 0) {
                        // if rNode and rightNode shared the same orbit in `left`
                        if (leftOrbit.contains(left.get(lNodeID))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isOrbitMissingNode(List<JerboaRuleNode> leftOrbit,
            List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {

        var nbLNodes = leftOrbit.size();
        var counter = 0;

        for (var rNode : rightOrbit) {
            int reverseID = reverseAssoc(rNode.getID());
            if (reverseID != -1) {
                if (leftOrbit.contains(left.get(reverseID))) {
                    counter += 1;
                }
            }
        }

        return counter != nbLNodes;
    }

    // REVIEW description, name, implementation
    // TODO change this method's name it is aweful
    // TODO cleanup needed
    /**
     * <p>
     * This method checks if there is at least one i-th implicit link present in an orbit from
     * `left` that is never reachable in the corresponding orbit from `right`.
     * </p>
     *
     * @param rightOrbit an orbit in `right`
     * @param tracker an embedding such as {vertex,half-face} tracker
     * @return true or false
     */
    private boolean isIthImplicitLinkReachable(List<JerboaRuleNode> rightOrbit,
            JerboaEmbeddingInfo tracker) {

        JerboaRuleNode leftNode = left.get(reverseAssoc(rightOrbit.get(0).getID()));
        List<JerboaRuleNode> leftOrbit = JerboaRuleNode.orbit(leftNode, tracker.getOrbit());

        // precondition -> all nodes in leftOrbit must be present in rightOrbit
        if (isOrbitMissingNode(leftOrbit, rightOrbit, tracker))
            return false;

        int nbImplicitLinks = leftNode.getOrbit().size();

        // 2D int array each value is the number of time an ith implicit link is
        // unreachable
        int[][] implicitLinks = new int[leftOrbit.size()][nbImplicitLinks];

        // initialize implicitLinks so that the untracked links are set to -1
        for (int i = 0; i < leftOrbit.size(); i++) {
            JerboaRuleNode lNode = leftOrbit.get(i);
            for (int j = 0; j < nbImplicitLinks; j++) {
                int iLink = lNode.getOrbit().get(j);
                if (!tracker.getOrbit().contains(iLink)) {
                    implicitLinks[i][j] = -1;
                }
            }
        }

        // for each ith implicit Link of each rNode increment the corresponding
        // index in implicitLinks. If an index is set to -1 it is already
        // missing or not tracked
        for (int i = 0; i < leftOrbit.size(); i++) {
            for (var rNode : rightOrbit) {
                for (int j = 0; j < nbImplicitLinks; j++) {
                    int iLink = rNode.getOrbit().get(j);
                    if (!tracker.getOrbit().contains(iLink)) {
                        if (implicitLinks[i][j] != -1) {
                            implicitLinks[i][j] += 1;
                        }
                        if (implicitLinks[i][j] == rightOrbit.size())
                            return true;
                    }
                }
            }
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

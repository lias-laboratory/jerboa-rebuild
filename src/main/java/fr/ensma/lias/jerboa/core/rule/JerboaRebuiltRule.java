package fr.ensma.lias.jerboa.core.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import fr.ensma.lias.jerboa.core.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.PIExpression;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.Event;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

    // public StringBuilder deletedLabels = new StringBuilder();
    // protected JerboaRebuiltModeler (JerboaRebuiltModeler) rebuiltModeler;
    protected int appID; // operation index in spec

    public JerboaRebuiltRule(JerboaRebuiltModeler modeler, String name, String category)
            throws JerboaException {
        super(modeler, name, category);
        // rebuiltModeler = (JerboaRebuiltModeler) modeler;
        this.appID = 1;
    }

    @Override
    public int attachedNode(int arg0) {
        return 0;
    }

    @Override
    public int reverseAssoc(int arg0) {
        return 0;
    }

    /**
     * Override of the computeEfficientTopoStructure method from JerboaRuleGenerated. Compute PIs
     * for each ruleNode.
     */
    @Override
    protected void computeEfficientTopoStructure() throws JerboaException {
        super.computeEfficientTopoStructure();
        for (JerboaRuleNode node : right) {
            node.addExpression(
                    new PIExpression(((JerboaRebuiltModeler) modeler).getPersistentID()));
        }
    }

    private ArrayList<PersistentID> collectPersistentIDs() {
        // TODO impl: collect persistent IDs to store in a PN

        // for all darts filtered by a hook node, collect a PI if it contains an
        // appID not yet registered within the list to return;
        return new ArrayList<>();
    }

    /**
     * Compute topological parameters (persistent names) for the current rule being applied. This
     * method is very likely to change in order to aggregate PersistentIDs (at the moment only one
     * is collected to at least match uml pattern)
     *
     * @param hooks JerboaInputHooks. Collection of JerboaDart used to retrieve PersistentID
     *
     * @return PNs. A LinkedList of PersistentName.
     */
    private LinkedList<PersistentName> computePersistentNames(JerboaInputHooks inputHooks) {

        LinkedList<PersistentName> PNs = new LinkedList<>();
        Iterator<JerboaRuleNode> nodeIterator = this.hooks.iterator();
        for (Iterator<JerboaDart> dartIterator = inputHooks.iterator(); dartIterator.hasNext();) {
            JerboaDart dart = dartIterator.next();
            JerboaRuleNode node = nodeIterator.next();
            PersistentName PN = new PersistentName();
            PN.add(new PersistentID(dart.<PersistentID>ebd("PersistentID")));
            PN.setOrbitType(node.getOrbit());
            PNs.add(PN);
        }

        return PNs;
    }

    /**
     * Override of the applyRule method from JerboaRuleGenerated. Retrieve hooks PIs and add an
     * entry in spec before trying to apply the current rule.
     */
    @Override
    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaInputHooks hooks)
            throws JerboaException {
        JerboaRuleResult res;

        LinkedList<PersistentName> PNs = computePersistentNames(hooks);
        // System.out.println("Check PNs to string : " + PNs.toString());

        try {
            setAppID(((JerboaRebuiltModeler) modeler).spec.getNextApplicationNumber());
            res = super.applyRule(gmap, hooks);
            ((JerboaRebuiltModeler) modeler).spec.addApplication(this, PNs, ApplicationType.INIT);
        } finally {
            System.out.println(((JerboaRebuiltModeler) modeler).spec.toString());
        }
        return res;
    }

    /**
     * Sets the field opID to the current operation's index in spec.
     *
     * @param int field opID
     */
    public void setAppID(int appID) {
        this.appID = appID;
    }

    /**
     * Return the field opID. Ths value refers the index of the current rule in spec.
     *
     * @return int field opID.
     */
    public int getAppID() {
        return appID;
    }

    /**
     * Check if a Right node is created
     *
     * @param node to check for creation
     *
     * @return true if Right node is created else false
     */
    public boolean isNodeCreated(JerboaRuleNode node) {
        return reverseAssoc(node.getID()) == -1 ? true : false;
    }

    /**
     * BFS to get closest hook in `left` from a given node
     *
     * @param node a given {@link JerboaRuleNode}
     * @param targets a list of {@link JerboaRuleNode} hooks
     * @return target a JerboaRuleNode hook
     */
    public JerboaRuleNode findClosestHook(JerboaRuleNode node, List<JerboaRuleNode> targets) {
        JerboaRuleNode target = null;
        int dimension = getOwner().getDimension();
        LinkedList<JerboaRuleNode> queue = new LinkedList<>();
        queue.push(node);

        while (!queue.isEmpty()) {
            JerboaRuleNode v = queue.pollFirst();

            if (targets.contains(v)) {
                target = v;
                break;
            }

            for (int index = 0; index <= dimension; index++) {

                if (v.alpha(index) != null) {
                    JerboaRuleNode w = v.alpha(index);

                    if (w.isNotMarked()) {
                        w.setMark(true);
                        queue.push(w);
                    }
                }
            }
        }

        for (JerboaRuleNode n : getLeft()) {
            if (!n.isNotMarked())
                n.setMark(false);;
        }

        if (target == null) {
            target = targets.get(0);
        }

        return target;
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
    public int findClosestPreservedNode(JerboaRuleNode node) {
        int dimension = getOwner().getDimension();
        LinkedList<JerboaRuleNode> queue = new LinkedList<>();
        queue.push(node);

        while (!queue.isEmpty()) {
            JerboaRuleNode v = queue.pollFirst();

            if (reverseAssoc(v.getID()) != -1) {
                return reverseAssoc(v.getID());
            }

            for (int index = 0; index <= dimension; index++) {

                if (v.alpha(index) != null) {
                    JerboaRuleNode w = v.alpha(index);
                    if (w.isNotMarked()) {
                        w.setMark(true);
                        queue.push(w);
                    }
                }
            }
        }

        for (JerboaRuleNode n : getRight()) {
            if (!n.isNotMarked())
                n.setMark(false);;
        }
        return attachedNode(node.getID());
    }

    /**
     * Check if all nodes in a given list of Right nodes are created
     *
     * @param nodeOrbit a list of Right nodes
     *
     * @param rule given to find an equivalent Left node to each nodes *if* they do exist
     *
     * @return true if all nodes ar created else false
     */
    public boolean isRuleNodesOrbitCreated(List<JerboaRuleNode> nodeOrbit) {
        // return true if all nodes are created else false
        return nodeOrbit.stream().allMatch((node) -> isNodeCreated(node));

        // same as above but actually readable
        // for (JerboaRuleNode node : nodeOrbit) {
        // if (((JerboaRebuiltRule) rule).isNodeCreated(node)) {
        // return false;
        // }
        // }
        // return true;
    }

    /**
     * Check if a rule node's orbit contains a created rule node
     *
     * @param ruleNodesOrbit a given list of rule nodes
     *
     * @return true if the orbit contains a created rule node else false
     */
    private boolean isThereAnyCreatedNode(List<JerboaRuleNode> ruleNodesOrbit) {

        // if at least a node is created return true
        return ruleNodesOrbit.stream().anyMatch((node) -> isNodeCreated(node));
    }

    /**
     * Check rule nodes' orbit is missing some rule node.
     *
     * @param ruleNodesOrbit a given list of right rule nodes
     * @param orbitType a JerboaOrbit
     * @return true if left rule nodes' list's size is the same as right rule nodes' list's size
     *         else false
     */
    private boolean isThereAnyMissingNode(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {

        JerboaRuleNode leftRuleNode = getLeftRuleNode(reverseAssoc(ruleNodesOrbit.get(0).getID()));
        List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

        return leftRuleNodesOrbit.size() == ruleNodesOrbit.size();
    }

    /**
     * Check if a node's aLink is set or not
     *
     * @param ruleNode a given node for which to test if a certain aLink exists
     *
     * @param aLink an integer defined between 0 and current modeler's dimension included
     *
     * @return true if an aLink is set for a given node else false
     */
    private boolean isAlphaSet(JerboaRuleNode ruleNode, Integer aLink) {
        return ruleNode.alpha(aLink) != null;
    }

    /**
     * Check if an explicit aLink has changed in between left and right side of this rule
     *
     * @param aLink an integer defined between 0 and current modeler's dimension included
     *
     * @param leftRuleNode a given jerboa rule node from the left hand side of this rule
     *
     * @param rightRuleNode a given jerboa rule node from the right hand side of this rule
     *
     * @return true if aLink has changed between the left and right rule nodes else false
     */
    private boolean isExplicitLinkUnchanged(int aLink, JerboaRuleNode leftRuleNode,
            JerboaRuleNode rightNode) {
        return isAlphaSet(leftRuleNode, aLink) == isAlphaSet(rightNode, aLink);
    }

    /**
     * Check if an implicit aLink's index or value is changed by this rule
     *
     * @param aLink an integer defined between 0 and current modeler's dimension included
     *
     * @param leftRuleNode a given jerboa rule node from the left hand side of this rule
     *
     * @param rightRuleNode a given jerboa rule node from the right hnad side of this rule
     *
     * @return true if aLink has changed between the left and right rule nodes else false
     */
    private boolean isImplicitLinkUnchanged(int aLink, JerboaRuleNode leftRuleNode,
            JerboaRuleNode rightRuleNode) {
        // test that alink is present in both orbit
        return leftRuleNode.getOrbit().contains(aLink) == rightRuleNode.getOrbit().contains(aLink)
                // test that alink kept the same position in the orbit
                && leftRuleNode.getOrbit().indexOf(aLink) == rightRuleNode.getOrbit()
                        .indexOf(aLink);
    }

    /**
     * Check if the nodes within rule node's orbit are unchanged
     *
     * @param ruleNodesOrbit a given list of rule nodes
     *
     * @param orbitType a given orbit type
     *
     * @return true if all nodes within ruleNodesOrbit are unchanged else false
     */

    private boolean areNodesUnchanged(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
        for (JerboaRuleNode node : ruleNodesOrbit) {
            // for each value in tracker's orbit test if a link changed between
            // left and right
            for (Integer aLink : orbitType) {
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

    /**
     * Check if a rule node's orbit is preserved or not
     *
     * @param ruleNodesOrbit a given list of rule nodes
     *
     * @return true if the orbit is preserved else false
     */
    private boolean isRuleNodesOrbitPreserved(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {
        return !isThereAnyCreatedNode(ruleNodesOrbit) // No Created Node
                && isThereAnyMissingNode(ruleNodesOrbit, orbitType) // No Missing Node
                && areNodesUnchanged(ruleNodesOrbit, orbitType); // No Change in (between) nodes
    }

    /**
     * Check if a rule node's orbit is unchanged or not
     *
     * @param ruleNodesOrbit a given list of rule nodes
     *
     * @param orbitType a given orbit type
     *
     * @return true if the orbit is unchanged else false
     */
    public boolean isRuleNodesOrbitUnchanged(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {
        return isRuleNodesOrbitPreserved(ruleNodesOrbit, orbitType)
                && areNodesUnchanged(ruleNodesOrbit, orbitType);
    }

    /**
     * This method checks if two nodes belonging to two different orbits in `right` shared the same
     * orbit in `left`
     *
     * @param ruleNodesOrbit an orbit in `right`
     * @param orbitType
     */
    private boolean isRuleNodesOrbitExplicitlySplitted(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {

        // if there is a preserved rule node that belongs to current rule node's
        // orbit in left and not in right then the orbit is explicitly splitted
        if (!left.isEmpty() && !right.isEmpty()) {

            List<JerboaRuleNode> leftRuleNodesOrbit = new ArrayList<>();
            for (JerboaRuleNode node : ruleNodesOrbit) {
                if (!isNodeCreated(node)) {
                    leftRuleNodesOrbit = JerboaRuleNode
                            .orbit(getLeftRuleNode(reverseAssoc(node.getID())), orbitType);
                    break;
                }
            }

            for (JerboaRuleNode leftRuleNode : left) {
                if (getRightIndexRuleNode(leftRuleNode.getName()) != -1
                        && leftRuleNodesOrbit.contains(leftRuleNode) && !ruleNodesOrbit
                                .contains(getRightRuleNodeFromLeftRuleNode(leftRuleNode))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method for isRuleNodesOrbitImplicitlySplitted. Initialize an array of the same size as
     * a rule node's orbit. This array is a mask on implicit aLink indexes. If an aLink's index is
     * always set to -1 or not in orbitType the corresponding column in aLinksArray is set to -1.
     *
     * @param leftRuleNodesOrbit a list of left rule nodes from which to compute aLinksArray
     * @param orbitType a JerboaOrbit
     * @param ruleNodesOrbitSize number of implicit aLinks within a rule node
     * @param aLinksArray an array of int working as a mask on implicit aLinks.
     */
    private void initializeImplicitLinksArray(List<JerboaRuleNode> leftRuleNodesOrbit,
            JerboaOrbit orbitType, int ruleNodesOrbitSize, int[] aLinksArray) {

        for (int iLinkIndex = 0; iLinkIndex < ruleNodesOrbitSize; iLinkIndex++) {
            var counter = 0;
            for (int lNodeIndex = 0; lNodeIndex < leftRuleNodesOrbit.size(); lNodeIndex++) {
                var lNode = leftRuleNodesOrbit.get(lNodeIndex);
                if (!orbitType.contains(lNode.getOrbit().get(iLinkIndex))) {
                    counter += 1;
                }
            }
            if (counter == leftRuleNodesOrbit.size())
                aLinksArray[iLinkIndex] = -1;
        }
    }

    /**
     * Look for at least one untracked ith implicit link within rightOrbit
     */

    /**
     * Helper method for isRuleNodesOrbitImplicitlySplitted. This method checks if for all right
     * rule nodes an ith aLink (which is not set to -1 in aLinksArray) never appears to belong to
     * orbitType.
     *
     * @param ruleNodesOrbit a list of right rule nodes from which to determine if there is a split
     *        or not
     * @param orbitType a JerboaOrbit
     * @param aLinksArray an array of int working as a mask on implicit aLinks.
     * @return true if an ith aLink is untracked else false
     */
    private boolean hasUntrackedIthLink(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType,
            int[] aLinksArray) {
        // for each ruleNode
        for (JerboaRuleNode ruleNode : ruleNodesOrbit) {
            // for each iLink in rNode
            for (int index = 0; index < ruleNode.getOrbit().size(); index++) {
                // if array index is -1 we do not need to test then continue
                if (aLinksArray[index] == -1) {
                    continue;
                }
                // if implicit arc at index is not in orbitType increment array value at index
                if (!orbitType.contains(ruleNode.getOrbit().get(index))) {
                    aLinksArray[index] += 1;
                }
                if (aLinksArray[index] == ruleNodesOrbit.size()) {
                    return true;
                }
            }
        }

        return false;
    }

    /***
     * This method checks if there is at least one i-th implicit aLink present in an orbit from
     * `left` that is never reachable in the corresponding orbit from `right`.
     *
     * @param ruleNodesOrbit a list of right rule nodes
     * @param orbitType a given JerboaOrbit
     * @return true if there is an untracked ith aLink else false
     */
    private boolean isRuleNodesOrbitImplicitlySplitted(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {

        // compute a collection of preserved rule nodes
        List<JerboaRuleNode> preservedRuleNodes = ruleNodesOrbit.stream()
                .filter((node) -> reverseAssoc(node.getID()) != -1).collect(Collectors.toList());

        // if there is no preserved rule nodes there is no split possible for this
        // ruleNodesOrbit
        if (preservedRuleNodes.isEmpty()) {
            return false;
        }

        JerboaRuleNode leftRuleNode =
                getLeftRuleNode(reverseAssoc(preservedRuleNodes.get(0).getID()));
        List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

        int nbImplicitLinks = leftRuleNodesOrbit.get(0).getOrbit().size();

        // track accessible implicit Links per nodes in leftOrbit
        int[] iLinksArray = new int[nbImplicitLinks];

        // compute untracked iLinks in leftOrbit
        initializeImplicitLinksArray(leftRuleNodesOrbit, orbitType, nbImplicitLinks, iLinksArray);

        return hasUntrackedIthLink(ruleNodesOrbit, orbitType, iLinksArray);
    }

    public boolean isRuleNodesOrbitSplitted(List<JerboaRuleNode> orbit, JerboaOrbit orbitType) {
        return isRuleNodesOrbitExplicitlySplitted(orbit, orbitType)
                || isRuleNodesOrbitImplicitlySplitted(orbit, orbitType);
    }

    private boolean isRuleNodesOrbitImplicitlyMerged(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {

        // compute a collection of preserved rule nodes
        List<JerboaRuleNode> preservedRuleNodes = ruleNodesOrbit.stream()
                .filter((node) -> reverseAssoc(node.getID()) != -1).collect(Collectors.toList());

        // if there is no preserved rule nodes there is no merge possible for this
        // ruleNodesOrbit
        if (preservedRuleNodes.isEmpty()) {
            return false;
        }

        JerboaRuleNode leftRuleNode =
                getLeftRuleNode(reverseAssoc(preservedRuleNodes.get(0).getID()));
        List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

        int nbImplicitLinks = ruleNodesOrbit.get(0).getOrbit().size();

        // track accessible implicit Links per nodes in leftOrbit
        int[] iLinksArray = new int[nbImplicitLinks];

        // compute untracked iLinks in leftOrbit
        initializeImplicitLinksArray(ruleNodesOrbit, orbitType, nbImplicitLinks, iLinksArray);

        return hasUntrackedIthLink(leftRuleNodesOrbit, orbitType, iLinksArray);
    }

    /**
     * This method checks if two nodes belonging to the same orbit in `right` belonged to at least
     * two separate orbits in `left`
     *
     * @param ruleNodesOrbit an orbit in `right`
     * @param orbitType
     */
    private boolean isRuleNodesOrbitExplicitlyMerged(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {

        // if there is a preserved rule node that belongs to current rule node's
        // orbit in right and not in left then the orbit is explicitly merged
        if (!left.isEmpty() && !right.isEmpty()) {

            List<JerboaRuleNode> leftRuleNodesOrbit = new ArrayList<>();
            for (JerboaRuleNode node : ruleNodesOrbit) {
                if (!isNodeCreated(node)) {
                    leftRuleNodesOrbit = JerboaRuleNode
                            .orbit(getLeftRuleNode(reverseAssoc(node.getID())), orbitType);
                    break;
                }
            }

            for (JerboaRuleNode rightRuleNode : right) {
                if (!isNodeCreated(rightRuleNode) //
                        && ruleNodesOrbit.contains(rightRuleNode) //
                        && !leftRuleNodesOrbit
                                .contains(getLeftRuleNode(reverseAssoc(rightRuleNode.getID())))) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isRuleNodesOrbitMerged(List<JerboaRuleNode> orbit, JerboaOrbit orbitType) {
        return isRuleNodesOrbitExplicitlyMerged(orbit, orbitType)
                || isRuleNodesOrbitImplicitlyMerged(orbit, orbitType);
    }

    public boolean isRuleNodesOrbitModified(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {
        return hasModifiedIthLink(ruleNodesOrbit, orbitType);
    }

    private boolean hasModifiedIthLink(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
        int hookIndex = attachedNode(ruleNodesOrbit.get(0).getID());
        if (hookIndex == -1) {
            return false;
        }
        JerboaRuleNode hookNode = getLeftRuleNode(hookIndex);
        List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(hookNode, orbitType);

        for (JerboaRuleNode leftRuleNode : leftRuleNodesOrbit) {
            for (JerboaRuleNode rightRuleNode : ruleNodesOrbit) {
                for (int aLinkIndex = 0; aLinkIndex < rightRuleNode.getOrbit()
                        .size(); aLinkIndex++) {
                    int leftALink = leftRuleNode.getOrbit().get(aLinkIndex);
                    int rightALink = rightRuleNode.getOrbit().get(aLinkIndex);
                    if (orbitType.contains(leftALink) && orbitType.contains(rightALink)
                            && leftALink != rightALink) {
                        return true;
                    }

                }
            }
        }
        return false;

    }

    /**
     * Collect implicit aLinks to build origin's orbit type if they belong to the given orbit type
     *
     * @param aLinkSet a Set containing the aLink to build the origin
     *
     * @param rightRuleNodesOrbit a given list of right rule nodes
     *
     * @param leftRuleNodesOrbit a given list of left rule nodes
     *
     * @param orbitType a given trace orbit
     */
    // private void collectRewrittenImplicitALinks(HashSet<Integer> aLinkSet,
    // List<JerboaRuleNode> leftRuleNodesOrbit, List<JerboaRuleNode> rightRuleNodesOrbit,
    // JerboaOrbit orbitType) {
    private void collectRewrittenImplicitALinks(HashSet<Integer> aLinkSet, JerboaRuleNode hookNode,
            List<JerboaRuleNode> rightRuleNodesOrbit, JerboaOrbit orbitType) {

        for (JerboaRuleNode rightRuleNode : rightRuleNodesOrbit) {

            // int controlRuleNodeIndex = attachedNode(rightRuleNode.getID());
            // JerboaRuleNode controlRuleNode = getLeftRuleNode(controlRuleNodeIndex);
            // if (!isNodeCreated(rightRuleNode)) {
            // var leftNodeIndex = reverseAssoc(rightRuleNode.getID());
            // controlRuleNode = getLeftRuleNode(leftNodeIndex);

            // }
            JerboaOrbit leftOrbit = hookNode.getOrbit();
            JerboaOrbit rightOrbit = rightRuleNode.getOrbit();

            for (int aLinkIndex = 0; aLinkIndex < leftOrbit.size(); aLinkIndex++) {
                int leftALink = leftOrbit.get(aLinkIndex);
                int rightALink = rightOrbit.get(aLinkIndex);
                if (orbitType.contains(rightALink)) {
                    aLinkSet.add(leftALink);
                }
            }
        }
    }

    /**
     * Collect aLinks to build origin's orbit type if they are not explicitly nor implicitly defined
     * in the rule (left and right hand sides). This condition concerns preserved nodes only.
     *
     * @param aLinkSet a Set containing the aLink to build the origin
     *
     * @param rightRuleNodesOrbit a given list of right rule nodes
     *
     * @param leftRuleNodesOrbit a given list of left rule nodes
     *
     * @param orbitType a given trace orbit
     */
    private void collectUnfilteredALinks(HashSet<Integer> aLinkSet,
            List<JerboaRuleNode> rightRuleNodesOrbit, JerboaOrbit orbitType) {


        List<JerboaRuleNode> reducedNodeList = rightRuleNodesOrbit.stream()
                .filter(node -> !isNodeCreated(node)).collect(Collectors.toList());
        if (!reducedNodeList.isEmpty()) {

            for (Integer aLink : orbitType) {
                if (reducedNodeList.stream().noneMatch(
                        (node) -> isAlphaSet(node, aLink) || node.getOrbit().contains(aLink))) {
                    aLinkSet.add(aLink);
                }
            }
        }

        // List<JerboaRuleNode> reducedNodeList = rightRuleNodesOrbit.stream().filter(node ->
        // !isNodeCreated(node))
        // .collect(Collectors.toList());
        // for(var ruleNode : reducedNodeList){
        // for(int aLink : orbitType){
        // if(isAlphaSet(ruleNode, aLink) || )
        // }
        // }
    }


    /**
     * Return the equivalent right rule node of a left rule node if it is preserved
     *
     * @param ruleNode a given left rule node
     *
     * @return a right rule node if it exists else null
     */
    private JerboaRuleNode getRightRuleNodeFromLeftRuleNode(JerboaRuleNode ruleNode) {
        int nodeIndex = getRightIndexRuleNode(ruleNode.getName());
        if (nodeIndex == -1)
            return null;
        return getRightRuleNode(nodeIndex);
    }

    // TODO split absorbs merge when there is both split and merge for same
    // parameters in rule --> to fix
    public Event getRuleNodesOrbitEvent(List<JerboaRuleNode> ruleNodesOrbit,
            JerboaOrbit orbitType) {
        if (isRuleNodesOrbitCreated(ruleNodesOrbit)) {
            return Event.CREATION;
        } else if (isRuleNodesOrbitUnchanged(ruleNodesOrbit, orbitType)) {
            return Event.NOMODIF;
        } else if (isRuleNodesOrbitSplitted(ruleNodesOrbit, orbitType)) {
            return Event.SPLIT;
        } else if (isRuleNodesOrbitMerged(ruleNodesOrbit, orbitType)) {
            return Event.MERGE;
        } else if (isRuleNodesOrbitModified(ruleNodesOrbit, orbitType)) {
            return Event.MODIFICATION;
        }
        return Event.NOEFFECT;
    }

    /**
     * Collect explicit aLinks to build origin's orbit type. Such aLink is collected if and only if
     * both its source is preserved and its target in Right belong to rightRuleNodesOrbit
     *
     * @param aLinkSet a Set containing the aLink to build the origin
     *
     * @param rightRuleNodesOrbit a given list of right rule nodes
     *
     * @param leftRuleNodesOrbit a given list of left rule nodes
     *
     * @param orbitType a given JerboaOrbit
     */
    private void collectPreservedExplicitALinks(HashSet<Integer> aLinkSet,
            List<JerboaRuleNode> leftRuleNodesOrbit, List<JerboaRuleNode> rightRuleNodesOrbit,
            JerboaOrbit orbitType) {

        for (int aLink : orbitType) {
            for (JerboaRuleNode leftRuleNode : leftRuleNodesOrbit) {
                if (isAlphaSet(leftRuleNode, aLink)) {
                    JerboaRuleNode preservedRightRuleNode =
                            getRightRuleNodeFromLeftRuleNode(leftRuleNode);
                    JerboaRuleNode otherPreservedRightRuleNode =
                            getRightRuleNodeFromLeftRuleNode(leftRuleNode.alpha(aLink));
                    if (preservedRightRuleNode != null && otherPreservedRightRuleNode != null
                            && preservedRightRuleNode.alpha(aLink)
                                    .equals(otherPreservedRightRuleNode)) {
                        aLinkSet.add(aLink);
                    }
                }
            }
        }
    }

    /**
     * Compute origin (orbit type) for a traced node and an orbit type in current rule. Assuming
     * that all hooks holds the same orbit label.
     *
     * @param nodeOrbitList traced orbit from which to compute origin
     *
     * @param orbitType traced orbit's type
     *
     * @return origin's orbit type
     */
    public JerboaOrbit computeBBOrigin(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

        if (left.isEmpty()) {
            return JerboaOrbit.orbit();
        }

        JerboaRuleNode hookNode;

        if (getHooks().size() == 1) {
            hookNode = getHooks().get(0);
        } else {
            int nodeOfInterest = findClosestPreservedNode(ruleNodesOrbit.get(0));
            if (nodeOfInterest != -1) {
                int leftNodeOfInterest = reverseAssoc(nodeOfInterest);

                JerboaRuleNode lNode = getLeftRuleNode(leftNodeOfInterest);
                hookNode = findClosestHook(lNode, getHooks());
            }
            hookNode = getHooks().get(0);
        }

        // List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(hookNode, orbitType);
        HashSet<Integer> aLinkSet = new HashSet<>();
        // collectRewrittenImplicitALinks(aLinkSet, leftRuleNodesOrbit, ruleNodesOrbit, orbitType);
        // NOTE: this method's call is for collecting aLinks only in `hook` node
        collectRewrittenImplicitALinks(aLinkSet, hookNode, ruleNodesOrbit, orbitType);
        // TODO: consider these methods in non-creation events only
        // collectUnfilteredALinks(aLinkSet, ruleNodesOrbit, orbitType);
        // collectPreservedExplicitALinks(aLinkSet, leftRuleNodesOrbit, ruleNodesOrbit, orbitType);
        return JerboaOrbit.orbit(aLinkSet);
    }

    // node is a right node
    public boolean isNodeHook(JerboaRuleNode node) {
        return !isNodeCreated(node) && getHooks().contains(left.get(reverseAssoc(node.getID())));
    }

}

// private void addAllNodesToVisited(List<JerboaRuleNode> orbit, List<Integer>
// visited) { visited.addAll(orbit.stream().map(n ->
// n.getID()).collect(Collectors.toList()));
// }

// protected void enrichTrackingExpressions() {
// List<JerboaEmbeddingInfo> trackers =
// ((JerboaRebuiltModeler)modeler).getTrackers();

// for (JerboaEmbeddingInfo tracker : trackers) {
// // List of visited nodes; Initialized at each orbit tracker
// var visited = new ArrayList<Integer>();

// // Course through right hand side nodes of the rule
// for (JerboaRuleNode node : right) {
// if (!visited.contains(node.getID())) {

// // orbit is the list of all the nodes starting from `node`
// // and following tracker's orbit
// List<JerboaRuleNode> orbit = JerboaRuleNode.orbit(node, tracker.getOrbit());

// addAllNodesToVisited(orbit, visited);

// if (testCreationCondition(orbit, tracker)) {
// node.addExpression(new CreationExpression(tracker));
// } else if (testUnchangedCondition(orbit, tracker)) {
// node.addExpression(new UnchangedExpression(tracker));
// } else if (testSplitCondition(orbit, tracker)) {
// node.addExpression(new SplitExpression(tracker));
// } else if (testMergeCondition(orbit, tracker)) {
// node.addExpression(new MergeExpression(tracker));
// } else {
// node.addExpression(new ModifyExpression(tracker));
// }
// }
// }
// }
// }

// private boolean testCreationCondition(List<JerboaRuleNode> orbit,
// JerboaEmbeddingInfo tracker) { return /** isOrbitFullyFiltered(orbit,
// tracker)
// && */ isOrbitFullyCreated(orbit);
// }

// private boolean testUnchangedCondition(List<JerboaRuleNode> orbit,
// JerboaEmbeddingInfo tracker) {
// return isOrbitPreserved(orbit, tracker) && areNodesUnchanged(orbit, tracker);
// }

// private boolean testMergeCondition(List<JerboaRuleNode> orbit,
// JerboaEmbeddingInfo tracker) { return isOrbitMerged(orbit, tracker) ||
// isOrbitPatternMerged(orbit, tracker);
// }

// private boolean isNodeCreated(JerboaRuleNode node) {
// return reverseAssoc(node.getID()) == -1;
// }

// private boolean isNodeCreated(Integer nodeID) {
// return reverseAssoc(nodeID) == -1;
// }

// private boolean isAlphaSet(JerboaRuleNode node, Integer aLink) {
// return node.alpha(aLink) != null;
// }

// private boolean isOrbitFullyFiltered(List<JerboaRuleNode> orbit,
// JerboaEmbeddingInfo tracker)
// {
// for (JerboaRuleNode node : orbit) {
// for (Integer aLink : tracker.getOrbit()) {
// // left side: check if aLink is not explicit
// // right side: check if aLink is not implicit
// if (!isAlphaSet(node, aLink) && !node.getOrbit().contains(aLink))
// // if in both cases it is false then
// // the node is not filtering the tracker's orbit
// return false;
// }
// }
// return true;
// }

// // If at least one node in orbit is present in the left hand side of the
// // rule then the orbit is not created
// private boolean isOrbitFullyCreated(List<JerboaRuleNode> orbit) {
// for (JerboaRuleNode node : orbit) {
// if (!isNodeCreated(node))
// return false;
// }
// return true;
// }

// private boolean isOrbitFullyDeleted(List<JerboaRuleNode> orbit) {
// for (JerboaRuleNode node : orbit) {
// // if right contains node then node is not deleted
// if (right.contains(node)) {
// return false;
// }
// }
// return true;
// }

// ISORBITPRESERVED section
// /***
// * This method checks that every nodes of an orbit is preserved meaning that
// all of them must
// * have an equivalent in `left` AND the number of must remains the same
// *
// * @param rightOrbit an orbit from current rule's right side
// * @param tracker a JerboaEmbeddingInfo to have informations on a specific
// orbit type
// */
// private boolean isOrbitPreserved(List<JerboaRuleNode> rightOrbit,
// JerboaEmbeddingInfo tracker) {
// // if at least one node is not associated to a node in left return false
// for (JerboaRuleNode rNode : rightOrbit) {
// if (isNodeCreated(rNode)) {
// return false;
// }
// }

// JerboaRuleNode leftNode = left.get(reverseAssoc(rightOrbit.get(0).getID()));
// List<JerboaRuleNode> leftOrbit = JerboaRuleNode.orbit(leftNode,
// tracker.getOrbit());

// for (JerboaRuleNode node : rightOrbit) {
// JerboaRuleNode lNode = left.get(reverseAssoc(node.getID()));
// if (!leftOrbit.contains(lNode))
// return false;
// }
// return leftOrbit.size() == rightOrbit.size();
// }

// /***
// * Check whether an explicit link between the left and right version of a node
// is modified or
// * not
// */
// private boolean isExplicitLinkUnchanged(Integer aLink, JerboaRuleNode
// leftNode, JerboaRuleNode rightNode) {
// // if alpha is set in left it must be set in right
// // OR if alpha is not set in left it must not be set in right
// return isAlphaSet(leftNode, aLink) == isAlphaSet(rightNode, aLink);
// }

// /***
// * Check for change in a node's orbit
// */
// private boolean isImplicitLinkUnchanged(Integer aLink, JerboaRuleNode
// leftNode, JerboaRuleNode rightNode) {
// // test that alink is present in both orbits
// return leftNode.getOrbit().contains(aLink) ==
// rightNode.getOrbit().contains(aLink)
// // test that alink kept the same position in the orbit
// && leftNode.getOrbit().indexOf(aLink) == rightNode.getOrbit().indexOf(aLink);
// }

// /***
// * Check for each node in an orbit if its links (explicit and explicit) have
// changed
// */
// private boolean areNodesUnchanged(List<JerboaRuleNode> orbit,
// JerboaEmbeddingInfo tracker) { for (JerboaRuleNode node : orbit) {
// // for each value in tracker's orbit test if a link changed between
// // left and right
// for (Integer aLink : tracker.getOrbit()) {
// // Using reverseAssoc to match the actual node ID in left
// JerboaRuleNode leftNode = left.get(reverseAssoc(node.getID()));
// if (!isImplicitLinkUnchanged(aLink, leftNode, node)
// || !isExplicitLinkUnchanged(aLink, leftNode, node)) {
// return false;
// }
// }
// }
// return true;
// }

// private boolean isOrbitMerged(List<JerboaRuleNode> rightOrbit,
// JerboaEmbeddingInfo tracker) { var leftNode = getLeftNode(rightOrbit.get(0));

// for (JerboaRuleNode rNode : rightOrbit) {
// if (isNodeCreated(rNode)) {
// break;
// }

// var lOrbit = getLeftOrbit(rNode, tracker);

// if (!lOrbit.contains(leftNode)) {
// return true;
// }

// }

// return false;

// }

// private boolean isOrbitMissingNode(List<JerboaRuleNode> leftOrbit,
// List<JerboaRuleNode> rightOrbit, JerboaEmbeddingInfo tracker) {

// Integer counter = 0;

// for (JerboaRuleNode rNode : rightOrbit) {
// int reverseID = reverseAssoc(rNode.getID());
// if (reverseID != -1) {
// if (leftOrbit.contains(left.get(reverseID))) {
// counter += 1;
// }
// }
// }

// return counter != leftOrbit.size();
// }

// /***
// * This method checks if there is at least one i-th implicit link present in
// an orbit from
// * `left` that is never reachable in the corresponding orbit from `right`.
// *
// * @param rightOrbit an orbit in `right`
// * @param tracker an embedding such as {vertex,half-face} tracker
// * @return true or false
// */
// private boolean isOrbitPatternSplitted(List<JerboaRuleNode> rightOrbit,
// JerboaEmbeddingInfo tracker) {

// JerboaRuleNode leftNode = getLeftNode(rightOrbit.get(0));
// List<JerboaRuleNode> leftOrbit = getOrbit(leftNode, tracker);

// // precondition -> all nodes in leftOrbit must be present in rightOrbit
// // if (isOrbitMissingNode(leftOrbit, rightOrbit, tracker))
// // return false;

// int nbImplicitLinks = leftNode.getOrbit().size();

// // track accessible implicit Links per nodes in leftOrbit
// int[] iLinksArray = new int[nbImplicitLinks];
// // compute untracked iLinks in leftOrbit
// initializeImplicitLinksArray(leftOrbit, tracker, nbImplicitLinks,
// iLinksArray);

// return hasUntrackedIthLink(rightOrbit, tracker, iLinksArray);
// }

// private boolean isOrbitPatternMerged(List<JerboaRuleNode> rightOrbit,
// JerboaEmbeddingInfo tracker) {

// for (var rNode : rightOrbit) {
// if (isNodeCreated(rNode)) {
// return false;
// }
// }

// JerboaRuleNode leftNode = getLeftNode(rightOrbit.get(0));
// List<JerboaRuleNode> leftOrbit = getOrbit(leftNode, tracker);

// int nbImplicitLinks = leftNode.getOrbit().size();

// int[] iLinksArray = new int[nbImplicitLinks];
// // compute untracked iLinks in leftOrbit
// initializeImplicitLinksArray(leftOrbit, tracker, nbImplicitLinks,
// iLinksArray);

// int[] iLinksArray2 = new int[nbImplicitLinks];
// // compute untracked iLinks in rightOrbit
// initializeImplicitLinksArray(rightOrbit, tracker, nbImplicitLinks,
// iLinksArray2);

// // if there is an ith implicit link that was untracked and is now
// // then the orbit pattern may have been merged
// for (int i = 0; i < nbImplicitLinks; i++) {
// if (iLinksArray[i] == -1 && iLinksArray2[i] != -1)
// return true;
// }

// return false;
// }

// public boolean hasMidprocess() {
// return true;
// }

// public boolean midprocess(JerboaGMap gmap, List<JerboaRowPattern>
// leftPattern) throws JerboaException {

// // Process each tracker
// for (JerboaEmbeddingInfo tracker :
// ((JerboaRebuiltModeler)modeler).getTrackers()) { var visitedNodes = new
// ArrayList<Integer>(); var visitedLabels = new ArrayList<Integer>();

// // Process each `deleted` node
// for (Integer nodeID : deleted) {

// if (visitedNodes.contains(nodeID)) {
// continue;
// }
// visitedNodes.add(nodeID);

// List<JerboaRuleNode> orbit =
// JerboaRuleNode.orbit(left.get(nodeID), tracker.getOrbit());

// // this lambda adds all node's IDs from the orbit to visited
// visitedNodes
// .addAll(orbit.stream().map(n -> n.getID()).collect(Collectors.toList()));

// // Process label extraction if orbit is fully deleted
// if (isOrbitFullyFiltered(orbit, tracker) && isOrbitFullyDeleted(orbit)) {

// for (JerboaRowPattern row : getLeftFilter()) {

// // Object label =
// // rowpattern.get(nodeID).getEmbedding(tracker.getID());
// Integer label = row.get(nodeID).<OrbitLabel>ebd(tracker.getID()).getLabel();

// if (visitedLabels.contains(label)) {
// continue;
// }

// visitedLabels.add(label);

// deletedLabels
// .append(tracker.getName().substring(0,
// tracker.getName().length() - 7))
// .append(" Delete Label:").append(label).append('\n');
// }
// }
// }
// }

// return true;
// }

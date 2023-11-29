package fr.ensma.lias.jerboa.core.rule;

import fr.ensma.lias.jerboa.core.JerboaRebuiltModeler;
import fr.ensma.lias.jerboa.core.rule.expression.PIExpression;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.Event;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

  // public StringBuilder deletedLabels = new StringBuilder();
  // protected JerboaRebuiltModeler (JerboaRebuiltModeler) rebuiltModeler;
  protected int appID; // operation index in spec - embedding parameter for persistent IDs

  public JerboaRebuiltRule(JerboaRebuiltModeler modeler, String name, String category)
      throws JerboaException {
    super(modeler, name, category);
    // rebuiltModeler = (JerboaRebuiltModeler) modeler;
    this.appID = 1;
  }

  @Override
  public int attachedNode(int arg0) {
    // default return: method overriden by generated rules
    return 0;
  }

  @Override
  public int reverseAssoc(int arg0) {
    // default return: method overriden by generated rules
    return 0;
  }

  /**
   * Override of the computeEfficientTopoStructure method from JerboaRuleGenerated. Compute PIs for
   * each ruleNode.
   */
  @Override
  protected void computeEfficientTopoStructure() throws JerboaException {
    super.computeEfficientTopoStructure();
    for (JerboaRuleNode node : right) {
      node.addExpression(new PIExpression(((JerboaRebuiltModeler) modeler).getPersistentID()));
    }
  }

  // // todo refactor: move to a suitable class (e.g: parametric specifaction)
  // private ArrayList<PersistentID> collectPersistentIDs() {
  // // todo impl: collect persistent IDs to store in a PN

  // // for all darts filtered by a hook node, collect a PI if it contains an
  // // appID not yet registered within the list to return;
  // return new ArrayList<>();
  // }

  // // todo refactor: move to a suitable class (e.g: parametric specifaction)
  // /**
  // * Compute topological parameters (persistent names) for the current rule being applied. This
  // * method is very likely to change in order to aggregate PersistentIDs (at the moment only one
  // * is collected to at least match uml pattern)
  // *
  // * @param hooks JerboaInputHooks. Collection of JerboaDart used to retrieve PersistentID
  // *
  // * @return PNs. A LinkedList of PersistentName.
  // */
  // private LinkedList<PersistentName> computePersistentNames(JerboaInputHooks inputHooks) {

  // LinkedList<PersistentName> PNs = new LinkedList<>();
  // Iterator<JerboaRuleNode> nodeIterator = this.hooks.iterator();
  // for (Iterator<JerboaDart> dartIterator = inputHooks.iterator(); dartIterator.hasNext();) {
  // JerboaDart dart = dartIterator.next();
  // JerboaRuleNode node = nodeIterator.next();
  // PersistentName PN = new PersistentName();
  // PN.add(new PersistentID(dart.<PersistentID>ebd("PersistentID")));
  // PN.setOrbitType(node.getOrbit());
  // PNs.add(PN);
  // }

  // return PNs;
  // }

  /**
   * Override of the applyRule method from JerboaRuleGenerated. Retrieve hooks PIs and add an entry
   * in spec before trying to apply the current rule.
   */
  @Override
  public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaInputHooks hooks)
      throws JerboaException {
    JerboaRuleResult res;

    LinkedList<PersistentName> PNs =
        ((JerboaRebuiltModeler) modeler).spec.computePersistentNames(this, hooks);
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
   * Check if a given {@link JerboaRuleNode} is created
   *
   * @param node A {@link JerboaRuleNode}
   * @return true if node is created else false
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
      if (!n.isNotMarked()) n.setMark(false);
      ;
    }

    if (target == null) {
      target = targets.get(0);
    }

    return target;
  }

  /**
   * Collect all labels between a source node to a target node within rule. This method is called to
   * compute a Path between two topological parameters in an evaluation or a reevaluation DAG.
   *
   * @param node A {@link JerboaRuleNode}
   * @param targets A list of {@link JerboaRuleNode}
   * @param hook A {@link JerboaRuleNode}
   * @return A list of Integers (the labels of a path)
   */
  public List<Integer> collectLabelsFromSourceToClosestTarget(
      JerboaRuleNode node, List<JerboaRuleNode> targets, JerboaRuleNode hook) {
    List<Integer> pathToCompute = new ArrayList<>();
    int dimension = getOwner().getDimension();
    LinkedList<Pair<JerboaRuleNode, List<Integer>>> queue = new LinkedList<>();
    queue.push(new Pair<JerboaRuleNode, List<Integer>>(node, Arrays.asList()));

    while (!queue.isEmpty()) {
      Pair<JerboaRuleNode, List<Integer>> p = queue.pollFirst();
      // JerboaRuleNode v = queue.pollFirst();

      // if match
      if (targets.contains(p.l())) {
        pathToCompute = p.r();
        if (hook == null) hook = p.l();
        break;
      }

      for (int index = 0; index <= dimension; index++) {

        if (p.l().alpha(index) != null) {
          JerboaRuleNode w = p.l().alpha(index);
          List<Integer> path = new ArrayList<Integer>(p.r());
          path.add(index);

          Pair<JerboaRuleNode, List<Integer>> q = new Pair<JerboaRuleNode, List<Integer>>(w, path);
          if (w.isNotMarked()) {
            w.setMark(true);
            // neighbors.push(w);
            queue.push(q);
          }
        }
      }
    }

    for (JerboaRuleNode n : getLeft()) {
      if (!n.isNotMarked()) n.setMark(false);
      ;
    }
    return pathToCompute;
  }

  /**
   * Breadth first search, find a closest preserved node to a source node
   *
   * @param node A source right {@link JerboaRuleNode}
   * @return int An ID of a preserved left {@link JerboaRuleNode} (possibly an attached hook) else
   *     -1 (pure creation rule)
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
      if (!n.isNotMarked()) n.setMark(false);
      ;
    }
    return attachedNode(node.getID());
  }

  /**
   * Check if all nodes in a given list of Right nodes are created
   *
   * @param nodeOrbit a list of Right {@link JerboaRuleNode}
   * @return true if all nodes are created else false
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
   * @param ruleNodesOrbit A list of right {@link JerboaRuleNode}
   * @return true if the orbit contains a least one created node else false
   */
  private boolean isThereAnyCreatedNode(List<JerboaRuleNode> ruleNodesOrbit) {

    // if at least a node is created return true
    return ruleNodesOrbit.stream().anyMatch((node) -> isNodeCreated(node));
  }

  /**
   * Check rule nodes' orbit is missing some rule node.
   *
   * @param ruleNodesOrbit A list of right {@link JerboaRuleNode}
   * @param orbitType A {@link JerboaOrbit} type
   * @return true if left rule nodes' list's size is the same as right rule nodes' list's size else
   *     false
   */
  private boolean isThereAnyMissingNode(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    JerboaRuleNode leftRuleNode = getLeftRuleNode(reverseAssoc(ruleNodesOrbit.get(0).getID()));
    List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

    return leftRuleNodesOrbit.size() == ruleNodesOrbit.size();
  }

  /**
   * Check if a node's aLink is set or not
   *
   * @param ruleNode a given {@link JerboaRuleNode} for which to test if a certain aLink exists
   * @param aLink an integer defined between 0 and current modeler's dimension included
   * @return true if an aLink is set for a given node else false
   */
  private boolean isAlphaSet(JerboaRuleNode ruleNode, Integer aLink) {
    return ruleNode.alpha(aLink) != null;
  }

  /**
   * Check if an explicit aLink has changed in between left and right side of this rule
   *
   * @param aLink an integer defined between 0 and current modeler's dimension included
   * @param leftRuleNode a given jerboa rule node from the left hand side of this rule
   * @param rightRuleNode a given jerboa rule node from the right hand side of this rule
   * @return true if aLink has changed between the left and right rule nodes else false
   */
  private boolean isExplicitLinkUnchanged(
      int aLink, JerboaRuleNode leftRuleNode, JerboaRuleNode rightNode) {
    return isAlphaSet(leftRuleNode, aLink) == isAlphaSet(rightNode, aLink);
  }

  /**
   * Check if an implicit aLink's index or value is changed by this rule
   *
   * @param aLink an integer defined between 0 and current modeler's dimension included
   * @param leftRuleNode a given {@link JerboaRuleNode} from the left hand side of this rule
   * @param rightRuleNode a given {@link JerboaRuleNode} from the right hnad side of this rule
   * @return true if aLink has changed between the left and right rule nodes else false
   */
  private boolean isImplicitLinkUnchanged(
      int aLink, JerboaRuleNode leftRuleNode, JerboaRuleNode rightRuleNode) {
    // test that alink is present in both orbit
    return leftRuleNode.getOrbit().contains(aLink) == rightRuleNode.getOrbit().contains(aLink)
        // test that alink kept the same position in the orbit
        && leftRuleNode.getOrbit().indexOf(aLink) == rightRuleNode.getOrbit().indexOf(aLink);
  }

  /**
   * Check if an orbit remains unchanged
   *
   * @param ruleNodesOrbit a list of {@link JerboaRuleNode}
   * @param orbitType a {@link JerboaOrbit} type
   * @return true if the orbit remains unchanged else false
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
   * Check if an orbit only contains preserved nodes
   *
   * @param ruleNodesOrbit A list right {@link JerboaRuleNode}
   * @return true if the orbit is preserved else false
   */
  private boolean isRuleNodesOrbitPreserved(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
    return !isThereAnyCreatedNode(ruleNodesOrbit) // No Created Node
        && isThereAnyMissingNode(ruleNodesOrbit, orbitType) // No Missing Node
        && areNodesUnchanged(ruleNodesOrbit, orbitType); // No Change in (between) nodes
  }

  /**
   * Check if nodes of an orbit remain unchanged
   *
   * @param ruleNodesOrbit A list of right {@link JerboaRuleNode}
   * @param orbitType A {@link JerboaOrbit} type
   * @return true if the orbit nodes remain unchanged else false
   */
  public boolean isRuleNodesOrbitUnchanged(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
    return isRuleNodesOrbitPreserved(ruleNodesOrbit, orbitType)
        && areNodesUnchanged(ruleNodesOrbit, orbitType);
  }

  /**
   * This method checks if two nodes belonging to two distinct right orbits shared the same left
   * orbit
   *
   * @param ruleNodesOrbit A list of right {@link JerboaRuleNode}
   * @param orbitType A {@link JerboaOrbit} type
   * @return true if the orbit is explicitly split else false
   */
  private boolean isRuleNodesOrbitExplicitlySplitted(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // if there is a preserved rule node that belongs to current rule node's
    // orbit in left and not in right then the orbit is explicitly splitted
    if (!left.isEmpty() && !right.isEmpty()) {

      List<JerboaRuleNode> leftRuleNodesOrbit = new ArrayList<>();
      for (JerboaRuleNode node : ruleNodesOrbit) {
        if (!isNodeCreated(node)) {
          leftRuleNodesOrbit =
              JerboaRuleNode.orbit(getLeftRuleNode(reverseAssoc(node.getID())), orbitType);
          break;
        }
      }

      for (JerboaRuleNode leftRuleNode : left) {
        if (getRightIndexRuleNode(leftRuleNode.getName()) != -1
            && leftRuleNodesOrbit.contains(leftRuleNode)
            && !ruleNodesOrbit.contains(getRightRuleNodeFromLeftRuleNode(leftRuleNode))) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Helper method for isRuleNodesOrbitImplicitlySplitted. Initialize an array of the same size as a
   * rule node's orbit. This array is a mask on implicit aLink indexes. If an aLink's index is
   * always set to -1 or not in orbitType the corresponding column in aLinksArray is set to -1.
   *
   * @param leftRuleNodesOrbit a list of left {@link JerboaRuleNode} from which to compute
   *     aLinksArray
   * @param orbitType a {@link JerboaOrbit} type
   * @param ruleNodesOrbitSize number of implicit aLinks within a rule node
   * @param aLinksArray an array of int working as a mask on implicit aLinks.
   */
  private void initializeImplicitLinksArray(
      List<JerboaRuleNode> leftRuleNodesOrbit,
      JerboaOrbit orbitType,
      int ruleNodesOrbitSize,
      int[] aLinksArray) {

    for (int iLinkIndex = 0; iLinkIndex < ruleNodesOrbitSize; iLinkIndex++) {
      var counter = 0;
      for (int lNodeIndex = 0; lNodeIndex < leftRuleNodesOrbit.size(); lNodeIndex++) {
        var lNode = leftRuleNodesOrbit.get(lNodeIndex);
        if (!orbitType.contains(lNode.getOrbit().get(iLinkIndex))) {
          counter += 1;
        }
      }
      if (counter == leftRuleNodesOrbit.size()) aLinksArray[iLinkIndex] = -1;
    }
  }

  /** Look for at least one untracked ith implicit link within rightOrbit */

  /**
   * Helper method for isRuleNodesOrbitImplicitlySplitted. This method checks if for all right rule
   * nodes an ith aLink (which is not set to -1 in aLinksArray) never appears to belong to
   * orbitType.
   *
   * @param ruleNodesOrbit a list of right {@link JerboaRuleNode} from which to determine if there
   *     is a split or not
   * @param orbitType a {@link JerboaOrbit} type
   * @param aLinksArray an array of int working as a mask on implicit aLinks.
   * @return true if an ith aLink is untracked else false
   */
  private boolean hasUntrackedIthLink(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType, int[] aLinksArray) {
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

  /**
   * This method checks if there is at least one i-th implicit aLink present in an orbit from `left`
   * that is never reachable in the corresponding orbit from `right`.
   *
   * @param ruleNodesOrbit A list of right {@link JerboaRuleNode}
   * @param orbitType a {@link JerboaOrbit} type
   * @return true if orbit is implicitly split else false
   */
  private boolean isRuleNodesOrbitImplicitlySplitted(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // compute a collection of preserved rule nodes
    List<JerboaRuleNode> preservedRuleNodes =
        ruleNodesOrbit.stream()
            .filter((node) -> reverseAssoc(node.getID()) != -1)
            .collect(Collectors.toList());

    // if there is no preserved rule nodes there is no split possible for this
    // ruleNodesOrbit
    if (preservedRuleNodes.isEmpty()) {
      return false;
    }

    JerboaRuleNode leftRuleNode = getLeftRuleNode(reverseAssoc(preservedRuleNodes.get(0).getID()));
    List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

    int nbImplicitLinks = leftRuleNodesOrbit.get(0).getOrbit().size();

    // track accessible implicit Links per nodes in leftOrbit
    int[] iLinksArray = new int[nbImplicitLinks];

    // compute untracked iLinks in leftOrbit
    initializeImplicitLinksArray(leftRuleNodesOrbit, orbitType, nbImplicitLinks, iLinksArray);

    return hasUntrackedIthLink(ruleNodesOrbit, orbitType, iLinksArray);
  }

  /**
   * Check if an orbit is either explicitly or implicitly split
   *
   * @param orbit A list of right {@link JerboaRuleNode}
   * @param orbitType A {@link JerboaOrbit} type
   * @return true if orbit is split else false
   */
  public boolean isRuleNodesOrbitSplitted(List<JerboaRuleNode> orbit, JerboaOrbit orbitType) {
    return isRuleNodesOrbitExplicitlySplitted(orbit, orbitType)
        || isRuleNodesOrbitImplicitlySplitted(orbit, orbitType);
  }

  /**
   * Check if an untrack i-th implicit link is rewritten into another implicit link which belongs to
   * a given orbit type. Such a rewrite characterizes an implicit merging.
   *
   * @param ruleNodesOrbit A liste of right {@link JerboaRuleNode}
   * @param orbitType A {@link JerboaOrbit} type
   * @return true if orbit is implicitly merged else false
   */
  private boolean isRuleNodesOrbitImplicitlyMerged(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // compute a collection of preserved rule nodes
    List<JerboaRuleNode> preservedRuleNodes =
        ruleNodesOrbit.stream()
            .filter((node) -> reverseAssoc(node.getID()) != -1)
            .collect(Collectors.toList());

    // if there is no preserved rule nodes there is no merge possible for this
    // ruleNodesOrbit
    if (preservedRuleNodes.isEmpty()) {
      return false;
    }

    JerboaRuleNode leftRuleNode = getLeftRuleNode(reverseAssoc(preservedRuleNodes.get(0).getID()));
    List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

    int nbImplicitLinks = ruleNodesOrbit.get(0).getOrbit().size();

    // track accessible implicit Links per nodes in leftOrbit
    int[] iLinksArray = new int[nbImplicitLinks];

    // compute untracked iLinks in leftOrbit
    initializeImplicitLinksArray(ruleNodesOrbit, orbitType, nbImplicitLinks, iLinksArray);

    return hasUntrackedIthLink(leftRuleNodesOrbit, orbitType, iLinksArray);
  }

  /**
   * This method checks if two right nodes (belonging to the same orbit) were in distinct left
   * orbits
   *
   * @param ruleNodesOrbit A liste of right {@link JerboaRuleNode}
   * @param orbitType A {@link JerboaOrbit} type
   * @return true if orbit is explicitly merged else false
   */
  private boolean isRuleNodesOrbitExplicitlyMerged(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // if there is a preserved rule node that belongs to current rule node's
    // orbit in right and not in left then the orbit is explicitly merged
    if (!left.isEmpty() && !right.isEmpty()) {

      List<JerboaRuleNode> leftRuleNodesOrbit = new ArrayList<>();
      for (JerboaRuleNode node : ruleNodesOrbit) {
        if (!isNodeCreated(node)) {
          leftRuleNodesOrbit =
              JerboaRuleNode.orbit(getLeftRuleNode(reverseAssoc(node.getID())), orbitType);
          break;
        }
      }

      for (JerboaRuleNode rightRuleNode : right) {
        if (!isNodeCreated(rightRuleNode) //
            && ruleNodesOrbit.contains(rightRuleNode) //
            && !leftRuleNodesOrbit.contains(getLeftRuleNode(reverseAssoc(rightRuleNode.getID())))) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * This method computes an orbit and determines if it is merged by the rule
   *
   * @param orbit A list of {@link JerboaRuleNode}
   * @param orbitType A {@JerboaOrbit} type of orbit
   * @return true if orbit is merged else false
   */
  public boolean isRuleNodesOrbitMerged(List<JerboaRuleNode> orbit, JerboaOrbit orbitType) {
    return isRuleNodesOrbitExplicitlyMerged(orbit, orbitType)
        || isRuleNodesOrbitImplicitlyMerged(orbit, orbitType);
  }

  /**
   * This method computes an orbit and determines if it is modified by the rule
   *
   * @param orbit A list of {@link JerboaRuleNode}
   * @param orbitType A {@JerboaOrbit} type of orbit
   * @return true if orbit is modified else false
   */
  public boolean isRuleNodesOrbitModified(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
    return hasModifiedIthLink(ruleNodesOrbit, orbitType);
  }

  /**
   * This method checks whether or not an implicit link is rewritten into another link which belongs
   * to a given orbit type
   *
   * @param ruleNodesOrbit A list of {@link JerboaRuleNode}
   * @param orbitType A {@JerboaOrbit} type
   * @return true if an implicit link is modified else false
   */
  private boolean hasModifiedIthLink(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
    int hookIndex = attachedNode(ruleNodesOrbit.get(0).getID());
    if (hookIndex == -1) {
      return false;
    }
    JerboaRuleNode hookNode = getLeftRuleNode(hookIndex);
    List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(hookNode, orbitType);

    for (JerboaRuleNode leftRuleNode : leftRuleNodesOrbit) {
      for (JerboaRuleNode rightRuleNode : ruleNodesOrbit) {
        for (int aLinkIndex = 0; aLinkIndex < rightRuleNode.getOrbit().size(); aLinkIndex++) {
          int leftALink = leftRuleNode.getOrbit().get(aLinkIndex);
          int rightALink = rightRuleNode.getOrbit().get(aLinkIndex);
          if (orbitType.contains(leftALink)
              && orbitType.contains(rightALink)
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
   * @param aLinkSet a Set of Integer containing the alpha links to build the origin
   * @param rightRuleNodesOrbit a given list of right {@link JerboaRuleNode}
   * @param leftRuleNodesOrbit a given list of left {@link JerboaRuleNode}
   * @param orbitType a {@link JerboaOrbit} type
   */
  // private void collectRewrittenImplicitALinks(HashSet<Integer> aLinkSet,
  // List<JerboaRuleNode> leftRuleNodesOrbit, List<JerboaRuleNode> rightRuleNodesOrbit,
  // JerboaOrbit orbitType) {
  private void collectRewrittenImplicitALinks(
      HashSet<Integer> aLinkSet,
      JerboaRuleNode hookNode,
      List<JerboaRuleNode> rightRuleNodesOrbit,
      JerboaOrbit orbitType) {

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
   * @param aLinkSet a Set of Integers containing the alpha links to build the origin
   * @param rightRuleNodesOrbit a given list of right {@link JerboaRuleNode}
   * @param leftRuleNodesOrbit a given list of left {@link JerboaRuleNode}
   * @param orbitType a {@link JerboaOrbit} type
   */
  private void collectUnfilteredALinks(
      HashSet<Integer> aLinkSet, List<JerboaRuleNode> rightRuleNodesOrbit, JerboaOrbit orbitType) {

    List<JerboaRuleNode> reducedNodeList =
        rightRuleNodesOrbit.stream()
            .filter(node -> !isNodeCreated(node))
            .collect(Collectors.toList());
    if (!reducedNodeList.isEmpty()) {

      for (Integer aLink : orbitType) {
        if (reducedNodeList.stream()
            .noneMatch((node) -> isAlphaSet(node, aLink) || node.getOrbit().contains(aLink))) {
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
   * @param ruleNode a {@link JerboaRuleNode}
   * @return a right {@link JerboaRuleNode} if it exists else null
   */
  private JerboaRuleNode getRightRuleNodeFromLeftRuleNode(JerboaRuleNode ruleNode) {
    String nodeName = ruleNode.getName();
    int rightIndex = getRightIndexRuleNode(nodeName);
    if (rightIndex == -1) return null;
    return getRightRuleNode(rightIndex);
  }

  // NOTE: Consider a mixed event SPLITMERGE when they both happen
  /**
   * Compute a topological change in an orbit depending on a specific orbit type
   *
   * @param ruleNodesOrbit A List of {@link JerboaRuleNode} making up an orbit
   * @param orbitType A {@link JerboaOrbit}
   * @return the computed {@link Event}
   */
  public Event getRuleNodesOrbitEvent(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
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
   * Compute a topological change in an orbit depending on a specific orbit type
   *
   * @param ruleNodesOrbit A List of {@link JerboaRuleNode} making up an orbit
   * @param orbitType A {@link JerboaOrbit}
   * @return the computed {@link Event}
   */
  public List<Event> getRuleNodesOrbitEvents(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
    List<Event> events = new ArrayList<>();

    if (isRuleNodesOrbitCreated(ruleNodesOrbit)) {
      events.add(Event.CREATION);
    }
    if (isRuleNodesOrbitUnchanged(ruleNodesOrbit, orbitType)) {
      events.add(Event.NOMODIF);
    }
    if (isRuleNodesOrbitSplitted(ruleNodesOrbit, orbitType)) {
      events.add(Event.SPLIT);
    }
    if (isRuleNodesOrbitMerged(ruleNodesOrbit, orbitType)) {
      events.add(Event.MERGE);
    }
    if (isRuleNodesOrbitModified(ruleNodesOrbit, orbitType)) {
      events.add(Event.MODIFICATION);
    }
    if (events.isEmpty()) {
      events.add(Event.NOEFFECT);
    }
    return events;
  }

  /**
   * Collect explicit aLinks to build origin's orbit type. Such aLink is collected if and only if
   * both its source is preserved and its target in Right belong to rightRuleNodesOrbit
   *
   * @param aLinkSet a Set of Integers containing the alpha links to build the origin
   * @param rightRuleNodesOrbit a given list of right {@link JerboaRuleNode}
   * @param leftRuleNodesOrbit a given list of left {@link JerboaRuleNode}
   * @param orbitType a {@link JerboaOrbit} type
   */
  private void collectPreservedExplicitALinks(
      HashSet<Integer> aLinkSet,
      List<JerboaRuleNode> leftRuleNodesOrbit,
      List<JerboaRuleNode> rightRuleNodesOrbit,
      JerboaOrbit orbitType) {

    for (int aLink : orbitType) {
      for (JerboaRuleNode leftRuleNode : leftRuleNodesOrbit) {
        if (isAlphaSet(leftRuleNode, aLink)) {
          JerboaRuleNode preservedRightRuleNode = getRightRuleNodeFromLeftRuleNode(leftRuleNode);
          JerboaRuleNode otherPreservedRightRuleNode =
              getRightRuleNodeFromLeftRuleNode(leftRuleNode.alpha(aLink));
          if (preservedRightRuleNode != null
              && otherPreservedRightRuleNode != null
              && preservedRightRuleNode.alpha(aLink).equals(otherPreservedRightRuleNode)) {
            aLinkSet.add(aLink);
          }
        }
      }
    }
  }

  /**
   * Compute origin (orbit type) for a traced node and an orbit type in current rule. Assuming that
   * all hooks holds the same orbit label.
   *
   * @param nodeOrbitList A list of {@link JerboaRuleNode} (traced orbit) from which to compute
   *     origin
   * @param orbitType A (traced) {@link JerboaOrbit} type
   * @return An (origin) {@link JerboaOrbit} type
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
    // collectUnfilteredALinks(aLinkSet, ruleNodesOrbit, orbitType);
    // collectPreservedExplicitALinks(aLinkSet, leftRuleNodesOrbit, ruleNodesOrbit, orbitType);
    return JerboaOrbit.orbit(aLinkSet);
  }

  /**
   * Check if a given {@link JerboaRuleNode} is a hook
   *
   * @param node A {@link JerboaRuleNode}
   * @return true if node is a hook else false
   */
  public boolean isNodeHook(JerboaRuleNode node) {
    return !isNodeCreated(node) && getHooks().contains(left.get(reverseAssoc(node.getID())));
  }
}

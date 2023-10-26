package fr.ensma.lias.jerboa.core.tracking;

import fr.ensma.lias.jerboa.core.utils.rule.ToolKit;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;

/** JerboaStaticTracking */
public class JerboaStaticDetection {

  private JerboaRuleGenerated rule;
  private int splitLink;
  private int splitLinkRewrite;

  public JerboaStaticDetection(JerboaRuleGenerated rule) {
    this.rule = rule;
    splitLink = -1;
    splitLinkRewrite = -1;
  }

  // HACK: temporary until a more adequate solution
  public int getSplitLink() {
    return splitLink;
  }

  public Integer getSplitLinkRewrites() {
    return splitLinkRewrite;
  }

  /**
   * Compute the event related to a orbit
   *
   * @param ruleNode a rule node of the orbit to compute
   * @param orbitType an orbit type
   * @return an event
   */
  public Event getEventFromOrbit(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {

    if (createdOrbit(ruleNode, orbitType)) {
      return Event.CREATION;
    } else if (unchangedOrbit(ruleNode, orbitType)) {
      return Event.NOMODIF;
    } else if (splittedOrbit(ruleNode, orbitType)) {
      return Event.SPLIT;
    } else if (mergedOrbit(ruleNode, orbitType)) {
      return Event.MERGE;
    } else if (modifiedOrbit(ruleNode, orbitType)) {
      return Event.MODIFICATION;
    }
    return Event.NOEFFECT;
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
  public JerboaOrbit computeOrigin(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {

    List<JerboaRuleNode> nodeOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);

    if (rule.getLeft().isEmpty()) {
      return JerboaOrbit.orbit();
    }

    JerboaRuleNode hookNode;

    if (rule.getHooks().size() == 1) {
      hookNode = rule.getHooks().get(0);
    } else {
      int nodeOfInterest = ToolKit.findClosestPreservedNode(rule, ruleNode);
      if (nodeOfInterest != -1) {
        int leftNodeOfInterest = rule.reverseAssoc(nodeOfInterest);

        JerboaRuleNode lNode = rule.getLeftRuleNode(leftNodeOfInterest);
        hookNode = ToolKit.findClosestHook(rule, lNode, rule.getHooks());
      }
      hookNode = rule.getHooks().get(0);
    }

    // List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(hookNode, orbitType);
    HashSet<Integer> aLinkSet = new HashSet<>();
    // collectRewrittenImplicitALinks(aLinkSet, leftRuleNodesOrbit, ruleNodesOrbit, orbitType);
    // NOTE: this method's call is for collecting aLinks only in `hook` node
    collectRewrittenImplicitALinks(aLinkSet, hookNode, nodeOrbit, orbitType);
    // TODO: consider these methods in non-creation events only
    // collectUnfilteredALinks(aLinkSet, ruleNodesOrbit, orbitType);
    // collectPreservedExplicitALinks(aLinkSet, leftRuleNodesOrbit, ruleNodesOrbit, orbitType);
    return JerboaOrbit.orbit(aLinkSet);
  }

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
   * Check if all nodes in a given list of Right nodes are created
   *
   * @param nodeOrbit a list of Right nodes
   * @param rule given to find an equivalent Left node to each nodes *if* they do exist
   * @return true if all nodes ar created else false
   */
  public boolean createdOrbit(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {
    List<JerboaRuleNode> nodeOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
    // return true if all nodes are created else false
    return nodeOrbit.stream().allMatch((node) -> isNodeCreated(node));
  }

  public boolean deletedOrbit(JerboaRuleNode leftRuleNode, JerboaOrbit orbitType) {
    List<JerboaRuleNode> nodeOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);
    return nodeOrbit.stream().allMatch((node) -> isNodeDeleted(node));
  }

  private boolean areAllNodesPreserved(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // Find a preserved node and get its left orbit
    List<JerboaRuleNode> leftRuleNodes = new ArrayList<>();
    for (JerboaRuleNode rNode : ruleNodesOrbit) {
      if (rule.getLeftIndexRuleNode(rNode.getName()) != -1) {
        JerboaRuleNode lNode = rule.getLeftRuleNode(rule.getLeftIndexRuleNode(rNode.getName()));
        leftRuleNodes = new ArrayList<>(JerboaRuleNode.orbit(lNode, orbitType));
        break;
      }
    }

    // Find if all left nodes are preservedin right
    for (JerboaRuleNode lNode : leftRuleNodes) {
      // If not
      if (rule.getRightIndexRuleNode(lNode.getName()) == -1) {
        return false;
      }
    }

    return leftRuleNodes.size() == ruleNodesOrbit.size();
  }

  /**
   * Check if a rule node's orbit is preserved or not
   *
   * @param ruleNodesOrbit a given list of rule nodes
   * @return true if the orbit is preserved else false
   */
  private boolean unchangedOrbit(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {
    List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
    // REVIEW: get through here to check for both implicit and explicit links preservation in <o>
    return areAllNodesPreserved(ruleNodesOrbit, orbitType)
        && areNodesUnchanged(ruleNodesOrbit, orbitType);
    // return !isThereAnyCreatedNode(ruleNodesOrbit) // No Created Node
    //     && isThereAnyMissingNode(ruleNodesOrbit, orbitType) // No Missing Node
    //     && areNodesUnchanged(ruleNodesOrbit, orbitType); // No Change in (between) nodes
  }

  public boolean splittedOrbit(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {
    List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
    return isRuleNodesOrbitExplicitlySplitted(ruleNodesOrbit, orbitType)
        || isRuleNodesOrbitImplicitlySplitted(ruleNodesOrbit, orbitType);
  }

  public boolean mergedOrbit(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {
    List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
    return isRuleNodesOrbitExplicitlyMerged(ruleNodesOrbit, orbitType)
        || isRuleNodesOrbitImplicitlyMerged(ruleNodesOrbit, orbitType);
  }

  public boolean modifiedOrbit(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {
    List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
    return hasRewrittenImplicitLink(ruleNodesOrbit, orbitType);
  }

  public boolean isNodeHook(JerboaRuleNode node) {
    return !isNodeCreated(node)
        && //
        this.rule
            .getHooks()
            .contains(this.rule.getLeft().get(this.rule.reverseAssoc(node.getID())));
  }

  /**
   * Check if a Right node is created
   *
   * @param node to check for creation
   * @return true if Right node is created else false
   */
  public boolean isNodeCreated(JerboaRuleNode node) {
    return rule.reverseAssoc(node.getID()) == -1 ? true : false;
  }

  public boolean isNodeDeleted(JerboaRuleNode node) {
    return rule.getRightIndexRuleNode(node.getName()) == -1 ? true : false;
  }

  /**
   * Check if a rule node's orbit contains a created rule node
   *
   * @param ruleNodesOrbit a given list of rule nodes
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
   * @return true if left rule nodes' list's size is the same as right rule nodes' list's size else
   *     false
   */
  private boolean isThereAnyMissingNode(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    JerboaRuleNode leftRuleNode =
        // rule.getLeftRuleNode(rule.reverseAssoc(ruleNodesOrbit.get(0).getID()));
        rule.getLeftRuleNode(rule.getLeftIndexRuleNode(ruleNodesOrbit.get(0).getName()));
    List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

    return leftRuleNodesOrbit.size() == ruleNodesOrbit.size();
  }

  /**
   * Check if a node's aLink is set or not
   *
   * @param ruleNode a given node for which to test if a certain aLink exists
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
   * @param leftRuleNode a given jerboa rule node from the left hand side of this rule
   * @param rightRuleNode a given jerboa rule node from the right hnad side of this rule
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
   * Check if the nodes within rule node's orbit are unchanged
   *
   * @param ruleNodesOrbit a given list of rule nodes
   * @param orbitType a given orbit type
   * @return true if all nodes within ruleNodesOrbit are unchanged else false
   */
  private boolean areNodesUnchanged(List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
    for (JerboaRuleNode node : ruleNodesOrbit) {
      // for each value in tracker's orbit test if a link changed between
      // left and right
      for (Integer aLink : orbitType) {
        // Using reverseAssoc to match the actual node ID in left
        JerboaRuleNode leftNode = rule.getLeft().get(rule.reverseAssoc(node.getID()));
        if (!isImplicitLinkUnchanged(aLink, leftNode, node)
            || !isExplicitLinkUnchanged(aLink, leftNode, node)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Return the equivalent right rule node of a left rule node if it is preserved
   *
   * @param ruleNode a given left rule node
   * @return a right rule node if it exists else null
   */
  private JerboaRuleNode getRightRuleNodeFromLeftRuleNode(JerboaRuleNode ruleNode) {
    int nodeIndex = rule.getRightIndexRuleNode(ruleNode.getName());
    if (nodeIndex == -1) return null;
    return rule.getRightRuleNode(nodeIndex);
  }

  /**
   * This method checks if two nodes belonging to two different orbits in `right` shared the same
   * orbit in `left`
   *
   * @param ruleNodesOrbit an orbit in `right`
   * @param orbitType
   */
  private boolean isRuleNodesOrbitExplicitlySplitted(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // if there is a preserved rule node that belongs to current rule node's
    // orbit in left and not in right then the orbit is explicitly splitted
    if (!rule.getLeft().isEmpty() && !rule.getRight().isEmpty()) {

      List<JerboaRuleNode> leftRuleNodesOrbit = new ArrayList<>();
      for (JerboaRuleNode node : ruleNodesOrbit) {
        if (!isNodeCreated(node)) {
          leftRuleNodesOrbit =
              JerboaRuleNode.orbit(
                  rule.getLeftRuleNode(rule.reverseAssoc(node.getID())), orbitType);
          break;
        }
      }

      for (JerboaRuleNode leftRuleNode : rule.getLeft()) {
        if (rule.getRightIndexRuleNode(leftRuleNode.getName()) != -1
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
   * @param leftRuleNodesOrbit a list of left rule nodes from which to compute aLinksArray
   * @param orbitType a JerboaOrbit
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

  /**
   * Helper method for isRuleNodesOrbitImplicitlySplitted. This method checks if for all right rule
   * nodes an ith aLink (which is not set to -1 in aLinksArray) never appears to belong to
   * orbitType.
   *
   * @param ruleNodesOrbit a list of right rule nodes from which to determine if there is a split or
   *     not
   * @param orbitType a JerboaOrbit
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
          // HACK: temporary until a more adequate solution
          if (splitLink == -1) {
            splitLink = rule.getHooks().get(0).getOrbit().get(index);
          }
          if (splitLinkRewrite == -1) {
            splitLinkRewrite = ruleNode.getOrbit().get(index);
          }

          aLinksArray[index] += 1;
        }
        if (aLinksArray[index] == ruleNodesOrbit.size()) {
          return true;
        }
      }
    }
    return false;
  }

  // public List<Integer> computeAllSplitLinks(JerboaRuleNode ruleNode, JerboaOrbit orbitType) {
  // List<JerboaRuleNode> nodeOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
  // return null;
  // }

  /***
   * This method checks if there is at least one i-th implicit aLink present in an orbit from
   * `left` that is never reachable in the corresponding orbit from `right`.
   *
   * @param ruleNodesOrbit a list of right rule nodes
   * @param orbitType a given JerboaOrbit
   * @return true if there is an untracked ith aLink else false
   */
  private boolean isRuleNodesOrbitImplicitlySplitted(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // compute a collection of preserved rule nodes
    List<JerboaRuleNode> preservedRuleNodes =
        ruleNodesOrbit.stream()
            .filter((node) -> rule.reverseAssoc(node.getID()) != -1)
            .collect(Collectors.toList());

    // if there is no preserved rule nodes there is no split possible for this
    // ruleNodesOrbit
    if (preservedRuleNodes.isEmpty()) {
      return false;
    }

    JerboaRuleNode leftRuleNode =
        rule.getLeftRuleNode(rule.reverseAssoc(preservedRuleNodes.get(0).getID()));
    List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

    int nbImplicitLinks = leftRuleNodesOrbit.get(0).getOrbit().size();

    // track accessible implicit Links per nodes in leftOrbit
    int[] iLinksArray = new int[nbImplicitLinks];

    // compute untracked iLinks in leftOrbit
    initializeImplicitLinksArray(leftRuleNodesOrbit, orbitType, nbImplicitLinks, iLinksArray);

    return hasUntrackedIthLink(ruleNodesOrbit, orbitType, iLinksArray);
  }

  /**
   * This method checks if two nodes belonging to the same orbit in `right` belonged to at least two
   * separate orbits in `left`
   *
   * @param ruleNodesOrbit an orbit in `right`
   * @param orbitType
   */
  private boolean isRuleNodesOrbitExplicitlyMerged(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // if there is a preserved rule node that belongs to current rule node's
    // orbit in right and not in left then the orbit is explicitly merged
    if (!rule.getLeft().isEmpty() && !rule.getRight().isEmpty()) {

      List<JerboaRuleNode> leftRuleNodesOrbit = new ArrayList<>();
      for (JerboaRuleNode node : ruleNodesOrbit) {
        if (!isNodeCreated(node)) {
          leftRuleNodesOrbit =
              JerboaRuleNode.orbit(
                  rule.getLeftRuleNode(rule.reverseAssoc(node.getID())), orbitType);
          break;
        }
      }

      for (JerboaRuleNode rightRuleNode : rule.getRight()) {
        if (!isNodeCreated(rightRuleNode) //
            && ruleNodesOrbit.contains(rightRuleNode) //
            && !leftRuleNodesOrbit.contains(
                rule.getLeftRuleNode(rule.reverseAssoc(rightRuleNode.getID())))) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean isRuleNodesOrbitImplicitlyMerged(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {

    // compute a collection of preserved rule nodes
    List<JerboaRuleNode> preservedRuleNodes =
        ruleNodesOrbit.stream()
            .filter((node) -> rule.reverseAssoc(node.getID()) != -1)
            .collect(Collectors.toList());

    // if there is no preserved rule nodes there is no merge possible for this
    // ruleNodesOrbit
    if (preservedRuleNodes.isEmpty()) {
      return false;
    }

    JerboaRuleNode leftRuleNode =
        rule.getLeftRuleNode(rule.reverseAssoc(preservedRuleNodes.get(0).getID()));
    List<JerboaRuleNode> leftRuleNodesOrbit = JerboaRuleNode.orbit(leftRuleNode, orbitType);

    int nbImplicitLinks = ruleNodesOrbit.get(0).getOrbit().size();

    // track accessible implicit Links per nodes in leftOrbit
    int[] iLinksArray = new int[nbImplicitLinks];

    // compute untracked iLinks in leftOrbit
    initializeImplicitLinksArray(ruleNodesOrbit, orbitType, nbImplicitLinks, iLinksArray);

    return hasUntrackedIthLink(leftRuleNodesOrbit, orbitType, iLinksArray);
  }

  private boolean hasRewrittenImplicitLink(
      List<JerboaRuleNode> ruleNodesOrbit, JerboaOrbit orbitType) {
    int hookIndex = rule.attachedNode(ruleNodesOrbit.get(0).getID());
    if (hookIndex == -1) {
      return false;
    }
    JerboaRuleNode hookNode = rule.getLeftRuleNode(hookIndex);
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
}

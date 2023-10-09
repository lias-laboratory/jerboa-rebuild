package fr.ensma.lias.jerboa.datastructures;

import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class ReevaluationTree2 {

  private List<List<LevelEventMT>> tree;
  private List<JerboaDart> topologicalParameters;
  private List<LevelEventMT> addedBranches;

  //// Constructor////////////////////////////////////////////////////////////
  ////

  public ReevaluationTree2() {
    this.tree = new ArrayList<>();
    this.topologicalParameters = new ArrayList<>();
    this.addedBranches = new ArrayList<>();
  }

  //// Getters////////////////////////////////////////////////////////////////

  public List<List<LevelEventMT>> getTree() {
    return tree;
  }

  public JerboaDart getTopologicalParameter(int index) {
    return topologicalParameters.get(index);
  }

  public List<JerboaDart> getTopologicalParameters() {
    return topologicalParameters;
  }

  public List<LevelEventMT> getBranch(int branchIndex) {
    return tree.get(branchIndex);
  }

  public LevelEventMT getBranchLastLevel(int branchIndex) {
    List<LevelEventMT> branch = getBranch(branchIndex);
    if (branch.isEmpty()) {
      return null;
    } else {
      return branch.get(branch.size() - 1);
    }
  }

  //// Reevaluation Methods///////////////////////////////////////////////////
  ////

  /**
   * Main method to add a new matching level in Reevaluation Tree
   *
   * @param evaluationTree {@link HistoryRecord}
   * @param levelEventEvaluation {@link LevelEventHR}
   * @param application {@link Application}
   * @param applicationResult {@link JerboaRuleResult}
   * @param controlDart {@link JerboaDart}
   */
  public void addLevel(
      HistoryRecord evaluationTree,
      LevelEventHR levelEventEvaluation,
      Application application,
      JerboaRuleResult applicationResult,
      JerboaDart controlDart) {

    JerboaRuleOperation rule = application.getRule();
    ApplicationType applicationType = application.getApplicationType();

    int nbBranches = 1;
    if (!topologicalParameters.isEmpty()) {
      nbBranches = topologicalParameters.size();
    }

    for (int branchIndex = 0; branchIndex < nbBranches; branchIndex++) {

      List<NodeEvent> newEventList = new ArrayList<>();
      List<NodeOrbit> newOrbitList = new ArrayList<>();
      switch (applicationType) {
        case INIT:
          String nodeName = levelEventEvaluation.getNextLevelOrbit().getNodeName();
          boolean NOEFFECT = isApplicationNOEFFECT(nodeName, branchIndex, applicationResult);
          matchDartParameters(
              branchIndex, levelEventEvaluation, nodeName, applicationResult, rule, NOEFFECT);
          matchLevel(
              branchIndex,
              levelEventEvaluation,
              newEventList,
              newOrbitList,
              application,
              nodeName,
              rule,
              NOEFFECT);
          registerLevel(
              branchIndex,
              levelEventEvaluation.getAppNumber(),
              newEventList,
              newOrbitList,
              applicationType);
          break;
        case ADD:
          int controlDartNodeIndex =
              getControlDartNodeID(controlDart, applicationResult, branchIndex);
          // List<EventNode> newEventList = new ArrayList<>();
          // List<NodeOrbit> newOrbitList = new ArrayList<>();
          computeAddedLevel(
              getBranchLastLevel(branchIndex),
              newOrbitList,
              newEventList,
              application,
              branchIndex,
              controlDartNodeIndex,
              applicationResult);
          registerLevel(
              branchIndex,
              application.getApplicationID(),
              newEventList,
              newOrbitList,
              applicationType);
          break;
        case DELETE:
          // TODO: prevent application
          // TODO: manage fully deleted orbit
          break;
        case MOVE:
          // TODO: manage side effects such as an orbit created (deleted) sooner or later
          break;
        default:
          break;
      }
    }
    return;
  }

  private void computeAddedLevel(
      LevelEventMT branchLastLevel,
      List<NodeOrbit> newOrbitList,
      List<NodeEvent> newEventList,
      Application application,
      int branchIndex,
      int controlDartNodeIndex,
      JerboaRuleResult applicationResult) {

    // Check if current branch is affected by added rule application
    boolean ISAFFECTED =
        isBranchMatched(topologicalParameters.get(branchIndex), applicationResult)
            && controlDartNodeIndex != -1;

    JerboaRuleGenerated addedRule = (JerboaRuleGenerated) application.getRule();

    // Register splitLinks -> may be useful
    List<Integer> splitLinks = new ArrayList<>();

    // For each event in branch last registered level
    for (NodeEvent oldEventNode : branchLastLevel.getEventList()) {

      // Get node orbit child from old node event
      NodeOrbit oldNodeOrbit = oldEventNode.getChild();

      // Create new Event with value NOEFFECT by default
      Event newEvent = Event.NOEFFECT;

      // Get orbit type from old node orbit
      JerboaOrbit orbitType = oldNodeOrbit.getOrbit();

      // When ISAFFECTED == true
      if (ISAFFECTED) {
        JerboaRuleNode ruleNode = application.getRule().getRightRuleNode(controlDartNodeIndex);
        JerboaStaticDetection detector = new JerboaStaticDetection(addedRule);
        // ~> compute event for new event
        newEvent = detector.getEventFromOrbit(ruleNode, orbitType);
        // ~> register splitLink
        splitLinks.add(detector.getSplitLink());
      } else {
        // Probably useless because splitLinks should be consulted only when
        // ISAFFECTED is `True` but add it just in case so that there is as much events as
        // splitLinks
        splitLinks.add(-1);
      }

      // Create new node events and node orbits
      NodeEvent newEventNode = new NodeEvent(newEvent);
      NodeOrbit newNodeOrbit =
          new NodeOrbit(orbitType, new ArrayList<>(oldNodeOrbit.getAlphaPath()));
      // Take care of setting their branch index
      newEventNode.setBranchIndex(branchIndex);
      newNodeOrbit.setBranchIndex(branchIndex);

      // Make newNodeOrbit as child of newEventNode
      newEventNode.setChild(newNodeOrbit);

      List<Link> newNodeOrbitChildren = new ArrayList<>();

      newNodeOrbit.setChildren(oldNodeOrbit.getChildren());
      newNodeOrbit.setChildren(newNodeOrbitChildren);
      newEventList.add(newEventNode);
      newOrbitList.add(newNodeOrbit);

      oldNodeOrbit.setChildren(
          new ArrayList<Link>(Arrays.asList(new Link(LinkType.TRACE, newEventNode))));
    }

    // For each index until size of newEventList
    for (int index = 0; index < newEventList.size(); index++) {
      // get index-th added event node
      NodeEvent addedEventNode = newEventList.get(index);
      // if event is both split AND branch is affected
      if (addedEventNode.getEvent() == Event.SPLIT && controlDartNodeIndex != -1) {

        // get link being splitted in rule
        int splitLink = splitLinks.get(index);
        JerboaOrbit orbitType = addedEventNode.getChild().getOrbit();
        final JerboaDart topoParam = getTopologicalParameter(branchIndex);

        // compute split orbits from application result
        List<List<JerboaDart>> splits =
            computeSplits(
                branchIndex,
                application.getRule(),
                orbitType,
                controlDartNodeIndex,
                applicationResult);

        splits.forEach(l -> System.out.println(l));

        List<JerboaDart> splitDarts = new ArrayList<>();
        List<JerboaDart> visits = new ArrayList<>();

        JerboaOrbit hookOrbitType = application.getRule().getHooks().get(0).getOrbit();

        Set<Integer> customOrbitTypeSet = new HashSet<>();

        /***********************************************************************/
        // This bit of code computes the largest common sub-orbit between an DAG
        // orbit node's type and a rule hook node's type. This HACK limits the
        // possible branches
        /***********************************************************************/
        for (var hookLink : hookOrbitType) {
          for (var nodeLink : orbitType) {
            if (hookLink == nodeLink) {
              customOrbitTypeSet.add(hookLink);
            }
          }
        }

        JerboaOrbit customOrbitType = new JerboaOrbit(customOrbitTypeSet);
        /***********************************************************************/

        JerboaDart dart = computeSplitAddedDart(topoParam, customOrbitType, splitLink, addedRule);
        while (!visits.contains(dart)) {
          visits.add(dart);
          dart = computeSplitAddedDart(dart, customOrbitType, splitLink, addedRule);
        }

        for (int i = 0; i < visits.size(); i++) {
          for (var split : splits) {
            System.out.println(split);
            System.out.println(visits.get(i));
            if (split.contains(visits.get(i))) {
              addBranch(newEventList, newOrbitList, application, branchIndex, split.get(0));
            }
          }
        }
      }
    }
  }

  private void registerLevel(
      int branchIndex,
      int applicationNumber,
      List<NodeEvent> eventList,
      List<NodeOrbit> orbitList,
      ApplicationType applicationType) {

    LevelEventMT newLevelEvent = new LevelEventMT(applicationNumber, eventList, applicationType);
    LevelOrbitMT newLevelOrbit =
        new LevelOrbitMT(getTopologicalParameter(branchIndex).getID(), orbitList);

    newLevelEvent.setNextLevelOrbit(newLevelOrbit);

    if (!tree.isEmpty()) {
      LevelEventMT lastRegisteredLevelEvent = getBranchLastLevel(branchIndex);
      lastRegisteredLevelEvent.getNextLevelOrbit().addNextLevelEventMT(newLevelEvent);
      getBranch(branchIndex).add(newLevelEvent);
      commitNewBranches(lastRegisteredLevelEvent);
      checkoutAddedBranches();
    } else if (branchIndex >= tree.size()) {
      tree.add(new ArrayList<LevelEventMT>(Arrays.asList(newLevelEvent)));
    }
  }

  private void checkoutAddedBranches() {
    addedBranches = new ArrayList<>();
  }

  private void commitNewBranches(LevelEventMT lastRegisteredLevelEvent) {
    for (LevelEventMT addedLevelEventMT : addedBranches) {
      tree.add(new ArrayList<LevelEventMT>(Arrays.asList(addedLevelEventMT)));
      connectLevelToBranch(lastRegisteredLevelEvent, addedLevelEventMT);
    }
  }

  private void connectLevelToBranch(
      LevelEventMT lastRegisteredLevelEvent, LevelEventMT addedLevelEventMT) {
    // For each parent node orbit
    for (NodeOrbit parentNodeOrbit : lastRegisteredLevelEvent.getNextLevelOrbit().getOrbitList()) {
      Link newChild = null;
      // select a child
      for (Link child : parentNodeOrbit.getChildren()) {
        // match child with a node orbit in added level event
        for (NodeEvent event : addedLevelEventMT.getEventList()) {
          // event.setBranchIndex(branchIndex);
          // event.getChild().setBranchIndex(branchIndex);
          // create link with event of match node orbit and similar link type
          if (child.getChild().getChild().getOrbit() == event.getChild().getOrbit()) {
            newChild = new Link(child.getType(), event);
          }
        }
      }
      // add child to parent node orbit
      if (newChild != null) parentNodeOrbit.addChild(newChild);
    }
    lastRegisteredLevelEvent.getNextLevelOrbit().addNextLevelEventMT(addedLevelEventMT);
  }

  //// Matching Methods///////////////////////////////////////////////////////
  ////

  private void matchDartParameters(
      int branchIndex,
      LevelEventHR levelEventEvaluation,
      String nodeName,
      JerboaRuleResult applicationResult,
      JerboaRuleOperation rule,
      boolean NOEFFECT) {
    if (NOEFFECT) {
      // TODO: method - control wether or not the current application remains NOEFFECT
      return;
    }

    if (topologicalParameters.isEmpty()) {
      int rowIndex = 0;
      int colIndex = rule.getRightIndexRuleNode(nodeName);
      JerboaDart dart = applicationResult.get(colIndex, rowIndex);
      topologicalParameters.add(dart);
    } else {

      int colIndex = rule.getRightIndexRuleNode(nodeName);

      int rowIndex = -1;
      for (int i = 0; i < applicationResult.sizeCol(); i++) {
        for (int j = 0; j < applicationResult.sizeRow(i); j++) {
          if (applicationResult.get(i, j) == getTopologicalParameter(branchIndex)) {
            rowIndex = j;
          }
        }
      }
      if (rowIndex != -1) {
        JerboaDart dart = applicationResult.get(colIndex, rowIndex);
        topologicalParameters.set(branchIndex, dart);
      }
    }
  }

  private void matchLevel(
      int branchIndex,
      LevelEventHR levelEventEvaluation,
      List<NodeEvent> newEventList,
      List<NodeOrbit> newOrbitList,
      Application application,
      String nodeName,
      JerboaRuleOperation rule,
      boolean NOEFFECT) {

    if (NOEFFECT) {
      return;
    }

    // JerboaRuleNode levelRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));

    for (NodeEvent nodeEvent : levelEventEvaluation.getEventList()) {

      NodeEvent newEventNode = new NodeEvent(nodeEvent.getEvent());
      NodeOrbit newOrbitNode =
          new NodeOrbit(
              nodeEvent.getChild().getOrbit(),
              new ArrayList<>(nodeEvent.getChild().getAlphaPath()));

      newEventNode.setChild(newOrbitNode);

      if (!tree.isEmpty()) {

        JerboaStaticDetection detector = new JerboaStaticDetection((JerboaRuleGenerated) rule);
        LevelEventMT parentEventList = getBranchLastLevel(branchIndex);

        for (NodeOrbit parentNodeOrbit : parentEventList.getNextLevelOrbit().getOrbitList()) {
          if (newEventNode.getEvent() == Event.CREATION
              || newEventNode.getEvent() == Event.SPLIT
              || newEventNode.getEvent() == Event.MERGE) {

            JerboaRuleNode originRuleNode =
                rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));

            for (int link : newOrbitNode.getAlphaPath()) {
              originRuleNode = originRuleNode.alpha(link);
            }

            JerboaOrbit detectedOrigin =
                detector.computeOrigin(originRuleNode, newOrbitNode.getOrbit());

            // If current parent orbit equals origin of new orbit
            if (parentNodeOrbit.getOrbit() == detectedOrigin) {

              parentNodeOrbit.addChild(new Link(LinkType.ORIGIN, newEventNode));
            }
          }
          if (newEventNode.getEvent() != Event.CREATION) {
            if (parentNodeOrbit.getOrbit() == newOrbitNode.getOrbit()) {
              parentNodeOrbit.addChild(new Link(LinkType.TRACE, newEventNode));
            }
          }
        }
      }

      newEventList.add(newEventNode);
      newOrbitList.add(newOrbitNode);
    }
    // JerboaOrbit orbitType = nodeEvent.getChild().getOrbit();

    // nodeEvent.setEvent(detector.getEventFromOrbit(levelRuleNode, orbitType));
    // nodeEvent.setBranchIndex(branchIndex);
    // nodeEvent.getChild().setBranchIndex(branchIndex);
  }

  //// Helpers////////////////////////////////////////////////////////////////
  ////

  private boolean isApplicationNOEFFECT(
      String nodeName, int branchIndex, JerboaRuleResult applicationResult) {
    return nodeName == "ø";
  }

  private int getControlDartNodeID(
      JerboaDart controlDart, JerboaRuleResult applicationResult, int topologicalParameterIndex) {
    int nodeIndex = -1;
    for (int i = 0; !topologicalParameters.isEmpty() && i < applicationResult.sizeCol(); i++) {
      if (applicationResult.get(i).contains(topologicalParameters.get(topologicalParameterIndex))
          || (controlDart != null && applicationResult.get(i).contains(controlDart))) {
        nodeIndex = i;
        break;
      }
    }
    return nodeIndex;
  }

  private boolean isBranchMatched(JerboaDart jerboaDart, JerboaRuleResult applicationResult) {
    for (int i = 0; i < applicationResult.sizeCol(); i++) {
      for (int j = 0; j < applicationResult.sizeRow(i); j++) {
        if (applicationResult.get(i, j) == jerboaDart) return true;
      }
    }
    return false;
  }

  private JerboaDart computeSplitAddedDart(
      JerboaDart dart, JerboaOrbit orbitType, int splitLink, JerboaRuleGenerated rule) {

    System.out.println("START: " + dart);

    if (orbitType.size() > 0) {
      for (int linkIndex = 0; linkIndex < orbitType.size(); linkIndex++) {
        int link = orbitType.get(linkIndex);
        dart = dart.alpha(link);
        System.out.println("\t" + link);
        if (link == splitLink) {
          dart = dart.alpha(link + 1);
          System.out.println("\t" + (link + 1));
          dart = dart.alpha(link);
          System.out.println("\t" + link);
        }
      }
    } else dart = dart.alpha(splitLink);

    System.out.println("END: " + dart);
    return dart;
  }

  /**
   * Compute all orbits of type orbitType within a {@link JerboaRuleResult} array and return them
   *
   * @param rule A {@link JerboaRuleOperation}
   * @param orbitType A {@link JerboaOrbit} type
   * @param controlDartNodeIndex An Integer
   * @param applicationResult A {@link JerboaRuleResult}
   * @return A list of list of {@link JerboaDart} (A list of orbits of type orbitType)
   */
  private List<List<JerboaDart>> computeSplits(
      int branchIndex,
      JerboaRuleOperation rule,
      JerboaOrbit orbitType,
      int controlDartNodeIndex,
      JerboaRuleResult applicationResult) {
    JerboaGMap gmap = rule.getOwner().getGMap();
    List<List<JerboaDart>> visitedOrbits = new ArrayList<>();

    for (int dartIndex = 0;
        dartIndex < applicationResult.sizeRow(controlDartNodeIndex);
        dartIndex++) {

      JerboaDart dart = applicationResult.get(controlDartNodeIndex, dartIndex);

      if (topologicalParameters.contains(dart)) {
        continue;
      }

      try {
        List<JerboaDart> orbit = gmap.orbit(dart, orbitType);
        if (!orbit.contains(topologicalParameters.get(branchIndex))
            && visitedOrbits.stream().noneMatch(l -> l.containsAll(orbit))) {
          visitedOrbits.add(orbit);
        }
      } catch (JerboaException e) {
        e.printStackTrace();
      }
    }
    return visitedOrbits;
  }

  private void addBranch(
      List<NodeEvent> newEventList,
      List<NodeOrbit> newOrbitList,
      Application application,
      int branchIndex,
      JerboaDart newDart) {

    LevelEventMT splitaddedLevelEvent =
        new LevelEventMT(
            application.getApplicationID(), newEventList, application.getApplicationType());
    LevelOrbitMT splitaddedLevelOrbit = new LevelOrbitMT(branchIndex, newOrbitList);

    splitaddedLevelEvent.setNextLevelOrbit(splitaddedLevelOrbit);
    splitaddedLevelOrbit.setDartID(newDart.getID());

    topologicalParameters.add(newDart);

    addedBranches.add(splitaddedLevelEvent);

    System.out.println("\tAddedBranch: " + splitaddedLevelEvent);
  }
  //// Outputs////////////////////////////////////////////////////////////////
  ////

  public void export(String directory, String fileName) {
    try {
      JSONPrinter.exportReevaluationTree(tree, directory, fileName);
    } catch (IOException exception) {
      System.out.println("Could not write to file");
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (var level : tree) {
      sb.append(level).append('\n');
      for (var orbit : level.get(0).getNextLevelOrbit().getOrbitList()) {
        sb.append(orbit.getChildren()).append('\n');
      }
    }
    return sb.toString();
  }
}

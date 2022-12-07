package fr.ensma.lias.jerboa.datastructures;

import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;

/**
 * This class represents a directed acyclic graph to trace the origins and the
 * evolution of a topological parameters described by a persistent name
 */
public class HistoryRecord {

  Map<Integer, List<LevelEventHR>> leaves;

  /**
   * Constructor. Initializes an empty history record.
   */
  public HistoryRecord() { leaves = new LinkedHashMap<>(); }

  /**
   * Constructor. Build a history record.
   *
   * @param pID Persistent ID of a topological parameter
   * @param orbitType a JerboaOrbit designating the orbit this history record is
   *     associated with
   * @param parametricSpecification
   */
  public HistoryRecord(PersistentID pID, JerboaOrbit orbitType,
                       ParametricSpecification parametricSpecification) {

    leaves = new LinkedHashMap<>();

    List<NodeOrbit> nodeOrbitList = new ArrayList<>();
    nodeOrbitList.add(new NodeOrbit(orbitType));

    List<PersistentIdElement> pIDElements = pID.getPIdElements();

    System.out.println("HistoryRecord: current PI is " + pID);
    for (int appIndex = parametricSpecification.size() - 1; appIndex >= 0;
         appIndex--) {

      PersistentIdElement pie = pIDElements.get(0);
      Application application =
          parametricSpecification.getApplications().get(appIndex);
      int appID = application.getApplicationID();

      if (isCurrentPIStoredInCurrentApplication(pID, application)) {
        continue;
      }

      boolean ISNOEFFECT = true;

      for (int pieIndex = 0; pieIndex < pIDElements.size(); pieIndex++) {
        pie = pIDElements.get(pieIndex);
        if (pie.getAppNumber() == appID) {
          ISNOEFFECT = false;
          break;
        }
      }

      if (ISNOEFFECT)
        pie = new PersistentIdElement(appID, "0");

      nodeOrbitList =
          addLevel(nodeOrbitList, pie, parametricSpecification, ISNOEFFECT);
    }
  }

  private boolean
  isCurrentPIStoredInCurrentApplication(PersistentID pID,
                                        Application application) {
    // HACK ugly mean to skip currents application current application
    // is skip because it cannot be part of its parameter's tree
    for (var PN : application.getPersistentNames()) {
      if (PN.getPIs().contains(pID)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the field leaves of this history record
   *
   * @return Map of this history record
   */
  public Map<Integer, List<LevelEventHR>> getLeaves() { return leaves; }

  /**
   * Get the top LevelEvent from this history record
   *
   * @return LevelEvent
   */
  private List<LevelEventHR> getTopLevelEvents() {
    List<Integer> entries = new LinkedList<Integer>(this.leaves.keySet());
    if (entries.isEmpty())
      return null;
    else {
      return leaves.get(((LinkedList<Integer>)entries).getLast());
    }
  }

  /**
   * Build a LevelEvent and add it to this history record.
   *
   * @param nodeOrbitList a list of {@link NodeOrbit}
   * @param pIDElement {@link PersistentIdElement} an element of a persistent ID
   *
   * @param parametricSpecification a {@link ParametricSpecification} instance
   * @param iSNOEFFECT
   *
   * @return a list of {@link NodeOrbit}s used for next/upper level construction
   */
  private List<NodeOrbit>
  addLevel(List<NodeOrbit> nodeOrbitList, PersistentIdElement pIDElement,
           ParametricSpecification parametricSpecification,
           boolean ISNOEFFECT) {

    List<LevelEventHR> entryLevelEvent = new ArrayList<>();
    List<NodeOrbit> nextStepOrbits = new ArrayList<>();
    String nodeName = pIDElement.getNodeName();
    int appNumber = pIDElement.getAppNumber();

    // boolean ISNOEFFECT = false;
    // if (!pIDElements.contains(pIDElement))
    // ISNOEFFECT = true;

    System.out.println("PIE is: " + pIDElement);
    System.out.println("noeffect is: " + ISNOEFFECT);

    LevelOrbitHR levelOrbit =
        new LevelOrbitHR(nodeName, nodeOrbitList, getTopLevelEvents());
    LevelEventHR levelEvent = new LevelEventHR(appNumber, levelOrbit);

    JerboaRuleOperation currentRule =
        parametricSpecification.getApplicationByID(appNumber).getRule();
    for (NodeOrbit currentNodeOrbit : nodeOrbitList) {
      fillLevel(currentNodeOrbit, levelEvent, nextStepOrbits,
                pIDElement.getNodeName(), currentRule, ISNOEFFECT);
    }

    if (leaves.containsKey(pIDElement.getAppNumber())) {
      entryLevelEvent = leaves.get(pIDElement.getAppNumber());
    }
    entryLevelEvent.add(levelEvent);
    // List<Integer> appNumbers =
    // entryLevelEvent.stream().mapToInt(LevelEventHR::getAppNumber)
    // .boxed().collect(Collectors.toList());
    // levelOrbit.setNextLevelEventsAppNumbers(appNumbers);
    leaves.put(appNumber, entryLevelEvent);

    return nextStepOrbits;
  }

  /**
   * Fill a LevelEvent with {@link NodeEvent}s. A building board entry is
   * computed in order to prepare the next level's construction.
   *
   * @param currentNodeOrbit a {@link NodeOrbit} from which to compute a
   *     building board entry
   * @param levelEvent {@link LevelEventHR} to fill
   * @param nextStepOrbits a list of node orbits to fill
   * @param nodeName String a jerboa rule node's name
   * @param rule a jerboa rule operation
   * @param iSNOEFFECT
   */
  private void fillLevel(NodeOrbit currentNodeOrbit, LevelEventHR levelEvent,
                         List<NodeOrbit> nextStepOrbits, String nodeName,
                         JerboaRuleOperation rule, boolean ISNOEFFECT) {

    currentNodeOrbit.BBBuildEntry(nodeName, rule, levelEvent, nextStepOrbits,
                                  ISNOEFFECT);
  }

  /**
   * Export this history record as a json file
   *
   * @param fileName name of the export file
   */
  public void export(String fileName) {
    try {
      JSONPrinter.exportHistoryRecord(leaves, fileName);
    } catch (IOException exception) {
      System.out.println("Could not write to file");
    }
  }

  /**
   * Export this history record as a json file
   *
   * @param directory Path relative to the project
   * @param filaName name of the export file
   */

  public void export(String directory, String fileName) {
    try {
      JSONPrinter.exportHistoryRecord(leaves, directory, fileName);
    } catch (IOException exception) {
      System.out.println("Could not write to file");
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (List<LevelEventHR> levels : leaves.values()) {
      sb.append(levels.toString()).append('\n');
    }
    return sb.toString();
  }
}

/* HistoryRecord data structure used to record the evolution of a construction
 */

// public class HistoryRecord {

// private LevelEventHR leaf;
// private List<LevelEventHR> leaves;

// public HistoryRecord() {
// this.leaves = new ArrayList<>();
// }

// public HistoryRecord(PersistentID pId, JerboaOrbit orbitType,
// ParametricSpecifications parametricSpecification) {

// // initialize leaves
// leaves = new ArrayList<LevelEventHR>();
// leaves.add(leaf);
// // initialize nodeOrbitList
// List<NodeOrbitHR> nodeOrbitList = new ArrayList<>();
// nodeOrbitList.add(new NodeOrbitHR(orbitType));

// List<PersistentIdElement> pIdElements = pId.getPIdElements();

// for (int index = pIdElements.size() - 1; index >= 0; index--) {
// nodeOrbitList =
// addLevel(nodeOrbitList, pIdElements.get(index), parametricSpecification);
// }
// }

// public LevelEventHR getLeaf(int index) {
// return leaves.get(index);
// }

// public List<LevelEventHR> getLeaves() {
// return leaves;
// }

// // public void setLevelEventLeaves(List<LevelEventHR> levelEventLeaves) {
// // this.levelEventLeaves = levelEventLeaves;
// // }

// /**
// * Create new empty LevelEvent and LevelOrbit containing nodeOrbitList,
// starting from pId's last
// * persistent Id element and given list of NodeOrbitHR
// *
// * @param nodeOrbitList current list of NodeOrbitHR
// *
// * @param pId current PersistentID
// *
// * @param parametricSpecification given parametric specification
// *
// * @return next step's nodeOrbitList
// */
// private List<NodeOrbitHR> addLevel(List<NodeOrbitHR> nodeOrbitList,
// PersistentIdElement pIdElement, ParametricSpecifications parameSpec) {

// List<NodeOrbitHR> nextStepOrbitHRs = new ArrayList<>();
// JerboaRuleOperation currentRule =
// parameSpec.getSpecEntry(pIdElement.getAppNumber()).getRule();

// // create empty level orbit
// LevelOrbitHR levelOrbit =
// new LevelOrbitHR(pIdElement.getNodeName(), nodeOrbitList,
// Arrays.asList(leaf));
// // create empty level event with last pIdElement appID
// LevelEventHR levelEvent = new LevelEventHR(pIdElement.getAppNumber(),
// levelOrbit); leaf = levelEvent;

// for (NodeOrbitHR currentNodeOrbit : nodeOrbitList) {
// fillLevel(currentNodeOrbit, levelEvent, nextStepOrbitHRs,
// pIdElement.getNodeName(), currentRule);
// }

// // On some condition leaf maybe be a distinct leaf from the last one in
// leaves
// // then add leaf to leaves
// leaves.set(leaves.size() - 1, leaf);

// return nextStepOrbitHRs;
// }

// /**
// * Fill current level event and current node orbit list with current node
// orbit trace for a
// * given rule
// *
// * @param currentNodeOrbit traced orbit
// *
// * @param levelEvent levelEvent to fill
// *
// * @param nodeOrbitList node orbit list to fill
// *
// * @param nodeName right node name in a given rule from which the orbit is
// traced
// *
// * @param rule given rule
// *
// */
// private void fillLevel(NodeOrbitHR currentNodeOrbit, LevelEventHR levelEvent,
// List<NodeOrbitHR> nextStepOrbitHRs, String nodeName, JerboaRuleOperation
// rule) {
// // Adding currentNodeOrbit because of ORIGIN nodes which are not taken from
// the
// // rule input
// currentNodeOrbit.BBBuildEntry(nodeName, rule, levelEvent, nextStepOrbitHRs);
// // System.out.println("nextStepOrbitHRs insidefillLevel " +
// nextStepOrbitHRs);
// // levelOrbit.mergeNodesFromList(nodeOrbitList);
// }

// /**
// * Draw a visual representation for a HistoryRecord
// */
// @Override
// public String toString() {
// StringBuilder sb = new StringBuilder();
// for (LevelEventHR leaf : leaves) {
// for (LevelEventHR level : leaf) {
// sb.append(level.getEventList()).append("\n");
// sb.append(level.getNextLevelOrbitHR().getOrbitList()).append("\n");
// }
// System.out.println("");
// }
// return sb.toString();
// }

// public void export(String fileName) {
// // List<LevelEventHR> levels = new ArrayList<>();
// // for (LevelEventHR level : this) {
// // levels.add(level);
// // }

// try

// {
// JSONPrinter.exportHistoryRecord(leaves, fileName);
// } catch (IOException exception) {
// System.out.println("Could not write to file");
// }

// }

// }

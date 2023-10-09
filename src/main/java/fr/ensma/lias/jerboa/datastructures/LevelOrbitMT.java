package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.List;
import up.jerboa.core.JerboaOrbit;

/**
 * LevelOrbitMT a structure that registers orbits of interests when reconstructing a model in
 * matching trees.
 */
public class LevelOrbitMT extends LevelOrbit {

  private int dartID;
  // private List<NodeOrbit> orbitList;
  private List<LevelEventMT> nextLevelEvents;

  /*
   * Constructor
   *
   * @param nodeName a name identifying a node from which to find an adequate dartID
   *
   * @param orbitList a list of node orbits
   */
  public LevelOrbitMT(int dartID, List<NodeOrbit> orbitList) {
    this.dartID = dartID;
    this.nextLevelEvents = new ArrayList<>();
    this.orbitList = orbitList;
  }

  public LevelOrbitMT(
      int dartID, List<NodeOrbit> orbitList, List<LevelEventMT> nextLevelEventList) {
    this.dartID = dartID;
    this.nextLevelEvents = nextLevelEventList;
    this.orbitList = orbitList;
  }

  public LevelOrbitMT() {}

  protected void setNextLevelEventMTList(List<LevelEventMT> nextLevelEventMTList) {
    this.nextLevelEvents = nextLevelEventMTList;
  }

  public void addNextLevelEventMT(LevelEventMT nextLevelEventMT) {
    this.nextLevelEvents.add(nextLevelEventMT);
  }

  public List<LevelEventMT> getNextLevelEventMTList() {
    return nextLevelEvents;
  }

  public void setDartID(int ID) {
    dartID = ID;
  }

  public int getDartID() {
    return dartID;
  }

  public List<NodeOrbit> getOrbitList() {
    return orbitList;
  }

  public void setOrbitList(List<NodeOrbit> orbitList) {
    for (NodeOrbit newOrbitNode : orbitList) {
      this.orbitList.add(
          new NodeOrbit(
              new JerboaOrbit(newOrbitNode.getOrbit()),
              new ArrayList<>(newOrbitNode.getAlphaPath())));
    }
  }

  @Override
  public String toString() {
    return "dartID " + dartID + " | " + orbitList;
  }
}

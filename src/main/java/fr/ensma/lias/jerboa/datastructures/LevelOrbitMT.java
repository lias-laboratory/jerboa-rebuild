package fr.ensma.lias.jerboa.datastructures;

import java.util.List;

/**
 * LevelOrbitMT a structure that registers orbits of interests when reconstructing a model in
 * matching trees.
 */
public class LevelOrbitMT extends LevelOrbit {

  private int dartID;
  // private List<NodeOrbit> orbitList;
  private List<LevelEventMT> nextLevelEvents;

  public LevelOrbitMT(int dartID, List<NodeOrbit> orbitList,
      List<LevelEventMT> nextLevelEventList) {
    this.dartID = dartID;
    this.orbitList = orbitList;
    this.nextLevelEvents = nextLevelEventList;
  }

  /*
   * Constructor
   *
   * @param nodeName a name identifying a node from which to find an adequate dartID
   *
   * @param orbitList a list of node orbits
   */
  public LevelOrbitMT(int dartID, List<NodeOrbit> orbitList) {
    this.dartID = dartID;
    this.orbitList = orbitList;
  }

  public LevelOrbitMT() {}

  protected void setNextLevelEventMTList(List<LevelEventMT> nexLevelEventMTList) {
    this.nextLevelEvents = nexLevelEventMTList;
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

  @Override
  public String toString() {
    return "dartID " + dartID + " | " + orbitList;
  }
}
